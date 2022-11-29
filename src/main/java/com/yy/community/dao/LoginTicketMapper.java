package com.yy.community.dao;

import com.yy.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {

    //登录状态
    @Insert({"insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"})
    @Options(useGeneratedKeys = true,keyProperty = "id")     //自增
    int insertLoginTicket(LoginTicket loginTicket);

    //修改评论状态
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);

    //查询
    @Select({
            "select id,user_id,ticket,status,expired",
            "from login_ticket where ticket = #{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

}
