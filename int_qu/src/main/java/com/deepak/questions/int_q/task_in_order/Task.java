package com.deepak.questions.int_q.task_in_order;

public class Task {

	private final String taskToDo;

	public Task(String taskToDo) {
		super();
		this.taskToDo = taskToDo;
	}

	public String getTaskToDo() {
		return taskToDo;
	}
	
	public void performTask(){
		System.out.format("%nPeforming %s%n", taskToDo);
	}
}
