package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.poscoict.mysite.vo.UserVo;

public class UserDao {
	
	
	public boolean update(Long no, String name, String password, String gender) {
		boolean result = false;
	     Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;

	      try {
	         conn = getConnection();
	         String sql;
	         
	         // 3. SQL 준비
	         if(password.isBlank()) {
	        	 sql = "update user set name = ? , gender = ? where no=?";
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, name);
		         pstmt.setString(2, gender);
		         pstmt.setLong(3, no);
	         }else {
	        	 sql = "update user set name = ?, password = ? , gender = ? where no=?";
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, name);
		         pstmt.setString(2, password);
		         pstmt.setString(3, gender);
		         pstmt.setLong(4, no);
	         }
	         
				
				int count = pstmt.executeUpdate();
				result = count == 1;
	   
	         // 5. SQL 실행
	      } catch (SQLException e) {
	         System.out.print("error : " + e.getMessage());
	      } finally {
	         // 자원정리
	         try {
	            if (rs != null) {
	               rs.close();
	            }
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return result;
	}	
	
	
	
	
	
	public UserVo findByEmailAndPassword(String email, String password) {
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      UserVo result = null;
	      try {
	         conn = getConnection();
	         
	         // 3. SQL 준비
	         String sql = "select no, name from user where email=? and password=?";
	         pstmt = conn.prepareStatement(sql);

	         // 4. 바인딩
	         pstmt.setString(1, email);
	         pstmt.setString(2, password);
	         
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	            Long no = rs.getLong(1);
	            String name = rs.getString(2);
	            
	            result = new UserVo();
	            result.setNo(no);
	            result.setName(name);
	            
	         }
	   
	         // 5. SQL 실행
	      } catch (SQLException e) {
	         System.out.print("error : " + e.getMessage());
	      } finally {
	         // 자원정리
	         try {
	            if (rs != null) {
	               rs.close();
	            }
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return result;
	   }
	
	
	public boolean insert(UserVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					" insert" +
					"   into user" +
					" values(null, ?, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;
	}
	
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
	Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.0.73:3307/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}





	public UserVo findByNo(Long no) {
	     Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      UserVo result = null;
	      try {
	         conn = getConnection();
	         
	         // 3. SQL 준비
	         String sql = "select name, email, gender from user where no=?";
	         pstmt = conn.prepareStatement(sql);

	         // 4. 바인딩
	         pstmt.setLong(1, no);
	         
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	            String name = rs.getString(1);
	            String email = rs.getString(2);
	            String gender = rs.getString(3);
	         
	            
	            result = new UserVo();
	            result.setNo(no);
	            result.setName(name);
	            result.setEmail(email);
	            result.setGender(gender);
	         }
	   
	         // 5. SQL 실행
	      } catch (SQLException e) {
	         System.out.print("error : " + e.getMessage());
	      } finally {
	         // 자원정리
	         try {
	            if (rs != null) {
	               rs.close();
	            }
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return result;
	}	
}
