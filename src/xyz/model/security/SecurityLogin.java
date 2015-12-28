
package xyz.model.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="security_login")
public class SecurityLogin {
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private int iidd;//
	
	@Column(name="apikey",unique=true)
	private String apikey;//密钥
	
	@Column(name="username")
	private String username;//用户名
	
	@Column(name="nick_name")
	private String nickName;//
	
	@Column(name="is_repeat")
	private int isRepeat;//是否允许重复登录
	
	@Column(name="position")
	private String position;//岗位
	
	@Column(name="decide_str",length=100000)
	private String decideStr;//行级别控制
	
	@Column(name="add_date")
	private Date addDate;//添加时间
	
	@Column(name="expire_date")
	private Date expireDate;//过期时间

	public int getIidd() {
		return iidd;
	}

	public void setIidd(int iidd) {
		this.iidd = iidd;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(int isRepeat) {
		this.isRepeat = isRepeat;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getDecideStr() {
		return decideStr;
	}

	public void setDecideStr(String decideStr) {
		this.decideStr = decideStr;
	}
}
