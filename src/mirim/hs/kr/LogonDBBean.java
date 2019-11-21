package mirim.hs.kr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class LogonDBBean {
	private static LogonDBBean instance = new LogonDBBean();
	
	public static LogonDBBean getInstance() {
		return instance;
	}
	
	private Connection getConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/mirim";
		String user = "root";
		String password = "1234";
		
		Class.forName("com.mysql.jdbc.Driver"); // 드라이버 불러옴
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	// LogonDBBean.getInstance();
	// 싱글턴 패턴
	
	public void insertMember(LogonDataBean member) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO member VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setTimestamp(4, member.getReg_date());
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) try{ pstmt.close(); }catch(Exception e) { e.printStackTrace(); }
			if(conn != null) try{ conn.close(); }catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public int userCheck(String id, String passwd) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;
		
		try {
			conn = getConnection();
			String sql = "SELECT * FROM member WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("passwd").equals(passwd)) x = 1;
				else x = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try{ rs.close(); }catch(Exception e) { e.printStackTrace(); }
			if(pstmt != null) try{ pstmt.close(); }catch(Exception e) { e.printStackTrace(); }
			if(conn != null) try{ conn.close(); }catch(Exception e) { e.printStackTrace(); }
		}
		return x;
	}
	
	public int confirmId(String id) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = -1;
		try {
			conn = getConnection();
			String sql = "SELECT * FROM member WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) x = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try{ rs.close(); }catch(Exception e) { e.printStackTrace(); }
			if(pstmt != null) try{ pstmt.close(); }catch(Exception e) { e.printStackTrace(); }
			if(conn != null) try{ conn.close(); }catch(Exception e) { e.printStackTrace(); }
		}
		return x;
	}
	
	public LogonDataBean getMember(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LogonDataBean member = null;
		try {
			conn = getConnection();
			String sql = "SELECT * FROM member WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				member = new LogonDataBean();
				member.setId(rs.getString("id"));
				member.setPasswd(rs.getString("passwd"));
				member.setName(rs.getString("name"));
				member.setReg_date(rs.getTimestamp("reg_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try{ rs.close(); }catch(Exception e) { e.printStackTrace(); }
			if(pstmt != null) try{ pstmt.close(); }catch(Exception e) { e.printStackTrace(); }
			if(conn != null) try{ conn.close(); }catch(Exception e) { e.printStackTrace(); }
		}
		return null;
	}
	
	public void updateMember(LogonDataBean member) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "UPDATE member SET passwd = ?, name = ? WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getPasswd());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) try{ pstmt.close(); }catch(Exception e) { e.printStackTrace(); }
			if(conn != null) try{ conn.close(); }catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public int deleteMember(String id, String passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String dbpasswd = "";
		int x = -1;
		
		try {
			conn = getConnection();
			String sql = "SELECT passwd FROM member WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpasswd = rs.getString("passwd");
				
				if(dbpasswd.equals(passwd)) {
					pstmt = conn.prepareStatement("DELETE FROM member WHERE id = ?");
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					x = 1; // success
				}
				else
					x = 0; // wrong password
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) try{ pstmt.close(); }catch(Exception e) { e.printStackTrace(); }
			if(conn != null) try{ conn.close(); }catch(Exception e) { e.printStackTrace(); }
		}
		
		return x;
	}
	
}