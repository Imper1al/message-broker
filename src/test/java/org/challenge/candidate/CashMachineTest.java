package org.challenge.candidate;

import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.discounts.impl.BulkDiscount;
import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Product;
import org.challenge.candidate.processors.CheckoutProcessor;
import org.challenge.candidate.processors.ScanProcessor;
import org.challenge.messaging.DomainError;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class CashMachineTest {

    @Test
    public void testScanProcessorAddItemToCart() {
        // Given
        Map<String, Product> products = new HashMap<>();
        products.put("MUG", new Product("MUG", 10.0));
        Cart cart = new Cart();
        ScanProcessor scanProcessor = new ScanProcessor(products, cart);

        // When
        scanProcessor.process("MUG");

        // Then
        assert cart.getItems().containsKey(products.get("MUG"));
    }

    @Test
    public void testScanProcessorUnknownItem() {
        // Given
        Map<String, Product> products = new HashMap<>();
        Cart cart = new Cart();
        ScanProcessor scanProcessor = new ScanProcessor(products, cart);

        // When
        Object result = scanProcessor.process("UNKNOWN");

        // Then
        assert result instanceof DomainError;
    }

    @Test
    public void testScanProcessorCommandDone() {
        // Given
        Cart cart = new Cart();
        ScanProcessor scanProcessor = new ScanProcessor(new HashMap<>(), cart);

        // When
        Object result = scanProcessor.process("done");

        // Then
        assert result instanceof String;
        assert ((String) result).contains("command");
        assert ((String) result).contains("done");
    }

    @Test
    public void testCheckoutProcessorCalculateTotal() {
        // Given
        Cart cart = new Cart();
        cart.addItem(new Product("MUG", 10.0), 2);
        CheckoutProcessor checkoutProcessor = new CheckoutProcessor(cart, new HashMap<>());

        // When
        Object result = checkoutProcessor.process("");

        // Then
        assert result instanceof String;
        assert Double.parseDouble((String) result) == 20.0;
    }

    @Test
    public void testCheckoutProcessorWithDiscount() {
        // Given
        Cart cart = new Cart();
        Product mug = new Product("MUG", 10.0);
        cart.addItem(mug, 3);
        Map<String, Discount> discounts = new HashMap<>();
        discounts.put("MUG", new BulkDiscount());
        CheckoutProcessor checkoutProcessor = new CheckoutProcessor(cart, discounts);

        // When
        Object result = checkoutProcessor.process("");

        // Then
        assert result instanceof String;
        assert Double.parseDouble((String) result) == 27.0;
    }
}