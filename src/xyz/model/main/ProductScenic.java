
package xyz.model.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="product_scenic",uniqueConstraints = {@UniqueConstraint(columnNames={"provider","name_cn"})})
public class ProductScenic {
	@Id
	@Column(name="iidd",unique=true,nullable=false)
	@GeneratedValue(generator = "paymentableGenerator")       
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")   
	private String iidd;//主键
	
	@Column(name="provider")
	private String provider;//
	
	@Column(name="number_code",unique=true,nullable=false)
	private String numberCode;//
	
	@Column(name="name_cn")
	private String nameCn;//名称
	
	public String getIidd() {
		return iidd;
	}

	public void setIidd(String iidd) {
		this.iidd = iidd;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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
}