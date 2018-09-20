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

    public static ObjectMapper objectMapper = new ObjectMapper();
}