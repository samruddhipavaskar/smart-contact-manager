package com.scm.config;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.plugins.EC2Plugin;
import com.amazonaws.xray.jakarta.servlet.AWSXRayServletFilter;

import jakarta.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class XRayConfig {

    static {
        AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard()
            .withPlugin(new EC2Plugin())
            .withDefaultPlugins();
        AWSXRay.setGlobalRecorder(builder.build());
    }

    @Bean
    public Filter xrayFilter() {
        return new AWSXRayServletFilter("SCM2-App");
    }
}


// package com.scm.config;

// import com.amazonaws.xray.AWSXRay;
// import com.amazonaws.xray.AWSXRayRecorderBuilder;
// import com.amazonaws.xray.plugins.EC2Plugin;
// import com.amazonaws.xray.spring.aop.XRayEnabled;
// import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.EnableAspectJAutoProxy;
// import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
// import jakarta.servlet.Filter;

// @Configuration
// @EnableAspectJAutoProxy
// public class XRayConfig {

//     static {
//         AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard()
//             .withPlugin(new EC2Plugin())  // auto-adds EC2 metadata to traces
//             .withDefaultPlugins();
//         AWSXRay.setGlobalRecorder(builder.build());
//     }

//     @Bean
//     public Filter xrayFilter() {
//         // 'SCM2-App' = service name shown in X-Ray console
//         return new AWSXRayServletFilter("SCM2-App");
//     }
// }
