package com.automatization.tests.generated;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("Tests for listFilms")
public class ListFilmsTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://swapi.dev/api";
    }

    private void assertExpectedJsonBody(String expectedBodyJson, String responseBody) {
        JsonNode expectedNode;
        try {
            expectedNode = objectMapper.readTree(expectedBodyJson);
        } catch (JsonProcessingException e) {
            Assertions.fail("Expected body is not valid JSON: " + e.getMessage());
            return;
        }

        JsonNode actualNode;
        try {
            actualNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException ignored) {
            actualNode = null;
        }

        if (actualNode != null) {
            Assertions.assertEquals(expectedNode, actualNode);
        } else if (expectedNode.isValueNode()) {
            Assertions.assertEquals(expectedNode.asText(), responseBody);
        } else {
            Assertions.fail("Response body is not valid JSON");
        }
    }

    @Test
    @DisplayName("Invalid Query page parameter: Integer Breaking")
    public void invalidQueryPageParameterIntegerBreaking() {
        RequestSpecification requestSpec = RestAssured.given();
        requestSpec.queryParam("page", "1.5");

        Response response = requestSpec.get("/films/");
        response.then().statusCode(404);
    }

    @Test
    @DisplayName("Invalid Query page parameter: Out Of Minimum Boundary Number")
    public void invalidQueryPageParameterOutOfMinimumBoundaryNumber() {
        RequestSpecification requestSpec = RestAssured.given();
        requestSpec.queryParam("page", "0");

        Response response = requestSpec.get("/films/");
        response.then().statusCode(404);
    }

}
