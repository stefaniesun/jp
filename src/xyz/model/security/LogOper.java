
package xyz.model.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="log_oper")
public class LogOper {
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private int iidd;// 
	
	@Column(name="username")
	private String username;//用户名
	
	@Column(name="ip_info")
	private String ipInfo;//ip信息
	
	@Column(name="interface_path")
	private String interfacePath;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="is_work")
	private int isWork;
	
	@Column(name="flag_result")
	private int flagResult;//结果
	
	@Column(name="data_content",length=20000)
	private String dataContent;//内容
	
	@Column(name="add_date")
	private Date addDate;//添加时间

	public int getIidd() {
		return iidd;
	}

	public void setIidd(int iidd) {
		this.iidd = iidd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}

	public String getInterfacePath() {
		return interfacePath;
	}

	public void setInterfacePath(String interfacePath) {
		this.interfacePath = interfacePath;
	}

	public int getIsWork() {
		return isWork;
	}

	public void setIsWork(int isWork) {
		this.isWork = isWork;
	}

	public String getDataContent() {
		return dataContent;
	}

	public void setDataContent(String dataContent) {
		this.dataContent = dataContent;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getFlagResult() {
		return flagResult;
	}

	public void setFlagResult(int flagResult) {
		this.flagResult = flagResult;
	}
}
