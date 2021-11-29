
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 박용수
 */
public class employeeDAO {
    String strDriver = "com.mysql.cj.jdbc.Driver";
    String strURL = "jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8&serverTimezone=UTC";
    String strUser = "root";
    String strPWD = "1018pskc!!";
    
    private boolean idCheck = false;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    public void dbOpen() throws IOException{
        try{
            Class.forName(strDriver);
            conn = DriverManager.getConnection(strURL,strUser,strPWD);
        }catch(Exception e){
            System.out.println("SQLException1 : " + e.getMessage());
        }
    }
    
    public void dbClose() throws IOException{
        try{
            pstmt.close();
            conn.close();
        }catch(Exception e){
            System.out.println("SQLException2 : " + e.getMessage());
        }
    }

    //1. 로그인창: 아이디 비번 확인 메서드
	public boolean searchId( String id, String pw ) throws SQLException {
		//sql문 선언 및 실행
		String sql  = "select id, pw from employee";
		pstmt = this.conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while ( rs.next() ) {
			if ( rs.getString( "id" ).equals( id ) ) {
				if ( rs.getString( "pw" ).equals( pw ) ) {
                                    idCheck = true;
				}
			}
		}
		if( rs != null ) rs.close();
		if( pstmt != null ) pstmt.close();
		if( conn != null ) conn.close();
		
		return idCheck;
	}
	
	//2. 회원가입창: 아이디 중복확인 메서드
	public boolean checkId( String id ) throws SQLException {
		//sql문 선언 및 실행
		String sql  = "select id from employee";
		pstmt = this.conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		while ( rs.next() ) {
			if ( rs.getString( "id" ).equals( id ) ) {
                            idCheck = true;
			} 
		}
		if( rs != null ) rs.close();
		if( pstmt != null ) pstmt.close();
		if( conn != null ) conn.close();
		
		return idCheck;
	}
	
	//2. 회원가입창: 회원가입메서드
	public void createUser(String id, String pw, String name, String gender, String address, String birthday, String phonenumber, String account) 
			throws SQLException {
		String sql = "insert into employee values (?,?,?,?,?,?,?,?)";
		pstmt = this.conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		pstmt.setString(3, name);
		pstmt.setString(4, gender);
                pstmt.setString(5, address);
		pstmt.setString(6, birthday);
		pstmt.setString(7, phonenumber);
                pstmt.setString(8, account);
		rs = pstmt.executeQuery();
		
		if( rs != null ) rs.close();
		if( pstmt != null ) pstmt.close();
		if( conn != null ) conn.close();
	}	
}
