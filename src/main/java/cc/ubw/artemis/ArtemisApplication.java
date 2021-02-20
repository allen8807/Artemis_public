package cc.ubw.artemis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ArtemisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtemisApplication.class, args);
    }

}
