package panasonic.tlms.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "redirect:user/login";
    }

    @GetMapping("/main")
    public String main() {


        return "main";
    }
}
//import requests
//import json
//
//url = "https://panasonic1.talentlms.com/api/v1/users/id:1"
//
//payload={}
//headers = {
//  'Authorization': 'Basic RFRDejRxWVI1eE1RemNwQjVVbktQWTFpZHNzR0lwOg==',
//  'Cookie': 'AWSALB=8xvEK5PROxKObva1bDM+j3CK/kF/VCI65W/lNf0ooJ3YgCEADYY6g6zrGwpNlBYyJ5sl49mJNX8IhmpL2FZB9lCBCGO6ISBeCySOsM29F69X4nPLwRfE94my0NoN17DBc0xW2HuJ3kuca6f0zuwCbJCF4KDTes0YtJ8GzhM1bYp7ocbXAUEO+zEBbZgVbw==; AWSALBCORS=8xvEK5PROxKObva1bDM+j3CK/kF/VCI65W/lNf0ooJ3YgCEADYY6g6zrGwpNlBYyJ5sl49mJNX8IhmpL2FZB9lCBCGO6ISBeCySOsM29F69X4nPLwRfE94my0NoN17DBc0xW2HuJ3kuca6f0zuwCbJCF4KDTes0YtJ8GzhM1bYp7ocbXAUEO+zEBbZgVbw==; PHPSESSID=elb~qdoi8nqik68t0jrc2hnkkma6om'
//}
//
//response = requests.request("GET", url, headers=headers, data=payload)
//user = json.loads(response.text)
//for id in user["courses"]:
//    print(id["name"])