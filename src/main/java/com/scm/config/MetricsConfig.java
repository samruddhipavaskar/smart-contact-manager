package com.scm.config;

import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import java.time.Duration;

@Configuration
public class MetricsConfig {

    @Bean
    public CloudWatchAsyncClient cloudWatchAsyncClient() {
        return CloudWatchAsyncClient.builder()
            .region(Region.AP_SOUTH_1)
            .build();
    }

    @Bean
    public MeterRegistry meterRegistry(CloudWatchAsyncClient cwClient) {
        CloudWatchConfig config = new CloudWatchConfig() {
            public String get(String key) { return null; }
            public String namespace() { return "SCM2/Application"; }
            public Duration step() { return Duration.ofMinutes(1); }
        };
        return new CloudWatchMeterRegistry(config, Clock.SYSTEM, cwClient); 
    }
}
