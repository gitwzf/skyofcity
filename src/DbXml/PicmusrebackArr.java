package DbXml;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
@Root
public class PicmusrebackArr implements ObjectArrInf {
	@ElementList
	private ArrayList<Pic_mus_reback> picmusrebackList;

	public PicmusrebackArr(ArrayList<Pic_mus_reback> picmusrebackList) {
		super();
		this.picmusrebackList = picmusrebackList;
	}

	public PicmusrebackArr() {
		super();
	}

	public ArrayList<Pic_mus_reback> getPicmusrebackList() {
		return picmusrebackList;
	}

	public void setPicmusrebackList(ArrayList<Pic_mus_reback> picmusrebackList) {
		this.picmusrebackList = picmusrebackList;
	}
	
	public ArrayList<Pic_mus_reback> getList() {
		return picmusrebackList;
	}
	
}
