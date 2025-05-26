package com.service.account_manage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.core.constants.ApiConstants;
import com.service.core.vo.response.CmnResponseVo;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@SpringBootApplication(scanBasePackages = "com.service")
@EnableJpaAuditing
@Slf4j
public class AccountManageApplication  implements ApplicationListener<ApplicationEvent> {

    private Integer currentPort;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(60))
            .build();

    public static void main(String[] args) {
        SpringApplication.run(AccountManageApplication.class, args);
    }


    @PreDestroy
    public void onApplicationShutdown() {
        if(currentPort != null) {
            try {
                // 서버 목록에서 현재 서버 제거 요청
                HttpRequest deleteServerRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:4000/api/delete-server"))
                        .header("Content-Type", "application/json")
                        .header("Service-Name", ApiConstants.API_ACCOUNT_MANAGE)
                        .header("Service-Url", "http://localhost:" + currentPort + ApiConstants.API_ACCOUNT_MANAGE)
                        .DELETE()
                        .build();

                HttpResponse<String> response = httpClient.send(deleteServerRequest, HttpResponse.BodyHandlers.ofString());
                log.info("responseCode : {}", response.statusCode());

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {  // 이벤트 타입 체크 추가
            try {
                ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
                int port = ((ServletWebServerApplicationContext) applicationContext).getWebServer().getPort();
                currentPort = port;

                HttpRequest setServerRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:4000/api/set-server"))
                        .header("Content-Type", "application/json")
                        .header("Service-Name", ApiConstants.API_ACCOUNT_MANAGE)
                        .header("Service-Url", "http://localhost:" + port + ApiConstants.API_ACCOUNT_MANAGE)
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();
                httpClient.send(setServerRequest, HttpResponse.BodyHandlers.ofString());

                HttpRequest getServerRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:4000/api/get-server"))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(getServerRequest, HttpResponse.BodyHandlers.ofString());
                log.info("httpCode = {}", response.statusCode());
                log.info("response = {}", response);
                if(response.body()!=null && !response.body().isEmpty()){
                    ObjectMapper objectMapper = new ObjectMapper();
                    CmnResponseVo cmnResponseVo = objectMapper.readValue(response.body(), CmnResponseVo.class);
                    log.info("serverList : {}", cmnResponseVo.getCommonResultMap());
                }

            } catch (Exception e) {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000); // 로그가 모두 출력될 수 있도록 잠시 대기
                        System.exit(1);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            }
        }
    }

}
