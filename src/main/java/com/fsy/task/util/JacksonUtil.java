package com.fsy.task.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsy.task.domain.Question;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static List<Map<String,String>> answersCache = null;
    public static void main(String[] args) throws IOException {
        JacksonUtil.getQuestions();
    }
    public static List<Map<String,String>> getQuestions() throws IOException {
        List<Map<String,String>> questions = JacksonUtil.objectMapper.readValue(
                new File("/Users/vincent/gitRepos/selfAnswerTool_250/target/classes/online_question_bank.json")
                , List.class);
        answersCache = questions;
        return questions;
    }
}