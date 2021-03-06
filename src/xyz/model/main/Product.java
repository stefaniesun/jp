package xyz.model.main;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="product")
public class Product {

	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String iidd;//
	
	
	@Column(name="number_code",unique=true,nullable=false)
	private String numberCode;//
	
	@Column(name="name")
	private String name;
	
	@Column(name="special")
	private String special;
	
	@Column(name="type")
	private String type;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="basePrice")
	private BigDecimal basePrice;
	
	@Column(name="stock")
	private int stock;
	
	@Column(name="product_content")
	private String content;
	
	@Column(name="image")
	private String image;
	
	@Column(name="area_flag")
	private int areaFlag;//0不限 1只限大重庆区售卖
	
	@Column(name="postage_price")
	private BigDecimal postagePrice;
	
	@Transient
	private String images;

	public String getIidd() {
		return iidd;
	}

	public void setIidd(String iidd) {
		this.iidd = iidd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getAreaFlag() {
		return areaFlag;
	}

	public void setAreaFlag(int areaFlag) {
		this.areaFlag = areaFlag;
	}

	public BigDecimal getPostagePrice() {
		return postagePrice;
	}

	public void setPostagePrice(BigDecimal postagePrice) {
		this.postagePrice = postagePrice;
	}
	
	
}