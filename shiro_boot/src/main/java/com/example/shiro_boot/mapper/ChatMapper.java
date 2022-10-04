package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.vo.ChatRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface ChatMapper {

    @Insert("insert `chat_record` (id,who,content,date) values (" +
            "#{id},#{who},#{content},#{date})")
    public Integer add_chat(@Param("id")String id,@Param("who")Long who,
    @Param("content")String content,@Param("date") Date date);

    @Select("select content,date,who from `chat_record` where id=#{id}")
    public List<ChatRecords> query_chat_records(String id);
}
