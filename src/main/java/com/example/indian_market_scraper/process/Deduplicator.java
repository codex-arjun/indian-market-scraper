package com.example.indian_market_scraper.process;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.indian_market_scraper.collector.NitterScraper;
import com.example.indian_market_scraper.collector.NitterScraper.Tweet;

    public class Deduplicator {
        private final Set<String> seen = new HashSet<>();

        public List<NitterScraper.Tweet> deduplicate(List<Tweet> tweets) {
            return tweets.stream()
                    .filter(t -> seen.add(t.content)) // keep only new tweets
                    .collect(Collectors.toList());
        }
    }




