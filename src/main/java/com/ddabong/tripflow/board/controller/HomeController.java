package com.ddabong.tripflow.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//controller 어노테이션 추가

public class HomeController {

    @GetMapping("/index") // "/"만 있는 경우 localhost8080과 동일한 의미를 가진다.
    public String index() {
        System.out.println("HelloController.index");
        return "index"; // index.html 페이지를 출력한다.
    }
}
