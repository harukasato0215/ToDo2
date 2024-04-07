package model;

public class Todo {
	private String title;
	private String timeLimit;

	public Todo() {}
	public Todo(String title , String timeLimit) {
		this.title = title;
		this.timeLimit = timeLimit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	
	
}
