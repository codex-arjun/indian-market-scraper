package com.example.indian_market_scraper.analysis;


import java.util.List;

public class SignalAggregator {

    public static double computeSignal(List<String> tweets) {
        int bullish = 0;
        int bearish = 0;

        for (String t : tweets) {
            String lower = t.toLowerCase();
            if (lower.contains("buy") || lower.contains("long") || lower.contains("bullish")) {
                bullish++;
            }
            if (lower.contains("sell") || lower.contains("short") || lower.contains("bearish")) {
                bearish++;
            }
        }

        int total = bullish + bearish;
        if (total == 0) return 0.0;

        // Signal between -1 (bearish) and +1 (bullish)
        return (double)(bullish - bearish) / total;
    }
}
