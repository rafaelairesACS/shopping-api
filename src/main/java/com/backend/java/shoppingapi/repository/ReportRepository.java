package com.backend.java.shoppingapi.repository;

import com.backend.java.shoppingapi.dto.ShopReportDTO;
import com.backend.java.shoppingapi.model.Shop;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface ReportRepository {
    List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo);

    ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim);

}
