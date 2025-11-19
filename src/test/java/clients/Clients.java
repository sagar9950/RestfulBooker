package clients;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Clients {

    private static final String BASE = "/booking";

    public Response getBookings() {
        return given().when().get(BASE);
    }

    public Response createBooking(Map<String, Object> body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .post(BASE);
    }

    public Response getBookingById(int id) {
        return given().when().get(BASE + "/" + id);
    }
}
