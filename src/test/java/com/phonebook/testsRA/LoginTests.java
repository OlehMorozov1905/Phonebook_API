package com.phonebook.testsRA;

import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.AuthResponseDto;
import com.phonebook.dto.ErrorDto;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTests  extends TestBase{
    AuthRequestDto auth = AuthRequestDto.builder()
            .username("pulp_fiction1994@gmail.com")
            .password("Chelsea$1905")
            .build();

//    получаем token
    @Test
    public void loginSuccessTest() {
        AuthResponseDto dto = given()
                .body(auth)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);

        System.out.println("Token: " + dto.getToken());
    }

//    второй вариант как получить token
    @Test
    public void loginSuccessTest_2() {
        String responseToken = given()
                .body(auth)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .extract().path("token");

        System.out.println("Token: " + responseToken);
    }

    @Test
    public void loginNegativeWithWrongEmailTest() {
        ErrorDto errorDto = given().body(AuthRequestDto.builder()
                        .username("pulp_fiction199@gmail.com")
                        .password("Chelsea$1905")
                        .build())
                        .when()
                        .post("user/login/usernamepassword")
                        .then()
                        .assertThat().statusCode(401)
                        .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getError());
    }

//    еще вариант
    @Test
    public void loginNegativeWithWrongEmailTest_2() {
        given().body(AuthRequestDto.builder()
                        .username("pulp_fiction199@gmail.com")
                        .password("Chelsea$1905")
                        .build())
                        .when()
                        .post("user/login/usernamepassword")
                        .then()
                        .assertThat().statusCode(401)
                        .assertThat().body("message", equalTo("Login or Password incorrect"));
//                .extract().response().as(ErrorDto.class);
    }
}
