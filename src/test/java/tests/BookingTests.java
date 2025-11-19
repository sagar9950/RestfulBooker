package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import services.Services;
import utils.JsonUtils;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookingTests extends BaseTest {

    private final Services services = new Services();
    private static int createdBookingId; // Store ID for dependent tests

    // ---------- 1. POST /booking ----------
    @Test(priority = 1)
    public void testCreateBookingPositive() throws Exception {
        List<Map<String, Object>> data = JsonUtils.readJsonArray("bookings.json");
        Map<String, Object> payload = data.get(0);

        Response response = services.createBooking(payload);
        assertEquals(response.getStatusCode(), 200);
        createdBookingId = response.jsonPath().getInt("bookingid"); // Save for later tests
        assertTrue(createdBookingId > 0);
    }

    @Test(priority = 2)
    public void testCreateBookingNegative() {
        Map<String, Object> invalidPayload = Map.of(
                "lastname", "Test",
                "totalprice", 100,
                "depositpaid", true
        );

        Response response = services.createBooking(invalidPayload);
        assertEquals(response.getStatusCode(), 500);
    }

    @Test(priority = 3)
    public void testCreateBookingBoundaryLongName() throws Exception {
        List<Map<String, Object>> data = JsonUtils.readJsonArray("bookings.json");
        Map<String, Object> payload = data.get(0);
        payload.put("firstname", "A".repeat(500));

        Response response = services.createBooking(payload);
        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 500);
    }

// ---------- 2. PUT /booking/{id} ----------
    @Test(priority = 4)
    public void testUpdateBookingPositive() throws Exception {
        // Generate auth token before updating
        services.generateToken("admin", "password123");

        List<Map<String, Object>> data = JsonUtils.readJsonArray("bookings.json");
        Map<String, Object> payload = data.get(0);
        payload.put("firstname", "UpdatedName");

        Response updateResponse = services.updateBooking(createdBookingId, payload);
        assertEquals(updateResponse.getStatusCode(), 200);
        assertEquals(updateResponse.jsonPath().getString("firstname"), "UpdatedName");
    }

    @Test(priority = 5)
    public void testUpdateBookingNegativeInvalidId() throws Exception {
        services.generateToken("admin", "password123");

        List<Map<String, Object>> data = JsonUtils.readJsonArray("bookings.json");
        Map<String, Object> payload = data.get(0);
        payload.put("firstname", "InvalidUpdate");

        Response response = services.updateBooking(999999, payload);
        // Invalid ID → 405 Method Not Allowed
        assertEquals(response.getStatusCode(), 405);
    }

    @Test(priority = 6)
    public void testUpdateBookingBoundaryEmptyPayload() {
        services.generateToken("admin", "password123");

        // Empty payload → API returns 400 Bad Request
        Response response = services.updateBooking(createdBookingId, Map.of());
        assertEquals(response.getStatusCode(), 400);
    }

    @Test(priority = 7)
    public void testUpdateBookingBoundaryMinimalPayload() {
        services.generateToken("admin", "password123");

        // Minimal valid payload (boundary)
        Map<String, Object> minimalPayload = Map.of(
                "firstname", "A",
                "lastname", "B",
                "totalprice", 0,
                "depositpaid", false,
                "bookingdates", Map.of(
                        "checkin", "2025-11-01",
                        "checkout", "2025-11-02"
                )
        );

        Response response = services.updateBooking(createdBookingId, minimalPayload);
        assertEquals(response.getStatusCode(), 200);
    }


    // ---------- 3. GET /booking/{id} ----------
    @Test(priority = 8)
    public void testGetBookingByIdPositive() {
        Response response = services.getBooking(createdBookingId);
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 9)
    public void testGetBookingByIdNegative() {
        Response response = services.getBooking(999999);
        assertEquals(response.getStatusCode(), 404);
    }

    @Test(priority = 10)
    public void testGetBookingByIdBoundaryZero() {
        Response response = services.getBooking(0);
        assertEquals(response.getStatusCode(), 404);
    }
}
