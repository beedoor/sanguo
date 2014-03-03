package com.game.sanguo.domain;

public class GameAreaInfo {
	Long id=0L;
	String name;
	Long recommend;
	Long statusAsInt;
	String url;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getRecommend() {
		return recommend;
	}
	public void setRecommend(Long recommend) {
		this.recommend = recommend;
	}
	public Long getStatusAsInt() {
		return statusAsInt;
	}
	public void setStatusAsInt(Long statusAsInt) {
		this.statusAsInt = statusAsInt;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "GameAreaInfo [id=" + id + ", name=" + name + ", recommend=" + recommend + ", statusAsInt=" + statusAsInt + ", url=" + url + "]";
	}
	
}
