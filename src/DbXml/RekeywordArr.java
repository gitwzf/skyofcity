package DbXml;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
@Root
public class RekeywordArr  implements ObjectArrInf{
	@ElementList
	private ArrayList<Re_keyword> rekeywordList;

	public RekeywordArr(ArrayList<Re_keyword> rekeywordList) {
		super();
		this.rekeywordList = rekeywordList;
	}

	public RekeywordArr() {
		super();
	}

	public ArrayList<Re_keyword> getRekeywordList() {
		return rekeywordList;
	}

	public void setRekeywordList(ArrayList<Re_keyword> rekeywordList) {
		this.rekeywordList = rekeywordList;
	}
	
	public ArrayList<Re_keyword> getList() {
		return rekeywordList;
	}

}
