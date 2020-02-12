package org.chu.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.chu.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(title, description, gmt_create, gmt_modified, creator, tag) " +
            "VALUES(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    public void createQuestion(Question question);

    @Select("SELECT * FROM question LIMIT #{offset}, #{size}")
    List<Question> list(@Param("offset") int offset,@Param("size") int size);

    @Select("SELECT COUNT(1) FROM question")
    Integer count();
}
