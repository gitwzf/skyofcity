package DbXml;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root
public class Openid_wid {
	@Element(required=false)
	private String openid;
	@Element(required=false)
	private String email;
	@Element(required=false)
	private String subtime;
	public Openid_wid(String openid, String email, String subtime) {
		super();
		this.openid = openid;
		this.email = email;
		this.subtime = subtime;
	}
	public Openid_wid() {
		super();
	}
	
	public Openid_wid(String openid) {
		super();
		this.openid = openid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubtime() {
		return subtime;
	}
	public void setSubtime(String subtime) {
		this.subtime = subtime;
	}
	

}
