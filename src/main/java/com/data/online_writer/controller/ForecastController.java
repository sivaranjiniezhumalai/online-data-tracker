package com.data.online_writer.controller;

import com.data.online_writer.model.BusinessData;
import com.data.online_writer.repository.BusinessDataRepository;
import com.data.online_writer.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ForecastController {

    @Autowired
    private ForecastService forecastService;

    @Autowired
    private BusinessDataRepository repository;

    @GetMapping("/forecast")
    public Map<String, Object> forecast(@RequestParam String category) {
        // 1. Fetch ONLY the specific category data ordered by date
        List<BusinessData> entries = repository.findByCategoryOrderByEntryDateAsc(category);

        // 2. Validation: Check if the category exists
        if (entries.isEmpty()) {
            throw new RuntimeException("Category '" + category + "' not found or has no data.");
        }

        // 3. Get the 7-day forecast from Python
        Map<String, Object> response = forecastService.getForecastFromPython(category, entries);

        // 4. ADDITION: Include the historical database entries in the response
        response.put("history", entries);

        return response;
    }
}