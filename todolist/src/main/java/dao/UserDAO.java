package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.User;

public class UserDAO {
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
	
	//ログイン情報の確認
	public User login(String loginName) {
		User user = null;
		System.out.println("loginName : " + loginName);
		try {
			this.connect();
			stmt = con.prepareStatement("SELECT * FROM users WHERE NAME=? ");
			stmt.setString(1, loginName);
			rs = stmt.executeQuery();

			if(rs.next()) {
				user = new User();
				user.setName(rs.getString("NAME"));	//カラム名を指定
				System.out.println("user.getName() : " + user.getName());
			}
			
			
		}catch(NamingException | SQLException e) {
			System.out.println("間違っています");
		}finally {
			this.disconnect();
		}
		return user;
	}

	//新規登録
	public void insertOne(User user) {
		try {
			this.connect();
			stmt = con.prepareStatement("INSERT INTO users(name) VALUES(?)");
			stmt.setString(1, user.getName());
			stmt.execute();
		} catch (NamingException |SQLException e) {
			System.out.println("接続失敗");
		}finally {
			this.disconnect();
		}
	}

}