package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BorderVo;
import com.poscoict.mysite.vo.UserVo;

public class BorderDao {
	
	
	public List<BorderVo> findAll(int i) {
		
		List<BorderVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql = "select "
					+ "b.no, b.title, a.name, b.hit, date_format(b.reg_date, '%Y/%m/%d %H:%i:%s') as reg_date, "
					+ "b.g_no, b.o_no, b.depth, b.user_no, b.status "
					+ "from  user a, board b where a.no=b.user_no order by b.g_no desc, b.o_no limit ?, 5";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				String status = rs.getString(10);
				
				BorderVo vo = new BorderVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setGroupNo(g_no);
				vo.setOrderNo(o_no);
				vo.setDepth(depth);
				vo.setRegDate(reg_date);
				vo.setUserNo(user_no);
				vo.setUserName(name);
				vo.setStatus(status);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
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
		
		return list;
	}
	
public List<BorderVo> findAll(int i, String kwd) {
		
		List<BorderVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql = "select "
					+ "b.no, b.title, a.name, b.hit, date_format(b.reg_date, '%Y/%m/%d %H:%i:%s') as reg_date, "
					+ "b.g_no, b.o_no, b.depth, b.user_no, b.status "
				+ "from  user a, board b where a.no=b.user_no and b.title like'%"+kwd+"%' order by b.g_no desc, b.o_no limit ?, 5";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, i);
			
			System.out.println(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				String status = rs.getString(10);
				
				BorderVo vo = new BorderVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setGroupNo(g_no);
				vo.setOrderNo(o_no);
				vo.setDepth(depth);
				vo.setRegDate(reg_date);
				vo.setUserNo(user_no);
				vo.setUserName(name);
				vo.setStatus(status);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
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
		
		return list;
	}
	
	
	public boolean update(Long no, String title, String contents) {
		boolean result = false;
	     Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;

	      try {
	         conn = getConnection();
	         String sql;
	         
	         // 3. SQL 준비
	         sql = "update board set title = ? , contents = ? where no=?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, title);
	         pstmt.setString(2, contents);
	         pstmt.setLong(3, no);
	         
				
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
	
	
	
	public boolean insert(String title, String contents, Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					" insert into board values(null, ?, ?, 0 , "
					+ "ifnull((select g_no + 1 from (select max(g_no) as g_no from board) A), 1), 1, 1, now(), ?, default, default)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, no);

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
	
	public boolean insert(String title, String contents, Long no, int g_no, int o_no, int depth) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					" insert into board values(null, ?, ?, 0, ?, ?, ?, now(), ?, default, default)";
			pstmt = conn.prepareStatement(sql);
			
			System.out.println(title);
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, g_no);
			pstmt.setLong(4, o_no);
			pstmt.setLong(5, depth);
			pstmt.setLong(6, no);

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
	
	
	
	public boolean updateboard(int o_no, int g_no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					"update board set o_no = o_no + 1 where o_no > ? and g_no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, o_no);
			pstmt.setInt(2, g_no);
			
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
	
	
	public boolean increasereplycnt(Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					"update board set reply_cnt = reply_cnt + 1 where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);

			
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
	
	
	public boolean hit(Long no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					"update board set hit = hit + 1 where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);

			
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
	
	
	
	public boolean increasereplycnt1(int depth, int g_no) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					"update board set reply_cnt = reply_cnt + 1 where depth = ? and g_no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, depth);
			pstmt.setInt(2, g_no);

			
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
	
	
	
	public BorderVo view(Long no) {
		boolean result = false;
		BorderVo vo = new BorderVo();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql = "select title, contents, user_no from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {

				String title = rs.getString(1);
				String contents = rs.getString(2);
				Long user_no = rs.getLong(3);
			
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserNo(user_no);
				
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
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
		
		return vo;
	}
	
	
	
	public BorderVo findByNo(Long no) {
	     Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      BorderVo result = null;
	      try {
	         conn = getConnection();
	         
	         // 3. SQL 준비
	         String sql = "select g_no, o_no, depth, reply_cnt  from board where no = ?";
	         pstmt = conn.prepareStatement(sql);

	         // 4. 바인딩
	         pstmt.setLong(1, no);
	         
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	            int g_no = rs.getInt(1);
	            int o_no = rs.getInt(2);
	            int depth = rs.getInt(3);
	        	int reply_cnt = rs.getInt(4);
	            
	            result = new BorderVo();
	            result.setGroupNo(g_no);
	            result.setOrderNo(o_no);
	            result.setDepth(depth);
	            result.setReplyCnt(reply_cnt);
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
	
	
	
	
	
	public boolean delete(BorderVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					"update board set status = 'deleted' where no = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			
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
	

	public int count() {
		boolean result = false;
		int cnt = 0;
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			  conn = getConnection();
		         
		         // 3. SQL 준비
		         String sql = "select count(*) from board;";
		         pstmt = conn.prepareStatement(sql);

		         
		         rs = pstmt.executeQuery();
		         if(rs.next()) {
		            cnt = rs.getInt(1);

		         }
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
		
		return cnt;	
	}	
	
	
	public int count(String kwd) {
		boolean result = false;
		int cnt = 0;
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			  conn = getConnection();
		         
		         // 3. SQL 준비
			  String sql = "select count(*) from  board where title  like '%"+kwd+"%' order by g_no desc, o_no;";
		         pstmt = conn.prepareStatement(sql);
		    
		         rs = pstmt.executeQuery();
		         if(rs.next()) {
		            cnt = rs.getInt(1);

		         }
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
		
		return cnt;	
	}	

	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
	Class.forName("org.mariadb.jdbc.Driver");
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.0.42:3307/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}



}
