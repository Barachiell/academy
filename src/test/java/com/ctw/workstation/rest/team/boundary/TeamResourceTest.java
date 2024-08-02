package com.ctw.workstation.rest.team.boundary;

import com.ctw.workstation.entity.DefaultLocation;
import com.ctw.workstation.rest.config.DatabaseTestResource;
import com.ctw.workstation.rest.team.control.TeamService;
import com.ctw.workstation.rest.team.entity.TeamDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasSize;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(TeamResource.class)
@QuarkusTestResource(DatabaseTestResource.class)
//@QuarkusTestResource(WireMockResource.class)
// The annotation below does what is above - check CommonProfile class
//@TestProfile(CommonProfile.class)
class TeamResourceTest {

    private static final Jsonb JSONB = JsonbBuilder.create();
    private TeamDTO teamDTO;
    private TeamDTO teamDTONull;
    private UUID teamId;

    @Inject
    TeamService teamService;

    @BeforeEach
    void setup(){

        teamService.clearDatabase();

        teamDTO = new TeamDTO(null, "productTest", "test", DefaultLocation.LISBON );
        teamDTONull = new TeamDTO(null, null, "Nullelement", DefaultLocation.BRAGA);

        Response response = given()
                .contentType("application/json")
                .body(teamDTO)
                .when().post()
                .then()
                .statusCode(201)
                .extract().response();

        teamId = UUID.fromString(response.jsonPath().getString("id"));
    }

    @Test
    void getAllTeams() {
            // Create another team
            TeamDTO teamDTO2 = new TeamDTO(null, "productTest", "test2", DefaultLocation.LISBON );
            given()
                    .contentType("application/json")
                    .body(teamDTO2)
                    .when().post()
                    .then()
                    .statusCode(201);

            // Retrieve the list of teams
            given()
                    .when().get()
                    .then()
                    .statusCode(200)
                    .body("", hasSize(2))
                    .body("[0].name", is("test"))
                    .body("[1].name", is("test2"));
    }

    @Test
    public void GetTeamById404() {
        UUID teamId = UUID.randomUUID();
        given()
                .pathParam("id", teamId)
                .when().get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void getTeamById() {
        given()
                .pathParam("id", teamId)
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("test"));
    }

    @Test
    public void AddTeamSuccess() {
        given()
                .contentType("application/json")
                .body(teamDTO)
                .when().post()
                .then()
                .statusCode(201)
                .body("name", is("test"));

        /*
        Alternative:
        given().contentType(ContentType.JSON).
                body(JSONB.toJson(teamDTO)).
                when().
                post().
                then().
                statusCode(201).
                body("name", is("Bots"));
         */
    }

    @Test
    void tryAddTeamWithNullElement() {
        given().contentType(ContentType.JSON).
                body(JSONB.toJson(teamDTONull)).
                when().
                post().
                then().
                statusCode(400);
    }


    @Test
    public void DeleteTeam() {
        // Then, delete the team
        given()
                .pathParam("id", teamId)
                .when().delete("/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void DeleteTeamFail() {
        UUID failId = UUID.randomUUID();
        // Then, delete the team
        given()
                .pathParam("id", failId)
                .when().delete("/{id}")
                .then()
                .statusCode(404);
    }


    @Test
    public void testUpdateTeam() {
        TeamDTO updatedTeamDTO = new TeamDTO(null,"FYI","Updated", DefaultLocation.BRAGA);
        given()
                .contentType("application/json")
                .body(updatedTeamDTO)
                .pathParam("id", teamId)
                .when().put("/{id}")
                .then()
                .statusCode(200)
                .body("name", is("Updated"));
    }

    @Test
    public void testUpdateTeamBadRequest() {
        TeamDTO updatedTeamDTO = new TeamDTO(teamId,null,"BadRequest", DefaultLocation.BRAGA);
        given()
                .contentType("application/json")
                .body(updatedTeamDTO)
                .pathParam("id", teamId)
                .when().put("/{id}")
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdateTeamWrongTeamId() {
        UUID wrongId = UUID.randomUUID();
        TeamDTO updatedTeamDTO = new TeamDTO(teamId,"FYI","WrongTeamId", DefaultLocation.BRAGA);
        given()
                .contentType("application/json")
                .body(updatedTeamDTO)
                .pathParam("id", wrongId)
                .when().put("/{id}")
                .then()
                .statusCode(404);
    }


}