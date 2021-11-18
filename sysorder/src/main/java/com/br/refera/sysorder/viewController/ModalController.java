package com.br.refera.sysorder.viewController;

import com.br.refera.sysorder.entity.Order;
import com.br.refera.sysorder.repository.CategoryRepository;
import com.br.refera.sysorder.service.ApiException;
import com.br.refera.sysorder.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("modals")
public class ModalController {

    private final CategoryRepository categoryRepository;
    private final OrderService orderService;

    @GetMapping("newOrder")
    public String newOrder(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "modalNewOrder";
    }

    @GetMapping("modal-open-order/{idOrder}")
    public String modalOpenOrder(@PathVariable Long idOrder, Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        try {
            model.addAttribute("order", orderService.getOrderById(idOrder));
        } catch (ApiException ignored) {
            model.addAttribute("order", new Order());
        }
        return "modalOpenOrder";
    }
}
