package com.example.miniblogfrontend.controller;

import com.example.miniblogfrontend.management.CurrentUserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MyErrorController implements ErrorController {

    private final CurrentUserHolder currentUserHolder;

    @RequestMapping("/error")
    public String handleError(Model model) {
        CurrentUserHolder.checkAuth(currentUserHolder, model);
        return "error";
    }

}
