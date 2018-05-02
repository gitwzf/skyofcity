package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import pubvari.Variable;

import com.oreilly.servlet.MultipartRequest;
import com.sina.sae.storage.SaeStorage;
import com.sina.sae.util.SaeUserInfo;


public class Upload2 extends HttpServlet{
    Logger log=Logger.getLogger("logfile");
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Variable vari=new Variable();
		String realPath = SaeUserInfo.getSaeTmpPath() + "/";
		MultipartRequest mr=new MultipartRequest(req,realPath,10485760);
		File str = mr.getFile("attachFile");
		String subfile=new SimpleDateFormat("yyyyMM").format(new Date());
		String time=new Date().getTime()+"";
		Writer out = resp.getWriter();
		if(!saveVideo(realPath,str,subfile,time)){
			out.write("upload fail");
			out.flush();
			out.close();
		}
	   out.write("<html><body bgcolor='green'><form name='news'><input type='text' name='_picture' id='apath' value='"+
			   vari.source_video_url+subfile+"/"+time+".mp3'/></form></body></html>");
       out.flush();
		out.close();
	}
	
	public boolean saveVideo(String realPath,File file,String subfile,String time){
		 
		//下载图片到storage
		try{
		   OutputStream os = new FileOutputStream(new File(realPath,time+".mp3"));
		   InputStream is =new FileInputStream(file);
		   byte[] buff = new byte[1024*1024*2],b=new byte[1024];
		   int readed=0,ind=0;
		   while((readed=is.read(b)) != -1) {
		    System.arraycopy(b, 0,buff,ind,  readed);
		    ind+=readed;
		   }
		   os.write(buff,0,ind);
		   is.close();
		   os.close();
			SaeStorage saesto=new SaeStorage();
			saesto.upload("video",realPath+time+".mp3",subfile+"/"+time+".mp3");//不替换会出错
			return true;
			}catch(Exception e){
			log.error(e);
		}
	return false;
}
    
}
