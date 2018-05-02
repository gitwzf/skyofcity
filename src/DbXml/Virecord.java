package DbXml;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root
public class Virecord {
	@Element(required=false)
	private String times;
	@Element(required=false)
	private String name;
	@Element(required=false)
	private String instruction;
	@Element(required=false)
	private String mode;
	@Element(required=false)
	private String param_loca;
	public Virecord(String times, String name, String instruction, String mode,
			String paramLoca) {
		super();
		this.times = times;
		this.name = name;
		this.instruction = instruction;
		this.mode = mode;
		param_loca = paramLoca;
	}
	public Virecord() {
		super();
	}
	
	public Virecord(String name) {
		super();
		this.name = name;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getParam_loca() {
		return param_loca;
	}
	public void setParam_loca(String paramLoca) {
		param_loca = paramLoca;
	}
	
}
