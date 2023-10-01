package com.centum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.centum.model.Task;
import com.centum.utils.JDBCUtils;

public class TaskDaoImpl implements ITaskDao {
	private static final String INSERT_TASKS_SQL = "INSERT INTO tasks" +
	        "  (title, username, description, target_date,  is_done) VALUES " + " (?, ?, ?, ?, ?);";

	    private static final String SELECT_TASK_BY_ID = "select id,title,username,description,target_date,is_done from tasks where id =?";
	    private static final String SELECT_ALL_TASKS = "select * from tasks";
	    private static final String DELETE_TASK_BY_ID = "delete from tasks where id = ?;";
	    private static final String UPDATE_TASK = "update tasks set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ?;";

	    
	public TaskDaoImpl() {
			
		}

	@Override
	public void insertTask(Task taskObj) throws SQLException {
        System.out.println(INSERT_TASKS_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = JDBCUtils.getConnection(); 
        		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASKS_SQL)) {
            preparedStatement.setString(1, taskObj.getTitle());
            preparedStatement.setString(2, taskObj.getUsername());
            preparedStatement.setString(3, taskObj.getDescription());
            preparedStatement.setDate(4, JDBCUtils.getSQLDate(taskObj.getTargetDate()));
            preparedStatement.setBoolean(5, taskObj.isStatus());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

		
	}

	@Override
	public Task selectTask(long taskId) {
        Task taskObj = null;
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID);) {
            preparedStatement.setLong(1, taskId);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String description = rs.getString("description");
                LocalDate targetDate = rs.getDate("target_date").toLocalDate();
                boolean isDone = rs.getBoolean("is_done");
                taskObj = new Task(id, title, username, description, targetDate, isDone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskObj;

	}

	@Override
	public List<Task> selectAllTasks() {
        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Task> taskObj = new ArrayList<>();

        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASKS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String description = rs.getString("description");
                LocalDate targetDate = rs.getDate("target_date").toLocalDate();
                boolean isDone = rs.getBoolean("is_done");
                taskObj.add(new Task(id, title, username, description, targetDate, isDone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskObj;

	}

	@Override
	public boolean deleteTask(int id) throws SQLException {
		boolean rowDeleted;
        try (Connection connection = JDBCUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_TASK_BY_ID);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;

	}

	@Override
	public boolean updateTask(Task taskObj) throws SQLException {
		boolean rowUpdated;
        try (Connection connection = JDBCUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_TASK);) {
            statement.setString(1, taskObj.getTitle());
            statement.setString(2, taskObj.getUsername());
            statement.setString(3, taskObj.getDescription());
            statement.setDate(4, JDBCUtils.getSQLDate(taskObj.getTargetDate()));
            statement.setBoolean(5, taskObj.isStatus());
            statement.setLong(6, taskObj.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;

	}

}
