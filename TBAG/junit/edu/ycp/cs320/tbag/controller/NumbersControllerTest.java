package edu.ycp.cs320.tbag.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.tbag.controller.NumbersController;
import edu.ycp.cs320.tbag.model.Numbers;

public class NumbersControllerTest {
	private NumbersController controller;
	
	@Before
	public void setUp() {
		controller = new NumbersController();
	}
	
	@Test
	public void testAdd() {
		assertEquals(12.5, controller.add(1.0, 5.2, 6.3), 0.001);
	}
	
	@Test
	public void testMultiply() {
		assertEquals(10.4, controller.multiply(2.0, 5.2), 0.001);
	}
}
