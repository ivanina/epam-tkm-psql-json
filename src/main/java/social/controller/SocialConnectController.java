package social.controller;


import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

@Named
public class SocialConnectController extends ConnectController {

    private HttpServletRequest request;

    private HttpServletResponse response;

    private static final String REDIRECT_PATH = "redirect:/";

    @Inject
    public SocialConnectController(ConnectionFactoryLocator connectionFactoryLocator,
                                   ConnectionRepository connectionRepository,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        super(connectionFactoryLocator, connectionRepository);
        this.request = request;
        this.response = response;

    }

    @Override
    protected String connectView() {
        return redirect();
    }

    @Override
    protected String connectView(String providerId) {
        return redirect();
    }

    @Override
    protected String connectedView(String providerId) {
        return redirect();
    }

    protected String redirect(){
        if(request.getHeader("referer") != null && request.getHeader("referer").length() > 1){
            try {
                URL url = new URL(request.getHeader("referer"));
                response.setHeader("referer","");
                return "redirect:"+url.getPath();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        return REDIRECT_PATH;
    }

}
