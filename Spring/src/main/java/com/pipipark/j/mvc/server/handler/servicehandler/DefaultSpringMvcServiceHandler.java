package com.pipipark.j.mvc.server.handler.servicehandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.pipipark.j.mvc.core.PPPResonpse;
import com.pipipark.j.mvc.core.PPPContext;
import com.pipipark.j.mvc.server.exception.PPPServiceMethodNoFoundException;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.core.PPPVersion;

@SuppressWarnings("serial")
public class DefaultSpringMvcServiceHandler extends AbsSpringMvcServiceHandler {

	public PPPResonpse access(final String serviceName, final PPPVersion ver,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// 获取service服务
		Object service = this.getService(serviceName);
		Object returnObject = null;
		if (service == null) {
			service = PPPContext.getBean(serviceName);
		}

		try {
			Method proxySource = service.getClass().getDeclaredMethod(
					"getTargetSource");
			if (proxySource != null) {
				SingletonTargetSource target = (SingletonTargetSource) proxySource
						.invoke(service);
				service = target.getTarget();
			}
		} catch (Exception e) {
		}

		Method serviceMethod=null;
		for (Method method : service.getClass().getDeclaredMethods()) {
			if (method.getName().equals(PPPContext.EXECUTE_METHOD)) {
				serviceMethod = method;
				break;
			}
		}
		;

		if (serviceMethod == null) {
			throw new PPPServiceMethodNoFoundException();
		}
		
		Map<String, List<MultipartFile>> fileParameters = new HashMap<String, List<MultipartFile>>();

		// 检查是否上传文件
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			for (Iterator<String> ite = multiRequest.getFileNames(); ite
					.hasNext();) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				String key = ite.next();
				List<MultipartFile> files = multiRequest.getFiles(key);
				if (files != null && !files.isEmpty()) {
					fileParameters.put(key, files);
					// System.out.println(myFileName);
					// // 重命名上传后的文件名
					// String fileName = "demoUpload"
					// + file.getOriginalFilename();
					// // 定义上传路径
					// String path = "H:/" + fileName;
					// File localFile = new File(path);
					// try {
					// file.transferTo(localFile);
					// } catch (IllegalStateException | IOException e) {
					// e.printStackTrace();
					// }
				}
				// 记录上传该文件后的时间
				int finaltime = (int) System.currentTimeMillis();
				System.out.println(finaltime - pre);
			}
		}

		// http参数
		Map<String, String[]> parameters = request.getParameterMap();

		// 获取服务参数
//		Class<?>[] pts = serviceMethod.getParameterTypes();
//		Annotation[][] anns = serviceMethod.getParameterAnnotations();
//		String[] names = Asms.getMethodParameterNamesByAsm4(service.getClass(),
//				serviceMethod);
		Object[] args = null;

//		if (names == null) {
//			names = new String[0];
//		}
//		args = new Object[names.length];

		// 参数封装
