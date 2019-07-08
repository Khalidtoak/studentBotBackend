package com.hacktiveCodeBenders.studentBot.studentBot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacktiveCodeBenders.studentBot.studentBot.payloads.Links;
import com.hacktiveCodeBenders.studentBot.studentBot.payloads.Result;
import com.hacktiveCodeBenders.studentBot.studentBot.service.ScrapeSercvice;

@RestController
public class ScrapeController {
	@Autowired
	ScrapeSercvice scrapeSercvice;
	
	@GetMapping("/api/arvix-scrape")
	public Links scrapeArvix(@RequestParam(value = "query") String query){
		return scrapeSercvice.scrapeArvix(query);
	}
	@GetMapping("/api/google-scrape")
	public List<Result> scrapeGoogle(@RequestParam(value = "query") String query){
		return scrapeSercvice.scrapeGoogleDataSearch(query);
	}

}
