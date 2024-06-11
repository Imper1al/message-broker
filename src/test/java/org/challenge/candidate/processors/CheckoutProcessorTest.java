package org.challenge.candidate.processors;

import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.discounts.impl.BulkDiscount;
import org.challenge.candidate.discounts.impl.TwoForOneDiscount;
import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CheckoutProcessorTest {

    @Test
    public void testProcess_NoDiscount() {
        // Given
        Cart cart = new Cart();
        Map<String, Discount> discounts = new HashMap<>();
        discounts.put("MUG", new TwoForOneDiscount());
        discounts.put("TSHIRT", new BulkDiscount());
        CheckoutProcessor checkoutProcessor = new CheckoutProcessor(cart, discounts);

        Product mug = new Product("MUG", 10.0);
        Product tshirt = new Product("TSHIRT", 20.0);
        cart.addItem(mug, 2); // No discount applied
        cart.addItem(tshirt, 3); // No discount applied

        // When
        checkoutProcessor.process(null);

        // Then
        Assertions.assertEquals(64.0, cart.getTotalPrice(), 0.01);
    }

    @Test
    public void testProcess_WithDiscount() {
        // Given
        Cart cart = new Cart();
        Map<String, Discount> discounts = new HashMap<>();
        discounts.put("MUG", new TwoForOneDiscount());
        discounts.put("TSHIRT", new BulkDiscount());
        CheckoutProcessor checkoutProcessor = new CheckoutProcessor(cart, discounts);

        Product mug = new Product("MUG", 10.0);
        Product tshirt = new Product("TSHIRT", 20.0);
        cart.addItem(mug, 3); // 2 for 1 discount applied
        cart.addItem(tshirt, 5); // Bulk discount applied

        // When
        checkoutProcessor.process(null);

        // Then
        Assertions.assertEquals(114.0, cart.getTotalPrice(), 0.01);
    }
}