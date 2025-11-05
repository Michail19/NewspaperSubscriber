package com.ms.apigateway;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class UserControllerTest {

    private static MockWebServer userServer;
    private static MockWebServer subscriptionServer;

    @Autowired
    private GraphQlTester graphQlTester;

    @BeforeAll
    static void setupServers() throws IOException {
        userServer = new MockWebServer();
        subscriptionServer = new MockWebServer();
        userServer.start(8082);
        subscriptionServer.start(8081);
    }

    @AfterAll
    static void shutdownServers() throws IOException {
        userServer.shutdown();
        subscriptionServer.shutdown();
    }

    @BeforeEach
    void setupMocks() {
        // Мокаем ответ от UserService
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

        // Мокаем ответ от SubscriptionService
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
    void testGetUserWithSubscriptions() {
        graphQlTester.document("""
                query {
                    getUser(id: "1") {
                        user { id firstName secondName age registrationDate }
                        subscriptions { id userId magazineId status }
                    }
                }
                """)
                .execute()
                .path("getUser.user.id").entity(String.class).isEqualTo("1")
                .path("getUser.user.firstName").entity(String.class).isEqualTo("John")
                .path("getUser.subscriptions[0].status").entity(String.class).isEqualTo("ACTIVE");
    }
}
