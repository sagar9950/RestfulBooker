package services;

import clients.Clients;
import io.restassured.response.Response;

import java.util.Map;

public class Services {

    private final Clients client = new Clients();

    public Response createBooking(Map<String, Object> bookingData) {
        return client.createBooking(bookingData);
    }

    public Response getBookings() {
        return client.getBookings();
    }

    public Response getBookingById(int id) {
        return client.getBookingById(id);
    }
}
