package com.method.other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.method.own.RandomString;
/**
 * 验证码图片（火狐浏览器 不支持 点击换图）
 * 使用：
 * 1.图片请求：/Yzm  页面.attr("src","Yzm")
 * 2.“yzm”字符串toLower后放入session，用post获取
 */
public class Yzm extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("image/jpeg");
        ServletOutputStream out = resp.getOutputStream(); 
        req.getSession().setAttribute("yzm",getCertPic(100,30,out).toLowerCase());//放入session
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		out.flush();
		out.close();
	}
	 public String getCertPic(int width, int height, OutputStream os) {
		   if(width<=0)width=60;
		   if(height<=0)height=30; 
		   BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			  
		   // 获取图形上下文 
		   Graphics g = image.getGraphics(); 
		   // 设定背景色 
		   g.setColor(new Color(0xDCDCDC)); 
		   g.fillRect(0, 0, width, height); 
		   //画边框 
//		   g.setColor(Color.black); 
//		   g.drawRect(0,0,width-1,height-1); 
		   // 取随机产生的认证码
		   String strEnsure =new RandomString().getRandomString(4, true, true, true, false, "0OolI1");
		   // 　　将认证码显示到图像中,如果要生成更多位的认证码,增加drawString语句
		   g.setColor(Color.BLUE); 
		   g.setFont(new Font("Atlantic Inline",Font.BOLD,24)); 
		   String str ="";
		   for(int i=0;i<strEnsure.length();i++){
		   str=strEnsure.substring(i,i+1); 
		   
		   g.drawString(str,8+(width-8)/strEnsure.length()*i,22); 
		   }
		   // 随机产生10个干扰点
		   Random rand = new Random();
		   for (int i=0;i<10;i++) { 
		    int x = rand.nextInt(width); 
		    int y = rand.nextInt(height); 
		    g.drawOval(x,y,1,1); 
		   } 
		   // 释放图形上下文
		   g.dispose();   
		   try {
		    // 输出图像到页面 
			 image=twistImage(width,height,image);
		    ImageIO.write(image, "JPEG", os);
		   } catch (IOException e) {
		    return "";
		   }  
		   return strEnsure;
		   }
	 private BufferedImage twistImage(int width, int height,BufferedImage image) {  
		 double dMultValue =10;// 波形的幅度倍数，越大扭曲的程序越高，一般为3  
	        double dPhase =2;// 波形的起始相位，取值区间（0-2＊PI）  
	  
	        BufferedImage destBi = new BufferedImage(100, 30, BufferedImage.TYPE_INT_RGB);
	  
	        for (int i = 0; i < destBi.getWidth(); i++) 
	            for (int j = 0; j < destBi.getHeight(); j++) 
	            	destBi.setRGB(i, j,image.getRGB(1, 1));
	        
	        for (int i = 0; i < destBi.getWidth(); i++) {  
	            for (int j = 0; j < destBi.getHeight(); j++) { 
	                int nOldX = getXPosition4Twist(dPhase, dMultValue,  
	                        destBi.getHeight(), i, j);  
	                int nOldY = j;  
	                if (nOldX >= 0 && nOldX < destBi.getWidth() && nOldY >= 0  
	                        && nOldY < destBi.getHeight()) {  
	                    destBi.setRGB(nOldX, nOldY,image.getRGB(i, j));  
	                }
	            }  
	        }  
	        return destBi;  
	    }
	 private int getXPosition4Twist(double dPhase, double dMultValue,  
	            int height, int xPosition, int yPosition) {  
	        double PI = 3.1415926535897932384626433832799; // 此值越大，扭曲程度越大  
	        double dx = (PI * yPosition) / height + dPhase;  
	        double dy = Math.sin(dx);  
	        return xPosition + (int) (dy * dMultValue);  
	    } 
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter pw=resp.getWriter();
		pw.write((String)req.getSession().getAttribute("yzm"));
		pw.flush();
		pw.close();
	}
	

}
