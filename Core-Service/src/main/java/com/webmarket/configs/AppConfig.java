package com.webmarket.configs;

import com.webmarket.properties.CartServiceIntegrationProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

/**
 * Configuration class application settings, properties for configurations
 * are pulled from the configuration file using @EnableConfigurationProperties.
 */
@Configuration
@EnableConfigurationProperties(
        CartServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class AppConfig {

    /**
     * Is an object linking its properties in with similar properties in the project properties file.
     */
    private final CartServiceIntegrationProperties cartServiceIntegrationProperties;

    /**
     * Client rest template.
     * @return RestTemplate
     */
    @Bean
    public RestTemplate userServiceClient(){
        return new RestTemplate();
    }

    /**
     * Web client for requests to the shopping cart service.
     * @return WebClient
     */
    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, cartServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(cartServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(cartServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(cartServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
