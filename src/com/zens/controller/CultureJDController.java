package com.zens.controller;

import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.zens.db.DBUtils;
import com.zens.utils.DownloadImg;
import com.zens.utils.HttpRequest;

/**
 * 
 * zyq zhuyq@zensvision.com 2016年3月18日 下午1:23:00
 */

public class CultureJDController extends Controller {

	HttpRequest request = new HttpRequest();
	JSONObject jsonObject = new JSONObject();

	DBUtils dbUtils = new DBUtils();
	DownloadImg downloadImg = new DownloadImg();

	public void activityTag() throws IOException {
		// http://localhost:8080/wapi/CultureCloud_JD/tpsActivity/activityTag
		long time1 = System.currentTimeMillis();

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl") + "/tpsActivity/activityTag.do";

		//String data = request.sendGet(jdurl);
		String data = request.sendGet(jdurl);

		JSONObject result = new JSONObject();
		result.element("result", data);

		jsonObject.element("responseJSON", result);

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}

	public void activityIndex() throws IOException {
		// http://localhost:8080/wapi/CultureCloud_JD/tpsActivity/activityIndex?activityType=3265de62d4454fef8f5df0d903fded52&pageIndex=0&pageNum=8
		// http://localhost:8080/wapi/CultureCloud_JD/tpsActivity/activityIndex?activityType=58786a47019b4b21b786e33eea369829&pageIndex=0&pageNum=8
		long time1 = System.currentTimeMillis();

		String activityType = getPara("activityType");
		String pageIndex = getPara("pageIndex");
		String pageNum = getPara("pageNum");

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl")
				+ "/tpsActivity/activityIndex.do?activityType=" + activityType + "&pageIndex=" + pageIndex + "&pageNum="
				+ pageNum;

		String data = request.sendGet(jdurl);

		// 下载并替换 activityIconUrl属性
		JSONObject resjson = JSONObject.fromObject(data);
		JSONArray datajson = JSONArray.fromObject(resjson.get("data"));
		for (Object o : datajson) {
			JSONObject act = (JSONObject) o;
			String pic = act.get("activityIconUrl").toString();
			String img = downloadImg.Setimg(pic);
			act.element("activityIconUrl", img);
		}
		JSONObject result = new JSONObject();
		result.element("result", resjson.element("data", datajson));

		jsonObject.element("responseJSON", result);

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}

	public void activityDetail() throws IOException {
		// http://localhost:8080/wapi/CultureCloud_JD/tpsActivity/activityDetail?activityId=4f746f6daf684a979387b06bbdf60e2d
		long time1 = System.currentTimeMillis();

		String activityId = getPara("activityId");

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl") + "/tpsActivity/activityDetail.do?activityId="
				+ activityId;

		String data = request.sendGet(jdurl);

		JSONObject resjson = JSONObject.fromObject(data);
		JSONArray datajson = JSONArray.fromObject(resjson.get("data"));

		// 下载并替换 activityIconUrl属性
		JSONObject act = (JSONObject) datajson.get(0);
		String pic = act.get("activityIconUrl").toString();
		String img = downloadImg.Setimg(pic);
		act.put("activityIconUrl", img);

		// 下载并替换 activityMemo 中img属性
		String memo = act.getString("activityMemo").toString();
		if (getImgStr(memo).size() != 0) {
			String pic2 = getImgStr(memo).get(0);
			String img2 = downloadImg.Setimg(pic2);
			act.put("activityMemo", memo.replace(pic2, img2));
		}

		JSONObject result = new JSONObject();
		result.element("result", resjson);

		jsonObject.element("responseJSON", result);

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}

	public void activityOrder() throws IOException {
		// http://localhost:8080/wapi/CultureCloud_JD/tpsActivity/activityOrder.do?deviceId=&msgToken=&userId=c6957fa977e04ceea1142355df50873f
		// &activityId=9961b979664a4aaba49c3c0b90dba4fd&seatId=&bookCount=2&orderMobileNum=15000209901

		// http://139.196.6.251:8081/tpsActivity/activityOrder.do?deviceId=&msgToken=&userId=c6957fa977e04ceea1142355df50873f
		// &activityId=9961b979664a4aaba49c3c0b90dba4fd&seatId=&bookCount=2&orderMobileNum=15000209901

		long time1 = System.currentTimeMillis();

		String userId = getPara("userId");
		String activityId = getPara("activityId");
		String seatId = getPara("seatId");
		String bookCount = getPara("bookCount");
		String orderMobileNum = getPara("orderMobileNum");

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl")
				+ "/tpsActivity/activityOrder.do?deviceId=&msgToken=&userId=" + userId + "&activityId=" + activityId
				+ "&seatId=" + seatId + "&bookCount=" + bookCount + "&orderMobileNum=" + orderMobileNum;
		// System.out.println(jdurl);
		String data = request.sendGet(jdurl);

		JSONObject resjson = JSONObject.fromObject(data);

		JSONObject result = new JSONObject();
		result.element("result", resjson);

		jsonObject.element("responseJSON", result);

		jsonObject.element("success", "1");
		jsonObject.element("msg", "");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}

	public static List<String> getImgStr(String htmlStr) { // 正则匹配获取img/src属性
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();

		// String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址

		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();

			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);

			while (m.find()) {
				pics.add(m.group(1));
			}
		}

		return pics;
	}
}
