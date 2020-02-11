package org.chu.community.service;

import org.chu.community.dto.QuestionDTO;
import org.chu.community.mapper.QuestionMapper;
import org.chu.community.mapper.UserMapper;
import org.chu.community.model.Question;
import org.chu.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// 当一个请求需要将两种Model进行组装的时候，需要Service层
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question q: questionList) {
            User user = userMapper.findById(q.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);   // 快速将对象属性进行拷贝
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;
    }
}
