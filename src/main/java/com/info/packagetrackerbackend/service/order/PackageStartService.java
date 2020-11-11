package com.info.packagetrackerbackend.service.order;

import com.info.packagetrackerbackend.model.Product;
import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.model.order.Order;
import com.info.packagetrackerbackend.model.order.PersonOrder;
import com.info.packagetrackerbackend.service.ProductService;
import com.info.packagetrackerbackend.service.SystemUserHelper;
import com.info.packagetrackerbackend.service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PackageStartService {

    private OrderRepository repository;
    private SystemUserHelper userHelper;
    private ProductService productService;

    public PackageStartService(OrderRepository repository, SystemUserHelper userHelper, ProductService productService) {
        this.repository = repository;
        this.userHelper = userHelper;
        this.productService = productService;
    }

    public Order create(Order order) {
        Product product = productService.findById(order.getProduct().getId());
        appendOrder(order);
        repository.save(order);
        appendProduct(product, order);
        productService.update(product);
        return order;
    }

    private void appendProduct(Product product, Order order) {
        product.setAvailable(false);
        product.setBasket(null);
        product.setOrder(order);
    }

    private void appendOrder(Order order) {
        SystemUser user = userHelper.getCurrentUser();
        PersonOrder personOrder = new PersonOrder();
        personOrder.setFirstName(user.getFirstName());
        personOrder.setLastName(user.getLastName());
        personOrder.setEmail(user.getEmail());
        order.setName("Order of product with name: " + order.getProduct().getName());
        order.setPerson(personOrder);
        order.setProduct(null);
    }

    public Order getOrder(Long id, String code) {
        return repository.getOrderByIdAndCode(id, code);
    }

}
