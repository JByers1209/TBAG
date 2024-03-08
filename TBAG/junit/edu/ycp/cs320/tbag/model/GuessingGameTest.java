package edu.ycp.cs320.tbag.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.tbag.model.GuessingGame;

public class GuessingGameTest {
	private GuessingGame model;
	
	@Before
	public void setUp() {
		model = new GuessingGame();
	}
	
	@Test
	public void testSet_Get_Min() {
		model.setMin(1);
		assertEquals(1, model.getMin());
	}
	
	@Test
	public void testSet_Get_Max() {
		model.setMax(100);
		assertEquals(100, model.getMax());
	}
	
	@Test
	public void testIsDone() {
		model.setMax(56);
		model.setMin(24);
		assertEquals(false, model.isDone());
	}
	
	@Test
	public void testGetGuess() {
		model.setMax(98);
		model.setMin(26);
		assertEquals(62, model.getGuess());
	}
	
	@Test
	public void testSetIsLessThan() {
		model.setIsLessThan(65);
		assertEquals(64, model.getMax());
	}
	
	@Test
	public void testSetIsGreaterThan() {
		model.setIsGreaterThan(73);
		assertEquals(74, model.getMin());
	}
}
