package com.zens.controller;

import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import com.zens.db.DBUtils;
import com.zens.utils.DownloadImg;
import com.zens.utils.HttpRequest;

/**
 * 为您推荐
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年3月28日 上午9:57:48
 */

public class CultureJDSubController extends Controller {

	HttpRequest request = new HttpRequest();
	JSONObject jsonObject = new JSONObject();

	DBUtils dbUtils = new DBUtils();
	DownloadImg downloadImg = new DownloadImg();

	public void subDetail() throws IOException, Exception {
		long time1 = System.currentTimeMillis();

		String appsbjID = getPara("appsbjID");
		String pageIndex = getPara("pageIndex");
		String pageNum = getPara("pageNum");

		JSONObject result = new JSONObject();
		JSONObject sbdetail = new JSONObject();

		String siteId = ResourceBundle.getBundle("url").getString("siteID");
		List<Record> bgIP = dbUtils.getPicIP();// 图片服务器IP
		String pic_ip = bgIP.get(0).getStr("PIC_IP");

		List<Record> subs = dbUtils.getSubs(siteId);// 所有推荐栏目信息
		for (Object o : subs) {
			Record rec = (Record) o;
			String blue = dbUtils.getUrl(rec.getStr("BLUR_PIC_ID"));
			String focus = dbUtils.getUrl(rec.getStr("FOCUS_PIC_ID"));
			rec.set("BLUR_PIC_ID", pic_ip + blue);
			rec.set("FOCUS_PIC_ID", pic_ip + focus);
		}

		List<Record> tables = dbUtils.getTbale(appsbjID);// table_offing

		if (tables.size() != 0 && appsbjID != "") {
			List<Record> specRec = dbUtils.getSubDetail(tables.get(0).getStr("TABLE_OFFERING"),
					Integer.parseInt(pageIndex) * Integer.parseInt(pageNum) + "", pageNum);// 影片列表

			for (Object o : specRec) {
				Record rec = (Record) o;
				List<Record> poster = dbUtils.getPoster(rec.getStr("OFF_ID"));
				if (poster.size() != 0) {
					byte[] blob = poster.get(0).get("AST_URL");
					String url = new String(blob);
					if (url.indexOf("http") != 0) {
						url = pic_ip + url;
					}
					rec.set("url", url);
				} else {
					rec.set("url", "Record.images/default.jpg");
				}
			}

			List<Record> countlist = dbUtils.getCount(tables.get(0).getStr("TABLE_OFFERING"));
			int count = countlist.get(0).getLong("Count(*)").intValue();// 文章count
			sbdetail.element("currPage", Integer.parseInt(pageIndex) + 1);
			sbdetail.element("nums", count);
			sbdetail.element("pages", count % Integer.parseInt(pageNum) == 0 ? count / Integer.parseInt(pageNum)
					: count / Integer.parseInt(pageNum) + 1);

			sbdetail.element("datas", JsonKit.toJson(specRec));
		}
		result.element("subdetail", sbdetail);
		result.element("subs", JsonKit.toJson(subs));

		 jsonObject.element("responseJSON", result);
		//jsonObject.element("responseJSON", result.toString().replace("http://172.11.7.58/group1/M00/00", "../../img"));

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		// render(ImgRender.render(blob));
		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}
	
	public void SZZGsubs() throws IOException, Exception {
		long time1 = System.currentTimeMillis();

		String SZZGID = getPara("SZZGID");

		JSONObject result = new JSONObject();

		List<Record> bgIP = dbUtils.getPicIP();// 图片服务器IP
		String pic_ip = bgIP.get(0).getStr("PIC_IP");

		List<Record> subs = dbUtils.getSZZGSubs(SZZGID);// 所有推荐栏目信息
		for (Object o : subs) {
			Record rec = (Record) o;
			String blue = dbUtils.getUrl(rec.getStr("BLUR_PIC_ID"));
			String focus = dbUtils.getUrl(rec.getStr("FOCUS_PIC_ID"));
			rec.set("BLUR_PIC_ID", pic_ip + blue);
			rec.set("FOCUS_PIC_ID", pic_ip + focus);
		}

		result.element("subs", JsonKit.toJson(subs));

		// jsonObject.element("responseJSON", result);
		jsonObject.element("responseJSON", result.toString().replace("http://172.11.7.58/group1/M00/00", "../../img"));

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		// render(ImgRender.render(blob));
		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}
	
	
	public void SZZGsubDetail() throws IOException, Exception {
		long time1 = System.currentTimeMillis();

		String appsbjID = getPara("appsbjID");
		String pageIndex = getPara("pageIndex");
		String pageNum = getPara("pageNum");

		JSONObject result = new JSONObject();
		JSONObject sbdetail = new JSONObject();

		List<Record> bgIP = dbUtils.getPicIP();// 图片服务器IP
		String pic_ip = bgIP.get(0).getStr("PIC_IP");


		List<Record> tables = dbUtils.getTbale(appsbjID);// table_offing

		if (tables.size() != 0 && appsbjID != "") {
			List<Record> specRec = dbUtils.getSubDetail(tables.get(0).getStr("TABLE_OFFERING"),
					Integer.parseInt(pageIndex) * Integer.parseInt(pageNum) + "", pageNum);// 影片列表

			for (Object o : specRec) {
				Record rec = (Record) o;
				List<Record> poster = dbUtils.getPoster(rec.getStr("OFF_ID"));
				if (poster.size() != 0) {
					byte[] blob = poster.get(0).get("AST_URL");
					String url = new String(blob);
					if (url.indexOf("http") != 0) {
						url = pic_ip + url;
					}
					rec.set("url", url);
				} else {
					rec.set("url", "exhibition.images/default.jpg");
				}
			}

			List<Record> countlist = dbUtils.getCount(tables.get(0).getStr("TABLE_OFFERING"));
			int count = countlist.get(0).getLong("Count(*)").intValue();// 文章count
			sbdetail.element("currPage", Integer.parseInt(pageIndex) + 1);
			sbdetail.element("nums", count);
			sbdetail.element("pages", count % Integer.parseInt(pageNum) == 0 ? count / Integer.parseInt(pageNum)
					: count / Integer.parseInt(pageNum) + 1);

			sbdetail.element("datas", JsonKit.toJson(specRec));
		}
		result.element("subdetail", sbdetail);

		// jsonObject.element("responseJSON", result);
		jsonObject.element("responseJSON", result.toString().replace("http://172.11.7.58/group1/M00/00", "../../img"));

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		// render(ImgRender.render(blob));
		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}
}
