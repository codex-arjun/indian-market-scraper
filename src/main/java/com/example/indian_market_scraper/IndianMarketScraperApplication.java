package com.example.indian_market_scraper;

import com.example.indian_market_scraper.collector.NitterScraper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IndianMarketScraperApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IndianMarketScraperApplication.class, args);
	}



    @Override
    public void run(String... args) {
        NitterScraper scraper = new NitterScraper();
        var tweets = scraper.scrapeTweets("nifty50", 10);

        System.out.println("Collected Tweets:");
        tweets.forEach(System.out::println);

    }
}