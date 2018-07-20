package io.stuffhub.api.test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeTest;

public class JUnitManagedIntegrationTest {

    private static final String WIREMOCK_PATH = "/staffhub/auth";
    private static final String APPLICATION_JSON = "application/json";
    private static String jsonObjectString = "";

    static int port;

    static {
        try {
            ServerSocket s = new ServerSocket(0);
            port = s.getLocalPort();
            s.close();
            JSONParser parser = new JSONParser();
            jsonObjectString = convertInputStreamToString(JUnitManagedIntegrationTest.class.getClassLoader()
                    .getResourceAsStream("wiremock_intro.json"));
        } catch (Exception e) {
        }
    }

    @BeforeTest
    public void setUp() {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", port);
    }

    @Test
    public void givenJUnitManagerServer_whenMatchingURL_thenCorrect() throws IOException {



        stubFor(post(urlEqualTo("/staffhub/auth"))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON))
                .withRequestBody(containing("\"password\": \"pass\""))
                .withRequestBody(containing("\"username\": \"375293339506\""))
                .willReturn(aResponse().withStatus(200).withBody(jsonObjectString)));

        InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("wiremock_intro.json");
        String jsonString = convertInputStreamToString(jsonInputStream);
        StringEntity entity = new StringEntity(jsonString);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(String.format("http://localhost:%s/staffhub/auth", port));
        request.addHeader("Content-Type", APPLICATION_JSON);
        request.setEntity(entity);

        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);
        System.out.println(stringResponse);


        verify(postRequestedFor(urlEqualTo(WIREMOCK_PATH))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON)));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());

    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        return IOUtils.toString(inputStream, "UTF-8");
    }

    private static String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }

}
