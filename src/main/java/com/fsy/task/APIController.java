package com.fsy.task;

import com.alibaba.fastjson.JSON;
import com.fsy.task.domain.*;
import com.fsy.task.dto.QuestionAnswerDto;
import com.fsy.task.selenium.SeleniumUtil;
import com.fsy.task.util.CollectionsUtil;
import com.fsy.task.util.HttpClientUtil;
import com.fsy.task.util.MD5Util;
import com.fsy.task.util.StringUtils;
import com.google.common.io.Files;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class APIController {

    private static SeleniumUtil seleniumUtil = new SeleniumUtil();

    private HashMap<String, String> cookieMap = new HashMap<String, String>();

    public static List<ImportUser> userList ;

    public static List<Map<String,String>> answersCache = new ArrayList<Map<String, String>>();

    private List<Test> tests = new ArrayList<Test>();

    private String lUserId;

    private String lschoolId;

    private String schoolToken ;

    private String schoolCode;

    private String username;

    private String password;

    private String nickName = "xiao xiao hai ";

    private CookieStore cookieStore = new BasicCookieStore();

    private HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

    private static HashMap<String,String> zhuGaunAnswerCache = new HashMap<>();
    private static HashMap<String,String> answersCacheIdAnswerMap = new HashMap<>();
    static {
        try {
            //导入主观题答案
            URL url = ClassLoader.getSystemResource("zgtda");
            File file = new File(url.getFile());
            if(file.exists() && file.isDirectory()) {
                Arrays.stream(file.list((File tempFile, String name) -> {
                    return Pattern.compile("(\\d{4})\\.txt").matcher(name).find();
                })).map((String str)->{
                    return  new File(file.getAbsolutePath() +"/"+str);
                })
            .forEach((File f)-> {
                            try {
                                //可以使用files方法files.lines()
                                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f) ,Charset.forName("GBK") ));
                                String content = br.lines().collect(Collectors.joining());
                                Matcher m = Pattern.compile("(\\d{4}).txt").matcher(f.getName());
                                m.find();
                                zhuGaunAnswerCache.put(m.group(1),content);
                                System.out.println(zhuGaunAnswerCache.get(m.group(1)));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                );


            }
        }catch (Exception e){
            e.printStackTrace();
        }




    }
    public APIController(String username , String password) throws IOException, ParserException, InterruptedException {
        //modified for 去selenium代之以cookiestore by fushiyong at 2018-12-18  17:30 start
                User user = seleniumUtil.getUser(username , password);

//        User user =  User.builder()
//                .username(username)
//                .password(password)
//                .userId(getUserId(userSchoolId))
//                .schoolId(getSchoolId(userSchoolId))
//                .schoolCode(username.split("-")[0])
//                .schoolToken(schoolToken)
//                .nickName(nickName)
//                .build();
        //modified for 去selenium代之以cookiestore by fushiyong at 2018-12-18  17:30 end


        this.nickName = user.getNickName();

        this.username = user.getUsername();

        this.password = user.getPassword();
        this.lUserId = user.getUserId();

        this.lschoolId = user.getSchoolId();

        this.schoolToken = user.getSchoolToken();

        this.schoolCode = user.getSchoolCode();

        validateParam();

        appendSchoolToken2CookieMap(user.getSchoolToken());
        doAnswer01();

        //登录获取cookie
        getSchoolLoginUrlAndCookie();
        doWatch01();

        //测评准备工作
        preTest();


    }

    /**
     *
     * 考试列表页面全部做掉
     */
    private void doExam() {

        String examListHtml = getExamListHtml();
        List<ExamDto> examDtos = parseExamListHtml(examListHtml);
        if(!CollectionsUtil.isEmpty(examDtos)){


            examDtos.parallelStream().filter((ExamDto examDto)->{
                return examDto.getScore().contains("--");
            }).map((ExamDto examDto)->{
                String [] urlSplited = examDto.getUrl().split("=");
                return urlSplited[urlSplited.length -1 ];
            }).forEach((String examId)->{

//                String checkResp = checkCourseRate(examId);
//                System.out.println("检查试卷是否可以考试返回:"+checkResp);
//
//                String examHtml = getExamHtml(examId);
//
//                //选择题解析
//                HashMap<String,String> xuanZeMap = getXuanZeTiMap(examHtml);
//                if(!checkResp.equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><CDO><CDOF N=\"cdoReturn\"><CDO><NF N=\"nCode\" V=\"-1\"/><STRF N=\"strText\" V=\"0\"/><STRF N=\"strInfo\" V=\"\"/></CDO></CDOF><CDOF N=\"cdoResponse\"><CDO></CDO></CDOF></CDO>")){
//                    //可以考试
//                    String answer = "";
//                    for (Map<String, String> map : answersCache) {
//                        //取部分标题
//                        if(xuanZeMap.containsKey(map.get("title"))){
//                            answer = map.get("answer");
//                            System.out.println(map.get("title")+"找到答案:"+answer);
//                        }
//                    }
//
//                }
//                //主观题解析
//                HashMap<String,String> zhuGuanMap = getZhuGuanQueMap(examHtml);
//                //主观题
//                postZhuGaunQue(examId ,planNo ,  zhuGuanMap);




            });
        }
    }

    /**
     *
     * @param examId
     * @param planId
     * @param examMap key为标题　　value为问题id
     */
    private void postZhuGaunQue(String examId,String planId ,HashMap<String,String> examMap ) {


        String postUrl = "http://"+this.schoolCode+".njcedu.com/student/prese/examin/handleTrans.cdo?strServiceName=StudentExminService&strTransName=commitPager";
//        HashMap<String,String> headerMap = new HashMap<>();
//        headerMap.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//        headerMap.put("Referer","http://"+schoolCode+".njcedu.com/student/prese/examin/pager.htm?lId=29270000074&nExaminType=0");
//        headerMap.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:64.0) Gecko/20100101 Firefox/64.0");

        HashMap<String,String> postParam = new HashMap<>();
        List<QuestionAnswerDto> zhuGuanAnswer = new ArrayList<>();

        for(Map.Entry<String,String> entry : examMap.entrySet()){
            if(zhuGaunAnswerCache.containsKey(entry.getValue())){
                zhuGuanAnswer.add(QuestionAnswerDto.builder()
                                    .answer(zhuGaunAnswerCache.get(entry.getValue()))
                                    .questionid(entry.getValue())
                                    .build()
                );
            }
        }
        String postJson = JSON.toJSONString(zhuGuanAnswer);
        String postValue = "<CDO>\n" +
                "    <STRF N=\"$strDestNodeName$\" V=\"TeachingBusiness\"/>\n" +
                "    <STRF N=\"strServiceName\" V=\"StudentExminService\"/>\n" +
                "    <STRF N=\"strTransName\" V=\"commitPager\"/>\n" +
                "    <LF N=\"lSchoolId\" V=\""+lschoolId+"\"/>\n" +
                "    <LF N=\"lUserId\" V=\""+lUserId+"\"/>\n" +
                "    <LF N=\"lExaminId\" V=\""+examId+"\"/>\n" +
                "    <LF N=\"lPlanId\" V=\""+planId+"\"/>\n" +
                "    <LF N=\"nExaminType\" V=\"0\"/>\n" +
                "    <STRF N=\"strAnswer1\" V=\""+postJson+"\"/>\n" +
                "    <NF N=\"nCommitType\" V=\"1\"/>\n" +
                "    <STRF N=\"strToken\" V=\"\"/>\n" +
                "</CDO>";
        postParam.put("$$CDORequest$$" , postValue);
        String resp = HttpClientUtil.postResByUrlAndCookie(postUrl , getCookie() , postParam , false);
        System.out.println(resp);
        if(isSuccess(resp)){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
    }

    private boolean isSuccess(String resp) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CDO><CDOF N=\"cdoReturn\"><CDO><NF N=\"nCode\" V=\"0\"/><STRF N=\"strText\" V=\"OK\"/><STRF N=\"strInfo\" V=\"OK\"/></CDO></CDOF><CDOF N=\"cdoResponse\"><CDO></CDO></CDOF></CDO>".equals(resp);
    }

    private HashMap<String, String> getZhuGuanQueMap(String examHtml) {
        //li class=\"ks_tm mb10\
        HashMap<String,String> result = new HashMap<>();
        Document document = Jsoup.parse(examHtml);
        Elements elements = document.select("li[class=ks_tm mb10]");
        for(int i=0;i<elements.size();i++){
            Element ele = elements.get(i);
            result.put(ele.text() , ele.attr("id"));

        }
        return result;
    }

    /**
     *
     * @param examHtml
     * @return key -> 问题标题　value -> 问题ID
     */
    private HashMap<String, String> getXuanZeTiMap(String examHtml) {
        //启用编程语言pattern match 模式匹配语言特性
        String regex = "title:\"(.*?)\",options:\\[.*?\\s+showQuestion\\(jsonQuestion,(\\d{4}),";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(examHtml);

        //key -> 问题标题　value -> 问题ID
        HashMap<String,String> questionMap = new HashMap<>();
        while(matcher.find()){
            questionMap.put(matcher.group(1) , matcher.group(2));
        }
        return questionMap;
    }

    private String getExamHtml(String examId) {
        String examUrl = "http://"+schoolCode+".njcedu.com/student/prese/examin/pager.htm?lId="+examId+"&nExaminType=0";
        String cookie = getCookie();
        HashMap<String,String> headerParam = new HashMap<String, String>();
        headerParam.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:64.0) Gecko/20100101 Firefox/64.0");
        String examHtml = HttpClientUtil.getResByUrlAndCookie(examUrl , headerParam , cookie , false );
        return examHtml;
    }

    /**
     *
     * @param examId 37270000020
     */
    private String checkCourseRate(String examId) {
        String checkCourseRateUrl = "http://"+schoolCode+".njcedu.com/student/prese/examin/handleTrans.cdo?strServiceName=StudentExminService&strTransName=chknCourseRate";
        String cookie = getCookie();
        Map<String,String> postMap  = new HashMap<>();
        String postValue =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<CDO>\n" +
                "  <STRF N=\"strServiceName\" V=\"StudentExminService\"/>\n" +
                "  <STRF N=\"strTransName\" V=\"chknCourseRate\"/>\n" +
                "  <LF N=\"lSchoolId\" V=\""+lschoolId+"\"/>\n" +
                "  <LF N=\"lUserId\" V=\""+lUserId+"\"/>\n" +
                "  <LF N=\"lPlanId\" V=\""+examId+"\"/>\n" +
                "  <NF N=\"nCourseRate\" V=\"90\"/>\n" +
                "</CDO>\n";

        postMap.put("$$CDORequest$$" , postValue);
        return HttpClientUtil.postResByUrlAndCookie(checkCourseRateUrl , cookie , postMap , false);

    }

    private List<ExamDto> parseExamListHtml(String examListHtml) {
        List<ExamDto> examDtos = new ArrayList<>();
        Document doc = Jsoup.parse(examListHtml);
        Elements eles = doc.select("td[style=padding-left:10px; color:#333;]");
        ExamDto examDto = new ExamDto();
        for(int i= 0 ; i<eles.size();i++){
            if(i%6 == 0){
                examDto.setName(eles.get(i).text());
            }else if(i%6 == 1){
                examDto.setValidTime(eles.get(i).text());
            }else if(i%6 == 2){
                examDto.setStatus(eles.get(i).text());
            }else if(i%6 == 3){
                examDto.setCanKaoStatus(eles.get(i).text());
            }else if(i % 6 == 4){
                examDto.setScore(eles.get(i).text());
            }else if( i % 6 == 5){
                examDto.setUrl(eles.get(i).select("a").attr("href"));
                examDtos.add(examDto);
                examDto = new ExamDto();
            }

        }
        return examDtos;
    }

    /**
     *
     * @return　获取考试列表返回的html
     */
    private String getExamListHtml() {
        String examListUrl = "http://"+this.schoolCode+".njcedu.com/student/prese/examin/list.htm";
        String cookie = getCookie();
        HashMap<String,String> headerParam = new HashMap<String, String>();
        headerParam.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:64.0) Gecko/20100101 Firefox/64.0");
        String examListHtml = HttpClientUtil.getResByUrlAndCookie(examListUrl , headerParam , cookie , false);
        return examListHtml;
    }

    private void preTest() {
        if(tests != null && tests.size()>0){
            for(Test test:tests){
                if(test.getId() == 7){
                    //DISC人格测验
                    String testIdPage = doTestId(test.getId()+"");
                    List<QuestionOption> questionOptions = getQuestionIds(testIdPage , true);
                    do7(lschoolId, lUserId, this.nickName.trim(), test.getId() +"", questionOptions);
                    continue;
                }
                String testIdPage = doTestId(test.getId()+"");
                List<QuestionOption> questionOptions = getQuestionIds(testIdPage , false);
                publishTestEvent(lschoolId, lUserId, this.nickName, test.getId() +"", questionOptions);
                System.out.println(this.nickName + "  测评" + test.getId() + " 通过");
            }
        }else{
            System.out.println(this.nickName + "没有测评" + " 跳过");
        }
    }

    private void do7(String lschoolId, String lUserId, String nickName, String s, List<QuestionOption> options) {
        String testId = "7";
        if(options == null || options.size()==0){
            System.out.println("获取该测评id选项失败:"+ testId  + "\n请联系管理员排除bug");
            throw new IllegalArgumentException("获取该测评id选项失败:"+ testId );
        }
        String url = "http://"+this.schoolCode+".njcedu.com/student/tc/careerPlaning/handleTrans.cdo?strServiceName=EvalutionService&strTransName=addEvaluationResult";
        String cookie = getCookie();
        HashMap postParam = new HashMap();
        StringBuffer postValue = new StringBuffer();
        String originalId = testId;
        postValue.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<CDO>\n" +
                "  <STRF N=\"strServiceName\" V=\"EvalutionService\"/>\n" +
                "  <STRF N=\"strTransName\" V=\"addEvaluationResult\"/>\n" +
                "  <LF N=\"lSchoolId\" V=\""+lschoolId+"\"/>\n" +
                "  <LF N=\"lUserId\" V=\""+lUserId+"\"/>\n" +
                "  <STRF N=\"strUserName\" V=\""+nickName+"\"/>\n" +
                "  <LF N=\"lEvaluationId\" V=\""+originalId+"\"/>\n" +
                "  <STRF N=\"strAnswer\" V=\"{answers:[\n");

        StringBuffer wrapValue = new StringBuffer();
        for(int i = 0 ; i <options.size() ; i++)
        {
            QuestionOption currentOption = options.get(i);
            String option = "A1,A2,B1,B2,C1,C2,D1,D2";
            String answer = "1,0,0,1,0,0,0,0";
            //{index:"1",lQuestionId:"2005",type:"0",options:"A,B,C,D,E",answer:"1,0,0,0,0",checkOptions:"A",checkAnswers:"1"}
            wrapValue.append("{index:\""+(i+1)+"\",lQuestionId:\""+currentOption.getQuestionId()+"\",type:\"2\",options:\""+option+"\",answer:\""+answer+"\",checkOptions:\"A1,B2\",checkAnswers:\"1,1\"}" + ",");
        }
        //" to &quot;
        wrapValue = new StringBuffer(wrapValue.toString().replaceAll("\"" , "&quot;"));
        postValue.append(wrapValue.toString());

        postValue.deleteCharAt(postValue.length() -1 );
        postValue.append("]}\"/>\n" +
                "  <STRF N=\"strToken\" V=\"\"/>\n" +
                "</CDO>\n");

        postParam.put("$$CDORequest$$" , postValue );
        HttpClientUtil.postResByUrlAndCookie(url , cookie , postParam , false  );
    }

    /**
     *
     * @param schoolId
     * @param userId
     * @param nickName
     * @param testId
     * @param options 选项的个数 影响答题 该题目的id
     */
    private void publishTestEvent(String schoolId , String userId , String nickName , String testId ,List<QuestionOption> options){

        if(options == null || options.size()==0){
            System.out.println("获取该测评id选项失败:"+ testId  + "\n请联系管理员排除bug");
            throw new IllegalArgumentException("获取该测评id选项失败:"+ testId );
        }
        String url = "http://"+this.schoolCode+".njcedu.com/student/tc/careerPlaning/handleTrans.cdo?strServiceName=EvalutionService&strTransName=addEvaluationResult";
        String cookie = getCookie();
        HashMap postParam = new HashMap();
        StringBuffer postValue = new StringBuffer();
        String originalId = testId;
        postValue.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<CDO>\n" +
                "  <STRF N=\"strServiceName\" V=\"EvalutionService\"/>\n" +
                "  <STRF N=\"strTransName\" V=\"addEvaluationResult\"/>\n" +
                "  <LF N=\"lSchoolId\" V=\""+schoolId+"\"/>\n" +
                "  <LF N=\"lUserId\" V=\""+userId+"\"/>\n" +
                "  <STRF N=\"strUserName\" V=\""+nickName+"\"/>\n" +
                "  <LF N=\"lEvaluationId\" V=\""+originalId+"\"/>\n" +
                "  <STRF N=\"strAnswer\" V=\"{answers:[\n");

        StringBuffer wrapValue = new StringBuffer();
        for(int i = 0 ; i <options.size() ; i++)
        {

            QuestionOption currentOption = options.get(i);
            String option = null;
            String answer = null;
            if(currentOption.getQuestionOptionCount().equals("2") ){
                option = "A,B";
                answer = "1,0";
            }else if(currentOption.getQuestionOptionCount().equals("3")){
                option = "A,B,C";
                answer = "1,0,0";
            }else if(currentOption.getQuestionOptionCount().equals("5")){
                option = "A,B,C,D,E";
                answer = "1,0,0,0,0";
            }else if(currentOption.getQuestionOptionCount().equals("6")){
                option = "A,B,C,D,E,F";
                answer = "1,0,0,0,0,0";
            }else if(currentOption.getQuestionOptionCount().equals("8")){
                option = "A,B,C,D,E,F,G,H";
                answer = "1,0,0,0,0,0,0,0";
            }else{
                option = "A,B,C,D,E";
                answer = "1,0,0,0,0";
                System.out.println("当前测评选项"+currentOption.getQuestionOptionCount()+"个 , 测评时，目前仅支持有2,5,6,8个项，采用默认配置5个选项 , 问题ID " + currentOption.getQuestionId());
            }

            //{index:"1",lQuestionId:"2005",type:"0",options:"A,B,C,D,E",answer:"1,0,0,0,0",checkOptions:"A",checkAnswers:"1"}
            wrapValue.append("{index:\""+(i+1)+"\",lQuestionId:\""+currentOption.getQuestionId()+"\",type:\"0\",options:\""+option+"\",answer:\""+answer+"\",checkOptions:\"A\",checkAnswers:\"1\"}" + ",");
        }
        //" to &quot;
        wrapValue = new StringBuffer(wrapValue.toString().replaceAll("\"" , "&quot;"));
        postValue.append(wrapValue.toString());

        postValue.deleteCharAt(postValue.length() -1 );
        postValue.append("]}\"/>\n" +
                "  <STRF N=\"strToken\" V=\"\"/>\n" +
                "</CDO>\n");

        postParam.put("$$CDORequest$$" , postValue );
        HttpClientUtil.postResByUrlAndCookie(url , cookie , postParam , false  );

    }

    private String doTestId(String testId) {
        //http://binhai.njcedu.com/student/tc/careerPlaning/evaluationtest.htm?id=46#begin
        String url = "http://"+this.schoolCode+".njcedu.com/student/tc/careerPlaning/evaluationtest.htm?id="+testId;
        String cookie = getCookie();
        HashMap<String,String> headerParam = new HashMap<String, String>();
        headerParam.put("Referer", "http://"+this.schoolCode+".njcedu.com/student/tc/careerPlaning/evaluationlist.htm");
        String respStr = HttpClientUtil.getResByUrlAndCookie(url , headerParam , cookie  , false);
        return respStr;
    }

    //是否是第七题 比较特殊
    private List<QuestionOption> getQuestionIds( String testIdPage , boolean isSeven) {
        List<QuestionOption> questionIds = new ArrayList<QuestionOption>();
        Parser parser = Parser.createParser(testIdPage, Charset.defaultCharset().toString());
        //缓冲层 parser解析一次之后，再次解析为空
        NodeList cacheNodeList = null;
        try {
            cacheNodeList = parser.parse(new NodeFilter() {
                public boolean accept(Node node) {
                    return true;
                }
            });
        } catch (ParserException e) {
            e.printStackTrace();
        }
        //startevaluation.htm TODO 完成的去重复
        NodeFilter questionIdFilter = new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof BulletList
                        && ((BulletList) node).getAttribute("class") != null
                        && ((BulletList) node).getAttribute("class").contains("nswer")
                        && ((BulletList) node).getAttribute("questionId") != null
                        )
                    return true;
                else return false;
            }
        };





        NodeList thatMatch = null ;
        if(!isSeven){
            thatMatch = cacheNodeList.extractAllNodesThatMatch(questionIdFilter);
        }else{
            NodeFilter questionId2Filter = new NodeFilter() {
                public boolean accept(Node node) {
                    if (node instanceof BulletList
                            && ((BulletList) node).getAttribute("class") != null
                            && ((BulletList) node).getAttribute("class").contains("faceSubject")
                            && ((BulletList) node).getAttribute("questionid") != null
                            )
                        return true;
                    else return false;
                }
            };
            thatMatch = cacheNodeList.extractAllNodesThatMatch(questionId2Filter);
        }
        if(thatMatch != null && thatMatch.size()>0) {
            for (int matchIndex = 0; matchIndex < thatMatch.size(); matchIndex++) {
                Node[] questionNode = thatMatch.toNodeArray();
                if (questionNode[matchIndex] instanceof BulletList) {
                    BulletList questionIdNode = (BulletList) questionNode[matchIndex];

                    int questionOptionCount = (int) Arrays.asList(questionIdNode.getChildren().toNodeArray()).stream()
                            .filter((Node node)->{
                        return node instanceof Bullet;
                            }).count();
                    String questionId = questionIdNode.getAttribute("questionId");

                    questionIds.add(
                            QuestionOption.builder()
                                    .questionId(questionId)
                                    .questionOptionCount(questionOptionCount + "")
                                    .build()
                    );
                }
            }
        }
        return questionIds;

    }

    private void validateParam() {
        if(this.lschoolId == null || this.lschoolId.equals("")){
            throw new IllegalArgumentException("学校ID不能为空!");
        }
        if(this.lUserId == null || this.lUserId.equals("")){
            throw new IllegalArgumentException("学生ID不能为空!");
        }
    }

    private void doWatch01() throws IOException, InterruptedException, ParserException {
        //需要登录
        System.out.println(this.username + "  开始看视频：");

        List<TeachPlan> plans = getTeachPlans(getCookie());
        String blankStyle = "      ";
        if(plans != null && plans.size() >0){
            for(TeachPlan plan : plans){
                System.out.println(blankStyle + plan.getTaskName());
                if(plan.getStatus().equals("进行中")){
                    ExercisesAndTests exercisesAndTests = getExercises("http://"+this.schoolCode+".njcedu.com/student/prese/teachplan/listdetail.htm?id="
                            + plan.getShowPlanNumber(),  this.getCookie() );
                    //过滤出已完成的
                    float hasCompleteExercise = 1.0f;
                    long startTimeMills = System.currentTimeMillis();
                    for(Exercise exercise : exercisesAndTests.getExercises()){
                        if(exercise.getStatus().contains("未完成")){
                            int leftNumberIndex = exercise.getStatus().indexOf("（");
                            int indexStart = exercise.getStatus().indexOf("/ ");
                            int hasCompleteCount = Integer.valueOf(exercise.getStatus().substring(leftNumberIndex + "（".length() ,indexStart -1 ));
                            int indexEnd = exercise.getStatus().indexOf("）");
                            String allNeedCount = exercise.getStatus().substring(indexStart + "/ ".length(), indexEnd  ).trim();
                            int need2Complete = Integer.valueOf(allNeedCount) - hasCompleteCount;
                            int needCount = -1;
                            if(need2Complete % 2 == 0){
                                needCount = need2Complete / 2 ;
                            }else{
                                needCount = need2Complete / 2 + 1 ;
                            }
                            for(int count = 0 ; count < needCount ; count++){
                                this.watchVideo(exercise.getNumber());
                                System.out.print(".");
                                //解决看视频太慢的问题
                                //        Thread.sleep(30000);
                                //支持秒刷的业务逻辑

                            }
                        }else{
                            System.out.println(exercise.getName() + " 已完成 ，自动跳过。" );
                        }

                    }

                    long endTimeMills = System.currentTimeMillis();

                    long periodTime = endTimeMills - startTimeMills;

                    long periodTimeSecond = periodTime / 1000 ;

                    if(periodTimeSecond < 60 ){
                        Thread.sleep((60 - periodTimeSecond) * 1000);
                    }

                    //支持秒刷的业务
                    for(Exercise exercise : exercisesAndTests.getExercises()){
                        if(exercise.getStatus().contains("未完成")){
                            // / )

                            int leftNumberIndex = exercise.getStatus().indexOf("（");

                            int indexStart = exercise.getStatus().indexOf("/ ");
                            int hasCompleteCount = Integer.valueOf(exercise.getStatus().substring(leftNumberIndex + "（".length() ,indexStart -1 ));

                            int indexEnd = exercise.getStatus().indexOf("）");
                            String allNeedCount = exercise.getStatus().substring(indexStart + "/ ".length(), indexEnd  ).trim();
                            int need2Complete = Integer.valueOf(allNeedCount) - hasCompleteCount;
                            int needCount = -1;
                            if(need2Complete % 2 == 0){
                                needCount = need2Complete / 2 ;
                            }else{
                                needCount = need2Complete / 2 + 1 ;
                            }
                            for(int count = 0 ; count < needCount ; count++){
                                //解决看视频太慢的问题 浪费了三十秒 很慢 要是多个答案 就非常慢了
                                //        Thread.sleep(30000);
                                //支持秒刷的业务逻辑
                                String url = "http://course.njcedu.com/Servlet/recordStudy.svl?lCourseId=" + exercise.getNumber() +
                                        "&lSchoolId=" + this.lschoolId + "&strStartTime=0";
                                HttpClientUtil.getResByUrlAndCookie(url, null , getCookieByMap(cookieMap), false);
                                System.out.print(".");
                            }
                        }
                        hasCompleteExercise++;
                        System.out.println(blankStyle+ blankStyle + exercise.getName()  + hasCompleteExercise / exercisesAndTests.getExercises().size() * 100 +"%" );
                    }

                }else{
                    System.out.println("看视频 "+ plan.getTaskName() + " 已完成 ，自动跳过。");
                }
            }
        }
    }

    private void doAnswer01() throws IOException, ParserException {
        //需要登录
        String blankStyle = "      ";
        System.out.println(this.username + "  开始做试题：");
        getSchoolLoginUrlAndCookie() ;
        List<TeachPlan> plans = getTeachPlans(getCookie());
        if(plans != null && plans.size() >0){

            for(TeachPlan plan : plans){
                System.out.println( blankStyle + plan.getTaskName());
                if(plan.getStatus().equals("进行中")){
                    ExercisesAndTests exercisesAndTests = getExercises("http://"+schoolCode+".njcedu.com/student/prese/teachplan/listdetail.htm?id="
                            + plan.getShowPlanNumber(),  this.getCookie() );

                    if(exercisesAndTests.getTests() != null && exercisesAndTests.getTests().size()>0){
                        tests.addAll(exercisesAndTests.getTests());
                    }
                    //过滤出已完成的
                    float hasCompleteExercise = 1.0f;
                    for(Exercise exercise : exercisesAndTests.getExercises()){
//                        log.info(exercise.getName());
                        if(!exercise.getRightPercent().equals("100.0%") && !exercise.getRightPercent().equals("-/-") ){
                            List<Question> questions = this.getQuestionByCourseId(exercise.getNumber() ,  this.getCookie());
                            this.doAnswer(this.getCookie() , exercise.getNumber() , questions);
                        }
                        hasCompleteExercise++;
                        System.out.println(blankStyle + blankStyle + exercise.getName() );
                        System.out.println( blankStyle + blankStyle + hasCompleteExercise / exercisesAndTests.getExercises().size() * 100 +"%");
                    }
                    //做考试
                    exercisesAndTests.getExamIds().parallelStream()
                            .filter(string-> string.contains("="))
                            .map(string-> string.split("=")[string.split("=").length - 1 ])
                            .forEach(examId->{
                                String checkResp = checkCourseRate(examId);
                                System.out.println("检查试卷是否可以考试返回:"+checkResp);

                                String secondCheckResp = checkCourseRate(examId);
                                System.out.println("检查试卷是否可以考试返回:"+secondCheckResp);

                                String examHtml = getExamHtml(examId);

                                //载入题库2 key -> id value -> answer
                                Map<String,String> tiku2 = new HashMap<>();
                                URL url = ClassLoader.getSystemResource("zgtda");
                                File file = new File(url.getFile());
                                if(file.exists() && file.isDirectory()){
                                    Arrays.stream(file.list((File tempFile, String name) -> {
                                        return Pattern.compile("[a-z]{2}\\.txt").matcher(name).find();
                                    })).map((String str)->{
                                        return  new File(file.getAbsolutePath() +"/"+str);
                                    })
                                            .forEach((File f)-> {
                                                        try {
                                                            //可以使用files方法files.lines()
                                                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f) ,Charset.forName("GBK") ));
                                                            String content = br.lines().collect(Collectors.joining(","));
                                                            for(String str : content.split(",")){
                                                                tiku2.put(str.split("=")[0],str.split("=")[1]);
                                                            }
                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                            );
                                }

                                //选择题解析
                                HashMap<String,String> xuanZeMap = getXuanZeTiMap(examHtml);
                                if(!checkResp.equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><CDO><CDOF N=\"cdoReturn\"><CDO><NF N=\"nCode\" V=\"-1\"/><STRF N=\"strText\" V=\"0\"/><STRF N=\"strInfo\" V=\"\"/></CDO></CDOF><CDOF N=\"cdoResponse\"><CDO></CDO></CDOF></CDO>")){
                                    //可以考试
                                    String questionAnswer = "";
                                    String questionId = "";
                                    for(Map<String,String> map : answersCache){
                                        answersCacheIdAnswerMap.put(map.get("id") , map.get("answer"));
                                    }

                                    for(Map.Entry<String,String> entry : xuanZeMap.entrySet()){
                                        if(answersCacheIdAnswerMap.containsKey(entry.getValue())){
                                            //有答案
                                            questionId = entry.getValue();
                                            questionAnswer = answersCacheIdAnswerMap.get(entry.getValue());
                                        }else{
                                            //无答案
                                            questionId = entry.getValue();
                                            questionAnswer = tiku2.get(entry.getValue());
                                        }

                                        //提交选择题
                                        String postXuanZeUrl = "http://"+schoolCode+".njcedu.com/student/prese/examin/handleTrans.cdo?strServiceName=StudentExminService&strTransName=doAnswer";
                                        HashMap<String,String> postMap = new HashMap<>();
                                        String postValue = "<CDO>\n" +
                                                "    <STRF N=\"$strDestNodeName$\" V=\"TeachingBusiness\"/>\n" +
                                                "    <STRF N=\"strServiceName\" V=\"StudentExminService\"/>\n" +
                                                "    <STRF N=\"strTransName\" V=\"doAnswer\"/>\n" +
                                                "    <LF N=\"lSchoolId\" V=\""+lschoolId+"\"/>\n" +
                                                "    <LF N=\"lUserId\" V=\""+lUserId+"\"/>\n" +
                                                "    <LF N=\"lExaminId\" V=\""+examId+"\"/>\n" +
                                                "    <LF N=\"lPlanId\" V=\""+plan.getShowPlanNumber()+"\"/>\n" +
                                                "    <LF N=\"nExaminType\" V=\"0\"/>\n" +
                                                "    <LF N=\"lQuestionId\" V=\""+questionId+"\"/>\n" +
                                                "    <STRF N=\"strQuestionAnswer\" V=\""+questionAnswer+"\"/>\n" +
                                                "</CDO>";
                                        postMap.put("$$CDORequest$$" ,postValue );
                                        String submitXuanZeTi = HttpClientUtil.postResByUrlAndCookie(postXuanZeUrl ,  getCookie() , postMap ,false);
                                        System.out.println("submitXuanZeTi"+submitXuanZeTi);
                                    }
                                    //主观题解析
                                    HashMap<String,String> zhuGuanMap = getZhuGuanQueMap(examHtml);
                                    //主观题
                                    postZhuGaunQue(examId ,plan.getShowPlanNumber() ,  zhuGuanMap);
                                }

                            });
                }else{
                    System.out.println("做试题 "+plan.getTaskName() + " 已完成 ，自动跳过。");
                }
            }
        }
    }

    /**
     * 全局登录 锦成
     *
     */
    public String getSchoolLoginUrlAndCookie() throws IOException {
        String loginUrl = "http://sso.njcedu.com/handleTrans.cdo?strServiceName=UserService&strTransName=SSOLogin";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("$$CDORequest$$", buildLoginParam(username , password));
        String respAndCookie = HttpClientUtil.postResByUrlAndCookie(loginUrl, null, params, true);
        String resp = respAndCookie.split("#")[0];
        String cookie = respAndCookie.split("#")[1];
        int start = resp.lastIndexOf("<STR>");
        int end = resp.lastIndexOf("</STR>");
        resp = resp.substring(start + "<STR>".length(), end);
        setUserId(respAndCookie);
        rebuildCookieMap(cookie);
        return resp + "," + cookie;
    }

    private String buildLoginParam(String username , String password ) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<CDO>\n" +
                "  <STRF N=\"strServiceName\" V=\"UserService\"/>\n" +
                "  <STRF N=\"strLoginId\" V=\"" + username + "\"/>\n" +
                "  <STRF N=\"strTransName\" V=\"SSOLogin\"/>\n" +
                "  <STRF N=\"strPassword\" V=\"" + MD5Util.MD5Encode(password, Charset.defaultCharset().toString()) + "\"/>\n" +
                "  <STRF N=\"strVerifyCode\" V=\"\"/>\n" +
                "  <STRF N=\"bIsCookieLogin\" V=\"change\"/>\n" +
                "  <STRF N=\"Sessioncheck\" V=\"sessionErr\"/>\n" +
                "  <LF N=\"lSchoolId\" V=\""+this.lschoolId+"\"/>\n" +
                "  <LF N=\"lEduId\" V=\"0\"/>\n" +
                "</CDO>\n";
    }

    /**
     *    <LF N="lUserId" V="26970010299"/>
          <LF N="lSchoolId" V="539"/>
     * @param resp
     */
    private void setUserId(String resp) {

        //setuserid
        int lUserIdKeyIndex = resp.indexOf("lUserId");

        if(lUserIdKeyIndex != -1){
            int lUserIdKeyEndIndex = resp.indexOf( "\"" , lUserIdKeyIndex + 13 + "lUserId".length());

            String tempUserId = resp.substring(lUserIdKeyIndex , lUserIdKeyEndIndex)
                    .replaceAll("\"" , "");

            this.lUserId = tempUserId.split("=" )[1];
        }
    }

    public List<TeachPlan> getTeachPlans(String cookie) throws IOException, ParserException {
        String teachPlanUrl = "http://"+this.schoolCode+".njcedu.com/student/prese/teachplan/index.htm";
        String respStr = HttpClientUtil.getResByUrlAndCookie(teachPlanUrl,  null , cookie, false);
        Parser parser = Parser.createParser(respStr, Charset.defaultCharset().toString());
        //缓冲层 parser解析一次之后，再次解析为空
        NodeList cacheNodeList = parser.parse(new NodeFilter() {
            public boolean accept(Node node) {
                return true;
            }
        });

        //class xxrw_l_fk
        NodeFilter teachPlanFilter = createTeachPlanFilter();

        NodeList thatMatch = cacheNodeList.extractAllNodesThatMatch(teachPlanFilter, false);


        List<TeachPlan> plans = new ArrayList<TeachPlan>();
        for (Node match : thatMatch.toNodeArray()) {
            if (match instanceof BulletList) {
                TeachPlan plan = TeachPlan.builder().build();
                NodeList childs = match.getChildren();
                int count = 0;
                for (Node filterBullet : childs.toNodeArray()) {
                    if (filterBullet instanceof Bullet) {
                        count++;
                        if (count == 1) {
                            int tempCount = 0;
                            int tempCountI = 0;
                            for (Node spanOrLink : filterBullet.getChildren().toNodeArray()) {

                                if (spanOrLink instanceof Span) {
                                    //taskname
                                    if (tempCount == 0) {
                                        plan.setTaskName(((Span) spanOrLink).getAttribute("title"));
                                    }
                                    tempCount++;
                                } else if (spanOrLink instanceof LinkTag) {
                                    //status
                                    if (tempCountI == 0)
                                        plan.setStatus(((LinkTag) spanOrLink).getLinkText());
                                    tempCountI++;
                                }
                            }
                            count++;
                        } else if (count == 3) {
                            int tempI = 0;
                            int tempJ = 0;
                            if (filterBullet instanceof Bullet) {
                                for (Node spanOrLink : filterBullet.getChildren().toNodeArray()) {

                                    if (spanOrLink instanceof Span) {
                                        //validTime
                                        if (tempI == 0) {
                                            plan.setValidTime(((Span) spanOrLink).getStringText());
                                        }
                                        tempI++;
                                    } else if (spanOrLink instanceof LinkTag) {
                                        //showPlanNumber
                                        if (tempJ == 0) {
                                            String showPlanNumber = ((LinkTag) spanOrLink).getLink();
                                            showPlanNumber = showPlanNumber.replaceAll("showPlan\\(", "")
                                                    .replaceAll("\\)", "");
                                            plan.setShowPlanNumber(showPlanNumber);
                                        }
                                        tempJ++;
                                    }
                                }
                            }
                        } else ;

                    }

                }
                plans.add(plan);
            }
        }

        return plans;
    }

    private NodeFilter createTeachPlanFilter() {
        return new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof BulletList
                        && ((BulletList) node).getAttribute("class") != null
                        && ((BulletList) node).getAttribute("class").equals("xxrw_l_fk"))
                    return true;
                else return false;
            }
        };
    }

    /**
     * @return
     */
    public ExercisesAndTests getExercises(String url, String cookie) throws IOException, ParserException {
        ExercisesAndTests exercisesAndTests = new ExercisesAndTests();

        String respStr = HttpClientUtil.getResByUrlAndCookie(url, null ,  cookie, false);


        Parser parser = Parser.createParser(respStr, Charset.defaultCharset().toString());
        //id courseTable
        NodeFilter tableIdFilter = createTableIdFilter("courseTable");
        //map filter 过滤出课程列表
        NodeFilter heightEqualsFilter = createHeightEqualsFilter("50");
        NodeFilter tableColumnFilter = createTableColumnFilter();
        NodeList exerciseList = parser.parse(tableIdFilter);

        //应用过滤链 sai选出所需数据
        NodeList exerciseColumns = exerciseList
                .extractAllNodesThatMatch(heightEqualsFilter, true)
                .extractAllNodesThatMatch(tableColumnFilter, true);
        List<Exercise> exercises = populateNodeList2Exercises(exerciseColumns);
        exercisesAndTests.setExercises(exercises);

        //获取指定课程下的测评
        List<Test> planTests = getPlanTest(respStr);
        exercisesAndTests.setTests(planTests);



        Document doc = Jsoup.parse(respStr);
        Element ele = doc.select("#ExamTable").first();
        Elements tdEles = ele.select("a");
        List<String> ids = tdEles.parallelStream()
                .map(tempEle ->{
                    return tempEle.attr("href");
                })
                .collect(Collectors.toList());

        exercisesAndTests.setExamIds(ids);
        return exercisesAndTests;
    }

    private List<Test> getPlanTest(String respStr) {
        List<Test> tests = new ArrayList<Test>();
        //
        final String idFilterStr = "/student/tc/careerPlaning/careerEvalutionBak.htm?id=";
        Parser parser = Parser.createParser(respStr, Charset.defaultCharset().toString());

        NodeFilter testIdFitler = new NodeFilter() {
            @Override
            public boolean accept(Node node) {
                if(node instanceof LinkTag
                        && ((LinkTag) node).getAttribute("onclick") != null
                        &&  ((LinkTag) node).getAttribute("onclick").contains(idFilterStr)){
                    return true;
                }else
                return false;
            }
        };

        try {
            NodeList testList = parser.parse(testIdFitler);

            if(testList != null && testList.size()>0){
                for(Node node : testList.toNodeArray()){
                    if(node instanceof LinkTag){
                        String wholeUrl = ((LinkTag) node).getAttribute("onclick");

                        String id = "window.open('/student/tc/careerPlaning/careerEvalutionBak.htm?id=";
                        int idStart = wholeUrl.indexOf(id);
                        int idEnd = wholeUrl.indexOf("'" , idStart + id.length());
                        int originTestId = Integer.valueOf(wholeUrl.substring(idStart + id.length() , idEnd));
                        tests.add(Test.builder()
                        .id(originTestId).build());
                    }
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return tests;

    }

    private List<Exercise> populateNodeList2Exercises(NodeList exerciseColumns) {
        List<Exercise> exercises = new ArrayList<Exercise>();
        Exercise exercise = null;
        int count = 0;
        for (Node node : exerciseColumns.toNodeArray()) {
            if (node instanceof TableColumn) {
                TableColumn columned = (TableColumn) node;
                if (count % 4 == 0) {
                    exercise = new Exercise();
                    String name = columned.getStringText();
                    exercise.setName(name);

                } else if (count % 4 == 1) {
                    String status = columned.getStringText();
                    exercise.setStatus(status);
                } else if (count % 4 == 2) {
                    String rightPercent = columned.getStringText();
                    // 过滤\r\n\t 特殊字符
                    rightPercent = rightPercent.replaceAll("\t", "")
                            .replaceAll("\r", "")
                            .replaceAll("\n", "");
                    exercise.setRightPercent(rightPercent);
                } else if (count % 4 == 3) {
                    // 解析出code

                    String number = columned.getStringText();
                    // 过滤\r\n\t 特殊字符 \backspace
                    number = number.replaceAll("\t", "")
                            .replaceAll("\r", "")
                            .replaceAll("\n", "")
                            .replaceAll(" ", "");
                    number = number.substring(number.indexOf("(") + 1, number.indexOf(")"));
                    exercise.setNumber(number);
                    exercises.add(exercise);
                } else {
                    return null;
                }
                count++;
            }

        }
        return exercises;
    }

    private NodeFilter createTableColumnFilter() {
        return new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof TableColumn
                        )
                    return true;
                else return false;
            }
        };
    }

    private NodeFilter createHeightEqualsFilter(String s) {
        return new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof TableRow
                        && ((TableRow) node).getAttribute("height") != null
                        && ((TableRow) node).getAttribute("height").equals("50"))
                    return true;
                else return false;
            }
        };

    }

    private NodeFilter createTableIdFilter(final String id) {

        NodeFilter filter = new NodeFilter() {
            public boolean accept(Node node) {
                if (node != null
                        && node instanceof TableTag
                        && ((TableTag) node).getAttribute("id") != null
                        && ((TableTag) node).getAttribute("id").equals(id)) {
                    return true;
                } else
                    return false;
            }
        };
        return filter;
    }


    private NodeFilter createExamTableFilter() {
        return new NodeFilter() {
            public boolean accept(Node node) {
                if (node != null
                        && node instanceof TableTag
                        && ((TableTag) node).getAttribute("id") != null
                        && ((TableTag) node).getAttribute("id").equals("ExamTable")) {
                    return true;
                } else
                    return false;
            }
        };
    }

    /**
     * 根据课程Id查找到所有问题的标题
     *
     * @param courseId 1322
     * @return
     * @throws IOException
     */
    public List<Question> getQuestionByCourseId(String courseId, String cookie) throws IOException, ParserException {
        String url = "http://course.njcedu.com/questionbefore.htm?courseId=" + courseId;
        String resp = HttpClientUtil.getResByUrlAndCookie(url,  null , cookie, false);
        NodeFilter titleFilter = new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof ParagraphTag
                        && ((ParagraphTag) node).getAttribute("class") != null
                        && ((ParagraphTag) node).getAttribute("class").equals("kcxt_wt mb10"))
                    return true;
                else return false;
            }
        };

        Parser parser = Parser.createParser(resp, Charset.defaultCharset().toString());

        //缓冲层 parser解析一次之后，再次解析为空
        NodeList cacheNodeList = parser.parse(new NodeFilter() {
            public boolean accept(Node node) {
                return true;
            }
        });

        NodeFilter questionOptionFilter = new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof ParagraphTag
                        && ((ParagraphTag) node).getAttribute("class") != null
                        && ((ParagraphTag) node).getAttribute("class").equals("ml10 mb5"))
                    return true;
                else return false;
            }
        };
        NodeList thatMatch = cacheNodeList.extractAllNodesThatMatch(titleFilter);

        List<Question> questions = new ArrayList<Question>();
        int index = 0;
        String userId = "";
        String schoolId = "";
        for (Node match : thatMatch.toNodeArray()) {
            if (match instanceof ParagraphTag) {
                index++;
                NodeList tableIndexNode = cacheNodeList.extractAllNodesThatMatch(createTableFilter(index));
                String questionId = ((TableTag) tableIndexNode.toNodeArray()[0]).getAttribute("index");


                if (index == 1) {
                    NodeList userIdNode = cacheNodeList.extractAllNodesThatMatch(createUserIdFilter());
                    userId = ((InputTag) userIdNode.toNodeArray()[0]).getAttribute("value");

                    NodeList schoolIdNode = cacheNodeList.extractAllNodesThatMatch(createlSchoolIdFilter());
                    schoolId = ((InputTag) schoolIdNode.toNodeArray()[0]).getAttribute("value");
                }


                questions.add(
                        Question.builder()
                                .id(questionId)
                                .userId(userId)
                                .schoolId(schoolId)
                                .title(((ParagraphTag) match).getStringText()).build());
            }
        }


        return questions;
    }

    /**
     * userId 25800009324
     * lSchoolId  192
     * lCoursewareId 1322
     * nExerciseAfterCount 4
     * <p>
     * lQuestionId 2413
     * strAnswer D 正确答案
     * strStudentAnswer D 学生答案
     *
     * @param cookie
     * @param courseId
     * @param questions 题目列表
     * @throws IOException
     */
    public void doAnswer(String cookie, String courseId, List<Question> questions) throws IOException {
        String url = "http://course.njcedu.com/handleTrans.cdo?strServiceName=StudentCourseExerciseService&strTransName=addExerciseAfterAnswer";
        HashMap<String, String> params = new HashMap<String, String>();
        String prefix = "<CDO>\n" +
                "  <STRF N=\"strServiceName\" V=\"StudentCourseExerciseService\"/>\n" +
                "  <STRF N=\"strTransName\" V=\"addExerciseAfterAnswer\"/>\n" +
                "  <LF N=\"lUserId\" V=\"%s\"/>\n" +
                "  <LF N=\"lSchoolId\" V=\"%s\"/>\n" +
                "  <LF N=\"lCoursewareId\" V=\"%s\"/>\n" +
                "  <NF N=\"bInOrAfter\" V=\"1\"/>\n" +
                "  <NF N=\"nExerciseAfterCount\" V=\"%s\"/>\n" +
                "  <CDOAF N=\"answerArrayList\">\n";
        StringBuffer sb = new StringBuffer(prefix);
        int questionSize = questions.size();
        String prefixFormated = String.format(sb.toString(), lUserId, this.lschoolId, courseId, questionSize);

        sb = new StringBuffer();
        sb.append(prefixFormated);
        for (int i = 0; i < questionSize; i++) {
            Question currentQuestion = questions.get(i);

            List<Map<String, String>> questionRepository = answersCache;

            String answer = "";
            for (Map<String, String> map : questionRepository) {
                //取部分标题
                if (map.get("title").contains(currentQuestion.getTitle())) {
                    answer = map.get("answer");
                }
            }

            sb.append("<CDO>");
            sb.append("      <LF N=\"lQuestionId\" V=\"" + currentQuestion.getId() + "\"/>\n");
            sb.append("      <STRF N=\"strAnswer\" V=\"" + answer + "\"/>");
            sb.append("      <STRF N=\"strStudentAnswer\" V=\"" + answer + "\"/>");
            sb.append("      <NF N=\"bTrue\" V=\"0\"/>");
            sb.append("</CDO>");
        }
        sb.append("</CDOAF>" + "</CDO>");
        params.put("$$CDORequest$$", sb.toString());

        String resp = HttpClientUtil.postResByUrlAndCookie(url, cookie, params, false);
    }

    private NodeFilter createTableFilter(final int index) {
        return new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof TableTag
                        && ((TableTag) node).getAttribute("id") != null
                        && ((TableTag) node).getAttribute("id").equals("table" + index))
                    return true;
                else return false;
            }
        };
    }

    private NodeFilter createUserIdFilter() {
        return new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof InputTag
                        && ((InputTag) node).getAttribute("id") != null
                        && ((InputTag) node).getAttribute("id").equals("lUserId"))
                    return true;
                else return false;
            }
        };
    }

    private NodeFilter createlSchoolIdFilter() {
        return new NodeFilter() {
            public boolean accept(Node node) {
                if (node instanceof InputTag
                        && ((InputTag) node).getAttribute("id") != null
                        && ((InputTag) node).getAttribute("id").equals("lSchoolId"))
                    return true;
                else return false;
            }
        };
    }

    /**
     * 后台看视频 进度 +2 系统需要30s的延迟接口请求的时间 30s请求两次 返回超时
     *
     * @param courseId http://course.njcedu.com/Servlet/recordStudy.svl?lCourseId=1286&lSchoolId=539&strStartTime=0

     **/
    public void watchVideo(String courseId) throws IOException, InterruptedException {
        //数据准备
        getCourseWareCookie(courseId, null, null);

        //解决看视频太慢的问题
//        Thread.sleep(30000);
        //支持秒刷的业务逻辑 将代码迁移出去

//        String url = "http://course.njcedu.com/Servlet/recordStudy.svl?lCourseId=" + courseId +
//                "&lSchoolId=" + this.schoolCodeMap.get(this.loginDomain) + "&strStartTime=0";
//        HttpClientUtil.getResByUrlAndCookie(url, null , getCookieByMap(cookieMap), false);

        return;

    }

    public String getCookieByMap(HashMap<String, String> cookieMap) {
        StringBuffer cookieStr = new StringBuffer();
        if (cookieMap != null && cookieMap.size() != 0) {
            for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                cookieStr.append(key + "=" + value + ";");
            }
        }
        return cookieStr.toString();
    }

    public void appendSchoolToken2CookieMap(String schoolToken){
            cookieMap.put(CookieConstant.SCHOOL_TOKEN , schoolToken);
    }

    public String getCookie(){
        return this.getCookieByMap(this.cookieMap);
    }




    /**
     * @param userId 44670010423
     * @param token  64ffbd146b19314b692606fc98655c34076172d9e4d6e2d7105477802849fd4ea38264275853eff9c379cb11f42c7137a36b6ac8a88d7bf97f383dd9386e358a6912c8ad313c39ff
     * @return
     */
    public void getCourseWareCookie(String courseId, String userId, String token) throws IOException {
        String coursewareCookie = getCookieByMap(cookieMap);

        String url = "http://course.njcedu.com/newcourse/coursecc.htm?courseId=" + courseId;

        String respAndCookie = HttpClientUtil.getResByUrlAndCookie(url, null,  coursewareCookie, true);

        rebuildCookieMap(respAndCookie.split("#")[1]);

        return;
    }


    private void rebuildCookieMap(String cookie) {
        //remove Path=/ HttpOnly Domain=njcedu.com
        cookie = cookie.replaceAll("Path=/", "")
                .replaceAll("HttpOnly", "")
                .replaceAll("Domain=njcedu.com", "")
                .replaceAll(";;;", ";;")
                .replaceAll(";;", ";")
                .replaceAll(";", "")
                .replaceAll("  ", ";");
        String[] nameValuePair = cookie.split(";");
        if (nameValuePair != null && nameValuePair.length > 0) {
            for (String nameValue : nameValuePair) {
                if (nameValue != null && nameValue.length() > 0) {
                    //luserid=44670010423
                    String key = nameValue.split("=")[0].trim();
                    String value = nameValue.split("=")[1];
//                    if(cookieMap.containsKey(key)){
//                        continue;
//                    }else
                    cookieMap.put(key, value);
                }

            }
        }
        //remove cpwd key value
        if(cookieMap.containsKey("cpwd")){
            cookieMap.remove("cpwd");
        }
    }



}

class CookieConstant {
    public static String COOKIE_USERID = "luserid";
    public static String COOKIE_TOKEN = "token";
    public static String COOKIE = "Cookie";
    public static String SCHOOL_TOKEN = "schoolToken";

}






