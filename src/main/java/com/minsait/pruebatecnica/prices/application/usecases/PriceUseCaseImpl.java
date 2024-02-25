package com.minsait.pruebatecnica.prices.application.usecases;

import com.minsait.pruebatecnica.prices.application.ports.PriceUseCase;
import com.minsait.pruebatecnica.prices.domain.dtos.PriceDto;
import com.minsait.pruebatecnica.prices.domain.entities.Brand;
import com.minsait.pruebatecnica.prices.domain.entities.Product;
import com.minsait.pruebatecnica.prices.domain.mappers.PriceDtoMapper;
import com.minsait.pruebatecnica.prices.domain.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PriceUseCaseImpl implements PriceUseCase {

    private final PriceRepository priceRepository;
    private final PriceDtoMapper priceDtoMapper;

    @Override
    public PriceDto findPriceByDate(LocalDateTime dateTime, Long productId, Long brandId) {
        Product product = Product.builder().id(productId).build();
        Brand brand = Brand.builder().id(brandId).build();
        return priceRepository.findPriceByDateAndProductAndBrand(dateTime, product, brand)
                .stream()
                .findFirst()
                .map(priceDtoMapper::priceToPriceDto)
                .orElseThrow();
    }
}
