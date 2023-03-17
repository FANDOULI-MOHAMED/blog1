package com.example.blog1;

import com.example.blog1.controllers.NewsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Blog1Application {

	public static void main(String[] args) {
		new File(NewsController.uploadDirectory).mkdir();
		SpringApplication.run(Blog1Application.class, args);
	}

}
