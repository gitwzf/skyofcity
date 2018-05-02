package org.marker.weixin.msg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Msg4Voice extends Msg{
	 private String mediaId;
	 private String format;
	 private String msgId;
	 
	 
	 public Msg4Voice()
	  {
	    this.head = new Msg4Head();
	    this.head.setMsgType("voice");
	  }
	 
	 
	public Msg4Voice(Msg4Head head)
	  {
	    this.head = head;
	  }
	
	
	
	@Override
	public void read(Document document) {
		
		 this.mediaId = document.getElementsByTagName("MediaId").item(0).getTextContent();
		  this.format = document.getElementsByTagName("Format").item(0).getTextContent();
		  this.msgId = document.getElementsByTagName("MsgId").item(0).getTextContent();
	}

	@Override
	public void write(Document document) {
		
		Element root = document.createElement("xml");
	    this.head.write(root, document);
	    Element voiceElement = document.createElement("Voice");
	    Element mediaIdElement = document.createElement("MediaId");
	    mediaIdElement.setTextContent(this.mediaId);
	    voiceElement.appendChild(mediaIdElement);
	    root.appendChild(voiceElement);
	    document.appendChild(root);
	}



	public String getMediaId() {
		return mediaId;
	}



	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}



	public String getFormat() {
		return format;
	}



	public void setFormat(String format) {
		this.format = format;
	}



	public String getMsgId() {
		return msgId;
	}



	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	

}
