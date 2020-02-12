package org.chu.community.controller;

import org.chu.community.dto.PaginationDTO;
import org.chu.community.dto.QuestionDTO;
import org.chu.community.mapper.QuestionMapper;
import org.chu.community.mapper.UserMapper;
import org.chu.community.model.Question;
import org.chu.community.model.User;
import org.chu.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "8") int size) {
        // 访问index-controller时，需要注入UserMapper
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null)
            for (Cookie c : cookies) {
                if (c.getName().equals("token")) {
                    token = c.getValue();
                    if (token != null) {
                        User user = userMapper.findByToken(token);
                        request.getSession().setAttribute("user", user);
                    }
                }
            }

        PaginationDTO paginationDTO = questionService.list(page, size);
        model.addAttribute("paginationDTOs", paginationDTO);

        return "index";
    }
}
