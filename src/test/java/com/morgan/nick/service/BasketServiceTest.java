package com.morgan.nick.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.morgan.nick.model.Basket;
import com.morgan.nick.model.BasketItem;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketServiceTest {
	
    @Autowired
    BasketServiceImpl basketServiceImpl;
    
    @Mock
    private BasketServiceImpl mockBasketService;
    
    @Autowired
    BasketItemServiceImpl basketItemServiceImpl;
    
	Gson gson = new Gson();

	@Test
	public void combineBasketTest() throws JsonIOException, IOException {
		
		Basket authBasket = gson.fromJson(new FileReader("src/test/resources/authBasket.json"), Basket.class);
		Basket anonBasket = gson.fromJson(new FileReader("src/test/resources/anonBasket.json"), Basket.class);
		
		authBasket = basketServiceImpl.combineBaskets(authBasket, anonBasket);
		
		assertEquals(6, authBasket.getBasketContent().size());
		assertEquals(2, authBasket.getBasketContent().get(4).getQuantity());
		assertEquals(1, authBasket.getBasketContent().get(5).getQuantity());

	}
	
	@Test
	public void calculateBasketPriceTest() throws JsonIOException, IOException {
		Basket anonBasket = gson.fromJson(new FileReader("src/test/resources/anonBasket.json"), Basket.class);
		
		assertEquals(503.00, basketServiceImpl.calculateBasketPrice(anonBasket), 0.01);
		
	}
	
	@Test
	public void getBasketItemByProductIdTest() throws JsonIOException, IOException {
		Basket authBasket = gson.fromJson(new FileReader("src/test/resources/authBasket.json"), Basket.class);
		BasketItem basketItem = basketServiceImpl.getBasketItemByProductId(authBasket.getBasketContent(), 2L);
		
		assertEquals("Game Console", basketItem.getProduct().getName());
		
	}
	


}
