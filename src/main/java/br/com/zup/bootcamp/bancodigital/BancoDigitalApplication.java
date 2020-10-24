package br.com.zup.bootcamp.bancodigital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class BancoDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancoDigitalApplication.class, args);
	}

}
