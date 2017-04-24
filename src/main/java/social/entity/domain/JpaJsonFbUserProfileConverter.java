package social.entity.domain;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.facebook.api.User;

import javax.persistence.AttributeConverter;
import java.io.IOException;


public class JpaJsonFbUserProfileConverter implements
        AttributeConverter<User, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(User meta) {
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(meta);

        } catch (JsonProcessingException ex) {
        }
        return jsonString;
    }

    @Override
    public User convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
