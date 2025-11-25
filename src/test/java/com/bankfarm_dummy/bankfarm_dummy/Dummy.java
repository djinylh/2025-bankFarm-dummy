package com.bankfarm_dummy.bankfarm_dummy;


import net.datafaker.Faker;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Locale;
@DataJpaTest
@MybatisTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class Dummy {
    @Autowired

    protected SqlSessionFactory sqlSessionFactory;

    protected Faker kofaker = new Faker(new Locale("ko")); //한국어
    protected Faker enfaker = new Faker(new Locale("en")); //영어
}
