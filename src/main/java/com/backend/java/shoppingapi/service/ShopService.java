package com.backend.java.shoppingapi.service;

import com.backend.java.shoppingapi.dto.DTOConverter;
import com.backend.java.shoppingapi.dto.ShopDTO;
import com.backend.java.shoppingapi.dto.ShopReportDTO;
import com.backend.java.shoppingapi.model.Shop;
import com.backend.java.shoppingapi.repository.ReportRepository;
import com.backend.java.shoppingapi.repository.ShopRepository;
import com.backend.java.shoppingapi.dto.ItemDTO;
import com.santana.java.back.end.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final ProductService productService;

    public List<ShopDTO> getAll() {
        List<Shop> shops = shopRepository.findAll();
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier) {
        List<Shop> shops = shopRepository.findAllByUserIdentifier(userIdentifier);
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        List<Shop> shops = shopRepository
                .findAllByDateGreaterThan(shopDTO.getDate());
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public ShopDTO findById(long ProductId) {
        Optional<Shop> shop = shopRepository
                .findById(ProductId);
        if (shop.isPresent()) {
            return ShopDTO.convert(shop.get());
        }
        return null;
    }

    public ShopDTO save(ShopDTO shopDTO) {
        if (userService
                .getUserByCpf(shopDTO.getUserIdentifier()) == null) {
            return null;
        }
        if (!validateProducts(shopDTO.getItems())) {
            return null;
        }
        shopDTO.setTotal(shopDTO.getItems()
                .stream()
                .map(x -> x.getPrice())
                .reduce((float) 0, Float::sum));
        Shop shop = Shop.convert(shopDTO);
        shop.setDate(LocalDateTime.now());
        shop = shopRepository.save(shop);
        return DTOConverter.convert(shop);
    }

    private boolean validateProducts(List<ItemDTO> items) {
        for (ItemDTO item : items) {
            ProductDTO productDTO = productService
                    .getProductByIdentifier(
                            item.getProductIdentifier());
            if (productDTO == null) {
                return false;
            }
            item.setPrice(productDTO.getPreco());
        }
        return true;
    }

    public List<ShopDTO> getShopsByFilter(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {
        List<Shop> shops = reportRepository
                .getShopByFilters(dataInicio, dataFim, valorMinimo);
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {
        return reportRepository.getReportByDate(dataInicio, dataFim);
    }
}
