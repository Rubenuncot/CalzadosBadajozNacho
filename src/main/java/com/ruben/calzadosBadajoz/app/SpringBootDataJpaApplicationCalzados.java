package com.ruben.calzadosBadajoz.app;

import com.ruben.calzadosBadajoz.app.models.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SpringBootDataJpaApplicationCalzados implements CommandLineRunner {
	@Autowired
	IUploadFileService uploadFileService;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplicationCalzados.class, args);
	}
	@Override
	public void run(String... args) throws IOException {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}
}
