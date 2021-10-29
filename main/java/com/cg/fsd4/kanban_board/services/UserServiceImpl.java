package com.cg.fsd4.kanban_board.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.fsd4.kanban_board.entity.ProjectEntity;
import com.cg.fsd4.kanban_board.repo.ProjectRepo;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private ProjectRepo projectRepo;

	@Override
	public ProjectEntity addProject(ProjectEntity projectEntity) {
		// TODO Auto-generated method stub
		
		return projectRepo.save(projectEntity);
	}

	@Override
	public List<ProjectEntity> getProjectDetails() {
		// TODO Auto-generated method stub
		return projectRepo.findAll();
	}

	@Override
	public ProjectEntity deleteProject(Integer projectId) {
		// TODO Auto-generated method stub
		projectRepo.deleteById(projectId);
		return null;
	}
	
	@Override
	public ProjectEntity getProject(int projectId) {
		// TODO Auto-generated method stub
		return projectRepo.findById(projectId).get();
	}


	
	
}
	
