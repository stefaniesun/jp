
package xyz.model.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="security_function")
public class SecurityFunction {
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private int iidd;//主键
	
	@Column(name="number_code",unique=true)
	private String numberCode;//编号
	
	@Column(name="name_cn",unique=true)
	private String nameCn;//中文名称
	
	@Column(name="is_api")
	private int isApi;//是否是孤立的API
	
	@Column(name="url")
	private String url;//服务地址
	
	@Column(name="add_date")
	private Date addDate;//添加日期
	
	@Column(name="alter_date")
	private Date alterDate;//修改时间
	
	@Column(name="group_cn")
	private String groupCn;//修改时间

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getGroupCn() {
		return groupCn;
	}

	public void setGroupCn(String groupCn) {
		this.groupCn = groupCn;
	}

	public int getIsApi() {
		return isApi;
	}

	public void setIsApi(int isApi) {
		this.isApi = isApi;
	}
}
