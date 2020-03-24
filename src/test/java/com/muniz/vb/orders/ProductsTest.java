package com.muniz.vb.orders;

import com.muniz.vb.orders.domain.Product;
import com.muniz.vb.orders.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static com.muniz.vb.orders.TestUtils.readJson;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductsTest {

	private final String URL = "/products";

	@Autowired
	private ProductRepository repository;

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@Sql("classpath:/sqls/products.sql")
	public void testListProducts() throws Exception{
		mvc.perform(get(URL))
            .andExpect(status().isOk())
			.andExpect(content().json(readJson("response_products.json")));
		repository.deleteAll();
	}


	@Test
	public void testAddProducts() throws Exception{
		mvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		        .content(readJson("request_product.json")))
				.andExpect(status().isCreated())
				;

		Product product = repository.findOne("A1");
		assertEquals("Pen",product.getDescription());
		assertEquals(1,product.getPrice().compareTo(new BigDecimal(10.12)));
	}

	@Test
	public void testAddProductsEmpty() throws Exception{
		mvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{}"))
				.andExpect(status().isBadRequest())
                .andExpect(content().json(readJson("response_products_empty.json")));

	}

	@Test
	@Sql("classpath:/sqls/products.sql")
	public void testAddProductsConflict() throws Exception{
		mvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(readJson("request_product.json")))
				.andExpect(status().isConflict())
				.andExpect(content().json(readJson("response_products_conflict.json")));
		repository.deleteAll();

	}

}
