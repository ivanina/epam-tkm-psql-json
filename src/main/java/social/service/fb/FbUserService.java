package social.service.fb;

import social.entity.domain.FbUser;
import social.entity.domain.FbUserProfile;
import social.repository.manager.FbUserManager;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;


import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;

@Aspect
@Service
@Transactional
public class FbUserService implements FbService {

    private Facebook facebook;

    private ConnectionRepository connectionRepository;

    FbUserManager fbUserManager;

    @Inject
    public FbUserService(ConnectionRepository connectionRepository,  FbUserManager fbUserManager) {
        this.connectionRepository = connectionRepository;
        this.fbUserManager = fbUserManager;
    }

    void init() {
        if(connectionRepository == null) return;
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
        //Facebook facebook = connection != null ? connection.getApi() : new FacebookTemplate(token);
        if(connection != null){
            this.facebook = connection.getApi();
        }
    }

    public void init(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
        this.init();
    }


    public boolean isAuthenticated(){
        this.init();
        if(facebook == null || !facebook.isAuthorized()){
            return false;
        }
        return true;
    }

    public FbUser getAuthenticatedFbUser(){

        // [ TODO: will drop this test

        // end test ]

        if(!isAuthenticated()){
            return null;
        }
        User userProfile = getFbUserProfile();
        Long fbId = Long.parseLong(userProfile.getId());
        FbUser existUser = fbUserManager.findByFbId( fbId );
        if(existUser != null){
            if(checkUpdateNeeded(existUser)){
                //TODO: updated user to repository
            }
        }else if(userProfile != null && userProfile.getId() != null && userProfile.getId().length() > 0) {
            //TODO: create new FbUser
            existUser = new FbUser(Long.parseLong(userProfile.getId()),userProfile.getFirstName());
            existUser.setDateAdded(new Date());
            existUser.setFbData((FbUserProfile) userProfile);
            existUser = fbUserManager.save(existUser);
        }
        return existUser;
    }

    public boolean checkUpdateNeeded(FbUser user){
        if(user == null) return false;
        //TODO: check by time interval between interval in properties and last update date
        return false;
    }

    public User getFbUserProfile(){
        if(!isAuthenticated()){
            return null;
        }
        String [] fields = { "id", "email",  "first_name", "last_name" };
        return facebook.fetchObject("me", User.class, fields);
    }

}
