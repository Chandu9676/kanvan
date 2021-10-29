package com.cg.fsd4.kanban_board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import com.cg.fsd4.kanban_board.entity.NotificationEntity;
import com.cg.fsd4.kanban_board.entity.TeamLeadEntity;
import com.cg.fsd4.kanban_board.entity.TeamMemberEntity;
import com.cg.fsd4.kanban_board.entity.UserEntity;
import com.cg.fsd4.kanban_board.exception.TeamMemberNotFoundException;
import com.cg.fsd4.kanban_board.response.KanbanBoardResponse;
import com.cg.fsd4.kanban_board.services.ManageTeamService;

@RequestMapping(path = "/api/kanban_board")
@RestController
@CrossOrigin("*")
@ComponentScan(basePackages = { "com.cg.fsd4.kanban_board.controller" })
public class ManageTeamContoller {

	@Autowired
	private ManageTeamService manageTeamService;

	@Autowired
	private JavaMailSender javaMailSender;

	@PostMapping(path = "/mail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean sendMail(@RequestBody NotificationEntity entity) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(entity.getEmailId());
		message.setSubject("Kanban_board_project_management");
		message.setText(
				"Hai, " + entity.getName()
				+ "\nNow you are tagged to project, project name is : "+ entity.getProjectName()+"." +"\nBellow are the credentials to login." +
						"\n1) Your Employee id : " + entity.getEmpId() + "\n2) Project password : " + entity.getProjectPassword() +"\n"
								+ entity.getBody() +"\nThank you");
		try {
			javaMailSender.send(message);
		} catch (Exception e) {
		System.err.println("server not responding plz restart the Tomcat server");
			return false;
		}

		return true;
	}


	@PostMapping(path = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse createUserAccount(@RequestBody UserEntity userEntity) {
		UserEntity user = manageTeamService.createUserAccount(userEntity);
		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();
		if (user == null) {
			kanbanBoardResponse.setMessage("User Account not created");
		} else {
			kanbanBoardResponse.setMessage("User Account created succesfully");
		}
		return kanbanBoardResponse;
	}



	@PostMapping(path = "/addteamlead", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse addTeamLead(@RequestBody TeamLeadEntity teamLeadEntity) {
		List<TeamLeadEntity> teamLeadList = manageTeamService.getTeamLead();
		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();
		TeamLeadEntity teamLead = manageTeamService.addTeamLead(teamLeadEntity);
		if (teamLead != null)
			kanbanBoardResponse.setMessage("Team lead details added");
		return kanbanBoardResponse;
	}
	
	@PostMapping(path = "/addteammember", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse addTeamMember(@RequestBody TeamMemberEntity teamMemberEntity) {
		TeamMemberEntity teamLead = manageTeamService.addTeamMember(teamMemberEntity);
		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();
		if (teamLead == null) {
			throw new TeamMemberNotFoundException("Fail to Add");
		} else {
			kanbanBoardResponse.setMessage("Team member added succesfully");
		}
		return kanbanBoardResponse;
	}
	
	
	@DeleteMapping(path = "/deleteteammember", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteTeamMember(int teamMemberId) throws TeamMemberNotFoundException {
		boolean isDeleted = false;
		try {
			TeamMemberEntity teamLead = manageTeamService.deleteTeamMember(teamMemberId);
			isDeleted = manageTeamService.deleteMember(teamLead);
		} catch (Exception e) {
			return false;
		}
		return isDeleted;
	}

	@DeleteMapping(path = "/deleteteamlead", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteTeamLead(int teamLeaderId) {
		boolean isDeleted = false;
		try {
			TeamLeadEntity teamLead = manageTeamService.deleteTeamLead(teamLeaderId);
			isDeleted = manageTeamService.deleteLead(teamLead);
		} catch (Exception e) {
			return false;
		}

		return isDeleted;
	}



	@GetMapping(path = "/getmembers", produces = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse getTeamMembers(int projectId) {

		List<TeamMemberEntity> teamMembers = manageTeamService.getTeamMembers();
		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();
		List<TeamMemberEntity> list = new ArrayList<TeamMemberEntity>();
		try {
			for (TeamMemberEntity onebyone : teamMembers) {
				if (onebyone.getProjectEntity().getProjectId() == projectId) {
					list.add(onebyone);
				}
			}
		} catch (Exception e) {
			kanbanBoardResponse.setMessage(e.getMessage());
		}

		kanbanBoardResponse.setMembersList(list);
		return kanbanBoardResponse;
	}

	@GetMapping(path = "/getlead", produces = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse getTeamLead(int projectId) {
		int id;
		List<TeamLeadEntity> teamLead = manageTeamService.getTeamLead();
		System.out.println(teamLead);
		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();
		List<TeamLeadEntity> lead = new ArrayList<TeamLeadEntity>();

		try {
			for (TeamLeadEntity onebyone : teamLead) {
				id = onebyone.getProjectEntity().getProjectId();
				if (id == projectId) {
					lead.add(onebyone);
				}
			}
		} catch (Exception e) {
			kanbanBoardResponse.setMessage(e.getMessage());
		}
		kanbanBoardResponse.setTeamLeadList(lead);
		return kanbanBoardResponse;

	}

}