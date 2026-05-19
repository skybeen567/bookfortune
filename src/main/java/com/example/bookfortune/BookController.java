package com.example.bookfortune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    // 메인 화면 매핑
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 자바스크립트에서 호출하는 API 매핑
    @GetMapping("/api/fortune")
    @ResponseBody
    public BookDto getFortune(@RequestParam int id) {
        return bookService.getRandomBookFortune(id);
    }
}