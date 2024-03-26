package edu.ycp.cs320.tbag.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.tbag.controller.GameEngine;
import edu.ycp.cs320.tbag.model.Game;

public class NumbersControllerTest {
	private GameEngine controller;
	
	@Before
	public void setUp() {
		controller = new GameEngine();
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
