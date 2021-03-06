<%@page contentType="text/html; charset=gbk" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
  <head>
    <title>节目播放页</title>
	<script type="text/javascript">
		window.onload = function () {
			try {
				var vodPara = "";
				var o1 = document.getElementsByTagName("vod")[0].attributes;
				for (var i=0;i<o1.length;i++) {
					vodPara += o1[i].name+ "=" +o1[i].value+ "&";
				}
				var o2 = document.getElementsByTagName("vodpara");
				for (var i=0;i<o2.length;i++) {
					vodPara += o2[i].getAttribute("name")+ "=" +o2[i].getAttribute("value")+ "&";
				}
				//alert(vodPara);
				jShow.play9dayEpgEvent(vodPara);
				setTimeout(function (){
					history.back(-1);
				}, 500);

			}catch (e) {}
		}
	</script>
</head>
<body>

<!-- 
	关于为什么直接将点播参数写在页面内而不使用配置资源文件
	前提：1. 点播参数未放入数据库进行管理。
	      2. 后台管理系统目前不提供点播参数的修改。
	原因：1. 此部分参数改动较少，如果改动也一般由开发人员改动。
	      2. 使用资源文件和写在页面的意义相同而且维护时更直观。
	      3. 因为没采用动态加载资源文件，修改资源文件后需重启tomcat。而直接修改页面不需要重启tomcat。
 -->
<vod index="${offID}" id="${offID}" ServiceName="${play.SRV_NAME}" ServiceType="SVOD" Preview="0" backstep="1"> 

 	
	<vodpara name="passthru_ip" value="10.27.65.50" /> 
	<vodpara name="mod_app_ip" value="10.27.65.50" /> 
	<vodpara name="srm_ip" value="10.27.65.50" /> 
	<vodpara name="poster_server_ip" value="10.27.65.50" /> 
	<vodpara name="lsc_comm_proxy_ip" value="10.27.65.50" /> 
	<vodpara name="session_gateway_ip" value="10.27.65.50" /> 

	<vodpara name="freq1" value="562000" />
	<vodpara name="sym_rate1" value="6900" />
	<vodpara name="qam_mode1" value="64" />
	<vodpara name="freq2" value="834000" />
	<vodpara name="sym_rate2" value="6900" />
	<vodpara name="qam_mode2" value="64" />
	
	<vodpara name="ApplicationType" value="vod" />
	<vodpara name="run_time" value="${run_time}" />
	<vodpara name="content_name" value="${content_name}" />
	
	<vodpara name="protocol_type" value="rtsp" />
	<!--现网地址-->
	<vodpara name="rtsp" value="10.27.65.80:18082" />
	<!--实验网地址
	<vodpara name="rtsp" value="10.27.63.101:13819" />
	-->
	<vodpara name="rtsp_vendor" value="OCN.RTSP" />
	

	<vodpara name="streamTransMode" value="ip_stream" />
	
	<!-- VOD 2.0点播参数 -->
	<vodpara name="purchaseType" value="2" />
	<vodpara name="category" value="${play.AST_GENRE}" />
	<vodpara name="provider" value="${play.AMS_PROVIDER}" />
	<vodpara name="ServiceCode" value="${play.SRV_CODE}" />
	

</vod>

</body>
</html>