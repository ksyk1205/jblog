package kr.co.itcen.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import kr.co.itcen.config.web.FileuploadConfig;
import kr.co.itcen.config.web.MVCConfig;
import kr.co.itcen.config.web.MessageConfig;
import kr.co.itcen.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy //<aop:aspectj-autoproxy />
@ComponentScan({"kr.co.itcen.jblog.controller","kr.co.itcen.jblog.exception"})
@Import({MVCConfig.class,FileuploadConfig.class,MessageConfig.class,SecurityConfig.class})
public class WebConfig {

}
