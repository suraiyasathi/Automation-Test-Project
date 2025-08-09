package utils;

public class PathConstants {
    // Base directories
    public static final String PROJECT_ROOT = System.getProperty("user.dir");
    public static final String TEST_RESOURCES = PROJECT_ROOT + "/src/test/java/resources";
    public static final String TEST_DATA_DIR = TEST_RESOURCES + "/testData";
    public static final String UPLOADS_DIR = TEST_DATA_DIR + "/uploads";

    // Specific files
    public static final String CONTACT_FORM_DATA = TEST_DATA_DIR + "/contactFormData.json";
    public static final String TEST_IMAGE_FILE = UPLOADS_DIR + "/test.jpeg";
}