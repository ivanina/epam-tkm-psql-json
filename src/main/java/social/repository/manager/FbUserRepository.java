package social.repository.manager;

import social.entity.domain.FbUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Transactional
@Repository
public interface FbUserRepository extends CrudRepository<FbUser,Long> {
    FbUser findByFbIdIs(Long fbId);
    FbUser save(FbUser fbUser);

}
