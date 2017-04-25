package social.entity.domain.converter;


import social.entity.domain.FbUserProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Slf4j
public class JpaJsonFbUserProfileConverter implements
        AttributeConverter<FbUserProfile, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(FbUserProfile meta) {
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage(),ex);
            jsonString = "";
        }
        return jsonString;
    }

    @Override
    public FbUserProfile convertToEntityAttribute(String dbData) {
        if(dbData == null) dbData = null;
        try {
            return objectMapper.readValue(dbData, FbUserProfile.class);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }
}
