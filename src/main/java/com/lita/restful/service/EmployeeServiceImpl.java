package com.lita.restful.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lita.restful.model.Employee;

public class EmployeeServiceImpl implements EmployeeService {
	
	
	private JdbcTemplate jdbcTemplate;
	
	private static final AtomicLong counter = new AtomicLong();
	
	//private static List<Employee> employees;
	
	@Autowired
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@SuppressWarnings("unchecked")
	public List<Employee> findAllUsers()
	{
		
		    System.out.println("fetching all employees");
	        String sql = "select * from Employee";
	        //List<Employee> listOfEmployee = new ArrayList<Employee>();

	        List<Employee> listofemployees   =  getJdbcTemplate().query(sql, new RowMapper<Employee>()
	                                   {
	        										public Employee mapRow(ResultSet rs, int rowNum) throws SQLException
	        										{
     													Employee employee = new Employee();
	        											employee.setId(rs.getInt("id"));
	        											employee.setName(rs.getString("name"));
	        											employee.setAge(rs.getInt("age"));
	        											employee.setSalary(rs.getDouble("salary"));
	        											employee.setGender(rs.getString("gender"));
	        											return employee;
	        										}
	                                   });

	        return listofemployees;
	    }
	
	
	public Employee findById(long id) 
	{
		 // Getting a particular Employee
	        System.out.println("In find single user");
	        String sql = "select * from Employee where id=?";
	        Employee employee = (Employee) jdbcTemplate.queryForObject(sql, new Object[]{ id }, new RowMapper<Employee>()
	        {
	            @Override
	            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException 
	            {
	                Employee employee = new Employee();
	                employee.setId(rs.getInt("id"));
	                employee.setName(rs.getString("name"));
	                employee.setAge(rs.getInt("age"));
	                employee.setSalary(rs.getDouble("salary"));
	                employee.setGender(rs.getString("gender"));
	               
	                return employee;
	            }
	        });
	        return employee;
	    }
	
	
	
	  
	 public Employee findByName(String name) 
	 {/*
		for(Employee user : users)
		{
			if(user.getName().equalsIgnoreCase(name))
			{
				return user;
			}
		}*/
		return null;
	}
	
	public void saveUser(Employee emp) 
	{
		    String sql = "insert into employee(id,name,age,gender,salary) values(?,?,?,?,?)";
	        jdbcTemplate.update(sql,new Object[]{emp.getId(),emp.getName(),emp.getAge(),emp.getGender(),emp.getSalary()});
	        System.out.println("Creation done");
	
	}

	public void updateUser(Employee emp)
	{
		String sql = "update employee set age = ? where id = ?";
        jdbcTemplate.update(sql,new Object[]{emp.getAge(),emp.getId()});
        System.out.println("Updation done");
	}

	/*public void deleteUserById(long id) 
	 * {
		
		for (Iterator<Employee> iterator = users.iterator(); iterator.hasNext(); )
		 {
		    Employee user = iterator.next();
		    if (user.getId() == id) 
		    {
		        iterator.remove();
		    }
		}
	}*/
	// Deletion of a particular Employee
		public void deleteUserById(long id)
		{
		    String sql = "delete from employee where id=?";
		    jdbcTemplate.update(sql, new Object[]{ id });
		    System.out.println("deletion done");
		}

	public boolean isUserExist(Employee user) 
	{
		return findByName(user.getName())!=null;
	}
	
	public void deleteAllUsers()
	{
		//employees.clear();
	}

}
