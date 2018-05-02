package org.marker.weixin.msg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Msg4Video  extends Msg{
	 private String mediaId;
	 private String thumbMediaId;
	 private String msgId;
	 
	 
	 public Msg4Video()
	  {
	    this.head = new Msg4Head();
	    this.head.setMsgType("video");
	  }
	 
	 
	public Msg4Video(Msg4Head head)
	  {
	    this.head = head;
	  }
	
	
	
	@Override
	public void read(Document document) {
		
		 this.mediaId = document.getElementsByTagName("MediaId").item(0).getTextContent();
		  this.thumbMediaId = document.getElementsByTagName("ThumbMediaId").item(0).getTextContent();
		  this.msgId = document.getElementsByTagName("MsgId").item(0).getTextContent();
	}

	@Override
	public void write(Document document) {
		
		Element root = document.createElement("xml");
	    this.head.write(root, document);
	    Element videoElement = document.createElement("Video");
	    Element mediaIdElement = document.createElement("MediaId");
	    mediaIdElement.setTextContent(this.mediaId);
	    videoElement.appendChild(mediaIdElement);
	    root.appendChild(videoElement);
	    document.appendChild(root);
	}



	public String getMediaId() {
		return mediaId;
	}



	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}


	public String getThumbMediaId() {
		return thumbMediaId;
	}


	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}


	public String getMsgId() {
		return msgId;
	}



	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	

}

