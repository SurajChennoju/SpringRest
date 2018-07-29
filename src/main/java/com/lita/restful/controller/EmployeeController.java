package com.lita.restful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.lita.restful.model.Employee;
import com.lita.restful.service.EmployeeServiceImpl;

@RestController
public class EmployeeController {
	
	@Autowired
    EmployeeServiceImpl empservice;  
	
    public EmployeeServiceImpl getEmpSer() {
		return empservice;
	}


	public void setEmpSer(EmployeeServiceImpl empSer) {
		this.empservice = empservice;
	}


	//---------------------------------------------------------------------------------------------   
    //-----------Retrieve All Users-----
    @RequestMapping(value = "/user", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Employee>> listAllUsers() 
    {
        List<Employee> employees = empservice.findAllUsers();
        if(employees.isEmpty())
        {
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }
 
 
    //-----------Retrieve Single User------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getUser(@PathVariable("id") long id)
    {
        System.out.println("Fetching User with id " + id);
        Employee user = empservice.findById(id);
        if (user == null) 
        {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(user, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a User----------
 
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Employee user,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getName());
 
        if (empservice.isUserExist(user)) 
        {
            System.out.println("A User with name " + user.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        empservice.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    //------------------- Update a User ----------
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateUser(@PathVariable("id") long id, @RequestBody Employee user) 
    {
        System.out.println("Updating User " + id);
         
        Employee currentUser = empservice.findById(id);
         
        if (currentUser==null) 
        {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        
       // currentUser.setName(user.getName());
        currentUser.setId(user.getId());
        currentUser.setAge(user.getAge());
       // currentUser.setSalary(user.getSalary());
       
        empservice.updateUser(currentUser);
        return new ResponseEntity<Employee>(currentUser, HttpStatus.OK);
    }
 
    //------------------- Delete a User -------
     //-------------working---
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteUser(@PathVariable("id") long id) 
    {
        System.out.println("Fetching & Deleting User with id " + id);
 
        Employee user = empservice.findById(id);
        if (user == null) 
        {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
 
        empservice.deleteUserById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
 
     
    //------------------- Delete All Users ------------
    //---------------working
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteAllUsers() 
    {
        System.out.println("Deleting All Users");
        empservice.deleteAllUsers();
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
}
