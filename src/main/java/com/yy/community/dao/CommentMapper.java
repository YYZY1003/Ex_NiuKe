package com.yy.community.dao;

import com.yy.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

//帖子评论 帖子详情
@Mapper
public interface CommentMapper {

    //分页查询
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    //帖子内容 一共有多少数据
    int selectCountByEntity(int entityType, int entityId);

    //新增评论
    int insertComment(Comment comment);

    //查询id
    Comment selectCommentById(int id);
}
