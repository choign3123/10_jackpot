package hackathon.jackpot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiTest() {
        return GroupedOpenApi.builder()
                .group("test api") //그룹 이름
                .pathsToMatch("/test/**") //localhost:9090/인자/~~ 로 시작하는 친구들을 다 매칭시긴다는 얘기
                .build();
    }

    @Bean
    public GroupedOpenApi apiUser() {
        return GroupedOpenApi.builder()
                .group("user api")
                .pathsToMatch("/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiPost() {
        return GroupedOpenApi.builder()
                .group("post api")
                .pathsToMatch("/posts/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        // swagger api 명세서에 들어왔을 때 어떻게 보여줄지 커스텀하는 부분.
        return new OpenAPI()
                .info(new Info().title("Gana Test API")
                        .description("test 프로젝트 API 명세서입니다.")
                        .version("v0.0.1"));
    }

}