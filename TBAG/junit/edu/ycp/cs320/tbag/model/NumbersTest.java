package edu.ycp.cs320.tbag.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.tbag.model.Game;

public class NumbersTest {
	private Game model;
	
	@Before
	public void setUp() {
		model = new Game(null, null, null, null);
	}
	
	@Test
	public void testSet_Get_First() {
		model.setFirst(10.0);
		assertEquals(10, model.getFirst(), 0.001);
	}
	
	@Test
	public void testSet_Get_Second() {
		model.setSecond(5.0);
		assertEquals(5, model.getSecond(), 0.001);
	}
	
	@Test
	public void testSet_Get_Third() {
		model.setThird(1.5);
		assertEquals(1.5, model.getThird(), 0.001);
	}
	
	@Test
	public void testResult() {
		model.setResult(4.6);
		assertEquals(4.6, model.getResult(), 0.001);
	}
	

}
