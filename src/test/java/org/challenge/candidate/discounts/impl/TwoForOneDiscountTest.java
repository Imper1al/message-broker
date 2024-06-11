package org.challenge.candidate.discounts.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwoForOneDiscountTest {

    private final double PRICE = 10.0;

    @Test
    public void testApplyDiscount_EvenQuantity() {
        // Given
        TwoForOneDiscount twoForOneDiscount = new TwoForOneDiscount();
        int QUANTITY = 4;
        double EXPECTED_RESULT = 20.0;

        // When
        double discountedPrice = twoForOneDiscount.applyDiscount(PRICE, QUANTITY);

        // Then
        assertEquals(EXPECTED_RESULT, discountedPrice, 0.01);
    }

    @Test
    public void testApplyDiscount_OddQuantity() {
        // Given
        TwoForOneDiscount twoForOneDiscount = new TwoForOneDiscount();
        int QUANTITY = 5;
        double EXPECTED_RESULT = 30.0;

        // When
        double discountedPrice = twoForOneDiscount.applyDiscount(PRICE, QUANTITY);

        // Then
        assertEquals(EXPECTED_RESULT, discountedPrice, 0.01);
    }

    @Test
    public void testApplyDiscount_SingleItem() {
        // Given
        TwoForOneDiscount twoForOneDiscount = new TwoForOneDiscount();
        int QUANTITY = 1;
        double EXPECTED_RESULT = 10.0;

        // When
        double discountedPrice = twoForOneDiscount.applyDiscount(PRICE, QUANTITY);

        // Then
        assertEquals(EXPECTED_RESULT, discountedPrice, 0.01);
    }

    @Test
    public void testApplyDiscount_NoItems() {
        // Given
        TwoForOneDiscount twoForOneDiscount = new TwoForOneDiscount();
        int QUANTITY = 0;
        double EXPECTED_RESULT = 0.0;

        // When
        double discountedPrice = twoForOneDiscount.applyDiscount(PRICE, QUANTITY);

        // Then
        assertEquals(EXPECTED_RESULT, discountedPrice, 0.01);
    }
}