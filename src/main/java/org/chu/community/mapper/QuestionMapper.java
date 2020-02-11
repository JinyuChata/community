package org.chu.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chu.community.model.Question;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(title, description, gmt_create, gmt_modified, creator, tag) " +
            "VALUES(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    public void createQuestion(Question question);
}
