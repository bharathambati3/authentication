package com.sravs.auth.db.dao;

import com.sravs.auth.db.domain.User;
import com.sravs.common.db.BaseJpaDao;
import com.sravs.common.db.Model;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Model
@Dependent
public class UserDao extends BaseJpaDao<Integer, User> {

    public UserDao() {
        super(User.class);
    }

    @PersistenceContext(name = "authPU")
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User getPassword(String username) throws NoResultException {
        Query query = entityManager.createNamedQuery("getUserPassword");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
