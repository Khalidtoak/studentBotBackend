package com.hacktiveCodeBenders.studentBot.studentBot.payloads;


public class Result {
	String title;
	String link;
	
	
	public Result() {
		super();
	}
	public Result(String title, String link) {
		super();
		this.title = title;
		this.link = link;
	}
	public String getInput() {
		return title;
	}
	public void setInput(String title) {
		this.title = title;
	}
	public String getLinks() {
		return link;
	}
	public void setLinks(String links) {
		this.link = links;
	}
	
}
