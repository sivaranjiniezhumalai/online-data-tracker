package com.data.online_writer.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.online_writer.model.BusinessData;
import com.data.online_writer.repository.BusinessDataRepository;
import com.data.online_writer.service.ForecastService;


@RestController
@RequestMapping("/data")
public class onlineWriterController {

    @Autowired
    private BusinessDataRepository repository;
    
    
    @GetMapping("/")
    public String home() {
        // This tells Spring Boot to automatically redirect to your dashboard
        return "redirect:/dashboard.html"; 
    }
    //  Save data
    @PostMapping("/save")
    public BusinessData saveData(@RequestBody BusinessData data) {
        return repository.save(data);
    }

    //  Get all data
    @GetMapping("/all")
    public List<BusinessData> getAll() {
        return repository.findAll();
    }

    // Delete by id
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "Deleted successfully";
    }
    
    @PatchMapping("/update/{id}")
    public BusinessData partialUpdate(@PathVariable Long id,
                                      @RequestBody Map<String, Object> updates) {

        BusinessData existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found"));

        if (updates.containsKey("category")) {
            existing.setCategory((String) updates.get("category"));
        }

        if (updates.containsKey("value")) {
            existing.setValue(Double.valueOf(updates.get("value").toString()));
        }

        if (updates.containsKey("entryDate")) {
            existing.setEntryDate(
                    java.time.LocalDate.parse(updates.get("entryDate").toString())
            );
        }

        return repository.save(existing);
    }
    
    @PostMapping("/saveAll")
    public List<BusinessData> saveAll(@RequestBody List<BusinessData> dataList) {
        return repository.saveAll(dataList);
    }
    
    //categories
    @GetMapping("/categories")
    public List<String> getUniqueCategories() {
        return repository.findAll()
                .stream()
                .map(BusinessData::getCategory)
                .distinct()
                .sorted()
                .toList();
    }


    //  Aggregation for chart
    @GetMapping("/summary")
    public Map<String, Double> summary() {
        List<BusinessData> list = repository.findAll();
        Map<String, Double> result = new HashMap<>();

        for (BusinessData data : list) {
            result.put(
                data.getCategory(),
                result.getOrDefault(data.getCategory(), 0.0) + data.getValue()
            );
        }

        return result;
    }
    
    @Autowired
    private ForecastService forecastService;

    @GetMapping("/forecast")
    public Map<String, Object> forecast(@RequestParam(defaultValue = "General") String category) {
        // Use your filtered repository method here for better results
        List<BusinessData> entries = repository.findByCategoryOrderByEntryDateAsc(category);
        
        if (entries.isEmpty()) {
            throw new RuntimeException("No data found for: " + category);
        }

        Map<String, Object> response = forecastService.getForecastFromPython(category, entries);
        response.put("history", entries);
        return response;
    }

}
