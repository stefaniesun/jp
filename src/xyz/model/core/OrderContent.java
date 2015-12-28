
package xyz.model.core;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 */
@Entity
@Table(name="order_content")
public class OrderContent{
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String iidd;//主键
	
	@Column(name="order_num")
	private String orderNum;//订单编号，索引
	
	@Column(name="client_code",unique=true,nullable=false)
	private String clientCode;//客户码（例如：二维码），索引
	
	@Column(name="buyer")
	private String buyer;
	
	@Column(name="remark_buy",length=10000)
	private String remarkBuy;//备注
	
	@Column(name="product")
	private String product;//产品名称
	
	@Column(name="product_name_cn")
	private String productNameCn;//
	
	@Column(name="provider")
	private String provider;//供应商编号，索引
	
	@Column(name="provider_name_cn")
	private String providerNameCn;//供应商名称
	
	@Column(name="price",precision=16,scale=2)
	private BigDecimal price;//单价
	
	@Column(name="count")
	private int count;//数量
	
	@Column(name="money",precision=16,scale=2)
	private BigDecimal money;//真实支付价
	
	@Column(name="person_info",length=10000)
	private String personInfo;//出行凭证
	
	@Column(name="date_info")
	private Date dateInfo;//出行日期
	
	@Column(name="flag_pay")
	private int flagPay;//是否支付
	
	@Column(name="flag_client")
	private int flagClient;//是否出票
	
	@Column(name="flag_use")
	private int flagUse;//是否使用
	
	@Column(name="flag_apply")
	private int flagApply;//是否申请退票
	
	@Column(name="flag_refund")
	private int flagRefund;//是否退票
	
	@Column(name="flag_over")
	private int flagOver;//是否冻结
	
	@Column(name="add_date")
	private Date addDate;//录单日期
	
	@Column(name="pay_date")
	private Date payDate;//支付日期
	
	@Column(name="client_date")
	private Date clientDate;//出票日期
	
	@Column(name="use_date")
	private Date useDate;//使用日期
	
	@Column(name="apply_date")
	private Date applyDate;//申请退票日期
	
	@Column(name="refund_date")
	private Date refundDate;//退票日期
	
	@Column(name="alter_date")
	private Date alterDate;//修改时间
	
	@Column(name="over_date")
	private Date overDate;//冻结时间
	
	@Column(name="clean_date")
	private Date cleanDate;//清洗时间

	@Column(name="flag_remark")
	private String flagRemark;//备注状态
	
	@Column(name="remark",length=5000)
	private String remark;//票单备注

	public String getIidd() {
		return iidd;
	}

	public void setIidd(String iidd) {
		this.iidd = iidd;
	}

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

	public String getRemarkBuy() {
		return remarkBuy;
	}

	public void setRemarkBuy(String remarkBuy) {
		this.remarkBuy = remarkBuy;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}

	public Date getDateInfo() {
		return dateInfo;
	}

	public void setDateInfo(Date dateInfo) {
		this.dateInfo = dateInfo;
	}

	public int getFlagPay() {
		return flagPay;
	}

	public void setFlagPay(int flagPay) {
		this.flagPay = flagPay;
	}

	public int getFlagClient() {
		return flagClient;
	}

	public void setFlagClient(int flagClient) {
		this.flagClient = flagClient;
	}

	public int getFlagUse() {
		return flagUse;
	}

	public void setFlagUse(int flagUse) {
		this.flagUse = flagUse;
	}

	public int getFlagApply() {
		return flagApply;
	}

	public void setFlagApply(int flagApply) {
		this.flagApply = flagApply;
	}

	public int getFlagRefund() {
		return flagRefund;
	}

	public void setFlagRefund(int flagRefund) {
		this.flagRefund = flagRefund;
	}

	public int getFlagOver() {
		return flagOver;
	}

	public void setFlagOver(int flagOver) {
		this.flagOver = flagOver;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getClientDate() {
		return clientDate;
	}

	public void setClientDate(Date clientDate) {
		this.clientDate = clientDate;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public Date getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public Date getCleanDate() {
		return cleanDate;
	}

	public void setCleanDate(Date cleanDate) {
		this.cleanDate = cleanDate;
	}

	public String getFlagRemark() {
		return flagRemark;
	}

	public void setFlagRemark(String flagRemark) {
		this.flagRemark = flagRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
