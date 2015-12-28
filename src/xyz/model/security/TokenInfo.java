
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
@Table(name="token_info")
public class TokenInfo{
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private int iidd;//主键
	
	@Column(name="token_num",unique=true)
	private String tokenNum;//动态令牌号
	
	@Column(name="authkey")
	private String authkey;//密钥
	
	@Column(name="current_success")
	private long currentSuccess;//成功值
	
	@Column(name="current_drift")
	private int currentDrift;//漂移指
	
	@Column(name="add_date")
	private Date addDate;//添加日期
	
	@Column(name="alter_date")
	private Date alterDate;//修改时间
	
	@Transient
	private int countUser;//绑定人数
	
	@Transient
	private String usernames;//绑定人数

	public int getIidd() {
		return iidd;
	}

	public void setIidd(int iidd) {
		this.iidd = iidd;
	}

	public String getTokenNum() {
		return tokenNum;
	}

	public void setTokenNum(String tokenNum) {
		this.tokenNum = tokenNum;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public long getCurrentSuccess() {
		return currentSuccess;
	}

	public void setCurrentSuccess(long currentSuccess) {
		this.currentSuccess = currentSuccess;
	}

	public int getCurrentDrift() {
		return currentDrift;
	}

	public void setCurrentDrift(int currentDrift) {
		this.currentDrift = currentDrift;
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

	public String getUsernames() {
		return usernames;
	}

	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}
}
