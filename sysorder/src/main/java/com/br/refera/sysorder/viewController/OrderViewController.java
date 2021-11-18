package com.br.refera.sysorder.viewController;

import com.br.refera.sysorder.controller.OrderController;
import com.br.refera.sysorder.entity.Order;
import com.br.refera.sysorder.service.ApiException;
import com.br.refera.sysorder.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class OrderViewController {

    private final OrderService orderService;


    @GetMapping("/")
    public String listOrders(Model model) {
        List<Order> orderList = null;
        try {
            orderList = orderService.getListOrder();
        } catch (ApiException e) {
            log.info(e.getReason());
        }
        if (orderList != null && orderList.isEmpty()) {
            model.addAttribute("orders", new ArrayList<>());
        } else {
            model.addAttribute("orders", orderList);
        }

        return "index";
    }
}
