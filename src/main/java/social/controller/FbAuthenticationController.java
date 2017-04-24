package social.controller;

import social.entity.domain.FbUser;
import social.service.fb.FbUserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("api/fb/authentication")
public class FbAuthenticationController {

    private FbUserService fbUserService;
    @Inject
    public FbAuthenticationController(FbUserService fbUserService) {
        this.fbUserService = fbUserService;
    }

    @RequestMapping(method = RequestMethod.GET)
    FbUser getCurrentFbUser(){
        FbUser fbCurrentUser = fbUserService.getAuthenticatedFbUser();
        if(fbCurrentUser == null){
            fbCurrentUser = new FbUser();
        }

        return fbCurrentUser;
    }

}
