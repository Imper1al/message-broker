package org.challenge.candidate;

import org.apache.log4j.Logger;
import org.challenge.candidate.discounts.Discount;
import org.challenge.candidate.initializers.impl.DiscountInitializer;
import org.challenge.candidate.initializers.impl.ProductInitializer;
import org.challenge.candidate.models.Product;
import org.challenge.candidate.facades.CashMachineFacade;
import java.util.Map;

public class CashMachine {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.properties";
    private static final String DISCOUNT_FILE_PATH = "src/main/resources/discount.properties";

    private static final Logger LOGGER = Logger.getLogger(CashMachine.class);

    public ProductInitializer productInitializer;
    public DiscountInitializer discountInitializer;
    public Map<String, Product> products;
    public Map<String, Discount> discounts;
    public CashMachineFacade cashMachineFacade;

    public static void main(String[] args) {
        CashMachine cashMachine = new CashMachine();
        cashMachine.start();
    }

    private void start() {

        initialize();
        productInfo();

        cashMachineFacade.execute();
    }

    private void initialize() {
        productInitializer = new ProductInitializer(PRODUCTS_FILE_PATH);
        discountInitializer = new DiscountInitializer(DISCOUNT_FILE_PATH);
        products = productInitializer.initialize();
        discounts = discountInitializer.initialize();
        cashMachineFacade = new CashMachineFacade(2, products, discounts);
    }

    private void productInfo() {
        LOGGER.info("Product list: ");
        products.keySet().forEach(LOGGER::info);
        LOGGER.info("---------------\n");
    }
}
