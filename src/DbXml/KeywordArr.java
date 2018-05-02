package DbXml;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
@Root
public class KeywordArr implements ObjectArrInf {
	@ElementList
    private ArrayList<Keyword> keywordList;
    
	public KeywordArr() {
		super();
	}

	public KeywordArr(ArrayList<Keyword> keywordList) {
		super();
		this.keywordList = keywordList;
	}

	public ArrayList<Keyword> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(ArrayList<Keyword> keywordList) {
		this.keywordList = keywordList;
	}
	
	public ArrayList<Keyword> getList() {
		return keywordList;
	}
}
