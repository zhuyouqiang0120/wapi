package com.zens.controller;

import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.zens.db.DBUtils;
import com.zens.utils.DownloadImg;
import com.zens.utils.HttpRequest;

/**
 * 
 * zyq zhuyq@zensvision.com 2016年3月23日 下午4:24:18
 */

public class CultureJDPicController extends Controller {

	HttpRequest request = new HttpRequest();
	JSONObject jsonObject = new JSONObject();

	DBUtils dbUtils = new DBUtils();
	DownloadImg downloadImg = new DownloadImg();

	public void indexPic() throws IOException {
		// http://localhost:8080/wapi/CultureCloud_JD/pic/IndexPic
		long time1 = System.currentTimeMillis();

		JSONObject bgobject = new JSONObject();
		JSONArray index_picArray = new JSONArray();
		JSONArray main_picArray = new JSONArray();

		String siteID = ResourceBundle.getBundle("url").getString("siteID");

		List<Record> bgIP = dbUtils.getPicIP();// 图片服务器IP
		String pic_ip = bgIP.get(0).getStr("PIC_IP");

		List<Record> bgurl = dbUtils.getBgPic(siteID);// 背景图
		if (bgurl.size() != 0) {
			for (int i = 0; i < bgurl.size(); i++) {
				JSONObject bgjson = new JSONObject();
				bgjson.element("id", bgurl.get(i).getStr("ID"));
				bgjson.element("bgname", bgurl.get(i).getStr("NAME"));
				bgjson.element("bgurl", pic_ip + bgurl.get(i).getStr("UPLOAD_URL"));
				index_picArray.element(bgjson);
			}
		}

		bgobject.element("bg_pic", index_picArray);

		List<Record> mainurl = dbUtils.getSubPic(siteID);// 四张栏目图
		if (mainurl.size() != 0) {
			for (int i = 0; i < mainurl.size(); i++) {
				JSONObject subjson = new JSONObject();
				subjson.element("subid", mainurl.get(i).getStr("ID"));
				subjson.element("subname", mainurl.get(i).getStr("NAME"));
				subjson.element("subpicid", mainurl.get(i).getStr("SPE_PIC_ID"));
				subjson.element("suburl", pic_ip + mainurl.get(i).getStr("UPLOAD_URL"));
				main_picArray.element(subjson);
			}
		}
		bgobject.element("sub_pic", main_picArray);

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl")
				+ "/tpsActivity/activityIndex.do?activityType=&pageIndex=0&pageNum=5";

		String data = request.sendGet(jdurl);

		// 下载并替换 activityIconUrl属性
		JSONObject resjson = JSONObject.fromObject(data);
		JSONArray datajson = JSONArray.fromObject(resjson.get("data"));

		for (Object o : datajson) {
			JSONObject act = (JSONObject) o;
			String pic = act.get("activityIconUrl").toString();
			String img = downloadImg.Setimg(pic);
			act.element("activityIconUrl", img);
			act.remove("activityEndTime");
			act.remove("activitySalesOnline");
			act.remove("activityAddress");
			act.remove("activityIsReservation");
			act.remove("activityName");
			act.remove("activityStartTime");
		}

		bgobject.element("force", datajson);

		JSONObject result = new JSONObject();
		 result.element("result", bgobject);
		//result.element("result", bgobject.toString().replace("http://172.11.7.58/group1/M00/00", "img"));

		jsonObject.element("responseJSON", result);

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}
}
