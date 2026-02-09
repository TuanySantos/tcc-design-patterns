package withPatterns.repository.impl;

import withPatterns.model.User;
import withPatterns.repository.UserRepositoryCustom;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAllCustom() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
