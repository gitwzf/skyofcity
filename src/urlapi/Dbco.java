package urlapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbco {
	private String database;
	private String port;
	private String username;
	private String password;
	private String ip="localhost";
	private Connection conn;
	private Statement stat;

	public Dbco(String database, String port, String username,
			String password) {
	this.database = database;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public Dbco(String database, String port, String username,
			String password,String ip) {
		this.database = database;
		this.port = port;
		this.username = username;
		this.password = password;
		this.ip=ip;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}

	private void connection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+database, username, password);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}

	private void statment() {
		connection();
		try {
			stat = conn.createStatement();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public String getTnewsIdTitle() throws SQLException{
		statment();
		String str="";
		String sql="select * from tnews order by id desc limit 0,9";
		ResultSet re=stat.executeQuery(sql);
		while(re.next())
			str+=re.getInt("id")+"  "+re.getString("title")+";";
		return str;
	}
	

}
