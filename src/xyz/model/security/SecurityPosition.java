
package xyz.model.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="security_position")
public class SecurityPosition {
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private int iidd;//主键
	
	@Column(name="number_code",unique=true)
	private String numberCode;//编号
	
	@Column(name="name_cn",unique=true)
	private String nameCn;//中文名称
	
	@Column(name="remark",length=5000)
	private String remark;//备注
	
	@Column(name="priority",length=5000)
	private int priority;//岗位级别
	
	@Column(name="add_date")
	private Date addDate;//添加日期
	
	@Column(name="alter_date")
	private Date alterDate;//修改时间
	
	@Transient
	private int countUser;

	public int getIidd() {
		return iidd;
	}

	public void setIidd(int iidd) {
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public int getCountUser() {
		return countUser;
	}

	public void setCountUser(Number countUser) {
		this.countUser = countUser==null?0:countUser.intValue();
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
