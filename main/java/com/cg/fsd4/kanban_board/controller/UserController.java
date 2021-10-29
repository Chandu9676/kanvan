package com.cg.fsd4.kanban_board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.fsd4.kanban_board.entity.ProjectEntity;
import com.cg.fsd4.kanban_board.entity.UserEntity;
import com.cg.fsd4.kanban_board.exception.ResourceNotFoundException;
import com.cg.fsd4.kanban_board.repo.UserRepo;
import com.cg.fsd4.kanban_board.response.KanbanBoardResponse;
import com.cg.fsd4.kanban_board.services.UserService;

@RestController
@RequestMapping("/api/kanban_board")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserRepo userRepository;
	

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<UserEntity> getAllUser() {
		return userRepository.findAll();
	}

	@PostMapping("/users")
	public void createUser(@RequestBody UserEntity user) {
		userRepository.save(user);

	}

	@GetMapping("/getuser")
	public KanbanBoardResponse getUserById(int userId) throws ResourceNotFoundException {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		KanbanBoardResponse boardResponse = new KanbanBoardResponse();
		boardResponse.setUserEntity(user);
		return boardResponse;
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable int id, @RequestBody UserEntity userDetails)
			throws ResourceNotFoundException {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		user.setUserName(userDetails.getUserName());
		user.setEmailId(userDetails.getEmailId());

		user.setPassword(userDetails.getPassword());

		user.setOrganisation(userDetails.getOrganisation());

		UserEntity updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);

	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable int id) throws ResourceNotFoundException {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@PostMapping(path = "/addProject", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse addProject(@RequestBody ProjectEntity projectEntity) {

		ProjectEntity project = userService.addProject(projectEntity);
		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();

		if (project != null) {
			kanbanBoardResponse.setMessage("Project Added");
		}
		return kanbanBoardResponse;
	}

	@GetMapping(path = "/getprojectdetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse getProjectDetails(int userId) {

		List<ProjectEntity> project = userService.getProjectDetails();
		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();
		List<ProjectEntity> list = new ArrayList<ProjectEntity>();
		try {
			for (ProjectEntity onebyone : project) {
				if (onebyone.getUserEntity().getUserId() == userId) {
					list.add(onebyone);
				}
			}
		} catch (Exception e) {
			kanbanBoardResponse.setMessage("No projects found");
			return kanbanBoardResponse;
		}
		kanbanBoardResponse.setProjectList(list);
		return kanbanBoardResponse;
	}

	@DeleteMapping(path = "/deleteproject", produces = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse deleteProject(int projectId) {

		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();
		try {
			userService.deleteProject(projectId);

		} catch (Exception e) {
			kanbanBoardResponse.setMessage(e.getMessage());
		}
		return kanbanBoardResponse;
	}
	

	@GetMapping(path = "/getproject", produces = MediaType.APPLICATION_JSON_VALUE)
	public KanbanBoardResponse getProject(int projectId) {

		ProjectEntity project = userService.getProject(projectId);
		KanbanBoardResponse kanbanBoardResponse = new KanbanBoardResponse();
		kanbanBoardResponse.setProjectEntity(project);
		return kanbanBoardResponse;
	}
}


//	
