package com.muniz.vb.orders;

import com.muniz.vb.orders.domain.Order;
import com.muniz.vb.orders.domain.OrderItem;
import com.muniz.vb.orders.domain.Product;
import com.muniz.vb.orders.repository.OrderRepository;
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
import java.time.LocalDateTime;
import java.util.List;

import static com.muniz.vb.orders.TestUtils.readJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersTest {

	private final String URL = "/orders";

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ProductRepository productRepository;

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	//@Test
	@Sql("classpath:/sqls/orders.sql")
	public void testListProducts() throws Exception{
		mvc.perform(get(URL))
            .andExpect(status().isOk())
			.andExpect(content().json(readJson("/orders/response.json")));
	}


	@Test
	@Sql("classpath:/sqls/products.sql")
	public void testPlaceOrder() throws Exception{
		LocalDateTime date = LocalDateTime.now();
		Thread.sleep(1000);

		repository.deleteAll();
		mvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		        .content(readJson("orders/request.json")))
				.andExpect(status().isCreated())
				;


		this.updateProductPrice();

		Order order = repository.findAll().get(0);
		assertEquals("marcelo@gmail.com",order.getBuyerEmail());
		assertTrue(order.getCreationDate().isAfter(date));
		assertEquals("146.05",order.getTotal().toString());

		List<OrderItem> items = order.getItems();
		assertEquals(1,items.get(0).getAmount().intValue());
		assertEquals("10.12",items.get(0).getPrice().toString());
		assertEquals("A1",items.get(0).getProduct().getId());

		assertEquals(2,items.get(1).getAmount().intValue());
		assertEquals("30.12",items.get(1).getPrice().toString());
		assertEquals("A2",items.get(1).getProduct().getId());

		assertEquals(3,items.get(2).getAmount().intValue());
		assertEquals("25.23",items.get(2).getPrice().toString());
		assertEquals("A3",items.get(2).getProduct().getId());

	}

	@Test
	public void testPlaceOrderMissingProduct() throws Exception{
		repository.deleteAll();
		productRepository.deleteAll();

		mvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(readJson("orders/request.json")))
				.andExpect(status().isPreconditionFailed())
				.andExpect(content().json("{\"message\": \"Product A1 not exists!\"}"));
		;
	}

	@Test
	public void testPlaceOrderInvalidEmail() throws Exception{
		this.testBadRequest("orders/request_invalid_email.json",
				           "orders/response_invalid_email.json");
	}

	@Test
	public void testPlaceOrderEmpty() throws Exception{
		this.testBadRequest("empty.json",
				"orders/response_empty.json");
	}

	@Test
	public void testPlaceItemEmpty() throws Exception{
		this.testBadRequest("orders/request_item_empty.json",
				"orders/response_item_empty.json");
	}

	private void testBadRequest(String request,String response) throws Exception{
		mvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(readJson(request)))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(readJson(response)))
		;
	}

	private void updateProductPrice(){
		Product product = productRepository.findOne("A1");
		product.setPrice(new BigDecimal(15.20));
		productRepository.save(product);
	}



}
