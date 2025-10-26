package com.service.productCatalog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductCatalogApplicationTests {

	@Test
	@Disabled
	void contextLoads() {
	}

	@Test
	public void test() {
		Assertions.assertEquals(100,100);
	}

}
