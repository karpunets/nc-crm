package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Karpunets
 * @since 30.04.2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintDaoImplTest {

    @Autowired
    private ComplaintDao complaintDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    private Complaint complaintCreated;
    private User userCreated;
    private Order orderCreated;
    private Product productCreated;

    @Before
    public void create() throws Exception {
        complaintCreated = new Complaint();
        complaintCreated.setTitle("test title complaint");
        complaintCreated.setMessage("test message complaint");
        complaintCreated.setStatus(ComplaintStatus.OPEN);
        complaintCreated.setDate(LocalDateTime.now());

        userCreated = new User();
        userCreated.setPassword("test password");
        userCreated.setFirstName("test first name");
        userCreated.setMiddleName("test middle name");
        userCreated.setEmail("test email");
        userCreated.setEnable(false);
        userCreated.setAccountNonLocked(false);
        userCreated.setContactPerson(false);
        userCreated.setUserRole(UserRole.ROLE_CUSTOMER);
        complaintCreated.setCustomer(userCreated);

        orderCreated = new Order();
        orderCreated.setStatus(OrderStatus.NEW);
        orderCreated.setCustomer(userCreated);

        productCreated = new Product();
        productCreated.setTitle("test product title");
        productCreated.setStatus(ProductStatus.OUTDATED);
        orderCreated.setProduct(productCreated);

        complaintCreated.setOrder(orderCreated);

        assertNotNull(complaintDao.create(complaintCreated));
    }

    @Test
    public void findAndUpdate() throws Exception {
        Complaint complaintFoundById = complaintDao.findById(complaintCreated.getId());
        assertEquals(complaintCreated.getTitle(), complaintFoundById.getTitle());

        List<Complaint> complaintsFoundByTitle = complaintDao.findByTitle(complaintCreated.getTitle());
        assertEquals(complaintCreated.getId(), complaintsFoundByTitle.get(0).getId());

        List<Complaint> complaintsFoundByDate = complaintDao.findAllByDate(complaintCreated.getDate().toLocalDate());
        assertEquals(complaintCreated.getId(), complaintsFoundByDate.get(0).getId());

        complaintCreated.setMessage("update test message complaint");
        assertEquals(complaintDao.update(complaintCreated), complaintCreated.getId());
    }

    @After
    public void delete() throws Exception {
        long affectedRows = complaintDao.delete(complaintCreated);
        assertEquals(affectedRows, 1L);
        orderDao.delete(orderCreated);
        productDao.delete(productCreated);
        userDao.delete(userCreated);
    }

}