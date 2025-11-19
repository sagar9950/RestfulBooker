package services;

import clients.Clients;
import io.restassured.response.Response;

import java.util.Map;

public class Services {
    private final Clients client = new Clients();

    public Response getAllBookings() {
        return client.getBookings();
    }

    public Response getBooking(int id) {
        return client.getBookingById(id);
    }

    public Response createBooking(Map<String, Object> payload) {
        return client.createBooking(payload);
    }

    public Response updateBooking(int id, Map<String, Object> payload) {
        return client.updateBooking(id, payload);
    }

    public void generateToken(String username, String password) {
        client.generateToken(username, password);
    }
}
