package com.yy.community.service;


import com.yy.community.dao.LoginTicketMapper;
import com.yy.community.dao.UserMapper;
import com.yy.community.entity.LoginTicket;
import com.yy.community.entity.User;
import com.yy.community.util.CommunityConstant;
import com.yy.community.util.CommunityUtil;
import com.yy.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService implements CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    //查询用户
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        //对空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        //没有账号
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }//没有密码
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }//没有邮箱
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }

        //如果都不为空 开始用户处理
        //验证账号
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "账号已存在");
            return map;
        }
        //邮箱
        User e = userMapper.selectByEmail(user.getEmail());
        if (e != null) {
            map.put("emailMsg", "邮箱已经被注册");
            return map;
        }

        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        //激活路径
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "YY", content);

        return null;
    }

    //激活方法
    public int activation(int userId,String code){
        User user = userMapper.selectById(userId);
        //重复激活
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {     //成功激活
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    //登录操作
    public Map<String,Object> login(String username,String password ,int expiredSeconds){
        Map<String,Object> map=new HashMap<>();

        //空值处理
        if (StringUtils.isBlank(username)){
            map.put("usernameMsg","用户名不能为空");
            return map;
        }if (StringUtils.isBlank(password)){
            map.put("passwordMsg","密码错误");
            return map;
        }

        //验证账号
        User user = userMapper.selectByName(username);
        if (user==null){
            map.put("usernameMsg","账号不存在");
            return map;
        }if (user.getStatus()==0){
            map.put("username","该账号未激活");
            return map;
        }

        //验证密码
        password=CommunityUtil.md5(password+user.getSalt());
        if (!user.getPassword().equals(password)){
            map.put("password","密码错误");
            return map;
        }

        //生成凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);

        //把凭证发送前台
        map.put("ticket",loginTicket.getTicket());

        return map;
    }

    //退出登录
    public void logout(String ticket){
        loginTicketMapper.updateStatus(ticket,1);
    }

    //查询ticket
    public LoginTicket findLoginTicket(String ticket){
        return loginTicketMapper.selectByTicket(ticket);
    }

    //更新头像
    public int updateHeader(int userId,String headerUrl){
        return userMapper.updateHeader(userId,headerUrl);
    }
}
