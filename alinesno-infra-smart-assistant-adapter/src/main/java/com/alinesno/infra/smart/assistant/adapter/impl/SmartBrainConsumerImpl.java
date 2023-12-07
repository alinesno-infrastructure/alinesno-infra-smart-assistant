//package com.alinesno.infra.smart.assistant.adapter.impl;
//
//import com.alinesno.infra.smart.assistant.adapter.SmartBrainConsumer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//@Service
//public class SmartBrainConsumerImpl implements SmartBrainConsumer {
//
//    private static final Logger log = LoggerFactory.getLogger(SmartBrainConsumerImpl.class) ;
//
//    @Value("${alinesno.infra.gateway.host}")
//    private String infraGatewayHost ;
//
//    @Override
//    public String chatProcess(String message) {
//
//        try {
//            URL url = new URL(infraGatewayHost + "/api/chat-process") ;
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setDoOutput(true);
//
//            String requestBody = "{\n" +
//                    "    \"prompt\":\"" + message + " \",\n" +
//                    "    \"options\":{\n" +
//                    "        \"conversationId\":\"\",\n" +
//                    "        \"parentMessageId\":\"\"\n" +
//                    "    },\n" +
//                    "    \"systemMessage\":\"You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown.\",\n" +
//                    "    \"temperature\":0.8,\n" +
//                    "    \"tokens\":1000\n" +
//                    "}";
//
//            OutputStream outputStream = connection.getOutputStream();
//            outputStream.write(requestBody.getBytes());
//            outputStream.flush();
//
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line;
//
//                String textData = "" ;
//
////                while ((line = reader.readLine()) != null) {
////                    JSONObject json = JSONObject.parseObject(line) ;
////                    textData = json.getString("text") ;
////
////                    log.debug(">>>>>>> line = {}" , textData);
////                }
//                reader.close();
//
//                log.debug("textData = {}" , textData);
//
//                return textData ;
//
//            } else {
//                System.out.println("HTTP request failed with response code: " + responseCode);
//            }
//
//            connection.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
