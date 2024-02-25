package com.minsait.pruebatecnica.prices.application.ports;

import java.time.LocalDateTime;

import com.minsait.pruebatecnica.prices.domain.dtos.PriceDto;
import com.minsait.pruebatecnica.prices.domain.entities.Price;

public interface PriceUseCase {

    PriceDto findPriceByDate(LocalDateTime dateTime, Long productId, Long brandId);

}
