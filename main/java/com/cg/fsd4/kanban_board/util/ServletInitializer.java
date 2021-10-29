package com.cg.fsd4.kanban_board.util;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.cg.fsd4.kanban_board.KanbanBoardManagementApplication;

public class ServletInitializer extends SpringBootServletInitializer {
	  @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    
	    return application.sources(KanbanBoardManagementApplication.class);
	  }
	}