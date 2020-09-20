package com.pierre.vendasonline;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VendasOnlineApplication implements CommandLineRunner {
	
	//@Autowired
	//private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(VendasOnlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//s3Service.uploadFile("/home/pierre/Documents/meus-projetos/Backend/java/vendas/imagens/avatar-blank.png");
	}	
	
}
