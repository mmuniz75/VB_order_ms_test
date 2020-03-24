package com.muniz.vb.orders;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
			.andExpect(content().json(TestUtils.readJson("products.json")));

	}

}
