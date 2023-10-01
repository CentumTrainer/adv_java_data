package com.centum.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centum.dao.ITaskDao;
import com.centum.dao.TaskDaoImpl;
import com.centum.model.Task;

/**
 * Servlet implementation class TaskController
 */
@WebServlet("/")
public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ITaskDao itaskDAO;
  
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		itaskDAO = new TaskDaoImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                	System.out.println("inside insert case...!");
                    insertTask(request, response);
                    break;
                case "/delete":
                    deleteTask(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateTask(request, response);
                    break;
                case "/list":
                    listTask(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void listTask(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, ServletException {
		        List < Task > listTask = itaskDAO.selectAllTasks();
		        request.setAttribute("listTask", listTask);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("tasks/task-list.jsp");
		        dispatcher.forward(request, response);
		    }

		    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        RequestDispatcher dispatcher = request.getRequestDispatcher("tasks/task-form.jsp");
		        dispatcher.forward(request, response);
		    }

		    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, ServletException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        Task existingTask = itaskDAO.selectTask(id);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("tasks/task-form.jsp");
		        request.setAttribute("task", existingTask);
		        dispatcher.forward(request, response);

		    }

		    private void insertTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		        String title = request.getParameter("title");
		        String username = request.getParameter("username");
		        String description = request.getParameter("description");

		        /*DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		        LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"),df);*/

		        boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		        Task newTask = new Task(title, username, description, LocalDate.now(), isDone);
		        itaskDAO.insertTask(newTask);
		        response.sendRedirect("list");
		    }

		    private void updateTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		        long id = Integer.parseInt(request.getParameter("id"));

		        String title = request.getParameter("title");
		        String username = request.getParameter("username");
		        String description = request.getParameter("description");
		        //DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		        LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));

		        boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		        Task updateTask = new Task(id, title, username, description, targetDate, isDone);

		        itaskDAO.updateTask(updateTask);

		        response.sendRedirect("list");
		    }

		    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        itaskDAO.deleteTask(id);
		        response.sendRedirect("list");
		    }


}
