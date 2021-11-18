package com.br.refera.sysorder.controller


import com.br.refera.sysorder.entity.Order
import com.br.refera.sysorder.model.OrderModel
import com.br.refera.sysorder.service.OrderService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.util.concurrent.ThreadLocalRandom

class OrderControllerSpec extends Specification {
    private OrderService orderService
    private OrderController orderController
    private MockMvc mockMvc

    void setup() {
        orderService = Mock(OrderService)
        orderController = new OrderController(orderService)
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build()
    }

    def "Test Success for GET /orders"() {
        given:
            orderService.getListOrder() >> listOrders(4)
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
        then:
            result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "Test Success for GET /orders/{id}"() {
        given:
            orderService.getOrderById(_ as Long) >> getOrder()
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.get("/orders/4"))
        then:
            result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "Test Success for POST /orders"() {
        given:
            orderService.createOrder(_ as OrderModel) >> getOrder()
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                    .content(getRequestJson(getOrderModel())).contentType(MediaType.APPLICATION_JSON_VALUE))
        then:
            result.andExpect(MockMvcResultMatchers.status().isCreated())
    }

    def "Test Success for PUT /orders/{id}"() {
        given:
            orderService.updateOrder(_ as OrderModel) >> getOrder()
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.put("/orders/4")
                    .content(getRequestJson(getOrderModel())).contentType(MediaType.APPLICATION_JSON_VALUE))
        then:
            result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "Test Success for DELETE /orders/{id}"() {
        given:
        orderService.deleteOrder(_ as Order) >> null
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.delete("/orders/4"))
        then:
            result.andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    def getOrder() {
        return Order.builder()
                .id(ThreadLocalRandom.current().nextLong())
                .company(RandomStringUtils.randomAlphabetic(15))
                .contactName(RandomStringUtils.randomAlphabetic(15))
                .contactPhone(RandomStringUtils.randomNumeric(11))
                .description(RandomStringUtils.randomNumeric(11))
                .realEstateAgency(RandomStringUtils.randomNumeric(20))
                .company(RandomStringUtils.randomNumeric(10))
                .deadline(new Date())
                .build()
    }

    def listOrders(qtd) {
        List<Order> orderList = new ArrayList<>()
        for (int x = 0; x < qtd; x++) {
            orderList.add(getOrder())
        }
        return  orderList
    }

    def getOrderModel() {
        return OrderModel.builder()
                .id(ThreadLocalRandom.current().nextLong())
                .company(RandomStringUtils.randomAlphabetic(15))
                .contactName(RandomStringUtils.randomAlphabetic(15))
                .contactPhone(RandomStringUtils.randomNumeric(11))
                .description(RandomStringUtils.randomNumeric(11))
                .realEstateAgency(RandomStringUtils.randomNumeric(20))
                .company(RandomStringUtils.randomNumeric(10))
                .deadline(new Date())
                .build()
    }

    def getRequestJson(param) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter()
        return ow.writeValueAsString(param)
    }
}
