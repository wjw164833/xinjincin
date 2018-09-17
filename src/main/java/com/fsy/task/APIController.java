package com.fsy.task;

import com.fsy.task.domain.Exercise;
import com.fsy.task.domain.ImportUser;
import com.fsy.task.domain.Question;
import com.fsy.task.domain.TeachPlan;
import com.fsy.task.domain.enums.AnswerOption;
import com.fsy.task.selenium.SeleniumUtil;
import com.fsy.task.util.HttpClientUtil;
import com.fsy.task.util.JacksonUtil;
import com.fsy.task.util.MD5Util;
import com.fsy.task.util.UserImportUtil;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APIController {

    private static SeleniumUtil util = new SeleniumUtil();
    private Logger log = Logger.getLogger(APIController.class.getName());

    private HashMap<String, String> cookieMap = new HashMap<String, String>();

    //学校编码 - 学校id 的字典表
    public static HashMap<String, String > schoolCodeMap = new HashMap<String, String>();

    public static List<ImportUser> userList ;


    private String lUserId;


    //TODO 支持可配置
    public static void initializeSchoolCodeMap() {
        //延安
        schoolCodeMap.put("yau" , "585" );
        //qqhru 齐齐哈尔
        schoolCodeMap.put("qqhru" , "539" );
        //贵阳学院 203
        schoolCodeMap.put("gyu" , "203");


    }

    public APIController(String schoolToken){
        setSchoolToken(schoolToken);
    }
    public APIController(){
        initializeSchoolCodeMap();
    }
    public APIController(String schoolToken ,String username , String password, AnswerOption option) throws IOException, ParserException, InterruptedException {
        this();
        setSchoolToken(util.getSchoolToken(username ,password));
        if(option.equals(AnswerOption.ANSWER)){
            doAnswer01(username , password);
        }else if(option.equals(AnswerOption.WATCH)){
           doWatch01(username , password);
        }else if(option.equals(AnswerOption.WATCH_AND_ANSWER)){
            doAnswer01(username , password);
            doWatch01(username , password);
        }else ;
    }

    private void doWatch01(String username, String password) throws IOException, InterruptedException, ParserException {
        //需要登录
        log.info(username + "  开始看视频：");

        String respAndCookie = getSchoolLoginUrlAndCookie(username , password);

        String loginUrl = respAndCookie.split(",")[0];
//        getSchoolCookie(loginUrl , getCookie());
        List<TeachPlan> plans = getTeachPlans(getCookie());
        String blankStyle = "      ";
        if(plans != null && plans.size() >0){
            for(TeachPlan plan : plans){
                log.info(blankStyle + plan.getTaskName());
                if(plan.getStatus().equals("进行中")){
                    List<Exercise> exercises = this.getExercises("http://"+this.getLoginDomain()+".njcedu.com/student/prese/teachplan/listdetail.htm?id="
                            + plan.getShowPlanNumber(),  this.getCookie() );
                    //过滤出已完成的
                    float hasCompleteExercise = 1.0f;
                    long startTimeMills = System.currentTimeMillis();
                    for(Exercise exercise : exercises){
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
                                this.watchVideo(exercise.getNumber());
                                System.out.print(".");
                                //解决看视频太慢的问题
                                //        Thread.sleep(30000);
                                //支持秒刷的业务逻辑

                            }
                        }else{
                            log.info(exercise.getName() + " 已完成 ，自动跳过。" );
                        }

                    }

                    long endTimeMills = System.currentTimeMillis();

                    long periodTime = endTimeMills - startTimeMills;

                    long periodTimeSecond = periodTime / 1000 ;

                    if(periodTimeSecond < 60 ){
                        Thread.sleep((60 - periodTimeSecond) * 1000);
                    }

                    //支持秒刷的业务
                    for(Exercise exercise : exercises){
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
                                        "&lSchoolId=" + this.schoolCodeMap.get(this.loginDomain) + "&strStartTime=0";
                                HttpClientUtil.getResByUrlAndCookie(url, null , getCookieByMap(cookieMap), false);
                                System.out.print(".");
                            }
                        }
                        hasCompleteExercise++;
                        log.info(blankStyle+ blankStyle + exercise.getName()  + hasCompleteExercise / exercises.size() * 100 +"%" );
                    }

                }else{
                    log.info("看视频 "+ plan.getTaskName() + " 已完成 ，自动跳过。");
                }
            }
        }
    }

    private void doAnswer01(String username , String password) throws IOException, ParserException {
        //需要登录
        String blankStyle = "      ";
        log.info(username + "  开始做试题：");
        getSchoolLoginUrlAndCookie(username , password) ;
        List<TeachPlan> plans = getTeachPlans(getCookie());
        if(plans != null && plans.size() >0){

            for(TeachPlan plan : plans){
                log.info( blankStyle + plan.getTaskName());
                if(plan.getStatus().equals("进行中")){
                    List<Exercise> exercises = this.getExercises("http://"+this.getLoginDomain()+".njcedu.com/student/prese/teachplan/listdetail.htm?id="
                            + plan.getShowPlanNumber(),  this.getCookie() );
                    //过滤出已完成的
                    float hasCompleteExercise = 1.0f;
                    for(Exercise exercise : exercises){
//                        log.info(exercise.getName());
                        if(!exercise.getRightPercent().equals("100.0%") && !exercise.getRightPercent().equals("-/-") ){
                            List<Question> questions = this.getQuestionByCourseId(exercise.getNumber() ,  this.getCookie());
                            this.doAnswer(this.getCookie() , exercise.getNumber() , questions);
                        }
                        hasCompleteExercise++;
                        log.info(blankStyle + blankStyle + exercise.getName() );
                        log.info( blankStyle + blankStyle + hasCompleteExercise / exercises.size() * 100 +"%");
                    }
                }else{
                    log.info("做试题 "+plan.getTaskName() + " 已完成 ，自动跳过。");
                }
            }
        }
    }

    public String getLoginDomain() {
        return loginDomain;
    }

    public void setLoginDomain(String loginDomain) {
        this.loginDomain = loginDomain;
    }

    private String loginDomain = null ;
    /**
     * 全局登录 锦成
     *
     * @param username
     * @param password
     * @throws IOException
     */
    public String getSchoolLoginUrlAndCookie(String username, String password) throws IOException {
        if(!username.contains("-")){
            log.log(Level.WARNING , "username must be contains - " );
            return null;
        }
        this.loginDomain = username.split("-")[0];
        String loginUrl = "http://sso.njcedu.com/handleTrans.cdo?strServiceName=UserService&strTransName=SSOLogin";
        HashMap<String, String> params = new HashMap<String, String>();
        if(!schoolCodeMap.containsKey(this.loginDomain)){
            log.log(Level.WARNING , "schooldId is not configured , please configure it later ,  " + this.loginDomain);
            return null;
        }
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
                "  <LF N=\"lSchoolId\" V=\""+schoolCodeMap.get(this.loginDomain)+"\"/>\n" +
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


//    public String getSchoolCookie(String url, String cookie) throws IOException {
//
//
////        String loginUrl = respStr.substring(respStr.indexOf("http:") +1 , respStr.indexOf("<"));
//
//        url = url.replace("|","%7c") + "&jsonpCallback=callback&_="+System.currentTimeMillis();
//
//        Map<String , String> headerPrams = new HashMap<String, String>();
//        headerPrams.put(Constant.USER_AGENT , Constant.MAC_USER_AGENT);
//        headerPrams.put(Constant.REFERER ,  " http://sso.njcedu.com/login.htm?domain="+loginDomain+".njcedu.com");
//
//        String respStr = HttpClientUtil.getResByUrlAndCookie(url, headerPrams , getCookie(), true);
//
//
//        rebuildCookieMap(respStr.split("#")[1]);
//
//        return null;
//    }

    public List<TeachPlan> getTeachPlans(String cookie) throws IOException, ParserException {
        String teachPlanUrl = "http://"+this.loginDomain+".njcedu.com/student/prese/teachplan/index.htm";
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
    public List<Exercise> getExercises(String url, String cookie) throws IOException, ParserException {
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
        ;
        return exercises;
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
        String prefixFormated = String.format(sb.toString(), lUserId, schoolCodeMap.get(this.loginDomain), courseId, questionSize);

        sb = new StringBuffer();
        sb.append(prefixFormated);
        for (int i = 0; i < questionSize; i++) {
            Question currentQuestion = questions.get(i);

            List<Map<String, String>> questionRepository = JacksonUtil.answersCache;

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

    public String setSchoolToken(String schoolToken){
            cookieMap.put(CookieConstant.SCHOOL_TOKEN , schoolToken);
            return null;
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

class Constant{
    public static String USER_AGENT = "User-Agent";
    public static String REFERER = "Referer";
    public static String MAC_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:61.0) Gecko/20100101 Firefox/61.0";
}


