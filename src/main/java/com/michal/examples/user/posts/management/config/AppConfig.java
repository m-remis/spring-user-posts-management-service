package com.michal.examples.user.posts.management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;



/**
 * @author Michal Remis
 */
@Configuration
public class AppConfig {

    @Bean
    public RestClient restTemplate(RestTemplateBuilder builder) {
        return RestClient.create(builder.build());
    }

    // to avoid @JsonProperty
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new ParameterNamesModule());
    }

    @Bean
    public OpenAPI myOpenAPI(@Value("${developer.info.name}") final String name,
                             @Value("${developer.info.linked-in}") final String linkedIn,
                             @Value("${application.name}") final String appName,
                             @Value("${application.desc}") final String desc

    ) {

        Contact contact = new Contact();
        contact.setName(name);
        contact.setUrl(linkedIn);

        Info info = new Info()
                .description(desc)
                .version("1.0")
                .title(appName)
                .contact(contact);

        return new OpenAPI().info(info);
    }
}
