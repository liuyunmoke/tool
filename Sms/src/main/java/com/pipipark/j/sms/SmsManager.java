package com.pipipark.j.sms;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.pipipark.j.sms.data.SmsDB;
import com.pipipark.j.sms.data.model.PhoneSms;
import com.pipipark.j.sms.entity.SmsConfig;
import com.pipipark.j.sms.entity.SmsContent;
import com.pipipark.j.sms.exception.SmsException;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.core.PPPDate;
import com.pipipark.j.system.core.PPPEnum;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.entity.field.validate.FieldVaildater;
import com.pipipark.j.system.exception.PPPServiceException;

/**
 * 短信管理者
 * 
 * @see 管理创建的Sms对象
 * @author cwj
 */
public final class SmsManager {

	/**
	 * 单例配置
	 */
	private static volatile SmsManager _me;
	private final static Object syncLock = new Object();
	
	private SmsManager() throws SmsException{
		if(SmsManager._me!=null){
			throw new SmsException("This is singleton object, you can't constructed it!");
		}
	}

	public static SmsManager getInstance() throws SmsException {
		if (_me == null) {
			synchronized (syncLock) {
				_me = new SmsManager();
			}
		}
		return _me;
	}

	/**
	 * 保存每个发送者的短信设置信息
	 */
	private final ConcurrentMap<String, SmsConfig> config = new ConcurrentHashMap<String, SmsConfig>();

	/**
	 * 发送过程处理方法
	 * 
	 * @param sms
	 * @param content
	 * @throws ServiceException
	 */
	public void handler(Sms sms, SmsContent content) throws PPPServiceException {
		String sendUser = sms.getSendUser();
		SmsConfig conf = null;

		// 获取发送者配置信息并进行配置验证
		if (sendUser == null) {
			conf = new SmsConfig();
			sms.config(conf);
			FieldVaildater.vaildate(conf);
		} else {
			conf = config.get(sendUser);
			if (conf == null) {
				conf = new SmsConfig();
				sms.config(conf);
				FieldVaildater.vaildate(conf);
				config.put(sendUser, conf);
			}
		}

		// 验证发送内容和接收者
		FieldVaildater.vaildate(content);

		// 组装发送字符串
		String sendStr = conf.toString() + content.toString();
		HttpClient httpClient = new HttpClient();
		if (sendStr.length() > 256) {
			conf.method(PPPEnum.HttpMethod.post);
		}
		HttpMethod method = null;
		if (conf.method() == PPPEnum.HttpMethod.get) {
			// 发送get请求
			method = new GetMethod(sendStr);
		} else {
			// 发送post请求
			method = new PostMethod(sendStr);
		}
		method.getParams().setContentCharset(PPPConstant.Charset.Default.value());
		int httpStatusCode = -1;
		SmsDB db = new SmsDB();
		try {
			PPPLogger.info(SmsManager.class, "即将发送短信,发送地址为: " + sendStr);
			
			//存库
			db.open();
			for (String phone : content.mobile()) {
				try{
					List<PhoneSms> ps = db.executeQuery("select * from PhoneSms where phone='"+phone+"' and type = "+content.type(), PhoneSms.class);
					String date = PPPDate.now().format(PPPDate.Dateformat.yyyyMMddHHmmssSSS);
					if(ps==null || ps.isEmpty()){
						db.executeUpdate("insert into PhoneSms ('sender','phone','content','type','updateDate') values ('"+sendUser+"','"+phone+"','"+content.content()+"',"+content.type()+", '"+date+"')");
					}else{
						db.executeUpdate("update PhoneSms set sender = '"+sendUser+"', content='"+content.content()+"', updateDate= '"+date+"' where phone='"+phone+"'");
					}
				}catch(Exception e){
					PPPLogger.info(SmsManager.class, "手机号码: " + phone+" 短信发送失败,发生数据异常.", e);
					continue;
				}
			}
			
			// 执行发送并返回状态码
			httpStatusCode = httpClient.executeMethod(method);
			
			// 状态码200
			if (httpStatusCode == HttpStatus.SC_OK) {
				StringBuilder phones = new StringBuilder();
				for (String phone : content.mobile()) {
					phones.append(phone);
					phones.append(",");
				}
				phones.deleteCharAt(phones.length() - 1);
				PPPLogger.info(SmsManager.class, "手机号: " + phones + " 的短信已发送成功.发送人: " + sendUser);
			}
		} catch (Exception e) {
			PPPLogger.error(SmsManager.class, "request: " + httpStatusCode + ". 短信发送失败.");
		} finally {
			// 断开http协议连接
			method.releaseConnection();
			db.close();
		}
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		PPPLogger.info(SmsManager.class, "This is singleton object, you can't clone it!");
		return null;
	}
}
