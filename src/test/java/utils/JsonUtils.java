package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Map<String, Object>> readJsonArray(String fileName) throws Exception {
        InputStream is = JsonUtils.class.getClassLoader().getResourceAsStream("data/" + fileName);
        if (is == null) throw new RuntimeException("File not found: " + fileName);
        return mapper.readValue(is, new TypeReference<List<Map<String, Object>>>() {});
    }
}
