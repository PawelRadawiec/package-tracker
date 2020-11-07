package com.info.packagetrackerbackend.validators;

import com.info.packagetrackerbackend.model.basket.AddToBasket;
import com.info.packagetrackerbackend.service.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddToBasketValidator extends GenericValidator implements Validator {

    private ProductRepository productRepository;

    public AddToBasketValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AddToBasket.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddToBasket addToBasket = (AddToBasket) o;
        validateProduct(addToBasket, errors);
    }

    private void validateProduct(AddToBasket addToBasket, Errors errors) {
        validateIfTrue(productRepository.inBasket(addToBasket.getProduct().getId()), "basket", "Product unavailable", errors);
    }

}
