package com.br.refera.sysorder.service

import com.br.refera.sysorder.entity.Category
import com.br.refera.sysorder.entity.Order
import com.br.refera.sysorder.model.OrderModel
import com.br.refera.sysorder.repository.CategoryRepository
import com.br.refera.sysorder.repository.OrderRepository
import com.br.refera.sysorder.service.impl.OrderServiceImpl
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.ThreadLocalRandom

class OrderServiceSpec extends Specification {
    private CategoryRepository categoryRepository
    private OrderRepository orderRepository
    private OrderService orderService

    void setup() {
        categoryRepository = Mock(CategoryRepository)
        orderRepository = Mock(OrderRepository)
        orderService = new OrderServiceImpl(orderRepository, categoryRepository)
    }

    def "Test Success for getListOrder method"() {
        given:
            orderRepository.findAll() >> listOrders(4)
        when:
            def result = orderService.getListOrder()
        then:
            result != null
            result instanceof List
            result.size() == 4
    }

    def "Test Exception for getListOrder method - orders not found"() {
        given:
            orderRepository.findAll() >> Collections.emptyList()
        when:
            orderService.getListOrder()
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 404
    }

    def "Test Success for getOrderById method"() {
        given:
            orderRepository.findById(_ as Long) >> Optional.of(getOrder())
        when:
            def result = orderService.getOrderById(4)
        then:
            result != null
            result instanceof Order
            result.id != null
    }

    def "Test Exception for getOrderById method - order not found"() {
        given:
            orderRepository.findById(_ as Long) >> Optional.empty()
        when:
            orderService.getOrderById(4)
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 404
    }

    def "Test Success for createOrder method"() {
        given:
            orderRepository.findById(_ as Long) >> Optional.empty()
            categoryRepository.findById(_ as Long) >> Optional.of(getCategory())
            orderRepository.save(_ as Order) >> getOrder()
        when:
            def result = orderService.createOrder(getOrderModel())
        then:
            result != null
            result instanceof Order
            result.id != null
    }

    @Unroll("#testId - Test Exception for createOrder method - #reason")
    def "Test Exception for createOrder method"() {
        given:
            orderRepository.findById(_ as Long) >> findOrderById
            categoryRepository.findById(_ as Long) >> Optional.empty()
        when:
            orderService.createOrder(getOrderModel())
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 400
            apiException.reason.contains(reason)
        where:
            testId | findOrderById            | findCategoryById | reason
            "01"   | Optional.of(getOrder())  | null             | "Order already exist"
            "02"   | Optional.empty()         | Optional.empty() | "Parameter categoryId is wrong"
    }

    def "Test Success for updateOrder method"() {
        given:
            orderRepository.findById(_ as Long) >> Optional.of(getOrder())
            categoryRepository.findById(_ as Long) >> Optional.of(getCategory())
            orderRepository.save(_ as Order) >> getOrder()
        when:
            def result = orderService.updateOrder(getOrderModel())
        then:
            result != null
            result instanceof Order
            result.id != null
    }

    @Unroll("#testId - Test Exception for updateOrder method - #reason")
    def "Test Exception for updateOrder method"() {
        given:
            orderRepository.findById(_ as Long) >> findOrderById
            categoryRepository.findById(_ as Long) >> Optional.empty()
        when:
            orderService.updateOrder(getOrderModel())
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == statusCode
            apiException.reason.contains(reason)
        where:
            testId | findOrderById           | findCategoryById | statusCode | reason
            "03"   | Optional.empty()        | null             | 404        | "Order informed not found"
            "04"   | Optional.of(getOrder()) | Optional.empty() | 400        | "Parameter categoryId is wrong"
    }

    def "Test Success for deleteOrder method"() {
        given:
            orderRepository.findById(_ as Long) >> Optional.of(getOrder())
            orderRepository.delete(_ as Order) >> null
        when:
            orderService.deleteOrder(getOrder())
        then:
            noExceptionThrown()
    }

    def "Test Exception for deleteOrder method - Order informed not found"() {
        given:
            orderRepository.findById(_ as Long) >> Optional.empty()
        when:
            orderService.deleteOrder(getOrder())
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 404
            apiException.reason.contains("Order informed not found")
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
                .categoryId(3)
                .deadline(new Date())
                .build()
    }

    def getCategory() {
        return Category.builder()
                .id(ThreadLocalRandom.current().nextLong())
                .desc(RandomStringUtils.randomAlphabetic(15))
                .build()
    }
}
