package com.electems.rmc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "T_LINEITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LineItem extends AbstractAuditingEntity implements Serializable {
	
private static final long serialVersionUID = -7722544975069356302L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "item_name")
	private String itemName;		
	
	@Column(name = "item_quantity")
	private Long itemQuantity;
	
	@Column(name = "item_unitrate")
	private Long itemUnitRate;
	
	/*@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", insertable = false, updatable = false)
	private Order order;*/
	
	@JsonBackReference("lineitem")
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Long getItemUnitRate() {
		return itemUnitRate;
	}

	public void setItemUnitRate(Long itemUnitRate) {
		this.itemUnitRate = itemUnitRate;
	}

}
