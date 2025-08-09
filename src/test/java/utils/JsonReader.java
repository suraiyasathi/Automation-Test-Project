package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class JsonReader {

    public static LinkedHashMap<String, LinkedHashMap<String, String>> readFormData(String jsonFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LinkedHashMap<String, LinkedHashMap<String, String>> rawData = mapper.readValue(
                    new File(jsonFilePath),
                    new TypeReference<>() {}
            );

            // Replace placeholders like {projectRoot} with actual values
            rawData.forEach((outerKey, innerMap) -> {
                innerMap.replaceAll((key, value) ->
                        value != null && value.contains(Data.PROJECT_ROOT)
                                ? value.replace(Data.PROJECT_ROOT, PathConstants.PROJECT_ROOT)
                                : value);
            });

            return rawData;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read JSON file: " + jsonFilePath, e);
        }
    }
}
