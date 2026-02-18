package com.data.online_writer.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.data.online_writer.dto.ForecastRequest;
import com.data.online_writer.dto.ValueDTO;
import com.data.online_writer.model.BusinessData;

@Service
public class ForecastService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${PYTHON_SERVICE_URL}")
    private String pythonServiceUrl;
    
    public Map<String, Object> getForecastFromPython(String category, List<BusinessData> entries) {
    	String url = pythonServiceUrl + "/forecast";

        List<ValueDTO> dataPoints = entries.stream()
                .map(e -> new ValueDTO(e.getEntryDate(), e.getValue()))
                .collect(Collectors.toList());

        ForecastRequest request = new ForecastRequest();
        request.setCategory(category); // CRITICAL: Set the category here
        request.setData(dataPoints);
        request.setFuture_points(7);

        return restTemplate.postForObject(url, request, Map.class);
    }
}
