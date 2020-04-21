package cockpit.mvpinfos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("cockpit.cockpitcore.repository")
@EntityScan("cockpit.cockpitcore.domaine.db")
@SpringBootApplication
public class MvpInfosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvpInfosApplication.class, args);
	}

}
