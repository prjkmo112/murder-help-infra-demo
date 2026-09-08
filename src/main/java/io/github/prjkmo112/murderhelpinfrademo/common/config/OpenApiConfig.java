package io.github.prjkmo112.murderhelpinfrademo.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("murder-help-infra-demo API")
                        .description("Item/File CRUD 데모 API")
                        .version("v1"));
    }
}