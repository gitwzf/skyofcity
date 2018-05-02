package model;
import org.simpleframework.xml.*; 
@Root
public class Rule {
	@Element(required=false)
	private int seqid;
	@Element
	private String name;
	/**
	 * 有效字符串开始
	 */
	@Element(required=false)
	private String beg;
	/**
	 * 有效字符串结束
	 */
	@Element(required=false)
	private String end;
	@Element
	private String tag;
	@Element
	private String url;
	/**
	 * 正则匹配
	 */
	@Element(required=false)
	private String regex;
	@Element(required=false)
	private String method;
	public Rule() {
		super();
	}
	public Rule(String name, String tag, String url) {
		super();
		this.name = name;
		this.tag = tag;
		this.url = url;
	}
	public int getSeqid() {
		return seqid;
	}
	public void setSeqid(int seqid) {
		this.seqid = seqid;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
