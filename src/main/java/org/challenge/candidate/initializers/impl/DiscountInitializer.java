package org.challenge.candidate.initializers.impl;

import org.apache.log4j.Logger;
import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.initializers.Initializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DiscountInitializer implements Initializer<Discount> {
    private final String filePath;

    private static final Logger LOGGER = Logger.getLogger(DiscountInitializer.class);

    public DiscountInitializer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Map<String, Discount> initialize() {
        Properties properties = new Properties();
        Map<String, Discount> discounts = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            for (String code : properties.stringPropertyNames()) {
                String discountClassName = properties.getProperty(code);
                try {
                    Class<?> discountClass = Class.forName(discountClassName);
                    Object discountObject = discountClass.getDeclaredConstructor().newInstance();
                    if (discountObject instanceof Discount) {
                        discounts.put(code, (Discount) discountObject);
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return discounts;
    }
}
