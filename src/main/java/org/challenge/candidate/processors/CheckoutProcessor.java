package org.challenge.candidate.processors;

import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Product;
import org.challenge.messaging.Processor;

import java.util.Map;

public class CheckoutProcessor implements Processor {

    private final Cart cart;
    private final Map<String, Discount> discounts;

    public CheckoutProcessor(Cart cart, Map<String, Discount> discounts) {
        this.cart = cart;
        this.discounts = discounts;
    }

    @Override
    public Object process(String input) {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice();
            if (discounts.containsKey(product.getCode())) {
                Discount discount = discounts.get(product.getCode());
                price = discount.applyDiscount(price, quantity);
                total += price;
            } else {
                total += price * quantity;
            }
        }
        cart.setTotalPrice(total);
        return String.valueOf(total);
    }
}
