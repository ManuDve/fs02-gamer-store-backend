package cl.maotech.gamerstoreback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GamerStoreBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamerStoreBackApplication.class, args);
    }

}
