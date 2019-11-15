package com.lab.software.engineering.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.lab.software.engineering.model.Employee;
import com.lab.software.engineering.repository.EmployeeRepository;
@Aspect
@Configuration
public class EmployeeAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EmployeeRepository employeeService;

	@AfterReturning("execution(* com.lab.software.engineering.controller.EmployeeController.createEmployee(..))")
    public void aroundAddAdvice( JoinPoint pjp){
        Object[] arguments = pjp.getArgs();
        Employee employee = (Employee) arguments[0];
        ETRLogger.logToFile("A new employee with email " + employee.getEmail() + " has been added to database."); 
        
    }
}