package com.automatization.tests.rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import  com.automatization.tests.Config;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  com.automatization.tests.data.TestData;
import com.automatization.tests.api.Endpoints;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

public class SwapiTest {
    

    @BeforeAll 
    static void setup(){
        RestAssured.baseURI = Config.baseUrl();
    }

    @Test
    void getPersonById_returns200_nameAndFilms() {
        given()
            .pathParam("id", TestData.EXISTING_PERSON_ID)
        .when()
            .get(Endpoints.PEOPLE_BY_ID)
        .then()
            .statusCode(200)
            .body("name", not(emptyOrNullString()))
            .body("name", instanceOf(String.class))
            .body("films", instanceOf(java.util.List.class))
            .body("films", not(empty()));
    }

    @Test 
    void getFilmsById_returns200_titleAndEpisode(){
        given().pathParam("id", TestData.EXISTING_FILM_ID)
        .when()
            .get(Endpoints.FILMS_BY_ID)
        .then()
            .statusCode(200)
            .body("episode_id", not(emptyOrNullString()))
            .body("director", containsString("George Lucas"))
            .body("release_date",  matchesPattern("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Test
    void getPersonById_returns404() {
        given()
            .pathParam("id", TestData.NON_EXISTENT_PERSON_ID)
        .when()
            .get(Endpoints.PEOPLE_BY_ID)
        .then()
            .statusCode(404);
    }


    @Test
    void getPersonById_returns404_notNumberParam() {
        given()
            .pathParam("id", TestData.NON_NUMERIC_ID)
        .when()
            .get(Endpoints.PEOPLE_BY_ID)
        .then()
            .statusCode(404);
    }

    @Test
    void getPeopleFirstPage_returnsValidPaginationMetadata() {
        given()
            .queryParam("page", TestData.FIRST_PAGE)
        .when()
            .get(Endpoints.PEOPLE)
        .then()
            .statusCode(200)
            .body("count", greaterThan(0))
            .body("results", not(empty()))
            .body("previous", nullValue())
            .body("next", notNullValue());
    }

    @Test
    void getPeopleFirstPage_returnsValidPagination() {
        List<String> namesPage1 = given()
        .queryParam("page", TestData.FIRST_PAGE)
        .when()
            .get(Endpoints.PEOPLE)
        .then()
            .statusCode(200)
            .extract()
            .jsonPath().getList("results.name", String.class);

        List<String> namesPage2 = given()
        .queryParam("page", TestData.FIRST_PAGE+1)
        .when()
            .get(Endpoints.PEOPLE)
        .then()
            .statusCode(200)
            .extract()
            .jsonPath().getList("results.name", String.class);

        assertThat(namesPage1).isNotEmpty();
        assertThat(namesPage2).isNotEmpty();
        Set<String> names2 = new HashSet<>(namesPage2);

        List<String> overlap = namesPage1.stream()
            .filter(names2::contains)
            .toList();

        assertThat(overlap)
            .as("people on page 1 and page 2 must not overlap")
            .isEmpty();
    }

    @Test
    void getPeoplePage2_hasCorrectNavigationLinks() {
        given()
            .queryParam("page", TestData.FIRST_PAGE+1)
        .when()
            .get(Endpoints.PEOPLE)
        .then()
            .statusCode(200)
            .body("previous", containsString("page=1"))
            .body("next", containsString("page=3"));
    }
    @Test
    void searchPeopleByName_returnsMatchingResults() {
        given()
            .queryParam("search", TestData.SEARCH_QUERY)
        .when()
            .get(Endpoints.PEOPLE)
        .then()
            .statusCode(200)
            .body("results", not(empty()))
            .body("results.name", hasItem(containsStringIgnoringCase("Luke")));
    }
}
