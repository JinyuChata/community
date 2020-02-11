package org.chu.community.controller;

import org.chu.community.mapper.QuestionMapper;
import org.chu.community.mapper.UserMapper;
import org.chu.community.model.Question;
import org.chu.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request, Model model) {

        Cookie[] cookies = request.getCookies();
        String token = null;
        User user = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("token")) {
                token = c.getValue();
                if (token != null) {
                    user = userMapper.findByToken(token);
                    request.getSession().setAttribute("user", user);
                }
            }
        }
        model.addAttribute("title", title);
        model.addAttribute("tag", tag);
        model.addAttribute("description", description);

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        if (title == null || "".equals(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || "".equals(description)) {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        }
        if (tag == null || "".equals(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }



        Question question = new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtModified());
        question.setCreator(user.getId());
        questionMapper.createQuestion(question);
        return "redirect:/";
    }

}
