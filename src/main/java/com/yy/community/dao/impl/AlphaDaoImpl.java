package com.yy.community.dao.impl;

import com.yy.community.dao.AlphaDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaDaoImpl implements AlphaDao {
    @Override
    public String select() {
        return "nihao";
    }
}
