package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {
	
	private final OrderDAO DAO = new OrderDAO();
	
	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final List<Long> itemId = new ArrayList<>();
		itemId.add(1L);
		itemId.add(1L);
		final Order created = new Order(2L, 1L, itemId);
		assertEquals(created, DAO.create(created));
	}
	
	@Test
	public void testCreateItemExpetion() {
		final List<Long> itemId = new ArrayList<>();
		itemId.add(1L);
		itemId.add(3L);
		final Order created = new Order(2L, 1L, itemId);
		assertEquals(null, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		List<Order> expected = new ArrayList<>();
		final List<Long> itemId = new ArrayList<>();
		itemId.add(1L);
		itemId.add(1L);
		expected.add(new Order(1L, 1L, itemId));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		final List<Long> itemId = new ArrayList<>();
		itemId.add(1L);
		itemId.add(1L);
		assertEquals(new Order(1L, 1L, itemId), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final List<Long> itemId = new ArrayList<>();
		itemId.add(1L);
		itemId.add(1L);
		final long ID = 1L;
		assertEquals(new Order(ID, 1L, itemId), DAO.read(ID));
	}
	
	@Test
	public void testReadByCustomer() {
		final List<Long> orderId = new ArrayList<>();
		orderId.add(1L);
		final long ID = 1L;
		assertEquals(orderId, DAO.readByCustomer(ID));
	}
	
	@Test
	public void testReadByItem() {
		final List<Long> itemId = new ArrayList<>();
		itemId.add(1L);
		itemId.add(1L);
		final long ID = 1L;
		assertEquals(itemId, DAO.readByItem(ID));
	}

	@Test
	public void testUpdateAdd() {
		final List<Long> itemIdUpdater = new ArrayList<>();
		itemIdUpdater.add(1L);
		final List<Long> itemIdUpdated = new ArrayList<>();
		itemIdUpdated.add(1L);
		itemIdUpdated.add(1L);
		itemIdUpdated.add(1L);
		final Order updater = new Order("ADD", 1L, itemIdUpdater);
		final Order updated = new Order(1L, 1L, itemIdUpdated);
		assertEquals(updated, DAO.update(updater));
	}
	
	@Test
	public void testUpdateRemove() {
		final List<Long> itemId = new ArrayList<>();
		itemId.add(1L);
		itemId.add(1L);
		final Order error = new Order("ERROR", 1L, itemId);
		DAO.update(error);
		final Order updater = new Order("REMOVE", 1L, itemId);
		itemId.remove(0);
		final Order updated = new Order(1L, 1L, itemId);
		assertEquals(updated, DAO.update(updater));
	}

	@Test
	public void testDelete() {
		assertEquals(3, DAO.delete(1));
	}
	
	@Test
	public void testCalculator() {
		final long ID = 1L;
		assertEquals(6980000F, DAO.calculateCost(ID), 0.1);
	}
}
