package com.cg.fsd4.kanban_board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.fsd4.kanban_board.entity.Login;
import com.cg.fsd4.kanban_board.entity.UserEntity;

import com.cg.fsd4.kanban_board.exception.ResourceNotFoundException;
import com.cg.fsd4.kanban_board.response.KanbanBoardResponse;
import com.cg.fsd4.kanban_board.services.LoginService;


@RequestMapping("/api/kanban_board")
@RestController
@CrossOrigin("*")
@ComponentScan(basePackages = { "com.cg.fsd4.kanban_board.controller" })
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public KanbanBoardResponse signIn(@RequestBody Login user) {
		UserEntity str = null;
		KanbanBoardResponse baseResponse = new KanbanBoardResponse();
		try {
			str = loginService.signIn(user);
		} catch (ResourceNotFoundException r) {

			baseResponse.setMessage("Login Fail");
			return baseResponse;
		}
		
		if(str == null) { 
			baseResponse.setMessage("Login Fail");
			return baseResponse;
		}else{
			baseResponse.setUserEntity(str);
			return baseResponse;
		}
	

	}
}
