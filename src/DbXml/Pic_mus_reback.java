package DbXml;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root
public class Pic_mus_reback {
	@Element(required=false)
	private String id;
	@Element(required=false)
	private String keyid;
	@Element(required=false)
	private String type;
	@Element(required=false)
	private String title ;
	@Element(required=false)
	private String main ;
	@Element(required=false)
	private String urlo ;
	@Element(required=false)
	private String pty ;
	@Element(required=false)
	private String url ;
	@Element(required=false)
	private String pid;
	public Pic_mus_reback(String id, String keyid, String type, String title,
			String main, String urlo, String pty, String url, String pid) {
		super();
		this.id = id;
		this.keyid = keyid;
		this.type = type;
		this.title = title;
		this.main = main;
		this.urlo = urlo;
		this.pty = pty;
		this.url = url;
		this.pid = pid;
	}
	public Pic_mus_reback() {
		super();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMain() {
		return main;
	}
	public void setMain(String main) {
		this.main = main;
	}
	public String getUrlo() {
		return urlo;
	}
	public void setUrlo(String urlo) {
		this.urlo = urlo;
	}
	public String getPty() {
		return pty;
	}
	public void setPty(String pty) {
		this.pty = pty;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyid() {
		return keyid;
	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
}
