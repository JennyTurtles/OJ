package edu.dhu.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StudentexaminfoCacheManager {
	private boolean update = false;
	private BaseCache cache;
	private static StudentexaminfoCacheManager instance;
	private static Object lock = new Object();

	private StudentexaminfoCacheManager() {
		// 这个根据配置文件来，初始BaseCache而已;
		Properties prop = new Properties();
		InputStream in = StudentexaminfoCacheManager.class
				.getResourceAsStream("/cache.properties");
		try {
			prop.load(in);
			String validTime = prop.getProperty("studentexaminfoValidTime")
					.trim();
			cache = new BaseCache("Studentexaminfo", Integer.parseInt(validTime));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static StudentexaminfoCacheManager getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new StudentexaminfoCacheManager();
				}
			}
		}
		return instance;
	}

	public void putObject(String key, Object value) { // key以classId_examId
		cache.put(key, value);
	}

	public void removeObject(String key) {
		cache.remove(key);
	}

	public void removeAllObject() {
		cache.removeAll();
	}

	public Object getObject(String key) {
		try {
			// 从Cache中获得
			return cache.get(key);
		} catch (Exception e) {
			// Cache中没有则从DB库获取
			// 数据库中读取数据
			// 把获取的对象再次存入Cache中
			this.putObject(key, null);
			update = true;
			return null;
		} finally {
			if (!update) {
				// 如果Cache中的内容更新出现异常，则终止该方法
				cache.cancelUpdate(key); // 取消对id的更新
			}
		}
	}

}
