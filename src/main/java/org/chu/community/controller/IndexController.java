package org.chu.community.controller;

import org.chu.community.mapper.UserMapper;
import org.chu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        // 访问index-controller时，需要注入UserMapper
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null)
            for (Cookie c : cookies) {
                if (c.getName().equals("token")){
                    token = c.getValue();
                    if (token != null) {
                        User user = userMapper.findByToken(token);
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
        return "index";
    }
}
