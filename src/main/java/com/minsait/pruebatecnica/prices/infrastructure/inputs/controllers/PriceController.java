package com.minsait.pruebatecnica.prices.infrastructure.inputs.controllers;

import com.minsait.pruebatecnica.prices.application.ports.PriceUseCase;
import com.minsait.pruebatecnica.prices.domain.dtos.PriceDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/price")
public class PriceController {

    private final PriceUseCase priceUseCase;

    @GetMapping("")
    public PriceDto priceToApplyByDate(@RequestParam @NotNull LocalDateTime dateTime,
                                       @RequestParam @NotNull Long productId,
                                       @RequestParam @NotNull Long brandId) {
        return priceUseCase.findPriceByDate(dateTime, productId, brandId);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.BAD_REQUEST,
                "No existe registro para la fecha y los parámetros proporcionados.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
