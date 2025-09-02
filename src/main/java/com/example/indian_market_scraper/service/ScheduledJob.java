package com.example.indian_market_scraper.service;


import com.example.indian_market_scraper.analysis.SignalAggregator;
import com.example.indian_market_scraper.collector.NitterScraper;
import com.example.indian_market_scraper.process.Cleaner;
import com.example.indian_market_scraper.process.Deduplicator;
import com.example.indian_market_scraper.storage.ParquetWriterService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduledJob {

    private final NitterScraper scraper = new NitterScraper();
    private final Deduplicator deduplicator = new Deduplicator();
    private final ParquetWriterService writer = new ParquetWriterService();

    private double lastSignal = 0.0;

    @Scheduled(fixedRate = 600000) // every 10 minutes
    public void fetchAndProcess() {
        try {
            System.out.println("Running scheduled job...");

            List<NitterScraper.Tweet> tweets = scraper.scrapeTweets("nifty50",200);
            tweets = deduplicator.deduplicate(tweets);

            // Clean content
            tweets.forEach(t -> t.content = Cleaner.cleanText(t.content));

            // Save to parquet
            if (!tweets.isEmpty()) {
                writer.writeTweets(tweets, "tweets.parquet");
            }

            // Compute signal
            List<String> contents = tweets.stream()
                    .map(t -> t.content)
                    .collect(Collectors.toList());

            lastSignal = SignalAggregator.computeSignal(contents);

            System.out.println("Computed Signal: " + lastSignal);

        } catch (IOException e) {
            System.err.println("Error scraping: " + e.getMessage());
        }
    }

    public double getLastSignal() {
        return lastSignal;
    }
}
