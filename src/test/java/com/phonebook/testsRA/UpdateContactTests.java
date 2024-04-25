package com.phonebook.testsRA;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class UpdateContactTests extends TestBase {
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
    public void updateContactSuccessTest() {
        ContactDto contactDto = ContactDto.builder()
                .id(id)
                .name("Jame")
                .lastName("Hetfil")
                .email("metallica19@gmail.com")
                .phone("12345543210")
                .address("San-Diego")
                .description("Singer")
                .build();

        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");

        System.out.println(message);
    }
}
