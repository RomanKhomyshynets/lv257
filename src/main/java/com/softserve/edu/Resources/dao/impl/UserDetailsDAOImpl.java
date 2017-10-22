package com.softserve.edu.Resources.dao.impl;

import com.softserve.edu.Resources.dao.UserDetailsDAO;
import com.softserve.edu.Resources.entity.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Repository
@Qualifier("userDrtailsDAO")
@Transactional
public class UserDetailsDAOImpl extends GenericDAOImpl<UserDetails, Long> implements UserDetailsDAO {

    static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    @PersistenceContext
    private EntityManager entityManager;

    protected UserDetailsDAOImpl() {
        super(UserDetails.class, LOGGER);
    }

    @Override
    public Optional<UserDetails> findByEmail(String email) {
        String queryUserDetails = "select i from User i where i.username = :username";
        return querySingleResult(queryUserDetails, "username", email);
    }

    public UserDetails findByUserId(long id) {
        Query query = entityManager.createQuery("select i from UserDetails i where i.id = :id")
                .setParameter("id", id);
        UserDetails userDetails = (UserDetails) query.getSingleResult();
        return userDetails;
    }

    public void save(UserDetails userDetails) {
        final UserDetails userDetailsToEntity1 = makePersistent(userDetails);
    }
}