package com.cg.fsd4.kanban_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KanbanBoardManagementApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(KanbanBoardManagementApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(KanbanBoardManagementApplication.class);
	}
	

}
//spring.datasource.url=jdbc:postgresql://springboot-react-database.chtobetpjmtm.us-east-2.rds.amazonaws.com:5432/kanban_board
//spring.datasource.username = postgres
//spring.datasource.password = Chandu9676