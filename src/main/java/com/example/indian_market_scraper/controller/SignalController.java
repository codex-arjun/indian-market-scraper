package com.example.indian_market_scraper.controller;


import com.example.indian_market_scraper.service.ScheduledJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SignalController {

    private final ScheduledJob job;

    public SignalController(ScheduledJob job) {
        this.job = job;
    }

    @GetMapping("/api/signal")
    public Map<String, Object> getSignal() {
        return Map.of(
                "signal", job.getLastSignal(),
                "description", job.getLastSignal() > 0 ? "Bullish" :
                        job.getLastSignal() < 0 ? "Bearish" : "Neutral"
        );
    }
}

