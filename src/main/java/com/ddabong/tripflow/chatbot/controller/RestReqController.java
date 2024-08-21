package com.ddabong.tripflow.chatbot.controller;

import com.fasterxml.jackson.core.util.BufferRecycler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class RestReqController {

    @RequestMapping("/test_req")
    public String testRestRequest(Model model){
        try{
            System.out.println("python 서버 요청");
            URL url = new URL("http://15.165.111.146:5000/test1?data=hihihi");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream in = con.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            BufferedReader response = new BufferedReader(reader);
            String str = null;
            StringBuffer  buff = new StringBuffer();
            while ((str = response.readLine()) != null){
                buff.append(str + "\n");
            }
            String data = buff.toString().trim();
            System.out.println("reqResult : " + data);
            model.addAttribute("reqResult", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "result";
    }
}
