package com.example.miniblogfrontend.controller;

import com.example.miniblogfrontend.dto.DatabaseCommentDto;
import com.example.miniblogfrontend.dto.DatabasePostDto;
import com.example.miniblogfrontend.dto.FullCommentDto;
import com.example.miniblogfrontend.dto.FullPostDto;
import com.example.miniblogfrontend.dto.PublicationCommentDto;
import com.example.miniblogfrontend.dto.PublicationPostDto;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final RestTemplate restTemplate;
    private final RequestTemplateHelper<Object> requestTemplateHelper;
    private final CurrentUserHolder currentUserHolder;

    @GetMapping("/myPosts")
    public String myPosts(Model model) {
        try {
            ResponseEntity<FullPostDto[]> response = restTemplate
                    .exchange("http://localhost:9191/posts",
                            HttpMethod.GET,
                            requestTemplateHelper.getRequestPayloadWithAuthorization(),
                            FullPostDto[].class);
            model.addAttribute("posts",
                    Arrays.stream(response.getBody())
                            .collect(Collectors.toList()));
            return "myPosts";
        } catch (RestClientException | NullPointerException ex) {
            return "error";
        }
    }

    @GetMapping("/createPost")
    public String createPostPage(Model model) {
        model.addAttribute("postForm", new PublicationPostDto("text"));
        return "createPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute PublicationPostDto postForm) {
        try {
            DatabasePostDto response = restTemplate
                    .postForObject("http://localhost:9191/posts",
                            requestTemplateHelper
                                    .getRequestPayloadWithAuthorization(postForm),
                            DatabasePostDto.class);
            return "redirect:/myPosts";
        } catch (RestClientException | NullPointerException ex) {
            return "redirect:/error";
        }
    }

    @PostMapping("/deletePost")
    public String deletePost(@ModelAttribute FullPostDto postForm) {
        try {
            ResponseEntity<Void> response = restTemplate
                    .exchange("http://localhost:9191/posts/" + postForm.getId(),
                            HttpMethod.DELETE,
                            requestTemplateHelper.getRequestPayloadWithAuthorization(),
                            Void.class);
            return "redirect:/myPosts";
        } catch (RestClientException | NullPointerException ex) {
            return "redirect:/error";
        }
    }

    @PostMapping("/createComment")
    public String createComment(@ModelAttribute FullCommentDto commentForm) {
        try {
            DatabaseCommentDto response = restTemplate
                    .postForObject("http://localhost:9191/posts/" + commentForm.getPostId() + "/comments",
                            requestTemplateHelper
                                    .getRequestPayloadWithAuthorization(new PublicationCommentDto(commentForm.getText())),
                            DatabaseCommentDto.class);
            return "redirect:/myPosts";
        } catch (RestClientException | NullPointerException ex) {
            return "redirect:/error";
        }
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@ModelAttribute FullCommentDto commentForm) {
        try {
            ResponseEntity<Void> response = restTemplate
                    .exchange("http://localhost:9191/posts/" + commentForm.getPostId() + "/comments/" + commentForm.getId(),
                            HttpMethod.DELETE,
                            requestTemplateHelper.getRequestPayloadWithAuthorization(),
                            Void.class);
            return "redirect:/myPosts";
        } catch (RestClientException | NullPointerException ex) {
            return "redirect:/error";
        }
    }

    @GetMapping("/latestPosts")
    public String latestPosts(Model model) {
        try {
            ResponseEntity<FullPostDto[]> response = restTemplate
                    .exchange("http://localhost:9191/latestPosts",
                            HttpMethod.GET,
                            requestTemplateHelper.getRequestPayloadWithAuthorization(),
                            FullPostDto[].class);
            model.addAttribute("posts",
                    Arrays.stream(response.getBody())
                            .collect(Collectors.toList()));
            CurrentUserHolder.checkAuth(currentUserHolder, model);
            return "latestPosts";
        } catch (RestClientException | NullPointerException ex) {
            return "error";
        }
    }

    @GetMapping("/feed")
    public String feed(Model model) {
        try {
            ResponseEntity<FullPostDto[]> response = restTemplate
                    .exchange("http://localhost:9191/feed",
                            HttpMethod.GET,
                            requestTemplateHelper.getRequestPayloadWithAuthorization(),
                            FullPostDto[].class);
            model.addAttribute("posts",
                    Arrays.stream(response.getBody())
                            .collect(Collectors.toList()));
            CurrentUserHolder.checkAuth(currentUserHolder, model);
            return "feed";
        } catch (Exception ex) {
            return "error";
        }
    }

}
