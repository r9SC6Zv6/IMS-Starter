package com.qa.ims.persistence.domain;

import java.util.List;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;

public class Order {

	private Long id;
	private Long customerId;
	private List<Long> itemId;
	private String update;

	public Order(Long customerId, List<Long> itemId) {
		this.setCustomerId(customerId);
		this.setItemId(itemId);
	}

	public Order(Long id, Long customerId, List<Long> itemId) {
		this.setId(id);
		this.setCustomerId(customerId);
		this.setItemId(itemId);
	}
	
	public Order(String update, Long id, List<Long> itemId) {
		this.setId(id);
		this.setItemId(itemId);
		this.setUpdate(update);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<Long> getItemId() {
		return itemId;
	}

	public void setItemId(List<Long> itemId) {
		this.itemId = itemId;
	}
	
	public String getUpdate() {
		return update;
	}
	
	public void setUpdate(String update) {
		this.update = update;
	}

	private String itemsToString(List<Long> itemId) {
		ItemDAO itemDAO = new ItemDAO();
		StringBuilder itemString = new StringBuilder();
		if (!itemId.isEmpty()) {
		for (Long l : itemId) {
			itemString.append(System.lineSeparator() + "  - " + itemDAO.read(l).getName() + "  Id: " + l);
		}
		} else {
			itemString.append(System.lineSeparator() + "  No items in this order");
		}
		itemString.append(System.lineSeparator());
		return itemString.toString();
	}

	@Override
	public String toString() {
		CustomerDAO customerDAO = new CustomerDAO();
		OrderDAO orderDAO = new OrderDAO();
		return "Order id: " + id + "  Customer name: " + customerDAO.read(customerId).getFirstName() + " "
				+ customerDAO.read(customerId).getSurname() + "  Customer id: " + customerId + "  Items: " + itemsToString(itemId) + "Total cost: " + orderDAO.calculateCost(id) + System.lineSeparator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((update == null) ? 0 : update.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (getCustomerId() == null) {
			if (other.getCustomerId() != null)
				return false;
		} else if (!getCustomerId().equals(other.getCustomerId()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (update == null) {
			if (other.update != null)
				return false;
		} else if (!update.equals(other.update))
			return false;
		return true;
	}

}
