package HackatonWaki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "HackatonWaki.repositorys")
public class HackatonWakiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackatonWakiApplication.class, args);
	}

}
