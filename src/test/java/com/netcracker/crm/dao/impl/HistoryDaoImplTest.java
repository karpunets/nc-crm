package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.*;
import com.netcracker.crm.domain.model.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.netcracker.crm.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Karpunets
 * @since 01.05.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryDaoImplTest {
    
    @Autowired
    private HistoryDao historyDao;
    @Autowired
    private ComplaintDao complaintDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    private History historyCreated;
    private Complaint complaintCreated;
    private User userCreated;
    private Order orderCreated;
    private Product productCreated;

    @Before
    public void create() throws Exception {
        userCreated = new User();
        userCreated.setPassword("test password");
        userCreated.setFirstName("test first name");
        userCreated.setMiddleName("test middle name");
        userCreated.setEmail("test email");
        userCreated.setEnable(false);
        userCreated.setAccountNonLocked(false);
        userCreated.setContactPerson(false);
        userCreated.setUserRole(UserRole.ROLE_CUSTOMER);

        productCreated = new Product();
        productCreated.setTitle("test product title");
        productCreated.setStatus(ProductStatus.OUTDATED);

        orderCreated = new Order();
        orderCreated.setStatus(OrderStatus.NEW);
        orderCreated.setCustomer(userCreated);
        orderCreated.setProduct(productCreated);

        complaintCreated = new Complaint();
        complaintCreated.setTitle("test title complaint");
        complaintCreated.setMessage("test message complaint");
        complaintCreated.setStatus(ComplaintStatus.OPEN);
        complaintCreated.setDate(LocalDateTime.now());
        complaintCreated.setCustomer(userCreated);
        complaintCreated.setOrder(orderCreated);

        historyCreated = new History();
        historyCreated.setOldStatus(OrderStatus.PAUSED);
        historyCreated.setDateChangeStatus(LocalDateTime.now());
        historyCreated.setDescChangeStatus("test History desc change status");
        historyCreated.setOrder(orderCreated);
        historyCreated.setComplaint(complaintCreated);
        historyCreated.setProduct(productCreated);

        assertNotNull(historyDao.create(historyCreated));
    }

    @Test
    public void findAndUpdate() throws Exception {
        History historyFoundById = historyDao.findById(historyCreated.getId());
        assertEquals(historyCreated.getOldStatus(), historyFoundById.getOldStatus());

        List<History> historyFoundByProductId = historyDao.findAllByProductId(productCreated.getId());
        assertEquals(historyCreated.getId(), historyFoundByProductId.get(0).getId());

        List<History> historyFoundByComplaintId = historyDao.findAllByComplaintId(complaintCreated.getId());
        assertEquals(historyCreated.getId(), historyFoundByComplaintId.get(0).getId());

        List<History> historyFoundByOrderId = historyDao.findAllByOrderId(orderCreated.getId());
        assertEquals(historyCreated.getId(), historyFoundByOrderId.get(0).getId());

        List<History> historyFoundByDate = historyDao.findAllByDate(historyCreated.getDateChangeStatus().toLocalDate());
        assertEquals(historyCreated.getId(), historyFoundByDate.get(0).getId());

        historyCreated.setOldStatus(OrderStatus.ACTIVE);
        assertEquals(historyDao.update(historyCreated), historyCreated.getId());
    }

    @After
    public void delete() throws Exception {
        long affectedRows = historyDao.delete(historyCreated);
        assertEquals(affectedRows, 1L);
        complaintDao.delete(complaintCreated);
        orderDao.delete(orderCreated);
        productDao.delete(productCreated);
        userDao.delete(userCreated);
    }
    
}
