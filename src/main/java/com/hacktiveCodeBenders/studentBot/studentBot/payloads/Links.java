package com.hacktiveCodeBenders.studentBot.studentBot.payloads;

import java.util.List;

public class Links {
	List<String> links;
	
	public Links(List<String> links) {
		super();
		this.links = links;
	}
	
	public Links() {
		super();
	}

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}
	
}
