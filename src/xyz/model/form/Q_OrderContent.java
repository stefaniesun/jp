
package xyz.model.form;

import java.util.Date;

/*
 * 基础数据：产品、单品、供应商、渠道
 * 操作数据+状态数据：状态、切换人、切换时间
 * 单号数据：订单号、渠道单号、供单号
 * 客户数据：渠道账号、付款人、实名人
 * 备注信息：渠道备注、内部备注
 * 账务数据：应收、应付、实收、实付
 * 数量数据：产品数、单品数、消费人数
 */
public class Q_OrderContent{
	private String orderNum;
	private String clientCode;
	private String buyer;
	private String product;
	private String productNameCn;
	private String provider;
	private String providerNameCn;
	private String personInfo;
	private String flagStr;
	private	String dateStr;
	private	Date dateStart;
	private Date dateEnd;
	private String remarkStr;
	private String remark;
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getProductNameCn() {
		return productNameCn;
	}
	public void setProductNameCn(String productNameCn) {
		this.productNameCn = productNameCn;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getProviderNameCn() {
		return providerNameCn;
	}
	public void setProviderNameCn(String providerNameCn) {
		this.providerNameCn = providerNameCn;
	}
	public String getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}
	public String getFlagStr() {
		return flagStr;
	}
	public void setFlagStr(String flagStr) {
		this.flagStr = flagStr;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getRemarkStr() {
		return remarkStr;
	}
	public void setRemarkStr(String remarkStr) {
		this.remarkStr = remarkStr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
