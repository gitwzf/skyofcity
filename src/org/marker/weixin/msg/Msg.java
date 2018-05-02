package org.marker.weixin.msg;

import org.w3c.dom.Document;

public abstract class Msg
{
  public static final String MSG_TYPE_TEXT = "text";
  public static final String MSG_TYPE_IMAGE = "image";
  public static final String MSG_TYPE_MUSIC = "music";
  public static final String MSG_TYPE_LOCATION = "location";
  public static final String MSG_TYPE_LINK = "link";
  public static final String MSG_TYPE_IMAGE_TEXT = "news";
  public static final String MSG_TYPE_EVENT = "event";
  protected Msg4Head head;

  public abstract void write(Document paramDocument);

  public abstract void read(Document paramDocument);

  public Msg4Head getHead()
  {
    return this.head;
  }

  public void setHead(Msg4Head head) {
    this.head = head;
  }

  public String getToUserName() {
    return this.head.getToUserName();
  }

  public void setToUserName(String toUserName) {
    this.head.setToUserName(toUserName);
  }

  public String getFromUserName() {
    return this.head.getFromUserName();
  }

  public void setFromUserName(String fromUserName) {
    this.head.setFromUserName(fromUserName);
  }

  public String getCreateTime() {
    return this.head.getCreateTime();
  }

  public void setCreateTime(String createTime) {
    this.head.setCreateTime(createTime);
  }
}