package com.ms.apigateway;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerTest {

    private MockWebServer userServer;
    private MockWebServer subscriptionServer;
    private HttpGraphQlTester graphQlTester;

    @BeforeAll
    void setupServers() throws IOException {
        userServer = new MockWebServer();
        subscriptionServer = new MockWebServer();
        userServer.start(8082);
        subscriptionServer.start(8081);

        String baseUrl = "http://localhost:" + userServer.getPort() + "/graphql";

        WebTestClient client = WebTestClient
                .bindToServer()
                .baseUrl(baseUrl)
                .build();

        graphQlTester = HttpGraphQlTester.create(client);
    }

    @AfterAll
    void shutdownServers() throws IOException {
        userServer.shutdown();
        subscriptionServer.shutdown();
    }

    @BeforeEach
    void setupMocks() {
        userServer.enqueue(new MockResponse()
                .setBody("""
                    {
                      "data": {
                        "getUser": {
                          "id": "1",
                          "firstName": "John",
                          "secondName": "Doe",
                          "age": 30,
                          "registrationDate": "2025-11-04T00:00:00"
                        }
                      }
                    }
                    """)
                .addHeader("Content-Type", "application/json"));

        subscriptionServer.enqueue(new MockResponse()
                .setBody("""
                    {
                      "data": {
                        "getUserSubscriptions": [
                          {
                            "id": "10",
                            "userId": "1",
                            "magazineId": "5",
                            "status": "ACTIVE"
                          }
                        ]
                      }
                    }
                    """)
                .addHeader("Content-Type", "application/json"));
    }

    @Test
    void testGetUser() throws InterruptedException {
        graphQlTester.document("""
                query GetUser($id: String!) {
                    getUser(id: $id) {
                        id
                        firstName
                        secondName
                        age
                        registrationDate
                    }
                }
                """)
                .variable("id", "1")
                .execute()
                .path("data.getUser.id").entity(String.class).isEqualTo("1")
                .path("data.getUser.firstName").entity(String.class).isEqualTo("John")
                .path("data.getUser.secondName").entity(String.class).isEqualTo("Doe")
                .path("data.getUser.age").entity(Integer.class).isEqualTo(30);

        RecordedRequest userRequest = userServer.takeRequest();
        assertEquals("/graphql", userRequest.getPath());
        assertEquals("POST", userRequest.getMethod());

        String userRequestBody = userRequest.getBody().readUtf8();
        assertTrue(userRequestBody.contains("getUser"));
        assertTrue(userRequestBody.contains("\"id\":\"1\""));
    }

    @Test
    void testGetUserSubscriptions() throws InterruptedException {
        String baseUrl = "http://localhost:" + subscriptionServer.getPort() + "/graphql";

        WebTestClient subscriptionClient = WebTestClient
                .bindToServer()
                .baseUrl(baseUrl)
                .build();

        HttpGraphQlTester subscriptionTester = HttpGraphQlTester.create(subscriptionClient);

        subscriptionTester.document("""
                query GetUserSubscriptions($id: String!) {
                    getUserSubscriptions(id: $id) {
                        id
                        userId
                        magazineId
                        status
                    }
                }
                """)
                .variable("id", "1")
                .execute()
                .path("data.getUserSubscriptions[0].id").entity(String.class).isEqualTo("10")
                .path("data.getUserSubscriptions[0].userId").entity(String.class).isEqualTo("1")
                .path("data.getUserSubscriptions[0].magazineId").entity(String.class).isEqualTo("5")
                .path("data.getUserSubscriptions[0].status").entity(String.class).isEqualTo("ACTIVE");

        RecordedRequest subscriptionRequest = subscriptionServer.takeRequest();
        assertEquals("/graphql", subscriptionRequest.getPath());
        assertEquals("POST", subscriptionRequest.getMethod());
    }
}
