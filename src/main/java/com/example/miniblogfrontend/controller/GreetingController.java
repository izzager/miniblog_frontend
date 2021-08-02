package com.example.miniblogfrontend.controller;

import com.example.miniblogfrontend.management.CurrentUserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final CurrentUserHolder currentUserHolder;

    @RequestMapping("/")
    public String index(Model model) {
        CurrentUserHolder.checkAuth(currentUserHolder, model);
        return "index";
    }

    @RequestMapping("/index")
    public String indexPage(Model model) {
        CurrentUserHolder.checkAuth(currentUserHolder, model);
        return "index";
    }

    @RequestMapping("/main")
    public String mainPage() {
        return "main";
    }

}
