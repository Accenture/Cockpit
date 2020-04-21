package cockpit.jiragateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@EnableJpaRepositories("cockpit.cockpitcore.repository")
@EntityScan("cockpit.cockpitcore.domaine.db")
public class JiraGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiraGatewayApplication.class, args);
	}

}
