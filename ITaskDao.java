package com.centum.dao;

import java.sql.SQLException;
import java.util.List;

import com.centum.model.Task;

public interface ITaskDao {
	
	//insert task into db table(tasks)
	void insertTask(Task taskObj)throws SQLException;
	
	//list a sp. tasks on the basis of a TaskId
	Task selectTask(long taskId);
	
	//Listing all tasks
	List<Task> selectAllTasks();
	
	//delete task
	boolean deleteTask(int id)throws SQLException;
	
	//update task
	boolean updateTask(Task taskObj)throws SQLException;
}
