package social.service.fb;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${facebook.user_profile.fields}")
    String [] fbFetchFields;

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

    public FbUser getAuthenticatedFbUser(){

        if(!isAuthenticated()){
            return null;
        }
        FbUserProfile userProfile = new FbUserProfile(getFbUserProfile());
        Long fbId = Long.parseLong(userProfile.getId());
        FbUser existUser = fbUserManager.findByFbId( fbId );
        if(existUser != null && userProfile != null){
            if(checkUpdateNeeded(existUser)){
                existUser.setDateModified(new Date());
                existUser.update(userProfile);
                existUser = fbUserManager.save(existUser);
            }
        }else if(userProfile != null && userProfile.getId() != null && userProfile.getId().length() > 0) { // NOSONAR
            existUser = new FbUser(Long.parseLong(userProfile.getId()),userProfile.getFirstName());
            existUser.setFbData( userProfile);
            existUser = fbUserManager.save(existUser);
        }
        return existUser;
    }

    public boolean checkUpdateNeeded(FbUser user){
        if(user == null) return false;
        //TODO: check by time interval between interval in properties and last update date
        return true; // temporally always to update
    }

    public boolean isAuthenticated(){
        this.init();
        if(facebook == null || !facebook.isAuthorized()){
            return false;
        }
        //facebook.userOperations().getUserProfile();
        return true;
    }

    public User getFbUserProfile(){
        if(!isAuthenticated()){
            return null;
        }
        return facebook.fetchObject("me", User.class, fbFetchFields);
    }

}
