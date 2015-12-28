
package xyz.dao.imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import xyz.dao.CommonDao;



@Repository
public class CommonDaoImp implements CommonDao{
	
	@Resource
	SessionFactory sessionFactory;
	
	@Override
	public void clear(){
		sessionFactory.getCurrentSession().clear();
	}
	
	@Override
	public void flush(){
		sessionFactory.getCurrentSession().flush();
	}
	
	@Override
	public void evict(Object obj){
		sessionFactory.getCurrentSession().evict(obj);
	}
	
	/**
	 * 将未知具体类型的实体对象存入数据库表
	 * @param object 实体对象
	 */
	@Override
	public void save(Object object){
		sessionFactory.getCurrentSession().save(object);
	}
	
	/**
	 * 从未知表名的数据库表删除一行相关记录
	 * @param object 实体对象
	 */
	@Override
	public void delete(Object object){
		sessionFactory.getCurrentSession().delete(object);
	}
	
	/**
	 * 从未知表名的数据库表更新一行相关记录
	 * @param object 实体对象
	 */
	@Override
	public void update(Object object){
		sessionFactory.getCurrentSession().update(object);
	}
	
	/**
	 * 根据未知类型的实体对象查出所有已知属性相同的表记录,若无须分页和排序，可将相关字段设为null
	 * 注：此方法指定id无效，int为0，无效
	 * @param object 实体对象
	 * @param page 第几页
	 * @param count 每页数据量
	 * @param orderPropertitys 排序字段
	 * @param isFull 是否全部数据
	 * @return 数据列表
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List queryByExample( Object object, int offset,
			 int count, String orderPropertitys, String orderType, boolean isFull){
		
		Session session = sessionFactory.getCurrentSession();
    	Criteria criteria = session.createCriteria(object.getClass());
    	
    	if(offset>0){
    		criteria.setFirstResult(offset);
    	}
    	
    	if(count>0){
    		criteria.setMaxResults(count);
    	}
    	
    	if(isFull==false){
    		criteria.add(Example.create(object).excludeZeroes());
    	}
    	
    	if(orderPropertitys!=null&&!"".equals(orderPropertitys)){
			if("desc".equals(orderType)){
				criteria.addOrder(Order.desc(orderPropertitys));
			}else{
				criteria.addOrder(Order.asc(orderPropertitys));
			}
    	}
    	return criteria.list();
	}

	@Override
	public int queryEntityCount(final Object object, boolean isFull) {
		if(isFull==true){
        	String hql = "select count(iidd) from "+object.getClass().getName();
        	return ((Number)sessionFactory.getCurrentSession().createQuery(hql).uniqueResult()).intValue();
		}else{
	        return sessionFactory.getCurrentSession().createCriteria(object.getClass()).add(Example.create(object)).list().size();
		}
	};
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryByHql(String hql){
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	
	@Override
	public Object queryUniqueByHql(String hql){
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setMaxResults(1);
		return query.uniqueResult();
	}

	@Override
	public int updateByHql(String hql) {
		int result = sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryByHqlHaveDate(String hql,Object[] keys,Date ... date) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<keys.length;i++){
			query.setTimestamp(keys[i].toString(), date[i]);
		}
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getObjectById(Class arg, int iidd) {
		return sessionFactory.getCurrentSession().get(arg,iidd);
	}
	
	@Override
	public Object getObjectByUniqueCode(String ClassName,String fieldName,String value) {
		String hql = "from "+ClassName+" t where t."+fieldName+" = '"+value+"'";
		return sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}
	
	@Override
	public Query getQuery(String hql) {
		return sessionFactory.getCurrentSession().createQuery(hql);
	}
	
	@Override
	public SQLQuery getSqlQuery(String sql) {
		return sessionFactory.getCurrentSession().createSQLQuery(sql);
	}

	@Override
	public Criteria getCriteria(Object object) {
		return sessionFactory.getCurrentSession().createCriteria(object.getClass());
	}
}
