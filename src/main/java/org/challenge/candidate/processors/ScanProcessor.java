package org.challenge.candidate.processors;

import org.challenge.candidate.models.Cart;
import org.challenge.candidate.models.Commands;
import org.challenge.candidate.models.Product;
import org.challenge.candidate.utils.JsonUtils;
import org.challenge.messaging.DomainError;
import org.challenge.messaging.Processor;

import java.util.Arrays;
import java.util.Map;

public class ScanProcessor implements Processor {

    private static final String KEY_COMMAND = "command";
    private final Map<String, Product> products;
    private final Cart cart;

    public ScanProcessor(Map<String, Product> products, Cart cart) {
        this.products = products;
        this.cart = cart;
    }

    @Override
    public Object process(String input) {
        if (Arrays.stream(Commands.values()).anyMatch(element -> element.name().equalsIgnoreCase(input))) {
            return JsonUtils.convertToJson(KEY_COMMAND, input);
        }
        if (products.containsKey(input)) {
            Product product = products.get(input);
            cart.addItem(product, 1);
            return product.getProductJSON();
        } else {
            return new DomainError("Unknown item code: " + input);
        }
    }
}
