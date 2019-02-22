package com.zens.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.zens.controller.CultureJDController;
import com.zens.controller.CultureJDPicController;
import com.zens.controller.CultureJDPlayController;
import com.zens.controller.CultureJDSubController;
import com.zens.controller.CultureJDUserController;
import com.zens.controller.LoginController;


/**
 * 
 * @author zyq 
 * @mail zhuyq@zensvision.com
 * @2016年3月8日 上午10:48:17
 *
 */

public class CultureJDConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// TODO 自动生成的方法存根
		me.setDevMode(true);
		me.setBaseViewPath("/"); // 路径
		me.setViewType(ViewType.JSP); // 视图类型

	}

	// 路由
	@Override
	public void configRoute(Routes me) {
		// TODO 自动生成的方法存根
		me.add("/CultureCloud_JD/tpsActivity", CultureJDController.class);
		me.add("/CultureCloud_JD/user", CultureJDUserController.class);
		me.add("/CultureCloud_JD/pic", CultureJDPicController.class);
		me.add("/CultureCloud_JD/sub", CultureJDSubController.class);
		me.add("/CultureCloud_JD/play", CultureJDPlayController.class);
		me.add("/", LoginController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO 自动生成的方法存根
		loadPropertyFile("jdbc.properties"); //load配置文件
		C3p0Plugin c3p0 = new C3p0Plugin(getProperty("jdbc.url"), getProperty("jdbc.username"), getProperty("jdbc.password"));
		me.add(c3p0); //添加插件
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void configHandler(Handlers me) {
		// TODO 自动生成的方法存根

	}

}
