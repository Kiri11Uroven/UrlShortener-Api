package com.example.urlShortener.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
        info = @Info(
                title = "Сервис сокращения ссылок",
                description = "Loyalty System", version = "1.0.0",
                contact = @Contact(
                        name = "Kirill Сhurilov",
                        email = "ila5264789@narod.ru"
                )
        )
)
public class OpenApiConfig {


}
