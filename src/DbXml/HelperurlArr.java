package DbXml;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
@Root
public class HelperurlArr implements ObjectArrInf{
	@ElementList
	private ArrayList<Helper_url> helperList;

	
	
	public HelperurlArr(ArrayList<Helper_url> helperList) {
		super();
		this.helperList = helperList;
	}



	public ArrayList<Helper_url> getHelperList() {
		return helperList;
	}



	public void setHelperList(ArrayList<Helper_url> helperList) {
		this.helperList = helperList;
	}



	public HelperurlArr() {
		super();
	}
	
	public ArrayList getList() {
		return helperList;
	}

}
