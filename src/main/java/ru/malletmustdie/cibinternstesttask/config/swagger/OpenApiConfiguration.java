package ru.malletmustdie.cibinternstesttask.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@OpenAPIDefinition(security = {
        @SecurityRequirement(name = "security_auth")
})
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(
                        authorizationCode = @OAuthFlow(
                                authorizationUrl = "${spring.security.oauth2.resource-server.jwt.issuer-uri}/protocol/openid-connect/auth",
                                tokenUrl = "${spring.security.oauth2.resource-server.jwt.issuer-uri}/protocol/openid-connect/token")
                )
)

@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {

    private static final String INTERNAL_SERVER_ERROR = "{\n"
            + "  \"type\": \"SystemError\",\n"
            + "  \"code\": 500,\n"
            + "  \"message\": \"An unknown error occurred with the API. Please contact an administrator\",\n"
            + "  \"details\": null\n"
            + "}";

    private static final String UNAUTHORIZED = "{\n"
            + "  \"type\": \"Not Authorized\",\n"
            + "  \"code\": 401,\n"
            + "  \"message\": \"You don't have permission to access on this server\",\n"
            + "  \"details\": null\n"
            + "}";

    private static final String NOT_FOUND = "{\n"
            + "  \"type\": \"ResourceNotFound\",\n"
            + "  \"code\": 404,\n"
            + "  \"message\": \"Resource not found\",\n"
            + "  \"details\": [\n"
            + "    {\n"
            + "      \"type\": \"ResourceNotFound\",\n"
            + "      \"code\": 404,\n"
            + "      \"target\": \"ResourceName\",\n"
            + "      \"message\": \"Resource not found\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    @Bean
    public OpenApiCustomiser addDefaultResponses() {
        var serverErrorResponse = getResponse("Internal Server Error", INTERNAL_SERVER_ERROR);
        var notAuthorizedResponse = getResponse("Not Authorized", UNAUTHORIZED);
        var notFoundResponse = getResponse("Not Found", NOT_FOUND);
        return openApi ->
                openApi.getPaths()
                       .values()
                       .forEach(pathItem ->
                                        pathItem.readOperations()
                                                .forEach(operation ->
                                                                 operation.getResponses()
                                                                          .addApiResponse("500", serverErrorResponse)
                                                                          .addApiResponse("401", notAuthorizedResponse)
                                                                          .addApiResponse("404", notFoundResponse)));
    }

    private ApiResponse getResponse(String description, String response) {
        var content = new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().example(response));
        return new ApiResponse()
                .description(description)
                .content(content);
    }

}
