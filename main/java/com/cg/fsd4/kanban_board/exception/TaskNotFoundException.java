package com.cg.fsd4.kanban_board.exception;

public class TaskNotFoundException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7532271359466729575L;
	private final String message;

    public TaskNotFoundException() {
        this.message="";
    }

    public TaskNotFoundException(String message) {
        this.message=message;

    }

    @Override
    public String toString() {
        return "Task Was Not Found "+this.message;
    }
}
