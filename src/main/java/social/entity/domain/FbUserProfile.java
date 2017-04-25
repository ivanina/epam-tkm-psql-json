package social.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.social.facebook.api.User;

import java.io.Serializable;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FbUserProfile extends  User implements Serializable {
    private boolean identityVerified; // NOSONAR


    public FbUserProfile(String id, String name, String firstName, String lastName, String gender, Locale locale) {
        super(id, name, firstName, lastName, gender, locale);
    }

    public FbUserProfile(User user){
        this(user.getId(),user.getName(),user.getFirstName(),user.getLastName(),user.getGender(),user.getLocale());
        this.identityVerified = user.isIdentityVerified();
    }

    public FbUserProfile(){
        super(null,null,null,null,null,null);
    }

    @Override
    public boolean isIdentityVerified() {
        return super.isIdentityVerified();
    }

    public boolean getIdentityVerified() {
        return super.isIdentityVerified();
    }

    public void setIdentityVerified(boolean identityVerified) {  // NOSONAR
        this.identityVerified = super.isIdentityVerified();
    }

}
