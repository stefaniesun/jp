
package xyz.model.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Security_api",
	uniqueConstraints = {
		@UniqueConstraint(columnNames={"function","name_cn"})
	})
public class SecurityApi {
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private int iidd;//主键
	
	@Column(name="function")
	private String function;//编号
	
	@Column(name="name_cn")
	private String nameCn;//中文名称
	
	@Column(name="flag_server")
	private int flagServer;//服务类型  0:本地服务  1:远程商户模块
	
	@Column(name="url")
	private String url;//服务地址(特指servletPath)
	
	@Column(name="is_work")
	private int isWork;//是否属于前台按钮
	
	@Column(name="button_code")
	private String buttonCode;//按钮id
	
	@Column(name="is_decide")
	private int isDecide;
	
	@Column(name="add_date")
	private Date addDate;//添加日期
	
	@Column(name="alter_date")
	private Date alterDate;//修改时间
	
	@Transient
	private String functionNameCn;//所属功能

	public int getIidd() {
		return iidd;
	}

	public void setIidd(int iidd) {
		this.iidd = iidd;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public int getFlagServer() {
		return flagServer;
	}

	public void setFlagServer(int flagServer) {
		this.flagServer = flagServer;
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

	public int getIsWork() {
		return isWork;
	}

	public void setIsWork(int isWork) {
		this.isWork = isWork;
	}

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getFunctionNameCn() {
		return functionNameCn;
	}

	public void setFunctionNameCn(String functionNameCn) {
		this.functionNameCn = functionNameCn;
	}

	public int getIsDecide() {
		return isDecide;
	}

	public void setIsDecide(int isDecide) {
		this.isDecide = isDecide;
	}

}