//		for (int i = 0; i < names.length; i++) {
//			Annotation[] as = anns[i];
//			String parameterName = null;
//			for (Annotation annotation : as) {
//				if (annotation instanceof PPPName) {
//					parameterName = ((PPPName) annotation).value();
//					break;
//				}
//			}
//			if (parameterName == null) {
//				parameterName = names[i];
//			}
//			String[] val = parameters.get(parameterName);
//			Class<?> clazz = pts[i];
//			if (val == null || val.length <= 0) {
//				List<MultipartFile> fileVal = fileParameters.get(parameterName);
//				if (fileVal != null && !fileVal.isEmpty()) {
//					if (clazz == MultipartFile.class) {
//						args[i] = fileVal.get(0);
//					} else {
//						args[i] = fileVal.toArray(new MultipartFile[0]);
//					}
//				} else if (clazz == HttpServletRequest.class) {
//					args[i] = request;
//				} else if (clazz == HttpServletResponse.class) {
//					args[i] = response;
//				} else {
//					if (clazz == Integer.TYPE) {
//						args[i] = 0;
//					} else if (clazz == Long.TYPE) {
//						args[i] = 0;
//					} else if (clazz == Short.TYPE) {
//						args[i] = 0;
//					} else if (clazz == Float.TYPE) {
//						args[i] = 0.0f;
//					} else if (clazz == Double.TYPE) {
//						args[i] = 0.0d;
//					} else if (clazz == Character.TYPE) {
//						args[i] = "\u0000";
//					} else if (clazz == Boolean.TYPE) {
//						args[i] = false;
//					} else {
//						args[i] = null;
//						// Object bean = clazz.newInstance();
//						// try{
//						// bean2Map(bean);
//						// }catch(Exception e){
//						// args[i] = null;
//						// }
//						// Map<String,Object> obj = new HashMap<String,
//						// Object>();
//						// for (Iterator<String> ite =
//						// parameters.keySet().iterator();ite.hasNext();) {
//						// String key = ite.next();
//						// String[] v = parameters.get(parameterName);
//						//
//						// }
//						// obj.put(key, value);
//						// }
//						// BeanUtils.populate(bean, temp);
//					}
//				}
//			} else if (val.length == 1) {
//				if (clazz == Integer.class || clazz == Integer.TYPE) {
//					args[i] = Integer.parseInt(val[0]);
//				} else if (clazz == Long.class || clazz == Long.TYPE) {
//					args[i] = Long.parseLong(val[0]);
//				} else if (clazz == Short.class || clazz == Short.TYPE) {
//					args[i] = Short.parseShort(val[0]);
//				} else if (clazz == Float.class || clazz == Float.TYPE) {
//					args[i] = Float.parseFloat(val[0]);
//				} else if (clazz == Double.class || clazz == Double.TYPE) {
//					args[i] = Double.parseDouble(val[0]);
//				} else if (clazz == Character.class || clazz == Character.TYPE) {
//					args[i] = val[0].charAt(0);
//				} else if (clazz == Boolean.class || clazz == Boolean.TYPE) {
//					args[i] = Boolean.parseBoolean(val[0]);
//				} else {
//					args[i] = val[0];
//				}
//			} else {
//				if (clazz == Integer[].class || clazz == int[].class) {
//					Integer[] array = new Integer[val.length];
//					for (int j = 0; j < val.length; j++) {
//						array[j] = Integer.parseInt(val[j]);
//					}
//					args[i] = Arrays.asList(array);
//				} else if (clazz == Long[].class || clazz == long[].class) {
//					Long[] array = new Long[val.length];
//					for (int j = 0; j < val.length; j++) {
//						array[j] = Long.parseLong(val[j]);
//					}
//					args[i] = Arrays.asList(array);
//				} else if (clazz == Short[].class || clazz == short[].class) {
//					Short[] array = new Short[val.length];
//					for (int j = 0; j < val.length; j++) {
//						array[j] = Short.parseShort(val[j]);
//					}
//					args[i] = Arrays.asList(array);
//				} else if (clazz == Double[].class || clazz == double[].class) {
//					Double[] array = new Double[val.length];
//					for (int j = 0; j < val.length; j++) {
//						array[j] = Double.parseDouble(val[j]);
//					}
//					args[i] = Arrays.asList(array);
//				} else if (clazz == Character[].class || clazz == char[].class) {
//					Character[] array = new Character[val.length];
//					for (int j = 0; j < val.length; j++) {
//						array[j] = val[j].charAt(0);
//					}
//					args[i] = Arrays.asList(array);
//				} else if (clazz == Boolean[].class || clazz == boolean[].class) {
//					Boolean[] array = new Boolean[val.length];
//					for (int j = 0; j < val.length; j++) {
//						array[j] = Boolean.parseBoolean(val[j]);
//					}
//					args[i] = Arrays.asList(array);
//				} else {
//					args[i] = Arrays.asList(val);
//				}
//			}
//		}
		
		PPPParam p = PPPParam.params(parameters, fileParameters, request, response);

		if (args == null || ArrayUtils.isEmpty(args)) {
			returnObject = serviceMethod.invoke(service);
		}

		PPPResonpse res = new PPPResonpse();
		res.setData(returnObject);
		return res;
	}

	@Override
	public void desc(StringBuilder string) throws Exception {
		string.append("this is default ServiceHandler, if you not have custom ServiceHandler, will be running it.");
	}
}
