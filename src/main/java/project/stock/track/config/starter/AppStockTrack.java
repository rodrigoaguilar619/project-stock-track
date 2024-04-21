package project.stock.track.config.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import lib.base.backend.modules.annotations.CatalogConfiguration;
import lib.base.backend.modules.annotations.JwtConfiguration;
import lib.base.backend.modules.annotations.WebConfiguration;

@EnableJpaRepositories("${app.config.jpa.repositories}")
@EntityScan("${app.config.jpa.entity.scan}")
@ComponentScan(basePackages = "${app.config.component.scan}")
@EnableScheduling
@WebConfiguration
@JwtConfiguration
@CatalogConfiguration
@SpringBootApplication
public class AppStockTrack {

	public static void main(String[] args) {
		SpringApplication.run(AppStockTrack.class, args);
	}

}
