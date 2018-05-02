package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import com.commsen.jwebthumb.WebThumbException;
import com.commsen.jwebthumb.WebThumbFetchRequest;
import com.commsen.jwebthumb.WebThumbService;
import com.commsen.jwebthumb.WebThumbFetchRequest.Size;

public class Zoompic extends TimerTask{
	private static File file;
	private static String id;
	private Timer timer;
	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		Zoompic.file = file;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		Zoompic.id = id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		getPicById();
		timer.cancel();
		System.gc();
	}

	/**
	 *ªÒ»°Àı¬‘Õº 
	 */
	public static void getPicById(){//commons.io.2.2.jar
		WebThumbService webThumbService = new WebThumbService("6f6b6c1f0b8e5de839bb4522c22211d6");
	  WebThumbFetchRequest fetchRequest = new WebThumbFetchRequest(id, Size.large);
	        try {
				webThumbService.fetch(fetchRequest, new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (WebThumbException e) {
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		Timer timer=new Timer();
		Zoompic zp=new Zoompic();
		zp.setFile(file);
		zp.setId(id);
		zp.setTimer(timer);
	    timer.schedule(zp, 10*1000);
	}

}
