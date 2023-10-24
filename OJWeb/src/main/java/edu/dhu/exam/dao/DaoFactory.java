package edu.dhu.exam.dao;

/**
 * 工厂模式,生产Dao对象,面向接口编程,返回实现业务接口定义的对象 DaoFactory.java TODO : class
 * DaoFactory.java is used for ...
 */
public class DaoFactory {

	private static DaoFactory daoFactory = new DaoFactory();

	// 单例设计模式, 私有构造,对外提供获取创建的对象的唯一接口,
	private DaoFactory() {

	}

	public static DaoFactory getInstance() {
		return daoFactory;
	}

}
