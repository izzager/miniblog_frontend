package com.example.miniblogfrontend.controller;

import com.example.miniblogfrontend.dto.AuthResponse;
import com.example.miniblogfrontend.dto.UserAuthDto;
import com.example.miniblogfrontend.dto.UserDataDto;
import com.example.miniblogfrontend.helper.RequestTemplateHelper;
import com.example.miniblogfrontend.management.CurrentUserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RestTemplate restTemplate;
    private final RequestTemplateHelper<Object> requestTemplateHelper;
    private final CurrentUserHolder currentUserHolder;

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("userDataDto", new UserDataDto());
        model.addAttribute("error", false);
        return "registration";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute UserDataDto userDataDto,
                           Model model) {
        try {
            ResponseEntity<Void> response = restTemplate
                    .postForEntity("http://localhost:9191/register",
                            requestTemplateHelper.getRequestPayload(userDataDto),
                            Void.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/login";
            } else {
                model.addAttribute("error", true);
                return "redirect:/registration";
            }
        } catch (Exception ex) {
            model.addAttribute("error", true);
            return "redirect:/registration";
        }
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userAuthDto", new UserAuthDto());
        model.addAttribute("error", false);
        return "login";
    }

    @PostMapping("/logging")
    public String login(@ModelAttribute UserAuthDto userAuthDto,
                        Model model) {
        try {
            AuthResponse response = restTemplate
                    .postForObject("http://localhost:9191/login",
                            requestTemplateHelper.getRequestPayload(userAuthDto),
                            AuthResponse.class);
            currentUserHolder.setUserToken(response.getToken());
            return "redirect:/main";
        } catch (RestClientException | NullPointerException ex) {
            model.addAttribute("error", true);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        currentUserHolder.setUserToken("");
        CurrentUserHolder.checkAuth(currentUserHolder, model);
        return "redirect:/index";
    }

}
