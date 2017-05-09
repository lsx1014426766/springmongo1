package com.tingmall;

import com.mongodb.WriteResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;
//静态导入
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by lsx on 2017/4/17.
 * 测试利用spring注解，spring-mongodb连接db，crud操作
 */
public class Test1 {
    @Test
    public void insert(){
        AnnotationConfigApplicationContext a=new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperations=a.getBean(MongoOperations.class);
        Person person=new Person();
        person.setName("white");
        person.setAge(23);
        mongoOperations.insert(person);
        List<Person>list=new ArrayList<Person>();
        for(int i=1;i<=20;i++){
            list.add(new Person("while"+i+"号",20+i));

        }
        mongoOperations.insertAll(list);
        a.close();
    }
    @Test
    public void query(){
        AnnotationConfigApplicationContext a=new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperations=a.getBean(MongoOperations.class);
        Person findOne=mongoOperations.findOne(new Query(where("name").is("white")),Person.class);
        //1findOne:Person{id='58f4588987ac29761d7f920a', name='white', age=23}
        System.out.println("1findOne:"+findOne);
        //查询所有记录
       List<Person> all=mongoOperations.findAll(Person.class);
        System.out.println("2findAll:"+all);
        Query query=new Query(where("age").gt(25).lt(30));
        List<Person> findbyConditions=mongoOperations.find(query,Person.class);
        System.out.println("3find:"+findbyConditions);
        //原生命令方式
        BasicQuery basicQuery=new BasicQuery("{name:'white'}");
        Person findone2=mongoOperations.findOne(basicQuery,Person.class);
        System.out.println("4BasicQuery findOne:"+findbyConditions);

        a.close();
    }
    @Test
    public void delete(){
        AnnotationConfigApplicationContext a=new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperations=a.getBean(MongoOperations.class);
        WriteResult writeResult=mongoOperations.remove(new Query(where("age").is(25)),Person.class);
        //先查再删除
        List<Person>findAllAndRemove= mongoOperations.findAllAndRemove(new Query(where("age").is(26)),Person.class);
        Person findOne=mongoOperations.findOne(new Query(where("name").is("white")),Person.class);
        mongoOperations.remove(findOne);
        //删除集合
       // mongoOperations.dropCollection(Person.class);

        a.close();

    }
    @Test
    public void update1(){
        AnnotationConfigApplicationContext a=new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperations=a.getBean(MongoOperations.class);
        mongoOperations.updateFirst(new Query(where("age").is(23)), Update.update("name","black"),Person.class);
        mongoOperations.updateMulti(new Query(where("age").gt(27)),update("name","lsx"),Person.class);

        a.close();

    }
}
