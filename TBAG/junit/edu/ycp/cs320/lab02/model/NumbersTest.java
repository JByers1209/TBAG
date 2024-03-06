package edu.ycp.cs320.lab02.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab02.model.Numbers;

public class NumbersTest {
	private Numbers model;
	
	@Before
	public void setUp() {
		model = new Numbers(null, null, null, null);
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
