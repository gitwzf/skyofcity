package org.marker.weixin;

import org.marker.weixin.msg.Msg4Event;
import org.marker.weixin.msg.Msg4Image;
import org.marker.weixin.msg.Msg4Link;
import org.marker.weixin.msg.Msg4Location;
import org.marker.weixin.msg.Msg4Text;
import org.marker.weixin.msg.Msg4Video;
import org.marker.weixin.msg.Msg4Voice;

public class HandleMessageAdapter
  implements HandleMessageListener
{
  public void onTextMsg(Msg4Text msg)
  {
  }

  public void onImageMsg(Msg4Image msg)
  {
  }

  public void onEventMsg(Msg4Event msg)
  {
  }

  public void onLinkMsg(Msg4Link msg)
  {
  }
  
  public void onVoiceMsg(Msg4Voice msg)
  {
  }
  
  public void onVideoMsg(Msg4Video msg)
  {
  }

  public void onLocationMsg(Msg4Location msg)
  {
  }

  public void onErrorMsg(int errorCode)
  {
  }
}