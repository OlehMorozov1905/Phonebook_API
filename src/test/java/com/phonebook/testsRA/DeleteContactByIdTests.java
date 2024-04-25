package com.phonebook.testsRA;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdTests extends TestBase{
    String id;

    @BeforeMethod
    public void precondition() {
        ContactDto contactDto = ContactDto.builder()
                .name("James")
                .lastName("Hetfild")
                .email("metallica1981@gmail.com")
                .phone("1234567890")
                .address("San-Diego")
                .description("Singer")
                .build();

        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");
//        System.out.println(message);

        String[] split = message.split(": ");
        id = split[1];
    }

    @Test
    public void deleteContactByIdSuccessTest() {
        given()
                .header(AUTH, TOKEN)
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
//                .extract().path("message");
//        System.out.println(message);
    }

    @Test
    public void deleteContactByIdFormatErrorTest() {
        given()
                .header(AUTH, TOKEN)
                .contentType(ContentType.JSON)
                .delete("contacts/" + id + "invalidSuffix")
                .then()
                .assertThat().statusCode(400)
                .assertThat().contentType(ContentType.JSON)
                .assertThat().body("message", equalTo("Contact with id: " + id + "invalidSuffix not found in your contacts!"));
    }

    @Test
    public void deleteContactByIdUnauthorizedTest() {
        given()
                .header(AUTH, "invalidToken")
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401)
                .assertThat().contentType(ContentType.JSON)
                .assertThat().body("message", equalTo("JWT strings must contain exactly 2 period characters. Found: 0"));
    }
}
