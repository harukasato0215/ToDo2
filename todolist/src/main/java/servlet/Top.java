package servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import dao.UserDAO;
import model.Todo;
import model.User;

/**
 * Servlet implementation class Top
 */
@WebServlet("/Top")
public class Top extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Top() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/top.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//インスタンス作成
		User user = new User();

		//入力情報の取得
		request.setCharacterEncoding("UTF-8");
		String loginName = request.getParameter("loginName");

		//入力した情報をインスタンスに入れる
		user.setName(loginName);
		//↓でる
		System.out.println("テスト" + user.getName());

		//問題　　データベースの中に入っているか確認
		UserDAO userDAO = new UserDAO();
		user = userDAO.login(user.getName());
		
		//出ない
		//System.out.println("テスト１" + user.getName());
		ServletContext application = this.getServletContext();

		//出ない↓
		//System.out.println("テスト2" + user.getName());
		
		HttpSession session = request.getSession();

		
		//もしいた場合
		if (user != null) {
			//アプリケーションスコープに保存
			session.setAttribute("user", user);

			//リストを取得,データベースから名前と一致するTODOリストを探す
			TodoDAO todoDAO = new TodoDAO();
			todoDAO.findAll(user.getName());

			List<Todo> todoList = (List<Todo>) application.getAttribute("todoList");
			
			//登録してるけど、リストを取得できなかった場合
			if (todoList == null) {
				todoList = new ArrayList<>();
				application.setAttribute("todoList", todoList);
			}
			
		} else {
			//いない場合
			user = new User();
			user.setName(loginName);
			//新規登録する
			userDAO.insertOne(user);
			session = request.getSession();
			session.setAttribute("user", user);
	
			//リストも新しく作成する
			List<Todo> todoList = new ArrayList<>();
			application.setAttribute("todoList", todoList);
		}

			//テスト１
			System.out.println("テスト3" + user.getName());

			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/todoMain.jsp");
			rd.forward(request, response);
		

	}	//doPost END

}	//class END