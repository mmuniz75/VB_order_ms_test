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
			.andExpect(content().json(readJson("products/response_products.json")));
	}


	@Test
	public void testAddProducts() throws Exception{
		repository.deleteAll();
		mvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		        .content(readJson("products/request_product.json")))
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
                .andExpect(content().json(readJson("products/response_products_empty.json")));

	}

	@Test
	@Sql("classpath:/sqls/products.sql")
	public void testAddProductsConflict() throws Exception{
		mvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(readJson("products/request_product.json")))
				.andExpect(status().isConflict())
				.andExpect(content().json("{\"message\": \"Product already exists!\"}"));

	}

	@Sql("classpath:/sqls/products.sql")
	@Test
	public void testUpdateProduct() throws Exception{
		mvc.perform(patch(URL + "/A1" )
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(readJson("products/request_update_product.json")))
				.andExpect(status().isOk())
		;

		Product product = repository.findOne("A1");
		assertEquals("Pen updated",product.getDescription());
		assertEquals(1,product.getPrice().compareTo(new BigDecimal(34.26)));
	}

	@Sql("classpath:/sqls/products.sql")
	@Test
	public void testUpdateProductDescription() throws Exception{
		mvc.perform(patch(URL + "/A1" )
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\"description\": \"New description\"}"))
				.andExpect(status().isOk())
		;

		Product product = repository.findOne("A1");
		assertEquals("New description",product.getDescription());
		assertEquals(1,product.getPrice().compareTo(new BigDecimal(10.12)));
	}

	@Sql("classpath:/sqls/products.sql")
	@Test
	public void testUpdateProductPrice() throws Exception{
		mvc.perform(patch(URL + "/A1" )
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\"price\": 9.87}"))
				.andExpect(status().isOk())
		;

		Product product = repository.findOne("A1");
		assertEquals("Pen",product.getDescription());
		assertEquals(1,product.getPrice().compareTo(new BigDecimal(9.87)));
	}

	@Sql("classpath:/sqls/products.sql")
	@Test
	public void testUpdateProductsEmpty() throws Exception{
		mvc.perform(patch(URL + "/A1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"message\": \"Inform description or/and price properties\"}"));
	}


	@Test
	public void testUpdateProductsNotFound() throws Exception{
		mvc.perform(patch(URL + "/A4")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(readJson("products/request_update_product.json")))
				.andExpect(status().isNotFound())
				.andExpect(content().json("{\"message\": \"Product not found!\"}"));
	}

	@Sql("classpath:/sqls/products.sql")
	@Test
	public void testUpdateProductsNoChange() throws Exception{
		mvc.perform(patch(URL + "/A1")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(readJson("products/request_product.json")))
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"message\": \"The current values is the same of the product\"}"));

	}


}
