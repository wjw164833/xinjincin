package com.fsy.task;

import com.fsy.task.domain.Exercise;
import com.fsy.task.domain.Question;
import com.fsy.task.domain.TeachPlan;
import com.fsy.task.util.HttpClientUtil;
import com.fsy.task.util.JacksonUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.emf.cdo.CDOAdapter;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;

import static org.eclipse.emf.cdo.view.CDOAdapterPolicy.CDO;

public class APIController {

    static {
        try {
            JacksonUtil.getQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {

            //获取学习任务
//            List<Question> questions = getQuestionByCourseId("1286", GlobalConfig.COOKIE2);
//            doAnswer(GlobalConfig.COOKIE2 , "1286" , questions);
            List<TeachPlan> plans = getTeachPlans(GlobalConfig.COOKIE2);

//            for(TeachPlan plan : plans){
                //过滤出正在进行中的
            //String url = "http://zync.njcedu.com/student/prese/teachplan/listdetail.htm?id="+listenId;
                List<Exercise> exercises = getExercises("http://qqhru.njcedu.com/student/prese/teachplan/listdetail.htm?id=26970000003"
                        , "26970" ,GlobalConfig.COOKIE2);
                //过滤出已完成的
                for(Exercise exercise : exercises){
                    List<Question> questions = getQuestionByCourseId(exercise.getNumber() ,  GlobalConfig.COOKIE2);

                    doAnswer(GlobalConfig.COOKIE2 , exercise.getNumber() , questions);
//                    //匹配标题 找到答案
//                    for(Question waitMatch : questions){
//
//
//                    }
//
////                    return;
                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }
    public static List<TeachPlan> getTeachPlans(String cookie) throws IOException, ParserException {
        String url = "http://zync.njcedu.com/student/prese/teachplan/index.htm";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "secret"));
        Header header = new BasicHeader("Cookie",cookie);
        httpGet.addHeader(header);
        CloseableHttpResponse response2 = httpclient.execute(httpGet);
        System.out.println(response2.getStatusLine());
        HttpEntity entity2 = response2.getEntity();
        // do something useful with the response body
        // and ensure it is fully consumed
        String respStr = EntityUtils.toString(entity2 , Charset.defaultCharset());

        Parser parser = Parser.createParser(respStr , Charset.defaultCharset().toString());

        //缓冲层 parser解析一次之后，再次解析为空
        NodeList cacheNodeList = parser.parse(new NodeFilter() {
            public boolean accept(Node node) {
                return true;
            }
        });

        //class xxrw_l_fk
        NodeFilter teachPlanFilter = new NodeFilter() {
            public boolean accept(Node node) {
                if(node instanceof BulletList
                        && ((BulletList) node).getAttribute("class") != null
                        && ((BulletList) node).getAttribute("class").equals("xxrw_l_fk"))
                    return true;
                else return false;
            }
        };

        NodeList thatMatch = cacheNodeList.extractAllNodesThatMatch(teachPlanFilter,false);


        List<TeachPlan> plans = new ArrayList<TeachPlan>();
        for(Node match : thatMatch.toNodeArray()){
            if(match instanceof  BulletList){
                TeachPlan plan = TeachPlan.builder().build();
                NodeList childs = match.getChildren();
                int count = 0;
                for(Node filterBullet : childs.toNodeArray()){


                    if(filterBullet instanceof Bullet){
                        count++;
                        if(count == 1){
                            int tempCount = 0;
                            int tempCountI = 0 ;
                            for(Node spanOrLink : filterBullet.getChildren().toNodeArray()){

                                if(spanOrLink instanceof Span){
                                    //taskname
                                    if(tempCount == 0 ){
                                        plan.setTaskName(((Span) spanOrLink).getAttribute("title"));
                                    }
                                    tempCount++;
                                }else if(spanOrLink instanceof LinkTag){
                                    //status
                                    if(tempCountI == 0)
                                    plan.setStatus(((LinkTag) spanOrLink).getLinkText());
                                    tempCountI++;
                                }
                            }
                            count++;
                        }else if(count == 3 ){
                            int tempI = 0;
                            int tempJ = 0;
                            if(filterBullet instanceof Bullet){
                                for(Node spanOrLink : filterBullet.getChildren().toNodeArray()){

                                    if(spanOrLink instanceof Span){
                                        //validTime
                                        if(tempI == 0){
                                            plan.setValidTime(((Span) spanOrLink).getStringText());
                                        }
                                        tempI++;
                                    }else if(spanOrLink instanceof LinkTag){
                                        //showPlanNumber
                                        if(tempJ == 0){
                                            String showPlanNumber = ((LinkTag) spanOrLink).getLink();
                                            showPlanNumber = showPlanNumber.replaceAll("showPlan\\(","")
                                                            .replaceAll("\\)","");
                                            plan.setShowPlanNumber(showPlanNumber);
                                        }
                                        tempJ++;
                                    }
                                }
                            }
                        }else ;

                    }

                }
                plans.add(plan);
            }
        }

        return plans;
    }

    /**
     *
     * @param listenId 9670000047
     * @return
     */
    public static List<Exercise> getExercises(String url , String listenId,String cookie ) throws IOException, ParserException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "secret"));
        Header header = new BasicHeader("Cookie",cookie);
        httpGet.addHeader(header);
        CloseableHttpResponse response2 = httpclient.execute(httpGet);
        System.out.println(response2.getStatusLine());
        HttpEntity entity2 = response2.getEntity();
        // do something useful with the response body
        // and ensure it is fully consumed
        String respStr = EntityUtils.toString(entity2 , Charset.defaultCharset());

        Parser parser = Parser.createParser(respStr , Charset.defaultCharset().toString());

        //id courseTable
        NodeFilter tableIdFilter = createTableIdFilter("courseTable");
        //map filter 过滤出课程列表
        NodeFilter heightEqualsFilter = createHeightEqualsFilter("50");
        NodeFilter tableColumnFilter = createTableColumnFilter();
        NodeList exerciseList = parser.parse(tableIdFilter);

        //应用过滤链 赛选出所需数据
        NodeList exerciseColumns = exerciseList
                .extractAllNodesThatMatch(heightEqualsFilter,true)
                .extractAllNodesThatMatch(tableColumnFilter,true);
        List<Exercise> exercises = populateNodeList2Exercises(exerciseColumns);;
        return exercises;
    }

    private static List<Exercise> populateNodeList2Exercises(NodeList exerciseColumns) {
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
                    rightPercent = rightPercent.replaceAll("\t","")
                            .replaceAll("\r","")
                            .replaceAll("\n","");
                    exercise.setRightPercent(rightPercent);
                } else if (count % 4 == 3) {
                    // 解析出code

                    String number = columned.getStringText();
                    // 过滤\r\n\t 特殊字符 \backspace
                    number = number.replaceAll("\t","")
                            .replaceAll("\r","")
                            .replaceAll("\n","")
                            .replaceAll(" ","");
                    number = number.substring(number.indexOf("(") + 1 , number.indexOf(")"));
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

    private static NodeFilter createTableColumnFilter() {
        return  new NodeFilter() {
            public boolean accept(Node node) {
                if(node instanceof TableColumn
                        )
                    return true;
                else return false;
            }
        };
    }

    private static NodeFilter createHeightEqualsFilter(String s) {
        return new NodeFilter() {
            public boolean accept(Node node) {
                if(node instanceof TableRow
                        && ((TableRow) node).getAttribute("height") != null
                        && ((TableRow) node).getAttribute("height").equals("50"))
                    return true;
                else return false;
            }
        };

    }

    private static NodeFilter createTableIdFilter(final String id) {

        NodeFilter filter = new NodeFilter() {
            public boolean accept(Node node) {
                if(node != null
                        && node instanceof TableTag
                        &&  ((TableTag)node).getAttribute("id") != null
                        &&  ((TableTag)node).getAttribute("id").equals(id)){
                    return true;
                }else
                    return false;
            }
        };
        return filter;
    }

    /**
     * 获取学习任务
     * @return
     * @throws IOException
     */
    public static int getListens() throws IOException {
        String url = "http://zync.njcedu.com/student/prese/teachplan/listdetail.htm?id=9670000047";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "secret"));
        Header header = new BasicHeader("Cookie",GlobalConfig.COOKIE);
        httpGet.addHeader(header);
        CloseableHttpResponse response2 = httpclient.execute(httpGet);
        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            String respStr = EntityUtils.toString(entity2 , Charset.defaultCharset());

            //class jl_table
            Parser parser = Parser.createParser(respStr , Charset.defaultCharset().toString());

            NodeFilter combinedFilter = new NodeFilter() {
                public boolean accept(Node node) {

                    return false;
                }
            };
            NodeFilter examTableFilter = new NodeFilter() {
                public boolean accept(Node node) {
                    if(node != null
                            && node instanceof TableTag
                            &&  ((TableTag)node).getAttribute("id") != null
                            &&  ((TableTag)node).getAttribute("id").equals("ExamTable")){
                        return true;
                    }else
                    return false;
                }
            };
            NodeList listenList = parser.parse(examTableFilter);
            int count = 0 ;

            Node baseNode = listenList.toNodeArray()[0];
            String taskName = baseNode.getChildren().toNodeArray()[3].getChildren().toNodeArray()[1].getChildren().toNodeArray()[0].getText();
            for(Node node : listenList.toNodeArray()){



            }
            System.out.println(respStr);
        } catch (ParserException e) {
            e.printStackTrace();
        } finally {
            response2.close();
        }
        return 0;
    }

    /**
     *根据课程Id查找到所有问题的标题
     * @param courseId 1322
     * @return
     * @throws IOException
     */
    public static List<Question> getQuestionByCourseId(String courseId , String cookie) throws IOException, ParserException {
        String url = "http://course.njcedu.com/questionbefore.htm?courseId="+courseId;
        String resp = HttpClientUtil.getResByUrlAndCookie(url , cookie);
        NodeFilter titleFilter = new NodeFilter() {
            public boolean accept(Node node) {
                if(node instanceof ParagraphTag
                        && ((ParagraphTag) node).getAttribute("class") != null
                        && ((ParagraphTag) node).getAttribute("class").equals("kcxt_wt mb10"))
                return true;
                else return false;
            }
        };

        Parser parser = Parser.createParser(resp , Charset.defaultCharset().toString()) ;

        //缓冲层 parser解析一次之后，再次解析为空
        NodeList cacheNodeList = parser.parse(new NodeFilter() {
            public boolean accept(Node node) {
                return true;
            }
        });

        NodeFilter questionOptionFilter = new NodeFilter() {
            public boolean accept(Node node) {
                if(node instanceof ParagraphTag
                        && ((ParagraphTag) node).getAttribute("class") != null
                        && ((ParagraphTag) node).getAttribute("class").equals("ml10 mb5"))
                    return true;
                else return false;
            }
        };
        NodeList thatMatch = cacheNodeList.extractAllNodesThatMatch(titleFilter);

        List<Question> questions = new ArrayList<Question>();
        int index = 0 ;
        String userId = "";
        String schoolId = "";
        for(Node match : thatMatch.toNodeArray()){
            if(match instanceof  ParagraphTag){
                index++;
                NodeList tableIndexNode = cacheNodeList.extractAllNodesThatMatch(createTableFilter(index));
                String questionId = ((TableTag)tableIndexNode.toNodeArray()[0]).getAttribute("index");


                if(index == 1){
                    NodeList userIdNode = cacheNodeList.extractAllNodesThatMatch(createUserIdFilter());
                    userId = ((InputTag)userIdNode.toNodeArray()[0]).getAttribute("value");

                    NodeList schoolIdNode = cacheNodeList.extractAllNodesThatMatch(createlSchoolIdFilter());
                    schoolId = ((InputTag)schoolIdNode.toNodeArray()[0]).getAttribute("value");
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
     *  userId 25800009324
     *  lSchoolId  192
     *  lCoursewareId 1322
     *  nExerciseAfterCount 4
     *
     *  lQuestionId 2413
     *  strAnswer D 正确答案
     *  strStudentAnswer D 学生答案
     * @param cookie
     * @param courseId
     * @param questions 题目列表
     * @throws IOException
     */
    public static void doAnswer(String cookie , String courseId , List<Question> questions ) throws IOException {
        String url = "http://course.njcedu.com/handleTrans.cdo?strServiceName=StudentCourseExerciseService&strTransName=addExerciseAfterAnswer";
        HashMap<String ,String> params = new HashMap<String, String>();
        String prefix =  "<CDO>\n" +
                "  <STRF N=\"strServiceName\" V=\"StudentCourseExerciseService\"/>\n" +
                "  <STRF N=\"strTransName\" V=\"addExerciseAfterAnswer\"/>\n" +
                "  <LF N=\"lUserId\" V=\"%s\"/>\n" +
                "  <LF N=\"lSchoolId\" V=\"%s\"/>\n" +
                "  <LF N=\"lCoursewareId\" V=\"%s\"/>\n" +
                "  <NF N=\"bInOrAfter\" V=\"1\"/>\n" +
                "  <NF N=\"nExerciseAfterCount\" V=\"%s\"/>\n" +
                "  <CDOAF N=\"answerArrayList\">\n"
                ;
        StringBuffer sb = new StringBuffer(prefix);
        String userId = "";
        String lSchoolId = "";


        int questionSize = questions.size();
        if(questionSize > 0){
            userId = questions.get(0).getUserId();
            lSchoolId = questions.get(0).getSchoolId();
        }
        String prefixFormated = String.format(sb.toString() ,  userId , lSchoolId , courseId , questionSize );

        sb = new StringBuffer();
        sb.append(prefixFormated);
        for(int i=0;i<questionSize ; i++){
            Question currentQuestion = questions.get(i);

            List<Map<String,String>> questionRepository = JacksonUtil.answersCache;

            String answer = "";
            for(Map<String,String> map : questionRepository){
                //取部分标题
                if(map.get("title").contains(currentQuestion.getTitle())){
                    answer = map.get("answer");
                }
            }

            sb.append("<CDO>");
            sb.append("      <LF N=\"lQuestionId\" V=\""+currentQuestion.getId()+"\"/>\n");
            sb.append("      <STRF N=\"strAnswer\" V=\""+answer+"\"/>");
            sb.append("      <STRF N=\"strStudentAnswer\" V=\""+answer+"\"/>");
            sb.append("      <NF N=\"bTrue\" V=\"0\"/>");
            sb.append("</CDO>");
        }
        sb.append("</CDOAF>" + "</CDO>");



        params.put("$$CDORequest$$", sb.toString());
        System.out.println(sb.toString());

        String resp = HttpClientUtil.postResByUrlAndCookie(url , cookie , params);
        System.out.println("doAnswer published!!");
        System.out.println(resp);
    }

    private static NodeFilter createTableFilter(final int index){
        return new NodeFilter() {
            public boolean accept(Node node) {
                if(node instanceof TableTag
                        && ((TableTag) node).getAttribute("id") != null
                        && ((TableTag) node).getAttribute("id").equals("table" + index))
                    return true;
                else return false;
            }
        };
    }

    private static NodeFilter createUserIdFilter(){
        return new NodeFilter() {
            public boolean accept(Node node) {
                if(node instanceof InputTag
                        && ((InputTag) node).getAttribute("id") != null
                        && ((InputTag) node).getAttribute("id").equals("lUserId"))
                    return true;
                else return false;
            }
        };
    }

    private static NodeFilter createlSchoolIdFilter(){
        return new NodeFilter() {
            public boolean accept(Node node) {
                if(node instanceof InputTag
                        && ((InputTag) node).getAttribute("id") != null
                        && ((InputTag) node).getAttribute("id").equals("lSchoolId"))
                    return true;
                else return false;
            }
        };
    }
}
