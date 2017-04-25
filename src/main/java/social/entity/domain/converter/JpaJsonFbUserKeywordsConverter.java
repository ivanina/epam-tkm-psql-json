package social.entity.domain.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JpaJsonFbUserKeywordsConverter implements
        AttributeConverter<List<String>, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(List<String> meta) {
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
    public List<String> convertToEntityAttribute(String dbData) {
        if(dbData == null) dbData = "null";
        try {
            return objectMapper.readValue(dbData, List.class);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }
}
