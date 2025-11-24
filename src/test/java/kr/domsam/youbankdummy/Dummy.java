package kr.domsam.youbankdummy;

import net.datafaker.Faker;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Locale;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Dummy {
    public static final Faker koFaker = new Faker(new Locale("ko")); //한글
    public static final Faker faker = new Faker(new Locale("en")); //영어
}
