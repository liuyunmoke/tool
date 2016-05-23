package com.pipipark.j.bootclick;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pipipark.j.bootclick.comparator.BootclickComparator;
import com.pipipark.j.bootclick.comparator.IndexBootclick;
import com.pipipark.j.bootclick.exception.PPPBootclickError;
import com.pipipark.j.bootclick.handler.PPPBootclickHandler;
import com.pipipark.j.bootclick.handler.shutdown.BootclickShutdownHandler;
import com.pipipark.j.bootclick.handler.startup.BootclickStartUpHandler;
import com.pipipark.j.system.classscan.v2.PPPScan;
import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.core.PPPLogger;

/**
 * 启动处理类, 定义启动规则.
 * 
 * @see AbsBootstrapHandler.class
 * @author pipipark:cwj
 */
@SuppressWarnings("rawtypes")
public class PPPApplicationBoot implements PPPBoot {

	private static PPPApplicationBoot _me = null;

	public static final List<String> bootArgs = new ArrayList<String>();
	private static final Object syncLock = new Object();
	private final Map<Class, Object> boots = new HashMap<Class, Object>();

	private PPPApplicationBoot() {}

	public static final PPPApplicationBoot getInstance() {
		if (_me == null) {
			synchronized (syncLock) {
				_me = new PPPApplicationBoot();
			}
		}
		return _me;
	}

	@Override
	public void boot() {
		PPPLogger.info("Boot");
		// 搜索启动类
		List<Class> startupClazzs = new ArrayList<Class>();
		// List<Class> initClazzs = new ArrayList<Class>();
		List<Class> shutdownClazzs = new ArrayList<Class>();

		Set<Class> clazzs = PPPScan.doScan("com");
		
		for (Class clazz : clazzs) {
			for (Class ic : clazz.getInterfaces()) {
				if (ic == BootclickStartUpHandler.class) {
					startupClazzs.add(clazz);
				}
				if (ic == BootclickShutdownHandler.class) {
					shutdownClazzs.add(clazz);
				}
				// if(ic == IBootclickInitHandler.class){
				// initClazzs.add(clazz);
				// }
			}
		}

		if (startupClazzs.size() <= 0) {
			throw new PPPBootclickError("can't be find any one bootstrap class!");
		}

		// 排序
		Collections.sort(startupClazzs, new BootclickComparator());
		Collections.sort(shutdownClazzs, new BootclickComparator());
		// Collections.sort(initClazzs, new BootstrapComparator());

		// 初始化处理类
		// List<IBootclickInitHandler> initHandlers =
		// this.<IBootclickInitHandler>get(initClazzs);
		List<BootclickStartUpHandler> upHandlers = this.<BootclickStartUpHandler> get(startupClazzs);

		// 初始化参数
		// PPPLogger.info(this, "Initialize start!");
		// for (IBootclickInitHandler h : initHandlers) {
		// h.init(config);
		// if(config==null){
		// throw new
		// BootclickError("Boot Config can't be null, don't set it with null.");
		// }
		// }

		// 触发处理方法
		PPPLogger.info(this, "Start end!");
		for (BootclickStartUpHandler h : upHandlers) {
			h.startUp();
		}
	}

	/**
	 * 冒泡排序,已用比较器取代.
	 * @param list
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Deprecated
	private void sort(List<Class> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				Class a = list.get(i);
				Class b = list.get(j);
				Class temp = null;
				Annotation ai = a.getAnnotation(IndexBootclick.class);
				Annotation bi = b.getAnnotation(IndexBootclick.class);
				int aInt = PPPConstant.Index.Minimum.value();
				int bInt = PPPConstant.Index.Minimum.value();
				if (ai == null) {
					aInt = PPPConstant.Index.Default.value();
				} else {
					aInt = ((IndexBootclick) ai).value().value();
				}
				if (bi == null) {
					bInt = PPPConstant.Index.Default.value();
				} else {
					aInt = ((IndexBootclick) bi).value().value();
				}
				if (aInt > bInt) {
					temp = b;
					list.set(j, a);
					list.set(i, b);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <M extends PPPBootclickHandler> List<M> get(List<Class> clazzs){
		List<M> handlers = new ArrayList<M>();
		for (Class clazz : clazzs) {
			if (Modifier.isAbstract(clazz.getModifiers())) {
				continue;
			}
			Object obj = boots.get(clazz);
			if (obj == null) {
				try {
					obj = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new PPPBootclickError("BootInit: " + clazz.getName()
							+ " happen create Exception!");
				}
				boots.put(clazz, obj);
			}
			handlers.add((M) obj);
		}
		return handlers;
	}

	/**
	 * @param args
	 * @throws ServiceException
	 */
	public static void main(String[] args) {

		/*
		 * 处理args参数
		 */
		if (args != null && args.length > 0) {
			for (String arg : args) {
				bootArgs.add(arg);
			}
		}
		/*
		 * 启动
		 */
		PPPApplicationBoot.getInstance().boot();
	}
}
