package fitVenture.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fitVenture.backend.FitVenture;

import java.io.File;
import java.io.IOException;

public class FileHandler {
    // reads the JSON file and deserialize it to a FitVenture object
    public static FitVenture jsonDeserializer(String filePath) throws IOException {
        // Deserializes the JSON file to a FitVenture object
        ObjectMapper objectMapper = new ObjectMapper();

        // Reads the JSON file
        File jsonfile = new File(filePath);
        return objectMapper.readValue(jsonfile, FitVenture.class);
    }

    // writes the FitVenture object to a JSON file
    public static void jsonSerializer(String filePath, FitVenture fitVenture) throws IOException {
        // Serializes the FitVenture object to a JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        // Breaks the JSON file into lines (to make it readable)
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        // Writes the JSON file
        File jsonFile = new File(filePath);
        objectWriter.writeValue(jsonFile, fitVenture);
    }
}
