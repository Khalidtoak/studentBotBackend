package com.hacktiveCodeBenders.studentBot.studentBot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hacktiveCodeBenders.studentBot.studentBot.controller.TaskController;
import com.hacktiveCodeBenders.studentBot.studentBot.payloads.Links;
import com.hacktiveCodeBenders.studentBot.studentBot.payloads.Result;

@Service
public class ScrapeSercvice {
	public Links scrapeArvix(String query) {
		Document document;
		List<String> links = new ArrayList<>();
		try {
			document = Jsoup.connect("https://arxiv.org/search/?query=" + query
					+ "&searchtype=all&abstracts=show&order=-announced_date_first&size=50").get();
			Elements linkElements = document.select("a[href]");
			final Logger logger = LoggerFactory.getLogger(TaskController.class);
			logger.info(linkElements.toString());
			linkElements.forEach(link -> {
				String linkURL = link.absUrl("href");
				if (links.size() == 3) {
					return;
				}
				if (linkURL.contains("190")) {
					links.add(linkURL);
				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Links(links);
	}

	public List<Result> scrapeGoogleDataSearch(String query) {
		Document document;
		List<Result> results = new ArrayList<>();
		try {
			document = Jsoup.connect("https://toolbox.google.com/datasetsearch/search?query=" + query
					+ "&docid=fcRwvT45i24JFz8lAAAAAA%3D%3D").get();
			//Elements titles = document.select("h1");
			Elements linkElements = document.select("a[href]");
			final Logger logger = LoggerFactory.getLogger(TaskController.class);
			logger.info(linkElements.toString());
			for (int i = 0; i < linkElements.size(); i++) {
				String linkURL = linkElements.get(i).absUrl("href");
				if (linkURL.contains("doi.org")) {
					results.add(new Result(linkURL));
				}
				
			}
		} catch (IOException e) {

		}
		return results;
	}
}
