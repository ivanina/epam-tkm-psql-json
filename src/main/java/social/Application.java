package social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.social.FacebookAutoConfiguration;
import org.springframework.boot.autoconfigure.social.TwitterAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.social.config.annotation.EnableSocial;


@EnableSocial
@EnableWebSecurity
@EnableJpaRepositories(basePackages = {
        "social.repository.manager",
        "social.entity.domain",
        "social.service.fb"
})
@SpringBootApplication(exclude = {FacebookAutoConfiguration.class, TwitterAutoConfiguration.class})
@EntityScan("social.entity.domain")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args); //NOSONAR
    }
}