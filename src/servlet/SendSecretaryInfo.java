package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.WxUser;

import pubvari.Variable;

import wxtry.Allapi;
import wxtry.Dbcon;

import com.wzf.mail.MailSender;

public class SendSecretaryInfo extends HttpServlet {
	public Variable vari=new Variable();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String work=req.getParameter("name");
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"��ʱС��������"+work);
		
		String strRe;
		try {
			Dbcon db=new Dbcon();
		 	List<WxUser> userList=db.getUsers();
			for(WxUser user : userList){
			strRe = new Allapi().getAllInfo(user.getFakeId());
		//���ͷǿ��ʼ�
			if(strRe!=null && !strRe.isEmpty()){
		new MailSender("smtp."+vari.email_name.split("@")[1], "25", vari.email_name, vari.email_pass, user.getEmail(),
				"Information from City of Sky", strRe, false);
			}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"��ʱС��������");
		
		String strRe;
		try {
			Dbcon db=new Dbcon();
		 	List<WxUser> userList=db.getUsers();
		 	System.out.println("�û�����"+userList.size());
			for(WxUser user : userList){
				System.out.println("���䣺"+user.getEmail());
			strRe = new Allapi().getAllInfo(user.getFakeId());
		//���ͷǿ��ʼ�
			if(strRe!=null && !strRe.isEmpty()){
		new MailSender("smtp."+Variable.email_name.split("@")[1], "25", Variable.email_name, Variable.email_pass, user.getEmail(),
				"Information from City of Sky", strRe, false);
			}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	

}
