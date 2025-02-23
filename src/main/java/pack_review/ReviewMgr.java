package pack_review;


import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ReviewMgr {
	
	public DBConnectionMgr pool;
	private static final String SAVEFOLDER
	 = "C:\\Project_Directory";
	
	private static String encType = "UTF-8";
	private static int maxSize = 8*1024*1024;
	
	public ReviewMgr() {
		
		try {
			pool = DBConnectionMgr.getInstance();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	// 리뷰 입력
	public void insertReview(HttpServletRequest req) {
		
		Connection objConn = null;
		PreparedStatement	objPstmt = null;
		ResultSet objRs = null;
		String sql	= null;
		MultipartRequest	multi = null;
		int fileSize = 0;
		String fileName = null;
		
		try {
			objConn = pool.getConnection();
			sql = "select max(num) from tblReview";
			objPstmt = objConn.prepareStatement(sql);
			objRs = objPstmt.executeQuery();
			
			int ref = 1;
			if(objRs.next()) ref = objRs.getInt(1)+1;
			
			File file = new File(SAVEFOLDER);
			
			if(!file.exists()) file.mkdirs();
			
			multi = new MultipartRequest(req, SAVEFOLDER, maxSize, encType, new DefaultFileRenamePolicy());
			
			if(multi.getFilesystemName("fileName") != null) {
				fileName = multi.getFilesystemName("fileName");
				fileSize = (int)multi.getFile("fileName").length();
			}
			String content = multi.getParameter("content");
			
			sql = "insert into tblReview(uName, subject, content, ref, pos, depth, regDate, pass, ip, count, fileName, fileSize)"
					+ " values (?, ?, ?, ?, 0, 0, now(), ?, ?, 0, ?, ?)";
			
			objPstmt = objConn.prepareStatement(sql);
			objPstmt.setString(1,  multi.getParameter("uName"));
			objPstmt.setString(2,  multi.getParameter("subject"));
			objPstmt.setString(3,  content);
			objPstmt.setInt(4,  ref);
			objPstmt.setString(5,  multi.getParameter("pass"));
			objPstmt.setString(6,  multi.getParameter("ip"));
			objPstmt.setString(7,  fileName);
			objPstmt.setInt(8,  fileSize);
			objPstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("SQL"+ e.getMessage());
		}catch (Exception e) {
			System.out.println("DB" + e.getMessage());
		}finally {
			pool.freeConnection(objConn, objPstmt, objRs);
		}
		
	}
	
	// 리뷰 입력 끝
	
	// List 출력
	
	public Vector<ReviewBean> getReviewList(){
		
		Vector<ReviewBean> vList = new Vector<>();
		Connection objConn = null;
		PreparedStatement	objPstmt = null;
		ResultSet objRs = null;
		String sql	= null;
		
		try {
			objConn = pool.getConnection();
			sql = "select*from tblReview order by num desc limit ?,?";
			objPstmt = objConn.prepareStatement(sql);
			objPstmt.setInt(1, 0);
			objPstmt.setInt(2, 10);
			objRs = objPstmt.executeQuery();
			
			while(objRs.next()) {
				ReviewBean bean = new ReviewBean();
				bean.setNum(objRs.getInt("num"));
				bean.setSubject(objRs.getString("subject"));
				bean.setRegDate(objRs.getString("regDate"));
				bean.setuName(objRs.getString("uName"));
				
				vList.add(bean);
			}
		}catch (Exception e) {
			System.out.println("SQL : "+e.getMessage());
		}finally {
			pool.freeConnection(objConn, objPstmt, objRs);
		}
		
		return vList;
	}
	
	// List 출력 끝
	
	// Read 시작
	
	public ReviewBean getReview(int num) {
		
		Connection objConn = null;
		PreparedStatement objPstmt = null;
		ResultSet objRs = null;
		String sql = null;
		
		ReviewBean bean = new ReviewBean();
		try {
			objConn = pool.getConnection();
			sql = "select*from tblReview where num = ?";
			objPstmt = objConn.prepareStatement(sql);
			objPstmt.setInt(1, num);
			objRs = objPstmt.executeQuery();
			
			if(objRs.next()) {
				bean.setNum(objRs.getInt("num"));
				bean.setuName(objRs.getString("uName"));
				bean.setSubject(objRs.getString("subject"));
				bean.setContent(objRs.getString("content"));
				bean.setPos(objRs.getInt("pos"));
				bean.setDepth(objRs.getInt("depth"));
				bean.setRegDate(objRs.getString("regDate"));
				bean.setPass(objRs.getString("pass"));
				bean.setCount(objRs.getInt("count"));
				bean.setFileName(objRs.getString("fileName"));
				bean.setFileSize(objRs.getInt("fileSize"));
				bean.setIp(objRs.getString("ip"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SQL : "+e.getMessage());
		} finally {
			pool.freeConnection(objConn, objPstmt, objRs);
		}
		
		return bean;
	}
	// Read 끝

}
