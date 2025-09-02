package com.example.indian_market_scraper.collector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NitterScraper {

        private static final String[] BASE_URLS = {
                "https://nitter.net",
                "https://nitter.it",
                "https://nitter.poast.org",
                "https://nitter.salastil.com",
                "https://nitter.fdn.fr"
        };

        private static String getRandomBaseUrl() {
            int idx = (int) (Math.random() * BASE_URLS.length);
            return BASE_URLS[idx];
        }

        // Helper: convert HTML element into Tweet object
        private static Tweet parseTweet(Element item) {
            Tweet t = new Tweet();
            try {
                t.username = item.select("a.username").text();
                t.timestamp = item.select("span.tweet-date a").attr("title");
                t.content = item.select("div.tweet-content").text();

                // engagement metrics
                String replies = item.select("div.icon-container:has(i.fa.fa-comment) span").text();
                String retweets = item.select("div.icon-container:has(i.fa.fa-retweet) span").text();
                String likes = item.select("div.icon-container:has(i.fa.fa-heart) span").text();

                t.replies = replies.isEmpty() ? 0 : Integer.parseInt(replies.replaceAll("\\D",""));
                t.retweets = retweets.isEmpty() ? 0 : Integer.parseInt(retweets.replaceAll("\\D",""));
                t.likes = likes.isEmpty() ? 0 : Integer.parseInt(likes.replaceAll("\\D",""));

            } catch (Exception e) {
                System.err.println("Error parsing tweet: " + e.getMessage());
            }
            return t;
        }

        // Inner class for Tweet


        private static final String BASE_URL = "https://nitter.net/search?f=tweets&q=";

        public static class Tweet {
            public String username;
            public String timestamp;
            public String content;
            public int replies;
            public int retweets;
            public int likes;

            public Tweet(String username, String timestamp, String content,
                         int replies, int retweets, int likes) {
                this.username = username;
                this.timestamp = timestamp;
                this.content = content;
                this.replies = replies;
                this.retweets = retweets;
                this.likes = likes;
            }

            public Tweet(String user, String time, String text) {
            }

            public Tweet() {

            }

            @Override
            public String toString() {
                return username + " | " + timestamp + " | " + content +
                        " | Replies: " + replies +
                        " | Retweets: " + retweets +
                        " | Likes: " + likes;
            }



            public List<Tweet> scrapeTweets(String hashtag, int limit) {
                List<Tweet> tweets = new ArrayList<>();
                int fetched = 0;

                while (fetched < limit) {
                    try {
                        String baseUrl = getRandomBaseUrl();
                        String url = baseUrl + "/search?f=tweets&q=%23" + hashtag + "&since=" + LocalDate.now().minusDays(1);

                        Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0")
                                .timeout(10000)
                                .get();

                        Elements items = doc.select("div.timeline-item");
                        for (Element item : items) {
                            if (fetched >= limit) break;
                            Tweet t = parseTweet(item);
                            tweets.add(t);
                            fetched++;
                        }

                        // Sleep to avoid 429
                        Thread.sleep(2000);

                    } catch (Exception e) {
                        System.err.println("Error fetching tweets: " + e.getMessage());
                        break; // stop on repeated errors
                    }
                }
                return tweets;
            }
        }
        public List<Tweet> scrapeTweets(String hashtag, int limit) {
            List<Tweet> tweets = new ArrayList<>();
            try {
                String url = getRandomBaseUrl() + "/search?f=tweets&q=%23" + hashtag + "&since=&until=&near=";
                System.out.println("Fetching: " + url);

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(10000)
                        .get();

                Elements items = doc.select("div.timeline-item");

                int count = 0;
                for (Element item : items) {
                    if (count >= limit) break;
                    Tweet t = parseTweet(item);
                    tweets.add(t);
                    count++;
                }

            } catch (IOException e) {
                System.err.println("Error fetching tweets: " + e.getMessage());
            }
            return tweets;
        }
    }