package com.minsait.pruebatecnica.prices.infrastructure.outputs.ports;

import com.minsait.pruebatecnica.prices.domain.entities.Brand;
import com.minsait.pruebatecnica.prices.domain.entities.Price;
import com.minsait.pruebatecnica.prices.domain.entities.Product;
import com.minsait.pruebatecnica.prices.domain.repositories.PriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface H2ProductoRepository extends JpaRepository<Price, Long>, PriceRepository {

    @Query("SELECT p FROM Price p " +
            "WHERE p.brand = :brand AND p.product = :product " +
            "AND :dateTime BETWEEN p.startDate AND p.endDate " +
            "ORDER BY p.priority DESC")
    List<Price> findPriceByDateAndProductAndBrand(
            @Param("dateTime") LocalDateTime dateTime,
            @Param("product") Product productId,
            @Param("brand") Brand brandId);
}
