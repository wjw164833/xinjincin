package com.fsy.task;

import com.fsy.task.domain.*;
import com.fsy.task.selenium.SeleniumUtil;
import com.fsy.task.util.HttpClientUtil;
import com.fsy.task.util.JacksonUtil;
import com.fsy.task.util.MD5Util;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

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

    public APIController(String username , String password) throws IOException, ParserException, InterruptedException {
        User user = seleniumUtil.getUser(username , password);

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
        doWatch01();

        //测评准备工作
        preTest();
    }

    private void preTest() {
        if(tests != null && tests.size()>0){
            for(Test test:tests){
                String testIdPage = doTestId(test.getId()+"");
                List<QuestionOption> questionOptions = getQuestionIds(testIdPage);
                publishTestEvent(lschoolId, lUserId, this.nickName, test.getId() +"", questionOptions);
                System.out.println(this.nickName + "  测评" + test.getId() + " 通过");
            }
        }else{
            System.out.println(this.nickName + "没有测评" + " 跳过");
        }
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

    private List<QuestionOption> getQuestionIds( String testIdPage) {
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



        NodeList thatMatch = cacheNodeList.extractAllNodesThatMatch(questionIdFilter);
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
        getSchoolLoginUrlAndCookie();
        List<TeachPlan> plans = getTeachPlans(getCookie());
        String blankStyle = "      ";
        if(plans != null && plans.size() >0){
            for(TeachPlan plan : plans){
                System.out.println(blankStyle + plan.getTaskName());
                if(plan.getStatus().equals("进行中")){
                    ExercisesAndTests exercisesAndTests = this.getExercises("http://"+this.schoolCode+".njcedu.com/student/prese/teachplan/listdetail.htm?id="
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
                    ExercisesAndTests exercisesAndTests = this.getExercises("http://"+this.schoolCode+".njcedu.com/student/prese/teachplan/listdetail.htm?id="
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
        if(!username.contains("-")){
            System.out.println("username must be contains - " );
            return null;
        }
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

    /**
     * 获取学习任务
     *
     * @param planId 9670000047
     * @return
     * @throws IOException
     */
    public int getListens(String planId, String cookie) throws IOException {
        try {
            String url = "http://zync.njcedu.com/student/prese/teachplan/listdetail.htm?id=" + planId;
            String respStr = HttpClientUtil.getResByUrlAndCookie(url, null ,  cookie, false);
            //class jl_table
            Parser parser = Parser.createParser(respStr, Charset.defaultCharset().toString());
            NodeFilter examTableFilter = createExamTableFilter();
            NodeList listenList = parser.parse(examTableFilter);
            int count = 0;
            Node baseNode = listenList.toNodeArray()[0];
            String taskName = baseNode.getChildren().toNodeArray()[3].getChildren().toNodeArray()[1].getChildren().toNodeArray()[0].getText();
            for (Node node : listenList.toNodeArray()) {


            }
            System.out.println(respStr);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return 0;
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






