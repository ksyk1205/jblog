package kr.co.itcen.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import kr.co.itcen.config.app.DBConfig;
import kr.co.itcen.config.app.MyBatisConfig;

@Configuration 
@ComponentScan({"kr.co.itcen.jblog.service", "kr.co.itcen.jblog.repository"})
@Import({DBConfig.class,MyBatisConfig.class})
public class AppConfig {


}
