package com.zens.controller;

import java.util.ResourceBundle;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.zens.db.DBUtils;
import com.zens.utils.HttpRequest;

/**
 * 
 * @author zyq 
 * @mail zhuyq@zensvision.com
 * @2016年3月8日 上午10:48:00
 *
 */

public class CultureJDUserController extends Controller {

	HttpRequest request = new HttpRequest();
	JSONObject jsonObject = new JSONObject();

	ArrayList<Object> list = new ArrayList<Object>();

	DBUtils dbUtils = new DBUtils();
	
	public void getRegisterCaptcha() throws IOException {//
		//http://localhost:8080/wapi/CultureCloud_JD/user/getRegisterCaptcha?userMobile=15951878624
		String userMobile = getPara("userMobile");
		
		long time1 = System.currentTimeMillis();

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl")
				+ "/tpsLogin/userCodeRegister.do?userMobile=" + userMobile;
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
	
	public void register() throws IOException {
		//http://localhost:8080/wapi/CultureCloud_JD/user/register??userName=zyq&userCode=1234&userMobile=15951878624&userPwd=123456";
		String userMobile = getPara("userMobile");
		String captcha = getPara("captcha");
		String userName = getPara("userName");
		String userPwd = getPara("userPwd");
		
		long time1 = System.currentTimeMillis();

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl")
				+ "/tpsLogin/doRegister.do?userName="+ userName +"&userCode="+ captcha +"&userMobile="+ userMobile +"&userPwd=" + userPwd;
		String data = request.sendGet(jdurl);

		JSONObject result = new JSONObject();
		result.element("result", data);

		jsonObject.element("msg", "");
		jsonObject.element("responseJSON", result);

		jsonObject.element("success", "1");
		long time2 = System.currentTimeMillis();
		jsonObject.element("elapsed", time2 - time1);

		renderJavascript(getPara("callback") + "(" + jsonObject + ")");
	}

	public void getLoginCaptcha() throws IOException {
		//http://localhost:8080/wapi/CultureCloud_JD/user/getLoginCaptcha?userMobile=15951878624
		String userMobile = getPara("userMobile");
		
		long time1 = System.currentTimeMillis();

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl")
				+ "/tpsLogin/userCodeLogin.do?userMobile=" + userMobile;
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
	
	public void login() throws IOException {
		//http://localhost:8080/wapi/CultureCloud_JD/user/login?userMobile=15951878624&captcha=1234
		String userMobile = getPara("userMobile");
		String captcha = getPara("captcha");
		
		long time1 = System.currentTimeMillis();

		String jdurl = ResourceBundle.getBundle("url").getString("jdurl")
				+ "/tpsLogin/Login.do?userMobile=" + userMobile + "&userCode=" + captcha;
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
	
}
