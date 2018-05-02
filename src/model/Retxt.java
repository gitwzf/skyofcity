package model;

public class Retxt {
	public String name="";
	public String nameintroduction;
	public String[] key;
	public  String type;
	public String[] keystyle;
	public String content;
	public Simplestyle[] sstyle;
	public News[] news={new News("1","1","1","1","1","1")};
	public String identity;
	
	
	public String getNameintroduction() {
		return nameintroduction;
	}
	public void setNameintroduction(String nameintroduction) {
		this.nameintroduction = nameintroduction;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public News[] getNews() {
		return news;
	}
	public void setNews(News[] news) {
		this.news = news;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getKey() {
		return key;
	}
	public void setKey(String[] key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getKeystyle() {
		return keystyle;
	}
	public void setKeystyle(String[] keystyle) {
		this.keystyle = keystyle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Simplestyle[] getSstyle() {
		return sstyle;
	}
	public void setSstyle(Simplestyle[] sstyle) {
		this.sstyle = sstyle;
	}
	

}
