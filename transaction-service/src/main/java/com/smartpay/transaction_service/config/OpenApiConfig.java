package com.smartpay.transaction_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI smartPayOpenApi() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("SmartPay Transaction Service API")
                                .version("1.0")
                                .description("Transaction management APIs for SmartPay Banking Platform")
                                .contact(
                                        new Contact()
                                                .name("Anubhav Ranjan")
                                )
                );
    }
}