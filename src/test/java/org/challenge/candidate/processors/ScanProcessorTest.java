package org.challenge.candidate.processors;

import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Product;
import org.challenge.messaging.DomainError;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScanProcessorTest {

    @Test
    public void testProcess_AddProductToCart() {
        // Given
        Map<String, Product> products = new HashMap<>();
        products.put("MUG", new Product("MUG", 10.0));
        Cart cart = new Cart();
        ScanProcessor scanProcessor = new ScanProcessor(products, cart);

        // When
        Object result = scanProcessor.process("MUG");

        // Then
        assertTrue(result instanceof String);
        assertTrue(cart.getItems().containsKey(products.get("MUG")));
    }

    @Test
    public void testProcess_UnknownItem() {
        // Given
        Map<String, Product> products = new HashMap<>();
        Cart cart = new Cart();
        ScanProcessor scanProcessor = new ScanProcessor(products, cart);

        // When
        Object result = scanProcessor.process("UNKNOWN");

        // Then
        assertTrue(result instanceof DomainError);
        assertEquals("Unknown item code: UNKNOWN", ((DomainError) result).getMessage());
    }

    @Test
    public void testProcess_Command() {
        // Given
        Map<String, Product> products = new HashMap<>();
        Cart cart = new Cart();
        ScanProcessor scanProcessor = new ScanProcessor(products, cart);

        // When
        Object result = scanProcessor.process("DONE");

        // Then
        assertTrue(result instanceof String);
        assertEquals("{\"command\":\"DONE\"}", result);
    }
}