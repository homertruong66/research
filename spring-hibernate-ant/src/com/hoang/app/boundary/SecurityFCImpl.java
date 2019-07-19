package com.hoang.app.boundary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.hoang.app.dto.AuthenticationDTO;

/**
 * 
 * @author Hoang Truong
 */

public class SecurityFCImpl implements SecurityFC {

	protected Logger logger = Logger.getLogger(SecurityFCImpl.class);
	
	private DataSource dataSource;
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public AuthenticationDTO authenticate(String username) {
		logger.info("authenticate('" + username + "')");
		
		AuthenticationDTO authenticationDTO = null;
		Connection connection = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();

			String authenticationSQL = 	"Select ID, PASSWORD " + 
										"From USER " + 
										"Where USER_NAME = '" + username + "'";

			String roleSQL = 	"Select r.NAME " + 
								"From USER_ROLE ur, ROLE r " + 
								"Where ur.USER_ID = ? " + 
								"  And ur.ROLE_ID = r.ID ";

			stmt = connection.createStatement();			
			rs = stmt.executeQuery(authenticationSQL);
			if (rs.next()) { // should only be one
				long userId = rs.getLong(1);
				String password = rs.getString(2);
				authenticationDTO = new AuthenticationDTO();
				authenticationDTO.setUsername(username);
				authenticationDTO.setPassword(password);
				
				pstmt = connection.prepareStatement(roleSQL);
				pstmt.setLong(1, userId);
				ResultSet rs1 = pstmt.executeQuery();
				List<String> authorities = new ArrayList<String>();				
				while (rs1.next()) {					
					authorities.add(rs1.getString(1));					
				}
				authenticationDTO.setAuthorities(authorities);			
			} 
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			try {
				rs.close();
				stmt.close();
				if (pstmt != null) { pstmt.close(); }
				connection.close();
			} 
			catch (Exception e) {}
		}				

		return authenticationDTO;
	}
}

