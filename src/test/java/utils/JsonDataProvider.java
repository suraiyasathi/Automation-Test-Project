package utils;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class JsonDataProvider {
    @DataProvider(name = "jsonDataProvider")
    public static Iterator<Object[]> provideJsonData(ITestContext context) {
        String jsonFilePath = context.getCurrentXmlTest().getParameter("jsonFilePath");
        if (jsonFilePath == null || jsonFilePath.isEmpty()) {
            throw new RuntimeException("Missing 'jsonFilePath' parameter for jsonDataProvider");
        }

        LinkedHashMap<String, LinkedHashMap<String, String>> rawData = JsonReader.readFormData(jsonFilePath);

        return rawData.entrySet().stream()
                .map(entry -> {
                    LinkedHashMap<String, String> dataMap = entry.getValue();
                    dataMap.put("Data Set", entry.getKey());
                    return new Object[]{dataMap};
                })
                .iterator();
    }
}
