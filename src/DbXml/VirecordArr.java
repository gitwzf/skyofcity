package DbXml;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
@Root
public class VirecordArr implements ObjectArrInf {
	@ElementList
	private ArrayList<Virecord> virecordList;

	public VirecordArr(ArrayList<Virecord> virecordList) {
		super();
		this.virecordList = virecordList;
	}

	public VirecordArr() {
		super();
	}

	public ArrayList<Virecord> getVirecordList() {
		return virecordList;
	}

	public void setVirecordList(ArrayList<Virecord> virecordList) {
		this.virecordList = virecordList;
	}
	
	public ArrayList<Virecord> getList() {
		return virecordList;
	}
	
}
