package br.ufac.edgeneoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EdgeneoapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeneoapiApplication.class, args);
		System.out.println("hello world");
	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
