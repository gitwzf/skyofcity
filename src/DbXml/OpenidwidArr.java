package DbXml;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
@Root
public class OpenidwidArr implements ObjectArrInf {
	@ElementList
	private ArrayList<Openid_wid> openidwidList;

	public OpenidwidArr(ArrayList<Openid_wid> openidwidList) {
		super();
		this.openidwidList = openidwidList;
	}

	public OpenidwidArr() {
		super();
	}

	public ArrayList<Openid_wid> getOpenidwidList() {
		return openidwidList;
	}

	public void setOpenidwidList(ArrayList<Openid_wid> openidwidList) {
		this.openidwidList = openidwidList;
	}
	
	public ArrayList<Openid_wid> getList() {
		return openidwidList;
	}
	
}
