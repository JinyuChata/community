package org.chu.community.service;

import org.chu.community.dto.PaginationDTO;
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

//    cnt 每页数量
//      0, 5 page1   (n-1)*cnt, cnt
//      5, 5 page2
//      10,5 page3
//
//    total: tot
//    tot%cnt==0 -> tot/cnt + 1
//    tot%cnt!=0 -> tot/cnt

    public PaginationDTO list(int currentPage, int sizeOfOnePage) {
        if (currentPage < 1) currentPage = 1;
        int offset = sizeOfOnePage * (currentPage - 1);

        List<Question> questionList = questionMapper.list(offset, sizeOfOnePage);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question q: questionList) {
            User user = userMapper.findById(q.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);   // 快速将对象属性进行拷贝
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        Integer totCount = questionMapper.count();
        paginationDTO.setPagination(totCount, currentPage, sizeOfOnePage);

        return paginationDTO;
    }
}
