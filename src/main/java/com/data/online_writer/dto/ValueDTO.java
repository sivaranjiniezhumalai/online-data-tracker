package com.data.online_writer.dto;

import java.time.LocalDate;

public class ValueDTO {
    private LocalDate date;
    private Double value;

    // Standard constructor for your new logic
    public ValueDTO(LocalDate date, Double value) {
        this.date = date;
        this.value = value;
    }

    // Default constructor for Jackson/JSON
    public ValueDTO() {}

    // Getters and Setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
}
