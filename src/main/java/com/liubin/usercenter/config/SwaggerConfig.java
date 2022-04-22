package com.liubin.usercenter.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
@Configuration
@EnableSwagger2
public class SwaggerConfig {
        @Bean
        public Docket docket(){
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .groupName("Control")
                    .select().paths(PathSelectors.ant("/user/**")).build();
        }
        //配置Swagger信息 =apiinfo
        private ApiInfo apiInfo(){
            //作者信息
            Contact DEFAULT_CONTACT = new Contact("刘斌", "http://101.132.174.210:8090/", "1741642120@QQ.com");
            return new ApiInfo(
                    "Api liubinAPI",
                    "去发光 而不是被照亮",
                    "v1.0",
                    "https://swagger.io/docs/open-source-tools/swagger-editor/",
                    DEFAULT_CONTACT, "Apache 2.0",
                    "http://www.apache.org/licenses/LICENSE-2.0",
                    new ArrayList()
            );
        }


}
