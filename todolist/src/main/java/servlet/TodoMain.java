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
import model.RegisterLogic;
import model.Todo;
import model.User;

/**
 * Servlet implementation class todoMain
 */
@WebServlet("/TodoMain")
public class TodoMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TodoMain() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/todoMain.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リストを取得
		ServletContext application = this.getServletContext();
		List<Todo> todoList = (List<Todo>) application.getAttribute("todoList");
		
		//ログインしている人のインスタンス
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		//リストを取得,データベースから名前と一致するTODOリストを探す
		TodoDAO todoDAO = new TodoDAO();
		todoDAO.findAll(user.getName());
		

		//入力内容取得
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		String timeLimit = request.getParameter("timeLimit");
		//インスタンスを作成
		Todo todo = new Todo(title, timeLimit);

		//登録
		RegisterLogic registerTodo = new RegisterLogic();
		registerTodo.execute(todo,todoList);
		
		//データベースにも登録
		todoDAO.newTask( title, timeLimit,user.getName());
		
		//アプリケーション保存
		application.setAttribute("todoList", todoList);

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/todoMain.jsp");
		rd.forward(request, response);
	}

}
