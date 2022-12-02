package com.misha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.misha.fileupload.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class ReactJsGoogleMapWebRestApi3Application {

	public static void main(String[] args) {
		SpringApplication.run(ReactJsGoogleMapWebRestApi3Application.class, args);
	}

}
