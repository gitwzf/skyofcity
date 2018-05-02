package servlet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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


public class Upload extends HttpServlet{
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
		MultipartRequest mr=new MultipartRequest(req, realPath,1048576);
		File str =mr.getFile("imgFile");
		Upload.scale(str);
		String subfile=new SimpleDateFormat("yyyyMM").format(new Date());
		String time=new Date().getTime()+"";
		Writer out = resp.getWriter();
		if(!savePic(realPath,str,subfile,time)){
			out.write("upload fail");
			out.flush();
			out.close();
		}
		
       out.write("<html><body  marginheight='0px' marginwidth='0px'><img height='50px' width='90px' src='"+
    		   vari.source_pic_url+"image/"+subfile+"/"+time+".jpg'/></body></html>");
       out.flush();
		out.close();
	}
	
	public boolean savePic(String realPath,File file,String subfile,String time){
		 
			//下载图片到storage
			try{
			   OutputStream os = new FileOutputStream(new File(realPath,time+".jpg"));
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
				saesto.upload("image",realPath+time+".jpg",subfile+"/"+time+".jpg");//不替换会出错
				return true;
				}catch(Exception e){
				log.error(e);
			}
		return false;
	}
	
	public final static void scale(File file) {
        try {
            BufferedImage src = ImageIO.read(file); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
           // 缩小
                width = 360;
                height = 200;
           
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG",file);// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
