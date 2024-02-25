package com.minsait.pruebatecnica.prices.domain.mappers;

import com.minsait.pruebatecnica.prices.domain.dtos.PriceDto;
import com.minsait.pruebatecnica.prices.domain.entities.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceDtoMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "brandId", source = "brand.id")
    PriceDto priceToPriceDto(Price price);
}
