package com.epam.tamentoring.bo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private DiscountUtility discountUtility;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void verifyUserDiscount() {
        UserAccount userAccount = createTestUserAccount();
        when(discountUtility.calculateDiscount(userAccount)).thenReturn(3.0);

        double priceWithDiscount = orderService.getOrderPrice(userAccount);

        assertEquals(204, priceWithDiscount);
        verify(discountUtility, times(1)).calculateDiscount(userAccount);
        verifyNoMoreInteractions(discountUtility);
    }

    private UserAccount createTestUserAccount() {
        Product product1 = new Product(12, "Banana", 7, 1);
        Product product2 = new Product(3, "Apples", 40, 5);
        ShoppingCart shoppingCart = new ShoppingCart(new ArrayList<>());

        shoppingCart.addProductToCart(product1);
        shoppingCart.addProductToCart(product2);

        return new UserAccount("John", "Smith", "1990/10/10", shoppingCart);
    }
}
