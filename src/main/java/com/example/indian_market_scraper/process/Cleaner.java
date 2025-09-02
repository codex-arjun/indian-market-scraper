package com.example.indian_market_scraper.process;


public class Cleaner {

    public static String cleanText(String text) {
        if (text == null) return "";

        // Remove URLs
        text = text.replaceAll("https?://\\S+\\s?", "");

        // Remove emojis and non-ASCII chars
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // Normalize spaces
        text = text.replaceAll("\\s+", " ").trim();

        return text.toLowerCase();
    }
}
