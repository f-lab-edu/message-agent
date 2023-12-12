package com.kth.mssage.info.web.controller.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;

public class JsonReader {

    public static String readJsonFile(String fileName) throws IOException {
        try (InputStream inputStream = new ClassPathResource(fileName).getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader)) {

            StringBuilder resultStringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
            return resultStringBuilder.toString();
        }
    }
}
