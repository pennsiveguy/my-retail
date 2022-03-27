package com.pennsive.myretail.model.external.redsky;

import com.pennsive.myretail.objectbuilder.TestObjectBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.NoSuchElementException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RedskyResponseV2Test {
	private TestObjectBuilder objectBuilder = new TestObjectBuilder();
	private String title = randomAlphabetic(10);

	@Test
	public void getTitle_HappyPath() {
		assertEquals(title, objectBuilder.buildRedskyResponse(title).getBody().getTitle());
	}

	@Test(expected = NoSuchElementException.class)
	public void getTitle_MissingProduct() {
		RedskyResponseV2 response = objectBuilder.buildRedskyResponse(title).getBody();
		
		ReflectionTestUtils.setField(response, "product", null);
		response.getTitle();
	}

	@Test(expected = NoSuchElementException.class)
	public void getTitle_MissingItem() {
		RedskyResponseV2 response = objectBuilder.buildRedskyResponse(title).getBody();
		
		ReflectionTestUtils.setField(ReflectionTestUtils.getField(response, "product"), "item", null);;
		response.getTitle();
	}

	@Test(expected = NoSuchElementException.class)
	public void getTitle_MissingDescription() {
		RedskyResponseV2 response = objectBuilder.buildRedskyResponse(title).getBody();
		
		ReflectionTestUtils.setField(ReflectionTestUtils.getField(ReflectionTestUtils.getField(response, "product"), "item"), "productDescription", null);
		response.getTitle();
	}

	@Test(expected = NoSuchElementException.class)
	public void getTitle_MissingTitle() {
		objectBuilder.buildRedskyResponse(null).getBody().getTitle();
	}
}
