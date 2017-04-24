package social.entity.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.social.facebook.api.User;


import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "facebook_user")
public class FbUser implements EntityDomain {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name="facebook_user_id_seq", sequenceName="facebook_user_id_seq",  allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator="facebook_user_id_seq")
    private Long id;

    @Column(name = "fb_id")
    private Long fbId;

    @Column(name = "first_name", columnDefinition = "VARCHAR(50)")
    private String firstName;

    /*@Column(name = "keywords", columnDefinition = "JSON")
    @Type(type = "FbUserProfileJson")
    private String keywords;*/

    @Column(name = "fb_data", columnDefinition = "JSON")
    @Convert(converter = JpaJsonFbUserProfileConverter.class)
    private FbUserProfile fbData;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_added", columnDefinition = "TIMESTAMP NOT NULL")
    private Date dateAdded;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modified", columnDefinition = "TIMESTAMP DEFAULT NULL")
    private Date dateModified;


    public FbUser(Long fbId, String firstName){
        this.fbId = fbId;
        this.firstName = firstName;
    }
    public FbUser(){}

}
