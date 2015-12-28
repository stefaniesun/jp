package xyz.model.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="log_ts_temp")
public class LogTsTemp {
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String iidd;//
	
	@Column(name="number_code",unique=true,nullable=false)
	private String numberCode;//通知编号
	
	@Column(name="system_type")
	private String systemType;//平台
	
	@Column(name="product")
	private String product;//
	
	@Column(name="product_name_cn")
	private String productNameCn;//
	
	@Column(name="date_info")
	private Date dateInfo;//库存使用日期
	
	@Column(name="count")
	private int count;//数量
	
	@Column(name="add_date")
	private Date addDate;//添加日期
	
	@Column(name="remark")
	private String remark;//备注

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

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public Date getDateInfo() {
		return dateInfo;
	}

	public void setDateInfo(Date dateInfo) {
		this.dateInfo = dateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	
}
