package withPatterns.repository;

import withPatterns.model.User;
import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAllCustom();
}
