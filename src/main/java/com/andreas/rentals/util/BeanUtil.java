package com.andreas.rentals.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BeanUtil {

	public static Object getBeanByName( String name ) {
		try
		{
			ApplicationContext context = new FileSystemXmlApplicationContext("src\\applicationContext.xml");
			return context.getBean(name);
		}
		catch( Exception e ) 
		{
			return null;
		}
	}
}