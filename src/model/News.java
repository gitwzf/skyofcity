package model;

public class News {
     public String title;
     public String main;
     public String urlo;
     public String url;
     public String pty;
     public String pid;
     
	public News() {
		super();
	}
	public News(String title, String main, String urlo, String url, String pty,
			String pid) {	this.title = title;
		this.main = main;
		this.urlo = urlo;
		this.url = url;
		this.pty = pty;
		this.pid = pid;
	}
	public String getPty() {
		return pty;
	}
	public void setPty(String pty) {
		this.pty = pty;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMain() {
		return main;
	}
	public void setMain(String main) {
		this.main = main;
	}
	
	public String getUrlo() {
		return urlo;
	}
	public void setUrlo(String urlo) {
		this.urlo = urlo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
     
}
