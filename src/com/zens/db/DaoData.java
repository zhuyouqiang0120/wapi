package com.zens.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONObject;

/**
 * 查询catalogDB库，获取影片信息
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月14日 下午12:59:09
 */
public class DaoData {
	 
	JdbcConnect jdbcConnect = new JdbcConnect();
	Statement stat;
	
//SELECT o.OFF_ID,o.SRV_NAME,s.SRV_TYPE,o.AST_RUNTIME,m.AST_CONTENTNAME,o.AST_GENRE,o.AMS_PROVIDER,o.SRV_CODE,o.AST_TITLE 
	//FROM T_RS_OFFERING o, T_RS_ASSET_APP_MOVIE m, T_RS_SERVICE s WHERE m.OFF_ID = o.OFF_ID AND s.SRV_NAME = o.SRV_NAME AND o.OFF_ID = '2972860'
	
	public String getRsOffering(String offId) throws SQLException{//2972860
		String strSql ="SELECT o.OFF_ID,o.SRV_NAME,o.AST_RUNTIME,o.AST_GENRE,o.AMS_PROVIDER,o.AMS_PROVIDER,o.AST_TITLE FROM t_rs_offering o WHERE o.OFF_ID = '"+ offId +"'";
		PreparedStatement  pstmt = jdbcConnect.getConnectionMysql().prepareStatement(strSql);

		ResultSet  rs = pstmt.executeQuery();
		JSONObject areajson = new JSONObject();
		while (rs.next()) {
			areajson.element("OFF_ID",  rs.getString("OFF_ID"));
			areajson.element("SRV_NAME",  rs.getString("SRV_NAME"));
			areajson.element("AST_RUNTIME", rs.getString("AST_RUNTIME"));
			areajson.element("AST_GENRE",  rs.getString("AST_GENRE"));
			areajson.element("AMS_PROVIDER",  rs.getString("AMS_PROVIDER"));
			areajson.element("AMS_PROVIDER",  rs.getString("AMS_PROVIDER"));
			areajson.element("AST_TITLE",  rs.getString("AST_TITLE"));
		}
		rs.close();
		jdbcConnect.getConnectionMysql().close();
		return areajson.toString();
	}
	
	public String getRsServerice(String srvname) throws SQLException{
		String strSql ="SELECT s.SRV_TYPE FROM t_rs_service s WHERE  s.SRV_NAME  = '"+ srvname +"'";
		PreparedStatement  pstmt = jdbcConnect.getConnectionMysql().prepareStatement(strSql);

		ResultSet  rs = pstmt.executeQuery();
		JSONObject areajson = new JSONObject();
		while (rs.next()) {
			areajson.element("SRV_TYPE", rs.getString("SRV_TYPE"));
		}
		rs.close();
		jdbcConnect.getConnectionMysql().close();
		return areajson.toString();
	}


	public String getReAssetAppMovie(String offId) throws SQLException {
		// TODO 自动生成的方法存根
		String strSql ="SELECT m.AST_CONTENTNAME FROM  t_rs_asset_app_movie m WHERE m.OFF_ID  = '"+ offId +"'";
		PreparedStatement  pstmt = jdbcConnect.getConnectionMysql().prepareStatement(strSql);

		ResultSet  rs = pstmt.executeQuery();
		JSONObject areajson = new JSONObject();
		while (rs.next()) {
			areajson.element("AST_CONTENTNAME",  rs.getString("AST_CONTENTNAME"));
		}
		rs.close();
		jdbcConnect.getConnectionMysql().close();
		return areajson.toString();
	}
	
}
