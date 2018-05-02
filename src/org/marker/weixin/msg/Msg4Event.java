package org.marker.weixin.msg;

import org.w3c.dom.Document;

public class Msg4Event extends Msg
{
  public static final String SUBSCRIBE = "subscribe";
  public static final String UNSUBSCRIBE = "unsubscribe";
  public static final String CLICK = "CLICK";
  private String event;
  private String eventKey;

  public Msg4Event(Msg4Head head)
  {
    this.head = head;
  }

  @Override
public void write(Document document)
  {
  }

  @Override
public void read(Document document)
  {
    this.event = document.getElementsByTagName("Event").item(0).getTextContent();
    this.eventKey = document.getElementsByTagName("EventKey").item(0).getTextContent();
  }

  public String getEvent()
  {
    return this.event;
  }

  public void setEvent(String event)
  {
    this.event = event;
  }

  public String getEventKey()
  {
    return this.eventKey;
  }

  public void setEventKey(String eventKey)
  {
    this.eventKey = eventKey;
  }
}