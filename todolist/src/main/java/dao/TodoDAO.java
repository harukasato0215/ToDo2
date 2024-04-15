package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Todo;

public class TodoDAO {
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;

	private void connect() throws NamingException, SQLException {
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/mariadb");
		this.con = ds.getConnection();
	}

	private void disconnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// メイン画面に表示させるよう
	public List<Todo> findAll(String loginName) {
		List<Todo> todoList = new ArrayList<>();
		try {
			this.connect();
			stmt = con.prepareStatement("SELECT * FROM todo WHERE USERNAME=?");
			stmt.setString(1, loginName);
			rs = stmt.executeQuery();
			while (rs.next()) {

				String title = rs.getString("title");
				String timeLimit = rs.getString("timeLimit");
				Todo todo = new Todo(title, timeLimit);
				todoList.add(todo);
			}
		} catch (NamingException | SQLException e) {
			System.out.println("接続失敗");
		} finally {
			this.disconnect();
		}
		return todoList;
	}

	//新規登録
	public void newTask(String title, String timeLimit, String userName) {
		try {
			this.connect();

			stmt = con.prepareStatement(
					"INSERT INTO todo(TITLE,TIMElIMIT,USERNAME) VALUES(?,?,?)");
			stmt.setString(1, title);
			stmt.setString(2, timeLimit);
			stmt.setString(3, userName);

			stmt.execute();

		} catch (NamingException | SQLException e) {
			System.out.println("");
		} finally {
			this.disconnect();
		}
	}
	//削除
	public void deleteOne(String title, String timeLimit, String userName) {
		try {
			this.connect();
			stmt = con.prepareStatement("DELETE FROM todo WHERE title=?title=?,timelimit=?,username=?");
			stmt.setString(1, title);
			stmt.setString(2, timeLimit);
			stmt.setString(3, userName);
			stmt.executeUpdate();
		}catch(NamingException | SQLException e) {
			System.out.println("接続失敗");
		}finally {
			this.disconnect();
		}
	}

}
