package io.stuffhub.functional.test;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Admin2 on 13.07.2018.
 */
public class TestTask {

    public static String totalPages;
    public static String totalRecords;
    public static String url = "http://192.168.50.19:8060/staffhub/auth";

    public static void getHTTPContext() throws Exception{

        String postUrl = "http://192.168.50.19:8060/staffhub/auth";// put in your url
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(postUrl);

        StringEntity postingString = new StringEntity("{\"password\": \"pass\", \"username\": \"375293339506\"}");
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);
        InputStream inputStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(inputStream);
        String responseText = "";
        while (scanner.hasNext()) {
            responseText += scanner.nextLine();
        }

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(responseText);
        String token = json.get("token").toString();

        HttpGet get = new HttpGet("http://192.168.50.19:8060/staffhub/candidate/invitations?page=1");
        get.setHeader("Authorization" ,token);
        response = httpClient.execute(get);
        inputStream = response.getEntity().getContent();
        scanner = new Scanner(inputStream);
        responseText = "";
        while (scanner.hasNext()) {
            responseText += scanner.nextLine();
        }

        parser = new JSONParser();
        json = (JSONObject) parser.parse(responseText);
        totalPages = json.get("totalPages").toString();
        totalRecords = json.get("records").toString();

    }

    public static String getCountOfPages() {
        return totalPages;
    }

    public static String getCountOfInvitations() {
        return totalRecords;
    }
}
