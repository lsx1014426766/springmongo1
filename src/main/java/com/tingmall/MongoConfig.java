package com.tingmall;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by lsx on 2017/4/17.
 * 这个文件相当于就是spring的一个xml配置文件，注入了2个bean对象
 */
@Configuration
public class MongoConfig {
    @Bean
    public MongoClientFactoryBean mongo() {
        MongoClientFactoryBean factoryBean = new MongoClientFactoryBean();
        // 数据库地址
        factoryBean.setHost("localhost");
        // 端口
        factoryBean.setPort(27017);

        return factoryBean;
    }

    @Bean
    public MongoOperations mongoTemplate(Mongo mongo) {
        // 操作Mongo的模板类，提供了非常纯粹的oo操作数据库的api
        // dbtest 为数据库名
        return new MongoTemplate(mongo, "dbtest");

    }
}

