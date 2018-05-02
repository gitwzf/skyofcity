package urlapi;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class Dbapi extends HttpServlet{
	Logger log = Logger.getLogger("logfile");
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Dbco db=new Dbco("cs_dk", "3306", "touclick", "touclick123","10.200.114.191");
		String str="";
         try {
			 str=db.getTnewsIdTitle();
		} catch (SQLException e) {
			
			log.error(e);
		}
		System.out.println("str="+str);
		resp.setCharacterEncoding("utf-8");
		Writer out=resp.getWriter();
		out.write(str);
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doPost(req, resp);
	}
		
}
