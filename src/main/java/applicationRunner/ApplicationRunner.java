package applicationRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"applicationRunner","controllers"})
@EntityScan(basePackages = {"entities"})
@EnableJpaRepositories(basePackages = {"repositories"})
public class ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationRunner.class, args);
	}

}
