package com.example.miniblogfrontend.management;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Data
@NoArgsConstructor
@Component
public class CurrentUserHolder {

    private String userToken = "";

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public static void checkAuth(CurrentUserHolder currentUserHolder, Model model) {
        if (!currentUserHolder.getUserToken().equals("")) {
            model.addAttribute("isUserAuthorize", true);
        }
    }
}
