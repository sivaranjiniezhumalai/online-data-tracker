package com.data.online_writer.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
@EnableScheduling
public class KeepAliveService {

    @Value("${PYTHON_SERVICE_URL}")
    private String pythonUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // 840,000 milliseconds = 14 minutes
    @Scheduled(fixedRate = 840000)
    public void keepAlive() {
        try {
            // 1. Ping Self (Spring Boot)
            // Replace with your actual Spring Boot Render URL
            String selfUrl = "https://online-data-tracker.onrender.com/data/categories";
            restTemplate.getForObject(selfUrl, String.class);
            
            // 2. Ping Python Service
            // This ensures Python stays awake too
            restTemplate.getForObject(pythonUrl + "/", String.class);
            
            System.out.println("Heartbeat sent: Both services are awake.");
        } catch (Exception e) {
            System.out.println("Waker failed: " + e.getMessage());
        }
    }
}