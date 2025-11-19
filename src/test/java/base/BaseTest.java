package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = System.getProperty("baseUrl", "https://restful-booker.herokuapp.com");
    }
}
