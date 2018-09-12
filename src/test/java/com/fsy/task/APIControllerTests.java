package com.fsy.task;

import com.fsy.task.domain.Exercise;
import com.fsy.task.domain.Question;
import com.fsy.task.domain.TeachPlan;
import com.fsy.task.domain.enums.AnswerOption;
import org.htmlparser.util.ParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class APIControllerTests {

//    @Test
    public void getExercises(){
        String listenId = "9670000047";
        try {
            APIController apiController = new APIController();
            apiController.getExercises(listenId , GlobalConfig.COOKIE2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void getQuestionByCourseId(){

        APIController apiController = new APIController();
        String courseId = "1322";
        try {
            List<Question> questions = apiController.getQuestionByCourseId(courseId , GlobalConfig.COOKIE2);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void watchVideoTests(){

        String courseId = "1317";
        String schoolId = "539";
        int need2Complete = 51-6;
        int needCount = -1;
        if(need2Complete % 2 == 0){
            needCount = need2Complete / 2 ;
        }else{
            needCount = need2Complete / 2 + 1 ;
        }
        APIController apiController = new APIController(null);
        try {
            for(int count = 0 ; count <=need2Complete / 2 + 1  ; count++){
                apiController.watchVideo(courseId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //http://course.njcedu.com/newcourse/coursecc.htm?courseId=1285
        //获取 courseWare cookie
    }

    @Test
    public void courseListTests(){
        APIController apiController = new APIController(null);
//        apiController.getExercises()
    }

    @Test
    public void doAnswerOnly() throws IOException, ParserException, InterruptedException {
//        String schoolToken = "4f165533b922eedfdff676209b47c6134b10377d6ba70d34ba4551753821b5e3561d9d8d03aa55283999b7a53f895bafa58137d1df2413dd1d860a4a4dc6a356118396cefbd83c36de4f384fa4c88334e0c697e0c98e71f3f7e5a1e45908aae4e47e5d31e7c4373c0d822564e84d9cc8bdd02af7c3366f5d50ff8fe75b4f76ca1f29b6e4c0ddb9e9";
//
//        APIController apiController = new APIController(schoolToken , "yau-1100116044021" , "123456" ,  AnswerOption.ANSWER);

        //sdnu201715030108 ncy1224
//        APIController apiController = new APIController(schoolToken , "yau-1100116044021" , "123456" ,  AnswerOption.ANSWER);
        String token = "67b5d00af78aae50d732a0aaeaf3c2c3923d2127a5e9d33d2edabb6f538eb07443b67c579038b2a82667a1c3ca7c3326ff3d0138af24a449450f47c7d4e7ee5199a9101c7b7fcc11f3fbf3d205b40bfb8359234e64a0a27fdba1ef711c5b95f93a3866da6fada12c27191046d9a74ffefc65730725b4088b65082de413409dde2b67f19979a8b9fb";

        //做试题
        //APIController apiController = new APIController(token , "gyu-160308401009" , "123456" ,  AnswerOption.WATCH);
        //gyu-160308401009  123456 看视频
        APIController apiController = new APIController(token , "gyu-160308401009" , "123456" ,  AnswerOption.WATCH);

    }
}