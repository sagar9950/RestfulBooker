package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.Services;
import utils.JsonUtils;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class BookingTests extends BaseTest {

    private final Services bookingService = new Services();

    @DataProvider(name = "positiveBookingData")
    public Object[][] positiveBookingData() {
        List<Map<String, Object>> bookings = JsonUtils.readJsonAsList("data/bookings.json");
        Object[][] data = new Object[bookings.size()][1];
        for (int i = 0; i < bookings.size(); i++) data[i][0] = bookings.get(i);
        return data;
    }

    // Positive POST /booking
    @Test(dataProvider = "positiveBookingData")
    public void testCreateBooking_Positive(Map<String, Object> bookingData) {
        Response response = bookingService.createBooking(bookingData);
        assertEquals(response.statusCode(), 200);
        assertEquals(response.jsonPath().getMap("booking").get("firstname"), bookingData.get("firstname"));
    }

    // Negative POST /booking - missing fields
    @Test
    public void testCreateBooking_Negative_MissingFields() {
        Map<String, Object> invalidBooking = Map.of("firstname", "NoLastName");
        Response response = bookingService.createBooking(invalidBooking);
        assertEquals(response.statusCode(), 500);
    }

    // Boundary POST /booking - long string
    @Test
    public void testCreateBooking_Boundary_LongStrings() {
        String longName = "A".repeat(500);
        Map<String, Object> bookingData = Map.of(
                "firstname", longName,
                "lastname", "Test",
                "totalprice", 100,
                "depositpaid", true,
                "bookingdates", Map.of("checkin", "2025-11-18", "checkout", "2025-11-25"),
                "additionalneeds", "Breakfast"
        );
        Response response = bookingService.createBooking(bookingData);
        assertEquals(response.statusCode(), 200);
        assertEquals(response.jsonPath().getMap("booking").get("firstname"), longName);
    }

    // Positive GET /booking/{id}
    @Test
    public void testGetBookingById_Positive() {
        Response bookingsResp = bookingService.getBookings();
        assertEquals(bookingsResp.statusCode(), 200);
        int id = bookingsResp.jsonPath().getInt("bookingid[0]");

        Response response = bookingService.getBookingById(id);
        assertEquals(response.statusCode(), 200);
        assertNotNull(response.jsonPath().getMap(""));
    }

    // Negative GET /booking/{id} - invalid
    @Test
    public void testGetBookingById_Negative_InvalidId() {
        Response response = bookingService.getBookingById(999999);
        assertEquals(response.statusCode(), 404);
    }

    // Boundary GET /booking/{id} - zero
    @Test
    public void testGetBookingById_Boundary_ZeroId() {
        Response response = bookingService.getBookingById(0);
        assertEquals(response.statusCode(), 404);
    }

    // Positive GET /booking
    @Test
    public void testGetAllBookings_Positive() {
        Response response = bookingService.getBookings();
        assertEquals(response.statusCode(), 200);
        assertFalse(response.jsonPath().getList("bookingid").isEmpty());
    }
}
