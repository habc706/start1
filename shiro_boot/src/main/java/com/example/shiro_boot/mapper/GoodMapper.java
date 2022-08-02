package com.example.shiro_boot.mapper;

import com.example.shiro_boot.pojo.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface GoodMapper {

    @Select("select * from good limit #{limit}, #{offset}")
    public List<Goods> query_limit(@Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("select * from good where good_owner=#{userid}")
    public List<Goods> query_by_user(String userid);

    @Update("update good set good_name=#{good_name}, good_dis=#{good_dis}," +
            "have=#{have},url =#{url} "+
            "where good_owner = #{good_owner} and add_time=#{add_time}"
            )
    public int change_info( Goods goods);


    @Insert("insert into good (good_name,good_dis,have,url,good_owner,add_time) values(" +
            "#{good_name},#{good_dis},#{have},#{url},#{good_owner},#{add_time})")
    public int add_good(Goods goods);

}
