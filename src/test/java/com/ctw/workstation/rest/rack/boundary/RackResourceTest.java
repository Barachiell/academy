package com.ctw.workstation.rest.rack.boundary;

import com.ctw.workstation.entity.DefaultLocation;
import com.ctw.workstation.rest.config.DatabaseTestResource;
import com.ctw.workstation.rest.rack.control.RackService;
import com.ctw.workstation.rest.rack.entity.Rack;
import com.ctw.workstation.rest.rack.entity.RackDTO;
import com.ctw.workstation.rest.team.entity.TeamDTO;
import com.ctw.workstation.rest.teammember.entity.TeamMemberDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.params.provider.Arguments.arguments;


@QuarkusTest
@QuarkusTestResource(DatabaseTestResource.class)
class RackResourceTest {

    private RackDTO rackDTOsuccess;
    private RackDTO rackDTOBadRequest;
    private RackDTO rackDTONotFound;
    private TeamDTO teamDTO;
    private static UUID rackId;
    private static UUID teamId;

    @Inject
    private RackService rackService;

    @BeforeEach
    void setup(){

        rackService.clearDatabase();

        teamDTO = new TeamDTO(null, "productTest", "test", DefaultLocation.LISBON );
        Response response = given()
                .contentType("application/json")
                .body(teamDTO)
                .when().post("/teams")
                .then()
                .statusCode(201)
                .extract().response();
        teamId = UUID.fromString(response.jsonPath().getString("id"));
        rackDTOsuccess = new RackDTO(null, teamId, "12345", Rack.Status.AVAILABLE, DefaultLocation.LISBON);

        Response response2 = given()
                .contentType("application/json")
                .body(rackDTOsuccess)
                .when().post("/racks")
                .then()
                .statusCode(201)
                .extract().response();
        rackId = UUID.fromString(response2.jsonPath().getString("id"));
        rackDTOBadRequest = new RackDTO(rackId, teamId, null, Rack.Status.AVAILABLE,DefaultLocation.LISBON);
        rackDTONotFound = new RackDTO(null, UUID.randomUUID(), "54321", Rack.Status.AVAILABLE, DefaultLocation.LISBON);
    }

    @AfterEach
    void clear(){
        rackService.clearDatabase();
    }

    /*
    private static Stream<Arguments> provideRackTestCases(){
        return Stream.of(
                Arguments.of(rackId,new RackDTO(null, teamId, "12345", Rack.Status.AVAILABLE, DefaultLocation.BRAGA), 200, DefaultLocation.BRAGA),
                Arguments.of(UUID.randomUUID() ,new RackDTO(null, teamId, "2434", Rack.Status.AVAILABLE,DefaultLocation.LISBON);, 404, null),
                Arguments.of(rackId,new RackDTO(null, teamId, null , Rack.Status.AVAILABLE,DefaultLocation.LISBON) , 400, null)
        );
    }

     */

    public void rackUpdate(UUID rackId, RackDTO updatedRackDTO, int expectedStatusCode, String expectedSerialNumber) {
        given()
                .contentType(ContentType.JSON)
                .body(updatedRackDTO)
                .pathParam("id", rackId)
                .when().put("/racks/{id}")
                .then()
                .statusCode(expectedStatusCode)
                .body("serialNumber", expectedSerialNumber == null ? is(nullValue()) : is(expectedSerialNumber));
    }

    @Test
    public void TestRackUpdate(){
        RackDTO rackDTOupdated = new RackDTO(null, teamId, "54321", Rack.Status.AVAILABLE, DefaultLocation.LISBON);
        UUID randomId = UUID.randomUUID();
        rackUpdate(rackId, rackDTOupdated, 200, "54321");
        rackUpdate(rackId, rackDTOBadRequest, 400,null);
        rackUpdate(randomId, rackDTONotFound, 404, null);

    }

    public void rackSave(RackDTO rackDTO, int expectedStatusCode, String expectedSerialNumber) {
        given()
                .contentType(ContentType.JSON)
                .body(rackDTO)
                .when().post("/racks")
                .then()
                .statusCode(expectedStatusCode)
                .body("serialNumber", expectedSerialNumber == null ? is(nullValue()) : is(expectedSerialNumber));
    }

    @Test
    public void TestRackSave(){
        RackDTO rackDTOnew = new RackDTO(null, teamId, "54321", Rack.Status.AVAILABLE, DefaultLocation.LISBON);
        rackSave(rackDTOnew, 201, "54321");
        rackSave(rackDTOBadRequest, 400,null);
        rackSave(rackDTONotFound, 404, null);
    }

    public void rackGet(UUID rackId, int expectedStatusCode, String expectedSerialNumber) {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", rackId)
                .when().get("/racks/{id}")
                .then()
                .statusCode(expectedStatusCode)
                .body("serialNumber", expectedSerialNumber == null ? is(nullValue()) : is(expectedSerialNumber));
    }

    @Test
    public void TestRackGet(){
        UUID randomId = UUID.randomUUID();
        rackGet(rackId, 200, "12345");
        rackGet(randomId, 404, null);

    }

    public void rackDelete(UUID rackId, int expectedStatusCode) {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", rackId)
                .when().delete("/racks/{id}")
                .then()
                .statusCode(expectedStatusCode);
    }

    @Test
    public void TestRackDelete(){
        UUID randomId = UUID.randomUUID();
        rackDelete(rackId, 204);
        rackDelete(randomId, 404);

    }

    @Test
    void getAllRackMembers() {
        RackDTO rackDTO2 = new RackDTO(null, teamId, "98765", Rack.Status.AVAILABLE,DefaultLocation.LISBON);
        given()
                .contentType("application/json")
                .body(rackDTO2)
                .when().post("/racks")
                .then()
                .statusCode(201);

        given()
                .when().get("/racks")
                .then()
                .statusCode(200)
                .body("", hasSize(2))
                .body("[0].serialNumber", is("12345"))
                .body("[1].defaultLocation", is("LISBON"));
    }



}