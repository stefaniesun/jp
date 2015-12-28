
package xyz.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonDao {
	
	public void clear();
	public void flush();
	public void evict(Object obj);
	
	/**
	 * 将未知具体类型的实体对象存入数据库表
	 * @param object 实体对象
	 */
	public void save(Object object);
	
	/**
	 * 从未知表名的数据库表删除一行相关记录
	 * @param object 实体对象
	 */
	public void delete(Object object);
	
	/**
	 * 从未知表名的数据库表更新一行相关记录
	 * @param object 实体对象
	 */
	public void update(Object object);
	
	/**
	 * 根据未知类型的实体对象查出所有已知属性相同的表记录,若无须分页和排序，可将相关字段设为null
	 * @param object 实体对象
	 * @param page 第几页
	 * @param count 每页数据量
	 * @param orderPropertitys 排序字段
	 * @param isFull 是否全部数据
	 * @return 数据列表
	 */
	@SuppressWarnings("rawtypes")
	public List queryByExample(Object object,int offset,int count,String orderPropertitys,String orderType,boolean isFull);
	
	/**
	 * 查询记录数
	 * @param object 实体对象
	 * @param isFull 是否全部数据
	 * @return 数据量
	 */
	public int queryEntityCount(Object object,boolean isFull);
	
	/**
	 * 依靠hql查询 
	 * @param hql 
	 * @return 集合
	 */
	@SuppressWarnings("rawtypes")
	public List queryByHql(String hql);
	
	/**
	 * 依靠hql查询 唯一结果
	 * @param hql 
	 * @return 集合
	 */
	public Object queryUniqueByHql(String hql);
	
	/**
	 * 依靠对象id查询 唯一结果
	 * @param hql 
	 * @return 集合
	 */
	@SuppressWarnings("rawtypes")
	public Object getObjectById(Class arg,int iidd);
	
	public Object getObjectByUniqueCode(String ClassName,String fieldName,String value);
	
	/**
	 * 依靠hql更新数据
	 * @param hql 
	 * @return 集合
	 */
	public int updateByHql(String hql);
	
	@SuppressWarnings("rawtypes")
	public List queryByHqlHaveDate(String hql,Object[] keys,Date ... date);
	
	public Query getQuery(String hql);
	
	public SQLQuery getSqlQuery(String sql);
	
	public Criteria getCriteria(Object object);
}
