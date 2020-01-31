package com.quickpark.in.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.quickpark.in.model.User;
import com.quickpark.in.model.Property;
@Repository
public class PropertyAvailableStatusDaoImple implements PropertyAvailableStatusDao {
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setJdbctemplate(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	
	
	@Override
	public int propertyAvaliablesatus(int status1) {
		
		String userName=(String) request.getSession().getAttribute("userName");
		String sql="select user_id  from user where user_name=?";
		String sql1="update property set available_status=?  where user_id=?";
		User p=jdbctemplate.queryForObject(sql, new Object[]{userName},new  RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User p1=new User();
				p1.setUserId(rs.getInt(1));
				
				return p1;
			}});
			int userid=p.getUserId();
			
			int i=jdbctemplate.update(sql1, new Object[] {status1,userid});
			
			
		
		return i;
	}
	
	@Override
	public User selectAll() {
		 String userName=(String) request.getSession().getAttribute("userName");
		String sql="select * from user where user_name=?";
		User user=null;
		try {
			user=jdbctemplate.queryForObject(sql, new Object[]{userName},new  RowMapper<User>(){
			
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User p1=new User();
				p1.setUserId(rs.getInt(1));
				p1.setUserName(rs.getString(2));
				p1.setFirstName(rs.getString(3));
				p1.setLastName(rs.getString(4));
				p1.setMobNo(rs.getLong(5));
				p1.setEmailId(rs.getString(6));
				p1.setAddress(rs.getString(7));
				p1.setPassword(rs.getString(8));
				return p1;
			}});
		}
		catch(Exception e)
		{
			
		}
		return user;
		
	}
	
	
	
	
	
	@Override
	public int updateDetails(User p1) {
		
		
		String sql1="update user set first_name=?,last_name=?,mobile_no=?,email_id=?,address=?,password=? where  user_name=?";
		    //String userName=(String) request.getSession().getAttribute("username");
		    
		
			int i=jdbctemplate.update(sql1, new Object[] {
					p1.getFirstName(),
					p1.getLastName(),
					p1.getMobNo(),
					p1.getEmailId(),
					p1.getAddress(),
					p1.getPassword(),
					p1.getUserName()
			});
			
			
		
		return i;
	}

	@Override
	public int delete(String username) {
		
		String sql="select user_id from user where user_name=? ";
		String sql1="update user set is_deleted=? where  user_name=?";
		String sql2="update property set is_deleted=? where  user_id=?";
		User p=null;
		int i=0;
		
		try {
			 p=jdbctemplate.queryForObject(sql, new Object[]{username},new  RowMapper<User>(){
			
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User p1=new User();
					p1.setUserId(rs.getInt(1));
					
					return p1;
				}});
			
			 i=jdbctemplate.update(sql1, new Object[] {0,username});
			 System.out.println(i);
			 i=jdbctemplate.update(sql2, new Object[] {0,p.getUserId()});
			 System.out.println(i);
			}
			catch(Exception e)
			{
				
			}
			
		
		
		return i;
	}
	

}
