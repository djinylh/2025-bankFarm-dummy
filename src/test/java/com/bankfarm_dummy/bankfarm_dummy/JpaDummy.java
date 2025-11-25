package com.bankfarm_dummy.bankfarm_dummy;

import net.datafaker.Faker;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Locale;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class JpaDummy {

    public static final Faker kofaker = new Faker(new Locale("ko")); //한국어
    public static final Faker faker = new Faker(new Locale("en")); //영어
}
