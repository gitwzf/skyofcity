package interf;

import java.text.DecimalFormat;

import org.marker.weixin.msg.Msg;
import org.marker.weixin.msg.Msg4Event;
import org.marker.weixin.msg.Msg4Location;
import org.marker.weixin.msg.Msg4Text;

public class ChooseMsg {
	
	public Msg4Text onMsg(Msg4Text msg){
		return msg;
	}
	public Msg4Event onMsg(Msg4Event msg){
		return msg;
	}
	public String onMsg(Msg msg){
		if(msg instanceof Msg4Text)
			return ((Msg4Text) msg).getContent();
		if(msg instanceof Msg4Event)
			return ((Msg4Event) msg).getEvent();
		if(msg instanceof Msg4Location)
			{
			DecimalFormat dformat=new DecimalFormat("0.000");
			String x=dformat.format(Double.parseDouble(((Msg4Location)msg).getLocation_X()));
			String y=dformat.format(Double.parseDouble(((Msg4Location)msg).getLocation_Y()));
			return x+","+y;
			}
		return "";
	}
	public String onReMsg(Msg msg){
		if(msg instanceof Msg4Text)
			return "[文字]"+((Msg4Text) msg).getContent();
		if(msg instanceof Msg4Event)
			return "[事件]"+((Msg4Event) msg).getEvent();
		if(msg instanceof Msg4Location)
			{
			DecimalFormat dformat=new DecimalFormat("0.000");
			String x=dformat.format(Double.parseDouble(((Msg4Location)msg).getLocation_X()));
			String y=dformat.format(Double.parseDouble(((Msg4Location)msg).getLocation_Y()));
			return "[位置]"+x+","+y;
			}
		return "";
	}
}
