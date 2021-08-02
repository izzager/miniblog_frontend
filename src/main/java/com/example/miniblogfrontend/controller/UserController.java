package com.example.miniblogfrontend.controller;

import com.example.miniblogfrontend.dto.AchievementDto;
import com.example.miniblogfrontend.dto.UserDto;
import com.example.miniblogfrontend.helper.RequestTemplateHelper;
import com.example.miniblogfrontend.management.CurrentUserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final RestTemplate restTemplate;
    private final RequestTemplateHelper<Object> requestTemplateHelper;
    private final CurrentUserHolder currentUserHolder;

    @GetMapping("/myProfile")
    public String myProfile() {
        return "myProfile";
    }

    @GetMapping("/achievements")
    public String achievements(Model model) {
        try {
            ResponseEntity<AchievementDto[]> response = restTemplate
                    .exchange("http://localhost:9191/achievements",
                            HttpMethod.GET,
                            requestTemplateHelper.getRequestPayloadWithAuthorization(),
                            AchievementDto[].class);
            model.addAttribute("achievements",
                    Arrays.stream(response.getBody())
                            .collect(Collectors.toList()));
            return "achievements";
        } catch (Exception ex) {
            return "error";
        }
    }

    @GetMapping("/subscriptions")
    public String subscriptions(Model model) {
        ResponseEntity<UserDto[]> response = restTemplate
                .exchange("http://localhost:9191/users/subscriptions",
                        HttpMethod.GET,
                        requestTemplateHelper.getRequestPayloadWithAuthorization(),
                        UserDto[].class);
        model.addAttribute("subscriptions",
                Arrays.stream(response.getBody())
                        .collect(Collectors.toList()));
        return "subscriptions";
    }

    @PostMapping("/subscribe")
    public String subscribe(@ModelAttribute UserDto userForm) {
        try {
            ResponseEntity<Void> response = restTemplate
                    .postForEntity("http://localhost:9191/users/" + userForm.getId() + "/subscribe",
                            requestTemplateHelper.getRequestPayloadWithAuthorization(),
                            Void.class);
            return "redirect:/subscriptions";
        } catch (Exception ex) {
            return "redirect:/error";
        }
    }

    @PostMapping("/unsubscribe")
    public String unsubscribe(@ModelAttribute UserDto userForm) {
        try {
            ResponseEntity<Void> response = restTemplate
                    .postForEntity("http://localhost:9191/users/" + userForm.getId() + "/unsubscribe",
                            requestTemplateHelper.getRequestPayloadWithAuthorization(),
                            Void.class);
            return "redirect:/subscriptions";
        } catch (Exception ex) {
            return "redirect:/error";
        }
    }

    @GetMapping("/users")
    public String users(Model model) {
        ResponseEntity<UserDto[]> response = restTemplate
                .exchange("http://localhost:9191/users/all",
                        HttpMethod.GET,
                        requestTemplateHelper.getRequestPayloadWithAuthorization(),
                        UserDto[].class);
        model.addAttribute("users",
                Arrays.stream(response.getBody())
                        .collect(Collectors.toList()));
        return "users";
    }
}
