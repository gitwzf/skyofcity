package DbXml;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root
public class Keyword {
     @Element(required=false)
     private String id;
     @Element(required=false)
     private String word;
     @Element(required=false)
     private String usersay;
     @Element(required=false)
     private String reback;
	public Keyword(String id, String word, String usersay, String reback) {
		super();
		this.id = id;
		this.word = word;
		this.usersay = usersay;
		this.reback = reback;
	}
	public Keyword() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getUsersay() {
		return usersay;
	}
	public void setUsersay(String usersay) {
		this.usersay = usersay;
	}
	public String getReback() {
		return reback;
	}
	public void setReback(String reback) {
		this.reback = reback;
	}
     
     
}
