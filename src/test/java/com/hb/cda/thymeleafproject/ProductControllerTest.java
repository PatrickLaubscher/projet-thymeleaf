package com.hb.cda.thymeleafproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.service.impl.CartServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;


@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {


    @Autowired
	MockMvc mvc;
	@Autowired
	EntityManager em;

    String idProduct;

    private CartServiceImpl cartService;
    private HttpSession httpSession;
    private Product product1;

    @BeforeEach
    public void setUp() {
        cartService = new CartServiceImpl();
        httpSession = new MockHttpSession();

        product1 = new Product();
        product1.setId("1");
        product1.setName("test1");
        product1.setPrice(2.0);
        product1.setStock(10);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("test2");
        product2.setPrice(6.0);
        product2.setStock(20);


        em.persist(product1);
        em.persist(product2);

    }


    @Test
	void getAllShouldReturnFirstPageByDefault() throws Exception{
		mvc.perform(get("/product"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content.length()").value(2))
		.andExpect(jsonPath("$.content[0].name").value("test1"))
		.andExpect(jsonPath("$.content[0].price").value(2.0))
        .andExpect(jsonPath("$.content[0].stock").value(10));
	}



    
}
