package DbXml;
import org.simpleframework.xml.*; 
@Root
public class Helper_url {
	@Element(required=false)
	private String name;
	@Element(required=false)
	private String beg;
	@Element(required=false)
	private String end;
	@Element(required=false)
	private String regex;
	@Element(required=false)
	private String url;
	@Element(required=false)
	private String method;
	@Element(required=false)
	private String tag;
	@Element(required=false)
	private String memo;
	public Helper_url(String name, String beg, String end, String regex,
			String url, String method, String tag, String memo) {
		super();
		this.name = name;
		this.beg = beg;
		this.end = end;
		this.regex = regex;
		this.url = url;
		this.method = method;
		this.tag = tag;
		this.memo = memo;
	}
	
	public Helper_url(String[] params, String tag) {
		super();
		this.name = params[0];
		this.url = params[1];
		this.regex = params[2].replace('/', '\\');
		this.beg = params[3];
		this.end = params[4];
		this.method = "get";
		this.tag = tag;
	}
	
	public Helper_url() {
		super();
	}
	public Helper_url(String name, String url, String tag) {
		super();
		this.name = name;
		this.url = url;
		this.tag = tag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBeg() {
		return beg;
	}
	public void setBeg(String beg) {
		this.beg = beg;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	

}
