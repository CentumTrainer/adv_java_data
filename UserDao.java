package com.centum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.centum.model.User;
import com.centum.utils.JDBCUtils;

public class UserDao {
	public int registerUser(User userObj)throws ClassNotFoundException{
		String INSERT_USERS_SQL = "Insert into users(first_name,last_name,username,password) values(?,?,?,?)";
		int result=0;
		try (Connection con=JDBCUtils.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT_USERS_SQL)){
			pstmt.setString(1, userObj.getFirstname());
			pstmt.setString(2, userObj.getLastname());
			pstmt.setString(3, userObj.getUsername());
			pstmt.setString(4, userObj.getPassword());
			
			System.out.println(pstmt);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
