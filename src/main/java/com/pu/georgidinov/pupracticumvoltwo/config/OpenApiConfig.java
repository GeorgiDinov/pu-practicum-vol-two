package com.pu.georgidinov.pupracticumvoltwo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@OpenAPIDefinition
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    private static final String LOGIN_MEDIA_TYPE = "application/json";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .paths(new Paths().addPathItem("/login", this.loginPathItem()))
                .info(applicationInfo());
    }

    private PathItem loginPathItem() {
        return new PathItem().post(this.loginOperation()).description("Login");
    }

    private Operation loginOperation() {
        return new Operation()
                .requestBody(this.loginRequestBody())
                .responses(this.loginResponses())
                .description("Login");
    }


    private RequestBody loginRequestBody() {
        return new RequestBody().content(this.loginContent());
    }

    private Content loginContent() {
        return new Content().addMediaType(LOGIN_MEDIA_TYPE, this.loginMediaType());
    }

    private MediaType loginMediaType() {
        return new MediaType().schema(new ObjectSchema()
                .addProperties("email", new StringSchema())
                .addProperties("password", new StringSchema())
        );
    }

    private ApiResponses loginResponses() {
        ApiResponses apiResponses = new ApiResponses();
        apiResponses.addApiResponse("200", new ApiResponse().headers(new HashMap<>()).description("OK"));
        apiResponses.addApiResponse("403", new ApiResponse().headers(new HashMap<>()).description("Forbidden"));
        return apiResponses;
    }

    private Info applicationInfo() {

        Contact contact = new Contact()
                .name("PROJECT REPOSITORY")
                .url("https://github.com/GeorgiDinov/pu-practicum-vol-two");

        Map<String, Object> licenceExtensions = new HashMap<>();
        licenceExtensions.put("https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

        License license = new License()
                .name("Apache Licence Version 2.0")
                .extensions(licenceExtensions);

        return new Info()
                .title("UNIVERSITY OF PLOVDIV PRACTICALS TASK")
                .description("Building Spring Boot Rest Api Related With University Project")
                .contact(contact)
                .version("1.0.0")
                .license(license);
    }

}