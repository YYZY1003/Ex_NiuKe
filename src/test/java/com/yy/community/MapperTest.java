package com.yy.community.dao;

import com.yy.community.CommunityApplication;
import com.yy.community.dao.DiscussPostMapper;
import com.yy.community.entity.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testDiscussPosts(){
//        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10);
//        for (DiscussPost discussPost : list) {
//            System.out.println("discussPost = " + discussPost);
//        }
        int rows = discussPostMapper.selectDiscussPostRows(0);
        System.out.println("rows = " + rows);
    }

}
