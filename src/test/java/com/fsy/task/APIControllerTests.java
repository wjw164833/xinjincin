package com.fsy.task;

import org.htmlparser.util.ParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APIControllerTests {

    @Test
    public void doAnswerOnly() throws IOException, ParserException, InterruptedException {
//        String schoolToken = "4f165533b922eedfdff676209b47c6134b10377d6ba70d34ba4551753821b5e3561d9d8d03aa55283999b7a53f895bafa58137d1df2413dd1d860a4a4dc6a356118396cefbd83c36de4f384fa4c88334e0c697e0c98e71f3f7e5a1e45908aae4e47e5d31e7c4373c0d822564e84d9cc8bdd02af7c3366f5d50ff8fe75b4f76ca1f29b6e4c0ddb9e9";
//
//        APIController apiController = new APIController(schoolToken , "yau-1100116044021" , "123456" ,  AnswerOption.ANSWER);

        //sdnu201715030108 ncy1224
//        APIController apiController = new APIController(schoolToken , "yau-1100116044021" , "123456" ,  AnswerOption.ANSWER);
//        String token = "67b5d00af78aae50d732a0aaeaf3c2c3923d2127a5e9d33d2edabb6f538eb07443b67c579038b2a82667a1c3ca7c3326ff3d0138af24a449450f47c7d4e7ee5199a9101c7b7fcc11f3fbf3d205b40bfb8359234e64a0a27fdba1ef711c5b95f93a3866da6fada12c27191046d9a74ffefc65730725b4088b65082de413409dde2b67f19979a8b9fb";

        //做试题
//        APIController apiController = new APIController(token , "gyu-160308401009" , "123456");
        //gyu-160308401009  123456 看视频

        APIController apiController = new APIController("tecsuda-1816473049" , "123456");

    }
}