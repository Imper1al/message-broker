package org.challenge.candidate.initializers.impl;

import org.apache.log4j.Logger;
import org.challenge.candidate.initializers.Initializer;
import org.challenge.candidate.models.Product;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProductInitializer implements Initializer<Product> {
    private final String filePath;

    private static final Logger LOGGER = Logger.getLogger(ProductInitializer.class);

    public ProductInitializer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Map<String, Product> initialize() {
        Properties properties = new Properties();
        Map<String, Product> products = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            for (String code : properties.stringPropertyNames()) {
                String property = properties.getProperty(code);
                if(!property.isEmpty()) {
                    double price = Double.parseDouble(property);
                    products.put(code, new Product(code, price));
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return products;
    }
}
