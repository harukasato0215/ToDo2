package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TodoDAO;
import model.Todo;
import model.User;

/**
 * Servlet implementation class TodoDelete
 */
@WebServlet("/TodoDelete")
public class TodoDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TodoDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//タスクの内容を取得
		String title =request.getParameter("title");
		
		//データベース
		TodoDAO todoDAO = new TodoDAO();
		
		//ログインしている人のインスタンス
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		//todoList取得
		ServletContext application = this.getServletContext();
		List<Todo> todoList =(List<Todo>)application.getAttribute("todoList");
		
		
		//task内容と合っているリストナンバーを取得
		int taskNum = 0;
		for(int i = 0;i < todoList.size();i++) {
			if(todoList.get(i).getTitle().equals(title)) {
				taskNum = i;
			}
		}
		
		
		//削除
		todoList.remove(taskNum);
		todoDAO.deleteOne(title, timeLimit,user.getName());
		
		//登録
		application.setAttribute("todoList", todoList);
		
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todoMain.jsp");
		dispatcher.forward(request, response);
	}

}
