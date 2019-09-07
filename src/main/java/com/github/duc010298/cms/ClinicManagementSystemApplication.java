package com.github.duc010298.cms;

import com.github.duc010298.cms.configuration.ApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        ApiConfig.class
})
public class ClinicManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicManagementSystemApplication.class, args);
    }

}
