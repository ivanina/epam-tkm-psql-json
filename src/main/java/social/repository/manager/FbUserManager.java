package social.repository.manager;


import social.entity.domain.FbUser;
import org.springframework.stereotype.Component;


import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@Component
public class FbUserManager {

    FbUserRepository fbUserRepository;

    @Inject
    public FbUserManager(FbUserRepository fbUserRepository) {
        this.fbUserRepository = fbUserRepository;
    }

    public FbUser save(FbUser user){
        return fbUserRepository.save(user);
    }
    public FbUser findByFbId(Long fbId){
        return fbUserRepository.findByFbIdIs(fbId);
    }
}
