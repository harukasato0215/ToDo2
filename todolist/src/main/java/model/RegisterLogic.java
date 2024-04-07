package model;

import java.util.List;

public class RegisterLogic {
	public void execute(Todo todo, List<Todo> todoList) {
		todoList.add(0, todo);
	}
}
