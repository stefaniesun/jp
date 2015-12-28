package xyz.model.member;

import java.util.Date;

public class XyzSessionLogin {
	private String apikey;//用户名
	
	private String username;//用户名
	
	private int isSeller; // 1 卖家
	
	private Date expireDate;//过期时间

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public int getIsSeller() {
		return isSeller;
	}

	public void setIsSeller(int isSeller) {
		this.isSeller = isSeller;
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
}
