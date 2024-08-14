package com.yubi.testSdk.config;


import com.yubi.yubidrive.sdk.client.YubiDriveClient;
import com.yubi.yubidrive.sdk.client.YubiDriveClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class YubiDriveConfig {
    @Value("${yubidrive.baseUrl}")
    private String baseUrl;

    @Value("${yubidrive.apiKey}")
    private String apiKey;

    @Value("${yubidrive.productKey}")
    private String productKey;

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public YubiDriveClient yubiDriveClient() {
        return YubiDriveClientImpl.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .productKey(productKey)
                .build();
    }

}
