package org.marker.weixin.msg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Msg4Image extends Msg
{
  private String mediaId;
  private String picUrl;
  private String msgId;

  public Msg4Image()
  {
    this.head = new Msg4Head();
    this.head.setMsgType("image");
  }

  public Msg4Image(Msg4Head head)
  {
    this.head = head;
  }

  @Override
public void write(Document document)
  {
    Element root = document.createElement("xml");
    this.head.write(root, document);
    Element imageElement = document.createElement("Image");
    Element mediaIdElement = document.createElement("MediaId");
    mediaIdElement.setTextContent(this.mediaId);
    imageElement.appendChild(mediaIdElement);
    root.appendChild(imageElement);
    document.appendChild(root);
  }

  @Override
public void read(Document document)
  {
    this.picUrl = document.getElementsByTagName("PicUrl").item(0).getTextContent();
    this.msgId = document.getElementsByTagName("MsgId").item(0).getTextContent();
    this.mediaId = document.getElementsByTagName("MediaId").item(0).getTextContent();
  }

  public String getPicUrl()
  {
    return this.picUrl;
  }

  public void setPicUrl(String picUrl)
  {
    this.picUrl = picUrl;
  }

  public String getMsgId()
  {
    return this.msgId;
  }

  public void setMsgId(String msgId)
  {
    this.msgId = msgId;
  }


  public String getMediaId() {
    return this.mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
}