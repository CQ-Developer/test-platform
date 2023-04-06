package org.huhu.test.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

import static java.time.ZoneOffset.UTC;

@SpringBootApplication
public class TestPlatformApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC));
        SpringApplication.run(TestPlatformApplication.class, args);
    }

}
