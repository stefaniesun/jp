
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
@Table(name="security_user")
public class SecurityUser{
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private int iidd;//主键
	
	@Column(name="username",unique=true)
	private String username;//用户名
	
	@Column(name="nick_name",unique=true)
	private String nickName;//昵称
	
	@Column(name="password")
	private String password;//密码
	
	@Column(name="enabled")
	private int enabled;//1：激活  0：锁死
	
	@Column(name="is_repeat")
	private int isRepeat;//是否允许重复登录
	
	@Column(name="token_num")
	private String tokenNum;//动态令牌号
	
	@Column(name="position")
	private String position;//岗位编号
	
	@Column(name="possessor")
	private String possessor;//资源组
	
	@Column(name="add_date")
	private Date addDate;//添加日期
	
	@Column(name="alter_date")
	private Date alterDate;//修改时间
	
	
	@Transient
	private String positionNameCn;
	
	@Transient
	private String possessorNameCn;

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(int isRepeat) {
		this.isRepeat = isRepeat;
	}

	public String getTokenNum() {
		return tokenNum;
	}

	public void setTokenNum(String tokenNum) {
		this.tokenNum = tokenNum;
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

	public Date getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}

	public String getPositionNameCn() {
		return positionNameCn;
	}

	public void setPositionNameCn(String positionNameCn) {
		this.positionNameCn = positionNameCn;
	}

	public String getPossessor() {
		return possessor;
	}

	public void setPossessor(String possessor) {
		this.possessor = possessor;
	}

	public String getPossessorNameCn() {
		return possessorNameCn;
	}

	public void setPossessorNameCn(String possessorNameCn) {
		this.possessorNameCn = possessorNameCn;
	}


}
