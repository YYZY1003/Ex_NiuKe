package com.yy.community.dao;

import com.yy.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit,int orderMode);

    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);

    //增加帖子
    int insertDiscussPost(DiscussPost discussPost);

    //帖子详情
    DiscussPost selectDiscussPostById(int id);

    //评论数量
    int updateCommentCount(int id,int commentCount);

    //更新
    int updateType(int id,int type);

    //更新状态
    int updateStatus(int id,int status);

    int updateScore(int id, double score);
}