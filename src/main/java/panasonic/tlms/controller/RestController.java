package panasonic.tlms.controller;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/courses")
    public String courses() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://panasonic1.talentlms.com/api/v1/courses/")
                .method("GET", null)
                .addHeader("Authorization", "Basic RFRDejRxWVI1eE1RemNwQjVVbktQWTFpZHNzR0lwOg==")
                .addHeader("Cookie", "AWSALB=39EFhH63DUtF3YJ1CyxQ+AbGeXo45sIawJgMJwUAmX+jUbtMVE7Wv2DC3fcdnqycyoZtUDwM4pMXh4KZbXmTfhQU6X1/CN3qUmPA61cCs4nrvnt3UrgQZPjgp/cQ0Xd5SnqdwAzBoz6qK+s3Y9TGi7jiWbB0/g+FK+DAd9jsjM5c1KFnpXGCUl/NPfFIBw==; AWSALBCORS=39EFhH63DUtF3YJ1CyxQ+AbGeXo45sIawJgMJwUAmX+jUbtMVE7Wv2DC3fcdnqycyoZtUDwM4pMXh4KZbXmTfhQU6X1/CN3qUmPA61cCs4nrvnt3UrgQZPjgp/cQ0Xd5SnqdwAzBoz6qK+s3Y9TGi7jiWbB0/g+FK+DAd9jsjM5c1KFnpXGCUl/NPfFIBw==; PHPSESSID=elb~u7cc97g5mhor60li21llaonbnr")
                .build();
        Response response = client.newCall(request).execute();

        return response.toString();
    }



}
