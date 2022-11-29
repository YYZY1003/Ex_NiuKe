package com.yy.community.util;

import com.yy.community.entity.User;
import org.springframework.stereotype.Component;

//容器 持有用户信息 用于代替session对象
@Component
public class HostHolder {

    private ThreadLocal<User> users=new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
