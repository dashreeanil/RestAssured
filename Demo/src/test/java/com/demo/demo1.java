package com.demo;

import org.apache.logging.log4j.*;
import org.testng.annotations.Test;

public class demo1 {
	
	Logger log = LogManager.getLogger(demo1.class.getName());
	
	@Test
	public void loggerTest()
	{
		log.debug("Hello World");
	}

}
