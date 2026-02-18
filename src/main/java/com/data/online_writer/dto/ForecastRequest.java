package com.data.online_writer.dto;

import java.util.List;

public class ForecastRequest {
    private String category; // ADD THIS
    private List<ValueDTO> data;
    private int future_points;

    // Getters and Setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<ValueDTO> getData() { return data; }
    public void setData(List<ValueDTO> data) { this.data = data; }

    public int getFuture_points() { return future_points; }
    public void setFuture_points(int future_points) { this.future_points = future_points; }
}
