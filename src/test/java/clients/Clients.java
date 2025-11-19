package clients;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.Map;

public class Clients {
    private String authToken;
    public void generateToken(String username, String password) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(Map.of("username", username, "password", password))
                .when()
                .post("/auth")
                .then()
                .extract().response();

        authToken = response.jsonPath().getString("token");
    }

    public Response getBookings() {
        return given()
                .when()
                .get("/booking")
                .then()
                .extract().response();
    }

    public Response getBookingById(int id) {
        return given()
                .when()
                .get("/booking/" + id)
                .then()
                .extract().response();
    }

    public Response createBooking(Map<String, Object> payload) {
        return given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/booking")
                .then()
                .extract().response();
    }

    public Response updateBooking(int id, Map<String, Object> payload) {
        return given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + authToken) // pass token
                .body(payload)
                .when()
                .put("/booking/" + id)
                .then()
                .extract().response();
    }
}
