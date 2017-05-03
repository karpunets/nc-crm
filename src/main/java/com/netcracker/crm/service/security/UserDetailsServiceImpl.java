package com.netcracker.crm.service.security;


import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 21.04.2017.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (null == user) {
            throw new UsernameNotFoundException("No user present with email : " + email);
        } else {
            UserRole role = user.getUserRole();
            List<String> userRoles = new ArrayList<>();
            userRoles.add(role.getName());
            log.info("User successful find with email  : " + email);
            return new UserDetailsImpl(user, userRoles);
        }
    }
}
