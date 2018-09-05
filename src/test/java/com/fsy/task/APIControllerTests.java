//package com.fsy.task;
//
//import com.fsy.task.domain.Question;
//import org.htmlparser.util.ParserException;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//public class APIControllerTests {
//
//
//    public void getNoReadMessageCount(){
//        try {
//            new APIController().getListens();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////    @Test
//    public void getExercises(){
//        String listenId = "9670000047";
//        try {
//            new APIController().getExercises(listenId , GlobalConfig.COOKIE2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParserException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getQuestionByCourseId(){
//        String courseId = "1322";
//        try {
//            List<Question> questions = APIController.getQuestionByCourseId(courseId , GlobalConfig.COOKIE2);
//            return;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParserException e) {
//            e.printStackTrace();
//        }
//    }
//}