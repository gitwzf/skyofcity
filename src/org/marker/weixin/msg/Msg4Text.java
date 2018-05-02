package org.marker.weixin.msg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Msg4Text extends Msg
{
  private String content;
  private String funcFlag;
  private String msgId;

  public Msg4Text()
  {
    this.head = new Msg4Head();
    this.head.setMsgType("text");
  }

  public Msg4Text(Msg4Head head)
  {
    this.head = head;
  }

  @Override
public void write(Document document)
  {
    Element root = document.createElement("xml");
    this.head.write(root, document);
    Element contentElement = document.createElement("Content");
    contentElement.setTextContent(this.content);
    Element funcFlagElement = document.createElement("FuncFlag");
    funcFlagElement.setTextContent(this.funcFlag);
    root.appendChild(contentElement);
    root.appendChild(funcFlagElement);
    document.appendChild(root);
  }

  @Override
public void read(Document document)
  {
    this.content = document.getElementsByTagName("Content").item(0).getTextContent();
    this.msgId = document.getElementsByTagName("MsgId").item(0).getTextContent();
  }

  public String getContent()
  {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMsgId() {
    return this.msgId;
  }

  public void setMsgId(String msgId) {
    this.msgId = msgId;
  }

  public String getFuncFlag()
  {
    return this.funcFlag;
  }

  public void setFuncFlag(String funcFlag) {
    this.funcFlag = funcFlag;
  }
}