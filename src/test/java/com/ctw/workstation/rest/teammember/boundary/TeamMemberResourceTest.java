package com.ctw.workstation.rest.teammember.boundary;

import com.ctw.workstation.entity.DefaultLocation;
import com.ctw.workstation.rest.config.DatabaseTestResource;
import com.ctw.workstation.rest.team.entity.TeamDTO;
import com.ctw.workstation.rest.teammember.entity.TeamMemberDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(DatabaseTestResource.class)
class TeamMemberResourceTest {

    private TeamMemberDTO teamMemberDTO;
    private TeamDTO teamDTO;
    private UUID teamMemberId;
    private UUID teamId;

    @BeforeEach
    void setup(){
        teamDTO = new TeamDTO(null, "productTest", "test", DefaultLocation.LISBON );
        Response response = given()
                .contentType("application/json")
                .body(teamDTO)
                .when().post("/teams")
                .then()
                .statusCode(201)
                .extract().response();
        teamId = UUID.fromString(response.jsonPath().getString("id"));
        teamMemberDTO = new TeamMemberDTO(null, teamId, "MemberTest","CTW-03451");

        Response response2 = given()
                .contentType("application/json")
                .body(teamMemberDTO)
                .when().post("/teammembers")
                .then()
                .statusCode(201)
                .extract().response();
        teamMemberId = UUID.fromString(response2.jsonPath().getString("id"));
    }

    @Test
    public void testGetTeamMemberById404() {
        UUID teamMemberId404 = UUID.randomUUID();
        given()
                .pathParam("id", teamMemberId404)
                .when().get("/teammembers/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetTeamMemberById() {
        given()
                .pathParam("id", teamMemberId)
                .when().get("teammembers/{id}")
                .then()
                .statusCode(200)
                .body("ctwId", equalTo("CTW-03451"));
    }

    @Test
    public void addTeamMemberSuccess() {
        TeamMemberDTO teamMemberDTO1 = new TeamMemberDTO(null ,teamId,"AddedTest", "CTW-00234");
        given()
                .contentType("application/json")
                .body(teamMemberDTO1)
                .when().post("/teammembers")
                .then()
                .statusCode(201)
                .body("name", is("AddedTest"));
    }

    @Test
    public void addTeamMemberBadRequest() {
        TeamMemberDTO teamMemberDTO1 = new TeamMemberDTO(null ,teamId,null, "CTW-00234");
        given()
                .contentType("application/json")
                .body(teamMemberDTO1)
                .when().post("/teammembers")
                .then()
                .statusCode(400);
    }

    @Test
    public void addTeamMemberTeamDoesntExist() {
        TeamMemberDTO teamMemberDTO1 = new TeamMemberDTO(null , UUID.randomUUID(),"NotFound", "CTW-00234");
        given()
                .contentType("application/json")
                .body(teamMemberDTO1)
                .when().post("/teammembers")
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteTeamMember() {
        given()
                .pathParam("id", teamMemberId)
                .when().delete("/teammembers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteTeamMemberFail() {
        UUID wrongId = UUID.randomUUID();
        given()
                .pathParam("id", wrongId)
                .when().delete("/teammembers/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void UpdateTeamMember() {
        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(teamMemberId, teamId, "Updated", "CTW12345");
        given()
                .contentType("application/json")
                .body(teamMemberDTO)
                .pathParam("id", teamMemberId)
                .when().put("/teammembers/{id}")
                .then()
                .statusCode(200)
                .body("name", is("Updated"));
    }

    @Test
    public void UpdateTeamMemberBadRequest() {
        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(teamMemberId, teamId, "Updated", null);
        given()
                .contentType("application/json")
                .body(teamMemberDTO)
                .pathParam("id", teamMemberId)
                .when().put("/teammembers/{id}")
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdateTeamMemberWrongTeamId() {
        UUID wrongTeamId = UUID.randomUUID();

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(teamMemberId, wrongTeamId, "Updated", "CTW12345");
        given()
                .contentType("application/json")
                .body(teamMemberDTO)
                .pathParam("id", teamMemberId)
                .when().put("/teammembers/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateTeamMemberWrongTeamMemberId() {
        UUID wrongTeamMemberId = UUID.randomUUID();

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO(wrongTeamMemberId, teamId, "Updated", "CTW12345");
        given()
                .contentType("application/json")
                .body(teamMemberDTO)
                .pathParam("id", wrongTeamMemberId)
                .when().put("/teammembers/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void getAllTeamMembers() {
        TeamMemberDTO teamMemberDTO2 = new TeamMemberDTO(null ,teamId,"Member2", "CTW-00234");
        given()
                .contentType("application/json")
                .body(teamMemberDTO2)
                .when().post("/teammembers")
                .then()
                .statusCode(201);

        given()
                .when().get("/teammembers")
                .then()
                .statusCode(200)
                .body("", hasSize(2))
                .body("[0].ctwId", is("CTW-03451"))
                .body("[1].name", is("Member2"));
    }

}