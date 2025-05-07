package app.mael.tekbetter.controller;

import app.mael.tekbetter.middleware.ApiKeyInterceptor;
import app.mael.tekbetter.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelloService helloService;

    @MockBean
    private ApiKeyInterceptor apiKeyInterceptor;

    @Configuration
    static class TestConfig implements WebMvcConfigurer {
        @Autowired
        private ApiKeyInterceptor apiKeyInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(apiKeyInterceptor);
        }
    }

    @Test
    void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/api/hello")
                .header("Authorization", "Bearer test-api-key"))
            .andExpect(status().isAccepted()); // 202
    }

    @Test
    void testHelloEndpointWithInvalidBearerFormat() throws Exception {
        mockMvc.perform(get("/api/hello")
                .header("Authorization", "Invalid test-api-key"))
            .andExpect(status().isUnauthorized()); // 401
    }
}