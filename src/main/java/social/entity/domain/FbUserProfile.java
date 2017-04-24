package social.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.social.facebook.api.User;

import java.io.Serializable;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true) //isIdentityVerified
public class FbUserProfile extends  User implements Serializable {
    private boolean identityVerified; //isIdentityVerified


    public FbUserProfile(String id, String name, String firstName, String lastName, String gender, Locale locale) {
        super(id, name, firstName, lastName, gender, locale);
    }

    @Override
    public boolean isIdentityVerified() {
        return super.isIdentityVerified();
    }

    public void setIdentityVerified(boolean identityVerified) {
        this.identityVerified = super.isIdentityVerified();
    }
}
