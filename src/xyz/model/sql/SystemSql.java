
package xyz.model.sql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="system_sql")
public class  SystemSql{
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String iidd;//主键
	
	@Column(name="number_code",unique=true,nullable=false)
	private String numberCode;
	
	@Column(name="name_cn")
	private String nameCn;
	
	@Column(name="source")
	private String source; //执行此SQL的库
	
	@Column(name="sql_type")
	private String sqlType; //执行此SQL的库
	
	@Column(name="sql_title",length=5000)
	private String sqlTitle;
	
	@Column(name="sql_content",length=5000)
	private String sqlContent;
	
	@Column(name="username_add")
	private String usernameAdd;//权利人
	
	@Column(name="username_role")
	private String usernameRole;//共享人
	
	@Column(name="use_count")
	private Integer useCount;//使用次数 
	
	@Column(name="add_date")
	private Date addDate;

	@Column(name="alter_date")
	private Date alterDate;
	
	public String getIidd() {
		return iidd;
	}

	public void setIidd(String iidd) {
		this.iidd = iidd;
	}

	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSqlTitle() {
		return sqlTitle;
	}

	public void setSqlTitle(String sqlTitle) {
		this.sqlTitle = sqlTitle;
	}

	public String getSqlContent() {
		return sqlContent;
	}

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	public String getUsernameAdd() {
		return usernameAdd;
	}

	public void setUsernameAdd(String usernameAdd) {
		this.usernameAdd = usernameAdd;
	}

	public String getUsernameRole() {
		return usernameRole;
	}

	public void setUsernameRole(String usernameRole) {
		this.usernameRole = usernameRole;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}
	
}
