package com.in28minutes.junit.helper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringHelperTest {
	
	@BeforeAll
	static void beforeClass()
	{
		System.out.println("before class!");
	}
	
	@AfterAll
	static void afterClass()
	{
		System.out.println("after class!");
	}
	
	@BeforeEach
	void setUp()
	{
		System.out.println("setUp!");
	}
	
	@AfterEach
	void tearDown()
	{
		System.out.println("tearDown!");
	}

	@Test
	void truncateAInFirst2PositionsTest() {
		
		// assertEquals(expected, actual)
		
		StringHelper stringHelper = new StringHelper();
		
		assertEquals("BB", stringHelper.truncateAInFirst2Positions("AABB"));
		
	}
	
	@Test
	void areFirstAndLastTwoCharactersTheSameTest()
	{
		StringHelper stringHelper = new StringHelper();
		
		assertFalse(stringHelper.areFirstAndLastTwoCharactersTheSame("ABCD"));
	}

}
