package com.hb.cda.thymeleafproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.service.impl.CartServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class ProductControllerTest {


    @Autowired
	MockMvc mvc;
	@Autowired
	EntityManager em;

    @MockitoBean
    private CartServiceImpl cartService;

    String idProduct;
    Product product1;

    @BeforeEach
    public void setUp() {

        product1 = new Product();
        product1.setName("test1");
        product1.setPrice(2.0);
        product1.setStock(10);

        Product product2 = new Product();
        product2.setName("test2");
        product2.setPrice(6.0);
        product2.setStock(20);


        em.persist(product1);
        em.persist(product2);
        idProduct = product1.getId(); 
    }


    @Test
	void getAllShouldReturnHtmlPage() throws Exception{
		mvc.perform(get("/product"))
		.andExpect(status().isOk())
        .andExpect(view().name("product-list")) 
        .andExpect(model().attributeExists("products"));
	}


    @Test
    void addProductInCartShouldRedirectAndCallService() throws Exception {

        mvc.perform(post("/product/add-to-cart")
            .param("productId", idProduct)
            .param("quantity", "2"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/product"));

        verify(cartService).addProductInCart(eq(product1), eq(2), any(HttpSession.class));

    }

    
}
