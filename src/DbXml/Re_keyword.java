package DbXml;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root
public class Re_keyword {
	@Element(required=false)
	private String id;
	@Element(required=false)
	private String type0;
	@Element(required=false)
	private String name;
	@Element(required=false)
	private String key0;
	@Element(required=false)
	private String re_id;
	@Element(required=false)
	private String content;
	@Element(required=false)
	private String identity;
	@Element(required=false)
	private String openid;
	public Re_keyword(String id, String type0, String name, String key0,
			String reId, String content, String identity, String openid) {
		super();
		this.id = id;
		this.type0 = type0;
		this.name = name;
		this.key0 = key0;
		re_id = reId;
		this.content = content;
		this.identity = identity;
		this.openid = openid;
	}
	public Re_keyword() {
		super();
	}
	
	public Re_keyword(String type0, String name, String key0, String reId,
			String content) {
		super();
		this.type0 = type0;
		this.name = name;
		this.key0 = key0;
		re_id = reId;
		this.content = content;
	}
	public Re_keyword(String id,String type0, String content, String openid) {
		super();
		this.id=id;
		this.type0 = type0;
		this.content = content;
		this.openid = openid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType0() {
		return type0;
	}
	public void setType0(String type0) {
		this.type0 = type0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey0() {
		return key0;
	}
	public void setKey0(String key0) {
		this.key0 = key0;
	}
	public String getRe_id() {
		return re_id;
	}
	public void setRe_id(String reId) {
		re_id = reId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
