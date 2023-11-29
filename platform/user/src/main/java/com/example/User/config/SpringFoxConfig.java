package com.example.User.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2) //
                .securityContexts(asList(securityContext())) //
                .securitySchemes(asList(apiKey())) //
                .select() //
                .apis(RequestHandlerSelectors.any()) //
                .paths(PathSelectors.any()).build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return asList(new SecurityReference("JWT", authorizationScopes));
    }
}