
package xyz.model.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="provider")
public class Provider {
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String iidd;//主键
	
	@Column(name="number_code",unique=true,nullable=false)
	private String numberCode;//编号
	
	@Column(name="name_cn")
	private String nameCn;//名称
	
	@Column(name="type")
	private String type;//类型 HO酒店\SC景点
	
	@Column(name="level")
	private String level;//级别
	
	@Column(name="level_system")
	private String levelSystem;//级别
	
	@Column(name="phone")
	private String phone;//电话
	
	@Column(name="qq")
	private String qq;//腾讯账号
	
	@Column(name="email")
	private String email;//邮箱
	
	@Column(name="linkman")
	private String linkman;//联系人
	
	@Column(name="region_first")
	private String regionFirst;//一级区域
	
	@Column(name="region_second")
	private String regionSecond;//二级区域
	
	@Column(name="region_third")
	private String regionThird;//三级区域
	
	@Column(name="address")
	private String address;//地址

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevelSystem() {
		return levelSystem;
	}

	public void setLevelSystem(String levelSystem) {
		this.levelSystem = levelSystem;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getRegionFirst() {
		return regionFirst;
	}

	public void setRegionFirst(String regionFirst) {
		this.regionFirst = regionFirst;
	}

	public String getRegionSecond() {
		return regionSecond;
	}

	public void setRegionSecond(String regionSecond) {
		this.regionSecond = regionSecond;
	}

	public String getRegionThird() {
		return regionThird;
	}

	public void setRegionThird(String regionThird) {
		this.regionThird = regionThird;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
