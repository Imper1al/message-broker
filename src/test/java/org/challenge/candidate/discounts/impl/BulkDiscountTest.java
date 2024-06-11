package org.challenge.candidate.discounts.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BulkDiscountTest {

    private final double PRICE = 10.0;

    @Test
    public void testApplyDiscount_NoDiscount() {
        // Given
        BulkDiscount bulkDiscount = new BulkDiscount();
        int QUANTITY_WITHOUT_DISCOUNT = 2;
        double EXPECTED_RESULT_WITHOUT_DISCOUNT = 20.0;

        // When
        double discountedPrice = bulkDiscount.applyDiscount(PRICE, QUANTITY_WITHOUT_DISCOUNT);

        // Then
        Assertions.assertEquals(EXPECTED_RESULT_WITHOUT_DISCOUNT, discountedPrice);
    }

    @Test
    public void testApplyDiscount_WithDiscount() {
        // Given
        BulkDiscount bulkDiscount = new BulkDiscount();
        double EXPECTED_RESULT_WITH_DISCOUNT = 27.0;
        int QUANTITY_WITH_DISCOUNT = 3;

        // When
        double discountedPrice = bulkDiscount.applyDiscount(PRICE, QUANTITY_WITH_DISCOUNT);

        // Then
        Assertions.assertEquals(EXPECTED_RESULT_WITH_DISCOUNT, discountedPrice);
    }

    @Test
    public void testApplyDiscount_WithHigherQuantity() {
        // Given
        BulkDiscount bulkDiscount = new BulkDiscount();
        double EXPECTED_RESULT_HIGHER_QUANTITY = 47.0;
        int HIGHER_QUANTITY = 5;

        // When
        double discountedPrice = bulkDiscount.applyDiscount(PRICE, HIGHER_QUANTITY);

        // Then
        Assertions.assertEquals(EXPECTED_RESULT_HIGHER_QUANTITY, discountedPrice);
    }
}