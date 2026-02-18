package com.data.online_writer.repository;

import com.data.online_writer.model.BusinessData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusinessDataRepository extends JpaRepository<BusinessData, Long> {
    // This finds ONLY the data matching the user's input
    List<BusinessData> findByCategoryOrderByEntryDateAsc(String category);
 // Keeps the CRUD tab organized by category and date automatically
    List<BusinessData> findAllByOrderByCategoryAscEntryDateDesc();
}