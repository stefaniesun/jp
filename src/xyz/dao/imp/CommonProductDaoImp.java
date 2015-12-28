
package xyz.dao.imp;




//@Repository
public class CommonProductDaoImp{
	
	/*@Resource
	SessionFactory sessionFactoryProduct;
	
	@Override
	public void clear(){
		sessionFactoryProduct.getCurrentSession().clear();
	}
	
	@Override
	public void flush(){
		sessionFactoryProduct.getCurrentSession().flush();
	}
	
	@Override
	public void evict(Object obj){
		sessionFactoryProduct.getCurrentSession().evict(obj);
	}
	
	*//**
	 * 将未知具体类型的实体对象存入数据库表
	 * @param object 实体对象
	 *//*
	@Override
	public void save(Object object){
		sessionFactoryProduct.getCurrentSession().save(object);
	}
	
	*//**
	 * 从未知表名的数据库表删除一行相关记录
	 * @param object 实体对象
	 *//*
	@Override
	public void delete(Object object){
		sessionFactoryProduct.getCurrentSession().delete(object);
	}
	
	*//**
	 * 从未知表名的数据库表更新一行相关记录
	 * @param object 实体对象
	 *//*
	@Override
	public void update(Object object){
		sessionFactoryProduct.getCurrentSession().update(object);
	}
	
	*//**
	 * 根据未知类型的实体对象查出所有已知属性相同的表记录,若无须分页和排序，可将相关字段设为null
	 * 注：此方法指定id无效，int为0，无效
	 * @param object 实体对象
	 * @param page 第几页
	 * @param count 每页数据量
	 * @param orderPropertitys 排序字段
	 * @param isFull 是否全部数据
	 * @return 数据列表
	 *//*
	@SuppressWarnings("rawtypes")
	@Override
	public List queryByExample( Object object, int offset,
			 int count, String orderPropertitys, String orderType, boolean isFull){
		
		Session session = sessionFactoryProduct.getCurrentSession();
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
        	return ((Number)sessionFactoryProduct.getCurrentSession().createQuery(hql).uniqueResult()).intValue();
		}else{
	        return sessionFactoryProduct.getCurrentSession().createCriteria(object.getClass()).add(Example.create(object)).list().size();
		}
	};
	
	@SuppressWarnings("rawtypes")
	@Override
	public List queryByHql(String hql){
		return sessionFactoryProduct.getCurrentSession().createQuery(hql).list();
	}
	
	@Override
	public Object queryUniqueByHql(String hql){
		Query query = sessionFactoryProduct.getCurrentSession().createQuery(hql);
		query.setMaxResults(1);
		return query.uniqueResult();
	}

	@Override
	public int updateByHql(String hql) {
		int result = sessionFactoryProduct.getCurrentSession().createQuery(hql).executeUpdate();
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryByHqlHaveDate(String hql,Object[] keys,Date ... date) {
		Query query = sessionFactoryProduct.getCurrentSession().createQuery(hql);
		for(int i=0;i<keys.length;i++){
			query.setTimestamp(keys[i].toString(), date[i]);
		}
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getObjectById(Class arg, int iidd) {
		return sessionFactoryProduct.getCurrentSession().get(arg,iidd);
	}
	
	@Override
	public Object getObjectByUniqueCode(String ClassName,String fieldName,String value) {
		String hql = "from "+ClassName+" t where t."+fieldName+" = '"+value+"'";
		return sessionFactoryProduct.getCurrentSession().createQuery(hql).uniqueResult();
	}
	
	@Override
	public Query getQuery(String hql) {
		return sessionFactoryProduct.getCurrentSession().createQuery(hql);
	}
	
	@Override
	public SQLQuery getSqlQuery(String sql) {
		return sessionFactoryProduct.getCurrentSession().createSQLQuery(sql);
	}

	@Override
	public Criteria getCriteria(Object object) {
		return sessionFactoryProduct.getCurrentSession().createCriteria(object.getClass());
	}*/
}
