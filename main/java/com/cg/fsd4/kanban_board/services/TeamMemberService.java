package com.cg.fsd4.kanban_board.services;

import java.util.List;

import com.cg.fsd4.kanban_board.entity.ProjectEntity;
import com.cg.fsd4.kanban_board.entity.TeamLeadEntity;
import com.cg.fsd4.kanban_board.entity.TeamMemberEntity;

public interface TeamMemberService {

	public TeamMemberEntity viewWorkStatus(Integer teamMemberId, Integer workStatus);

	public ProjectEntity getProjectDetails(Integer projectId);
	
	public List<ProjectEntity> getUser();
	
	public List<TeamLeadEntity> getTeamLead();
	
	public List<TeamMemberEntity> getTeamMembers();
}
