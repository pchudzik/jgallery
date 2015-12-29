package com.pchudzik.jgallery.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by pawel on 28.11.15.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("full api")
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.any())
				.build();
	}
}
