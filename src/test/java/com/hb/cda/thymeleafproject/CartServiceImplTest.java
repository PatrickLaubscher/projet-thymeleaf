package com.hb.cda.thymeleafproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.service.Cart;
import com.hb.cda.thymeleafproject.service.impl.CartServiceImpl;

import jakarta.servlet.http.HttpSession;

public class CartServiceImplTest {
   
    private CartServiceImpl cartService;
    private HttpSession httpSession;

    private Product product1;

    @BeforeEach
    public void setUp() {
        cartService = new CartServiceImpl();
        httpSession = new MockHttpSession();

        product1 = new Product();
        product1 .setId("1");
        product1 .setName("test1");
        product1 .setPrice(2.0);

        cartService.addProductInCart(product1, 3, httpSession);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("test2");
        product2.setPrice(6.0);

        cartService.addProductInCart(product2, 4, httpSession);
    }


    @Test
    public void testAddProductInCart() {
        Product product = new Product();
        product.setId("3");
        product.setName("test3");
        product.setPrice(4.0);

        cartService.addProductInCart(product, 5, httpSession);

        Cart result = cartService.getCart(httpSession);
        assertNotNull(result);
        assertEquals(5, result.getItems().get(product));
    }


    @Test
    public void testRemoveProductFromCart() {

        Cart before = cartService.getCart(httpSession);
        assertTrue(before.getItems().containsKey(product1));
        assertEquals(3, before.getItems().get(product1));

        cartService.removeProductFromCart(this.product1, httpSession);

        Cart after = cartService.getCart(httpSession);
        assertFalse(after.getItems().containsKey(product1));
    }


    @Test
    public void testCalculateTotalPrice() {

        double expectedTotalPrice = 6.0 + 24.0;
        double totalPriceFromCart = cartService.getTotalPrice(httpSession);

        assertEquals(expectedTotalPrice, totalPriceFromCart);
    }



}
