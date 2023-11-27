package com.epam.tamentoring.bo;

import com.epam.tamentoring.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        shoppingCart = new ShoppingCart(new ArrayList<>());
    }

    @Test
    public void addProductsToCartTest() {
        Product product = new Product(12, "Dyson", 15999.00, 1);
        shoppingCart.addProductToCart(product);
        assertEquals(product, shoppingCart.getProductById(12));
    }

    @Test
    public void addProductsToCartWithTheSameIdTest() {
        Product product1 = new Product(12, "Banana", 199.00, 1);
        Product product2 = new Product(12, "Banana", 199.00, 4);
        shoppingCart.addProductToCart(product1);
        shoppingCart.addProductToCart(product2);

        assertEquals(5, shoppingCart.getProductById(12).getQuantity());
    }

    @Test
    public void checkTotalPriceTest() {
        Product product1 = new Product(12, "Dyson", 15999.00, 1);
        Product product2 = new Product(13, "Banana", 21.7, 5);
        shoppingCart.addProductToCart(product1);
        shoppingCart.addProductToCart(product2);

        assertEquals(16107.50, shoppingCart.getCartTotalPrice());
    }

    @Test
    public void checkPriceAndQuantityAfterPartialProductRemovingTest() {
        Product product1 = new Product(12, "Banana", 199.00, 1);
        Product product2 = new Product(12, "Banana", 199.00, 4);
        shoppingCart.addProductToCart(product1);
        shoppingCart.addProductToCart(product2);

        shoppingCart.removeProductFromCart(product2);

        assertEquals(199, shoppingCart.getCartTotalPrice());
    }

    @Test
    public void removeProductsFromCartTest() {
        Product product1 = new Product(12, "Dyson", 15999.00, 1);
        Product product2 = new Product(13, "Banana", 199.00, 3);
        shoppingCart.addProductToCart(product1);
        shoppingCart.addProductToCart(product2);

        shoppingCart.removeProductFromCart(product2);

        assertEquals(1, shoppingCart.getProducts().size());
        assertEquals(product1, shoppingCart.getProducts().get(0));
    }

    @Test
    public void removeNotExistingIdTest() {
        Product product = new Product(12, "Dyson", 15999.00, 1);

        Exception exception = assertThrows(ProductNotFoundException.class, () -> shoppingCart.removeProductFromCart(product));
        assertEquals("Product is not found in the cart: " + product, exception.getMessage());
    }

    @Test
    public void getNotExistingIdTest() {
        Exception exception = assertThrows(ProductNotFoundException.class, () -> shoppingCart.getProductById(3));
        assertEquals("Product with 3 ID is not found in the shopping cart!", exception.getMessage());
    }
}
