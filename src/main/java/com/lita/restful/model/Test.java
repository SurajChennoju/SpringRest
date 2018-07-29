package com.lita.restful.model;

import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lita.restful.service.EmployeeService;

//import com.lita.restful.service.EmployeeService;


public class Test {

	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("springrest-servlet.xml");

		//EmployeeDAO employeeDAO = (EmployeeDAO) context.getBean("empDAO");
		EmployeeService empSer = (EmployeeService)context.getBean("empSer");

	}
}
