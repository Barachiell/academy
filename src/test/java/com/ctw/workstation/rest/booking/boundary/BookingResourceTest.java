package com.ctw.workstation.rest.booking.boundary;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(BookingResource.class)
class BookingResourceTest {

    @Test
    void getAllBookings() {
        when().get()
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    void addBooking() {
        /*given().
                params("firstName", "John", "lastName", "Doe").
                when().
                post("/greetMe").
                then().
                body();

         */
    }

    @Test
    void getBooking() {
    }


    @Test
    void updateBooking() {
    }

    @Test
    void deleteBooking() {
    }
}