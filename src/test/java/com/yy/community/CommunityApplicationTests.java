package com.yy.community;

import com.yy.community.dao.AlphaDao;
import com.yy.community.service.AlphaService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

	ApplicationContext applicationContext;

	@Test
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException{
		this.applicationContext=applicationContext;
	}

	@Test
	public void testApplicationContext(){
		System.out.println("applicationContext = " + applicationContext);

		AlphaDao alphaDao=applicationContext.getBean(AlphaDao.class);
		System.out.println("alphaDao.select() = " + alphaDao.select());
	}

	@Test
	public void testBeanManagement(){
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println("alphaService = " + alphaService);
	}

	@Test
	public void testBeanConfig(){
		SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println("simpleDateFormat.format(new Date()) = " + simpleDateFormat.format(new Date()));
	}


}
