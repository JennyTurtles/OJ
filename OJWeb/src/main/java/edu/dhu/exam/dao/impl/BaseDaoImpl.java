package edu.dhu.exam.dao.impl;

import edu.dhu.exam.dao.BaseDaoI;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository("baseDao")
@Component
public class BaseDaoImpl<T> implements BaseDaoI<T> {
	@Resource
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
//
//	@Autowired
//	public void setSessionFactory(EntityManagerFactory factory) {
//		if (factory.unwrap(SessionFactory.class) == null) {
//			throw new NullPointerException("factory is not a hibernate factory");
//		}
//		this.sessionFactory = factory.unwrap(SessionFactory.class);
//	}
	protected Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/*
	 * 注册时保存对象
	 */
	@Override
	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	/*
	 * 检查登录信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(String hql, Object[] params) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(String hql, Map<String, Object> params) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	/*
	 * 删除
	 */
	@Override
	public void delete(T o) {
		this.sessionFactory.getCurrentSession().delete(o);
	}

	/*
	 * 更新
	 */
	@Override
	public void update(T o) {
		this.sessionFactory.getCurrentSession().update(o);
	}

	/*
	 * 更新和保存
	 */
	@Override
	public void saveOrUpdate(T o) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(o);
	}

	/*
	 * 查询多项
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}

	/*
	 * 查询多项 带参数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	/*
	 * 分页显示查询结果并且带参数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, Map<String, Object> params, int page,
			int rows) {
		Query q = this.getCurrentSession().createQuery(hql);

		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		// （当前页-1）×每页显示记录数 为 setFirstResult的参数
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();

	}

	/*
	 * 查询多项，可分页
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/*
	 * 查询分页结果的总数
	 */
	@Override
	public Long count(String hql) {
		Query sum = this.getCurrentSession().createQuery(hql);
		return (Long) sum.uniqueResult();
	}

	/*
	 * 查询分页结果的总数
	 */

	@Override
	@Transactional
	public Long count(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	/*
	 * 根据ID查询数据库 返回T类型的数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	/*
	 * 执行一个HQL语句
	 */
	@Override
	public int executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public int countInt(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return Integer.valueOf(((Long) q.uniqueResult()).toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> relationalQuery(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> relationalQuery(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> relationalQuery(String hql,
			Map<String, Object> params, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		// （当前页-1）×每页显示记录数 为 setFirstResult的参数
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<Object> findBeans(Class c, int maxNum, String key1,
	 * Object value1) { try { String hql = "from " + c.getName() +
	 * " as bean where bean." + key1 + "? " + "order by id asc";
	 * 
	 * Query query = this.getCurrentSession().createQuery(hql);
	 * 
	 * query.setParameter(0, value1);
	 * 
	 * query.setFirstResult(0); query.setMaxResults(maxNum); List<Object> rs =
	 * query.list();
	 * 
	 * return rs; } catch (Exception ex) { return new ArrayList<Object>(); } }
	 */

	@Override
	public int executesql(String sql) { 
	        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);    
	        query.list(); 
	        return 0;    
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findBySql(String sql, Map<String, Object> params) {
		SQLQuery sqry = this.getCurrentSession().createSQLQuery(sql);
		if(null != params && !params.isEmpty())
			sqry.setProperties(params);
		return sqry.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	@Override
	public int executeSql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		int num = q.executeUpdate();
		this.getCurrentSession().clear();
		return num;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findBySql(String sql, Map<String, Object> params,Class<T> c) {
		SQLQuery sqry = this.getCurrentSession().createSQLQuery(sql);
		if(null != params && !params.isEmpty())
			sqry.setProperties(params);
		return sqry.setResultTransformer(Transformers.aliasToBean(c)).list();
	}
}
