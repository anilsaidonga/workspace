package requests;

import org.junit.jupiter.api.Assertions;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class GetRequests {
    public static void main(String[] args) {

        RestAssured.baseURI = "http://bookstore.toolsqa.com";

        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.request(Method.GET, "/Bookstore/v1/Books");

        int statusCode = response.getStatusCode();

        statusCode = 300;

        Assertions.assertEquals(300, statusCode);

        Headers headers = response.getHeaders();

        ResponseBody responseBody = response.getBody();

        System.out.println(response);

    }
}
