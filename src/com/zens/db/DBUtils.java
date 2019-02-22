package com.zens.db;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月29日 下午1:42:57
 */

public class DBUtils {
	private static final String T_CS_PICTURE = "t_cs_picture";
	private static final String T_CS_PICTURE_IP = "t_cs_picture_ip";
	private static final String T_CS_BACKGROUND = "t_cs_background";
	private static final String T_CS_APPSUBJECT = "t_cs_appsubject";
	private static final String  T_RS_ASSET_APP_MOVIE = "t_rs_asset_app_movie";
	private static final String  T_RS_SERVICE = "t_rs_service";
	// private static final String T_CS_SPECIAL = "T_CS_SPECIAL";
	private static final String T_RS_ASSET_APP_POSTER = "t_rs_asset_app_poster";
	private static final String T_RS_OFFERING = "t_rs_offering";

	public List<Record> getBgPic(String siteid) {
		String sql = "select p.ID,p.NAME,p.THUMBNAIL_URL,p.UPLOAD_URL from " + T_CS_BACKGROUND
				+ " b, "+T_CS_PICTURE+" p where p.ID = b.PIC_ID and b.SITE_ID = '" + siteid + "'";
		System.out.println("getBgPic: " + sql);
		return Db.find(sql);
	}

	//
	/*
	 * public List<Record> getSubPic(String siteid) { String sql =
	 * "SELECT a.ID,a.`NAME`,a.BLUR_PIC_ID,a.FOCUS_PIC_ID,a.TABLE_OFFERING,s.SPE_PIC_ID, p.UPLOAD_URL FROM "
	 * + T_CS_APPSUBJECT + " a," + T_CS_SPECIAL + " s, " + T_CS_PICTURE +
	 * " p WHERE s.LINK_APPSBJ_ID = a.ID AND p.ID = s.SPE_PIC_ID AND  a.SITE_ID = '"
	 * + siteid + "' ORDER BY a.ID ASC limit 0,4"; System.out.println(
	 * "getSubPic: " + sql); return Db.find(sql); }
	 */

	public List<Record> getSubPic(String siteid) {
		String sql = "SELECT a.ID,a.`NAME`,a.TABLE_OFFERING, p.UPLOAD_URL FROM " + T_CS_APPSUBJECT + " a, "
				+ T_CS_PICTURE + " p, " + T_CS_BACKGROUND + " b"
				+ " where b.APPSBJ_ID = a.ID AND b.PIC_ID = p.ID AND a.SITE_ID = '" + siteid
				+ "' ";
		//System.out.println("getSubPic: " + sql);
		return Db.find(sql);
	}

	public List<Record> getPicIP() {
		String sql = "select * from " + T_CS_PICTURE_IP + " order by ID DESC limit 0,1";
	//	System.out.println("getPicIP: " + sql);
		return Db.find(sql);
	}

	public List<Record> getSubs(String siteid) {
		String sql = "SELECT a.ID,a.`NAME`,a.FOCUS_PIC_ID,a.BLUR_PIC_ID from " + T_CS_APPSUBJECT
				+ " a WHERE a.SITE_ID = '" + siteid + "' LIMIT 3,4";
	//	System.out.println("getSubs: " + sql);
		return Db.find(sql);
	}

	public String getUrl(String picid) {
		String sql = "SELECT a.UPLOAD_URL from " + T_CS_PICTURE + " a WHERE  a.ID = '" + picid + "' ";
	//	System.out.println("getUrl: " + sql);
		return Db.queryStr(sql);
	}

	public List<Record> getTbale(String subid) {
		String sql = "SELECT * from " + T_CS_APPSUBJECT + " a WHERE  a.ID = '" + subid + "' ";
	//	System.out.println("getTbale: " + sql);
		return Db.find(sql);
	}

	public List<Record> getCount(String tablename) {
		String sql = "SELECT Count(*) from " + tablename;
	//	System.out.println("getCount: " + sql);
		return Db.find(sql);
	}


	public List<Record> getSubDetail(String tablename, String pageIndex, String pageNum) {
		String sql = "SELECT o.OFF_ID,o.AST_TITLE,o.AST_RUNTIME,o.AST_GENRE,o.AMS_ASSETID, o.AST_POSTERIMG from " + tablename
				+ " o LIMIT " + pageIndex + "," + pageNum;
	//	System.out.println("getSubDetail: " + sql);
		return Db.find(sql);
	}

	public List<Record> getPoster(String offid) {
		String sql = "SELECT p.AST_URL,p.AST_POSTERNAME from " + T_RS_ASSET_APP_POSTER + " p WHERE p.OFF_ID = " + offid;
	//	System.out.println("getPoster: " + sql);
		return Db.find(sql);
	}

	public List<Record> Play(String offid) {
		String sql = "SELECT o.OFF_ID,o.SRV_NAME,s.SRV_TYPE,o.AST_RUNTIME,m.AST_CONTENTNAME,o.AST_GENRE,o.AMS_PROVIDER,o.SRV_CODE,o.AST_TITLE FROM "
				+ T_RS_OFFERING
				+ " o, "+T_RS_ASSET_APP_MOVIE+" m, "+T_RS_SERVICE+" s WHERE m.OFF_ID = o.OFF_ID AND s.SRV_NAME = o.SRV_NAME AND o.OFF_ID = '"
				+ offid + "'";
	//	System.out.println("Play: " + sql);
		return Db.find(sql);
	}

	public List<Record> getSZZGSubs(String sZZGID) {
		String sql = "SELECT a.ID,a.`NAME`,a.FOCUS_PIC_ID,a.BLUR_PIC_ID from " + T_CS_APPSUBJECT
				+ " a WHERE a.PARENT_APP_ID = '" + sZZGID + "'";
	//	System.out.println("getSubs: " + sql);
		return Db.find(sql);
	}
}
