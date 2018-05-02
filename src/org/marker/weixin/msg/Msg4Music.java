package org.marker.weixin.msg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Msg4Music extends Msg
{
  private String title;
  private String description;
  private String musicUrl;
  private String hQMusicUrl;
  private String funcFlag;

  public Msg4Music()
  {
    this.head = new Msg4Head();
    this.head.setMsgType("music");
  }

  @Override
public void write(Document document)
  {
    Element root = document.createElement("xml");
    this.head.write(root, document);

    Element musicElement = document.createElement("Music");

    Element titleElement = document.createElement("Title");
    titleElement.setTextContent(this.title);
    Element descriptionElement = document.createElement("Description");
    descriptionElement.setTextContent(this.description);
    Element musicUrlElement = document.createElement("MusicUrl");
    musicUrlElement.setTextContent(this.musicUrl);
    Element hQMusicUrlElement = document.createElement("HQMusicUrl");
    hQMusicUrlElement.setTextContent(this.hQMusicUrl);

    musicElement.appendChild(titleElement);
    musicElement.appendChild(descriptionElement);
    musicElement.appendChild(musicUrlElement);
    musicElement.appendChild(hQMusicUrlElement);
    root.appendChild(musicElement);

    Element funcFlagElement = document.createElement("FuncFlag");
    funcFlagElement.setTextContent(this.funcFlag);
    root.appendChild(funcFlagElement);

    document.appendChild(root);
  }

  @Override
public void read(Document document)
  {
  }

  public String getTitle()
  {
    return this.title; }

  public void setTitle(String title) {
    this.title = title; }

  public String getDescription() {
    return this.description; }

  public void setDescription(String description) {
    this.description = description; }

  public String getMusicUrl() {
    return this.musicUrl; }

  public void setMusicUrl(String musicUrl) {
    this.musicUrl = musicUrl; }

  public String getHQMusicUrl() {
    return this.hQMusicUrl; }

  public void setHQMusicUrl(String hQMusicUrl) {
    this.hQMusicUrl = hQMusicUrl; }

  public String getFuncFlag() {
    return this.funcFlag; }

  public void setFuncFlag(String funcFlag) {
    this.funcFlag = funcFlag;
  }
}