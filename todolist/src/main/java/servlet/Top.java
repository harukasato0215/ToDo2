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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/top.jsp");
	rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//インスタンス作成
		User user = new User();
		
		//入力情報の取得
		String loginName = request.getParameter("loginName");
		
		//入力した情報をインスタンスに入れる
		user.setName(loginName);
		
		//アプリケーションスコープに保存
		ServletContext application  =this.getServletContext();
		application.setAttribute("user", user);
		
		//リストを作成
		HttpSession session = request.getSession();
		List<Todo> todoList = (List<Todo>)application.getAttribute("todoList");
		
		//リストを取得できなかった場合
		if(todoList == null) {
		   todoList = new ArrayList<>();
			session.setAttribute("todoList", todoList);
		}
		
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/todoMain.jsp");
		rd.forward(request, response);
	}

}
