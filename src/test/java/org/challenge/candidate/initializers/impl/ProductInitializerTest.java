package org.challenge.candidate.initializers.impl;

import org.apache.log4j.Logger;
import org.challenge.candidate.models.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductInitializerTest {
    private static final String TEMP_FILE_PATH = "temp.properties";

    private static final Logger LOGGER = Logger.getLogger(ProductInitializerTest.class);

    @Before
    public void setUp() {
        try (FileWriter writer = new FileWriter(TEMP_FILE_PATH)) {
            writer.write("MUG=4.00\n");
            writer.write("TSHIRT=21.00\n");
            writer.write("USBKEY=10.00\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @After
    public void tearDown() {
        File file = new File(TEMP_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testInitializeProducts() {
        // Given
        ProductInitializer productInitializer = new ProductInitializer(TEMP_FILE_PATH);

        // When
        Map<String, Product> products = productInitializer.initialize();

        // Then
        assertEquals(3, products.size());
        assertTrue(products.containsKey("MUG"));
        assertTrue(products.containsKey("TSHIRT"));
        assertTrue(products.containsKey("USBKEY"));
        assertEquals(4.00, products.get("MUG").getPrice(), 0.01);
        assertEquals(21.00, products.get("TSHIRT").getPrice(), 0.01);
        assertEquals(10.00, products.get("USBKEY").getPrice(), 0.01);
    }
}