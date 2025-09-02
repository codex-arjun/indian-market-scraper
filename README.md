# Indian Market Scraper

A Java-based application for scraping, processing, and analyzing Indian market data.  
This project collects raw market data (such as signals, news, and stock-related information), cleans and deduplicates it, applies text/vector analysis, and stores the results in a structured format (Parquet).

---

## 🚀 Features
- *Data Collection:* Scrapes market-related signals using custom collectors.  
- *Data Cleaning:* Removes duplicates and applies preprocessing.  
- *Analysis Layer:* Supports TF-IDF vectorization and aggregation of signals.  
- *Scheduling:* Automated jobs to collect and process data periodically.  
- *Storage:* Saves processed data into *Parquet* format for scalability.  
- *Modular Architecture:* Separate layers for collector, analysis, process, storage, and services.  

---

## 🛠 Tech Stack
- *Language:* Java 17+  
- *Framework:* Spring Boot  
- *Build Tool:* Maven  
- *Libraries:*  
  - Spring Web (REST APIs)  
  - Spring Scheduling  
  - Apache Parquet  
  - Apache Commons / Guava  
  - (Optional) NLP libraries for text analysis  

---

## AApproach /Workflow
Data Collection (Collector Layer)
	•	NitterScraper fetches market-related signals and sentiment data.
	•	Collector is designed to be extensible for future data sources.
	2.	Processing & Cleaning (Process Layer)
	•	Cleaner normalizes raw inputs.
	•	Deduplicator removes duplicate entries to improve data accuracy.
	3.	Analysis (Analysis Layer)
	•	TfidfVectorizer converts text into numerical features.
	•	SignalAggregator combines multiple sources into a unified signal.
	4.	Scheduling & Automation (Service Layer)
	•	ScheduledJob triggers scraping and processing at regular intervals.
	•	Ensures real-time updates without manual effort.
	5.	Storage (Storage Layer)
	•	ParquetWriterService saves processed data in Parquet format for efficient analytics.
	•	Optimized for large-scale queries and reporting.
	6.	API / Controller Layer
	•	SignalController exposes REST APIs to access processed insights.
	•	Supports integration with dashboards and external systems.

🔹 End-to-End Workflow
	1.	Scrape raw signals from market/social sources.
	2.	Clean, filter, and deduplicate data.
	3.	Apply TF-IDF vectorization and signal aggregation.
	4.	Store the results in optimized Parquet files.
	5.	Serve insights via REST API.
	6.	Automate the full cycle using scheduled jobs.

## 📂 Project Structure
indian-market-scraper/
│── .mvn/                   # Maven wrapper
│── src/
│   ├── main/
│   │   ├── java/com/example/indian_market_scraper/
│   │   │   ├── analysis/
│   │   │   │   ├── SignalAggregator.java
│   │   │   │   ├── TfidfVectorizer.java
│   │   │   ├── collector/
│   │   │   │   ├── NitterScraper.java
│   │   │   ├── controller/
│   │   │   │   ├── SignalController.java
│   │   │   ├── process/
│   │   │   │   ├── Cleaner.java
│   │   │   │   ├── Deduplicator.java
│   │   │   ├── service/
│   │   │   │   ├── ScheduledJob.java
│   │   │   ├── storage/
│   │   │   │   ├── ParquetWriterService.java
│   │   │   ├── IndianMarketScraperApplication.java
│   │   ├── resources/       # application.properties
│   ├── test/                # Unit/Integration tests
│── target/                  # Build output



## ScreenShots
Screenshots-
--Project 
--Classes
--Result