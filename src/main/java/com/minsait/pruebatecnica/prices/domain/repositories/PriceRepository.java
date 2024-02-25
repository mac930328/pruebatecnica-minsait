package com.minsait.pruebatecnica.prices.domain.repositories;

import com.minsait.pruebatecnica.prices.domain.entities.Brand;
import com.minsait.pruebatecnica.prices.domain.entities.Price;
import com.minsait.pruebatecnica.prices.domain.entities.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface PriceRepository {

    List<Price> findPriceByDateAndProductAndBrand(LocalDateTime dateTime, Product product, Brand brand);

}
