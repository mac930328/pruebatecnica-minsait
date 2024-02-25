package com.minsait.pruebatecnica.price.infrastructure.inputs.controllers

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringJUnitWebConfig
@AutoConfigureMockMvc
class PriceControllerSpec extends Specification {

    MockMvc mockMvc

    @Autowired
    WebApplicationContext webApplicationContext;

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    def "Test Error: parametros no pueden tener valor null"(){
        given:
        def date = LocalDateTime.of(2020, 6, 14, 10, 0)

        when:
        def result = mockMvc.perform(get("/api/price")
                .param("dateTime", date.toString())
                .param("productId", "")
                .param("brandId", ""))

        then:
        result.andExpect(status().isBadRequest())

    }

    def "Test Error: No hay resultados para la busqueda"(){
        given:
        def date = LocalDateTime.of(2020, 6, 14, 10, 0)

        when:
        def result = mockMvc.perform(get("/api/price")
                .param("dateTime", date.toString())
                .param("productId", "35455")
                .param("brandId", "2"))

        then:
        result.andExpect(status().isBadRequest())

    }

    @Unroll
    def "Test #test: Consulta a las #hour:00 del día #day para el producto #productId y la marca #brandId"() {
        given:
        def date = LocalDateTime.of(year, month, day, hour, minute)

        when:
        def result = mockMvc.perform(get("/api/price")
                .param("dateTime", date.toString())
                .param("productId", productId.toString())
                .param("brandId", brandId.toString())).andReturn()

        then:
        assertPriceInfo(result, productId, brandId, priceList, startDate, endDate, expectedPrice)

        where:
        test | year | month | day | hour | minute | productId | brandId | priceList | startDate             | endDate               | expectedPrice
        1    | 2020 | 6     | 14  | 10   | 0      | 35455     | 1       | 1         | '2020-06-14T00:00:00' | '2020-12-31T23:59:59' | 35.50
        2    | 2020 | 6     | 14  | 16   | 0      | 35455     | 1       | 2         | '2020-06-14T15:00:00' | '2020-06-14T18:30:00' | 25.45
        3    | 2020 | 6     | 14  | 21   | 0      | 35455     | 1       | 1         | '2020-06-14T00:00:00' | '2020-12-31T23:59:59' | 35.50
        4    | 2020 | 6     | 15  | 10   | 0      | 35455     | 1       | 3         | '2020-06-15T00:00:00' | '2020-06-15T11:00:00' | 30.50
        5    | 2020 | 6     | 15  | 21   | 0      | 35455     | 1       | 4         | '2020-06-15T16:00:00' | '2020-12-31T23:59:59' | 38.95
    }

    def assertPriceInfo(result, productId, brandId, priceList, startDate, endDate, price) {
        def mockMvcResultMatchers = MockMvcResultMatchers
        status().isOk().match(result)

        def body = result.response.getContentAsString()
        jsonPath("productId").value(productId)
        jsonPath("brandId").value(brandId)
        jsonPath("priceList").value(priceList)
        jsonPath("startDate").value(startDate)
        jsonPath("endDate").value(endDate)
        jsonPath("price").value(price)
    }
}
