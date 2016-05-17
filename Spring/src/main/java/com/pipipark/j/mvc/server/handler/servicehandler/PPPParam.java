package com.pipipark.j.mvc.server.handler.servicehandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public final class PPPParam extends HashMap<String, Object> {

	public static final String HEAD_VER = "ver";
	public static final String HEAD_IMEI = "imei";
	public static final String HEAD_CLIENT_TYPE = "clientType";
	public static final String HEAD_SIGN = "sign";
	public static final String HEAD_TOKEN = "token";
	public static final String HEAD_APP_TYPE = "appType";

	private Integer resultStatus = null;
	private String resultMessage = null;
	private String headVer;
	private String headImei;
	private String headClientType;
	private String headSign;
	private String headToken;
	private String headAppType;
	private String headBundleId;

	private HttpServletRequest request;
	private HttpServletResponse response;

	private static final ThreadLocal<PPPParam> local = new ThreadLocal<PPPParam>();

	static PPPParam params(final Map<String, String[]> maps,
			final Map<String, List<MultipartFile>> files, HttpServletRequest request,
			HttpServletResponse response) {
		PPPParam map = local.get();
		if (map == null) {
			map = new PPPParam();
			local.set(map);
		}
		map.request = request;
		map.response = response;
		map.resultStatus = null;
		map.resultMessage = null;
		map.clear();
		for (Iterator<String> ite = maps.keySet().iterator(); ite.hasNext();) {
			String key = ite.next();
			String[] keyArray = maps.get(key);
			if (keyArray.length == 0) {
				map.put(key, null);
			} else if (keyArray.length == 1) {
				map.put(key, keyArray[0]);
			} else {
				map.put(key, Arrays.asList(keyArray));
			}
		}
		for (Iterator<String> ite = files.keySet().iterator(); ite.hasNext();) {
			String key = ite.next();
			List<MultipartFile> keyArray = files.get(key);
			if (keyArray.size() == 0) {
				map.put(key, null);
			} else if (keyArray.size() == 1) {
				map.put(key, keyArray.get(0));
			} else {
				map.put(key, keyArray.toArray(new MultipartFile[0]));
			}
		}
		return map;
	}

	public static PPPParam params() {
		return local.get();
	}

	public String getVer() {
		return headVer;
	}

	public String getImei() {
		return headImei;
	}

	public String getSign() {
		return headSign;
	}

	public String getClientType() {
		return headClientType;
	}
	
	public String getBundleId() {
		return headBundleId;
	}

	public String getAppType() {
		return headAppType;
	}

	public String getToken() {
		return headToken;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void resultMsg(Integer status, String msg) {
		resultStatus = status;
		resultMessage = msg;
	}

	public void resultMsg(String msg) {
		resultMessage = msg;
	}

	Integer status() {
		return resultStatus;
	}

	String message() {
		return resultMessage;
	}
	
}
