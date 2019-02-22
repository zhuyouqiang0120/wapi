package com.zens.controller;

import java.util.List;
import java.io.IOException;

import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import com.zens.db.DBUtils;
import com.zens.db.DaoData;
import com.zens.utils.DownloadImg;
import com.zens.utils.HttpRequest;

/**
 * 为您推荐
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月28日 上午9:57:48
 */

public class CultureJDPlayController extends Controller {

	HttpRequest request = new HttpRequest();
	JSONObject jsonObject = new JSONObject();

	DBUtils dbUtils = new DBUtils();
	DaoData daoData = new DaoData();
	DownloadImg downloadImg = new DownloadImg();

	public void Play() throws IOException {
		// redirect("/play_new.html");

		String offID = getPara("offID");
		List<Record> play = dbUtils.Play(offID);// table_offing
		String run_time = play.get(0).getStr("AST_RUNTIME");
		String content_name = play.get(0).getStr("AST_CONTENTNAME");
		if (play.size() == 0) {
			redirect("/error.html");
		} else {
			this.setAttr("offID", offID);
			this.setAttr("run_time", run_time);
			this.setAttr("content_name", content_name);
			this.setAttr("play", play.get(0));
			render("/play.jsp");
			//redirect("/play.html?offID=" + offID + "&run_time=" + run_time + "&content_name="+ content_name);
		}

	}

	// {"elapsed":3,"responseJSON":{"result":[{"SRV_CODE":"00003","AST_RUNTIME":"01:16:54","AST_GENRE":"嘉定文化云","AST_TITLE":"缤纷的铜管乐器","AMS_PROVIDER":"JDWG","SRV_NAME":"FOD-TV","SRV_TYPE":"2017-01-13
	// 23:23:23","OFF_ID":"5648333","AST_CONTENTNAME":"JDWHY-TGYQ.ts"}]},"msg":"","success":"1"}

	public void getParam() throws IOException, Exception {
		long time1 = System.currentTimeMillis();

		String offID = getPara("offID");

		JSONObject result = new JSONObject();

		List<Record> play = dbUtils.Play(offID);// table_offing
		/*
		 * String program = daoData.getRsOffering(offID); String service =
		 * daoData.getRsServerice(JSONObject.fromObject(program).getString(
		 * "SRV_NAME")); String asset = daoData.getReAssetAppMovie(offID);
		 * 
		 * result.element("program", program); result.element("service",
		 * service); result.element("asset", asset);
		 */
		result.element("result", JsonKit.toJson(play));

		jsonObject.element("responseJSON", result);

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJson(jsonObject);
		// renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}
}
