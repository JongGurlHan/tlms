package panasonic.tlms.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import panasonic.tlms.beans.UserBean;
import panasonic.tlms.course.Course;
import panasonic.tlms.course.CourseRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourseRepository courseRepository;


    @GetMapping("/")
    public String home(){
        return "redirect:user/login";
    }

    @PostMapping("/main")
    public String main(Model model, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {

        String id = request.getParameter("user_id");
        String user_pw = request.getParameter("user_pw");

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("login",id)
                .addFormDataPart("password",user_pw)
                .build();
        Request request2 = new Request.Builder()
                .url("https://panasonic1.talentlms.com/api/v1/userlogin")
                .method("POST", body)
                .addHeader("Authorization", "Basic RFRDejRxWVI1eE1RemNwQjVVbktQWTFpZHNzR0lwOg==")
                .addHeader("Cookie", "AWSALB=HNXZ1v2IPVbC6Bhh9BxoJDgq1MteFGnG1xsSeho0O9ZAaWpo7VSLNlgmgGQM5XLpkMAQvCpMEBQ2pe6u2Jx6QhA4FACFdLyM2LAKoPiYxh5eaB1b/dGKBmcL8WFrRK4OWbk60U7KXEr70LzKwXRVXRAi0DSOZ7JMBkHDilUZ92uevakYXqOc6+p6DX7ppA==; AWSALBCORS=HNXZ1v2IPVbC6Bhh9BxoJDgq1MteFGnG1xsSeho0O9ZAaWpo7VSLNlgmgGQM5XLpkMAQvCpMEBQ2pe6u2Jx6QhA4FACFdLyM2LAKoPiYxh5eaB1b/dGKBmcL8WFrRK4OWbk60U7KXEr70LzKwXRVXRAi0DSOZ7JMBkHDilUZ92uevakYXqOc6+p6DX7ppA==; PHPSESSID=elb~sgpmkin8r2cq3o41ru79ps2du0")
                .build();
        Response response2 = client.newCall(request2).execute();


        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(response2.body().string(), UserBean.class);


        model.addAttribute("id", userBean.getUser_id());
        model.addAttribute("login_key", userBean.getLogin_key());

//				System.out.println(userBean.getLogin_key());
//				System.out.println(userBean.getUser_id());

        //강의정보
        OkHttpClient client2 = new OkHttpClient().newBuilder()
                .build();
        Request request3 = new Request.Builder()
                .url("https://panasonic1.talentlms.com/api/v1/users/id:"+userBean.getUser_id())
                .method("GET", null)
                .addHeader("Authorization", "Basic RFRDejRxWVI1eE1RemNwQjVVbktQWTFpZHNzR0lwOg==")
                .addHeader("Cookie", "AWSALB=BMrb5JNU1MJMwLUZDnqSqyGhxcUQwfYVos2nthPQ8EU+41COg65UeDedxNZeQsw0gfeqPKKhI2VTDxw2HIiqqPe6T+Oi57IY/EtEOKraj9oPqNn2QFF1f0Dtr9rFhVWKfu1vfkBCTNIVRxXnGKDXBYFWXNtcBP+YYLb5PSTukcAb+6z2cWgqaD4XnwfJhw==; AWSALBCORS=BMrb5JNU1MJMwLUZDnqSqyGhxcUQwfYVos2nthPQ8EU+41COg65UeDedxNZeQsw0gfeqPKKhI2VTDxw2HIiqqPe6T+Oi57IY/EtEOKraj9oPqNn2QFF1f0Dtr9rFhVWKfu1vfkBCTNIVRxXnGKDXBYFWXNtcBP+YYLb5PSTukcAb+6z2cWgqaD4XnwfJhw==; PHPSESSID=elb~qdoi8nqik68t0jrc2hnkkma6om")
                .build();

        Response response3 = client.newCall(request3).execute();
//				System.out.println(response3.getClass().getName());

        //Response형 -> String형
        String userString = response3.body().string();

        // String형 ->json형
        JSONObject userJson = new JSONObject(userString);

        //json에서 배열가져오기
        JSONArray courses = userJson.getJSONArray("courses");
        System.out.println(courses);


        List<String>allCourses = new ArrayList<>();

        for(int i =0; i<courses.length(); i++){

            JSONObject object  = courses.getJSONObject(i);

            String course_id = object.getString("id");
            String name = object.getString("name");
            String url = object.getString("last_accessed_unit_url");

            allCourses.add(course_id);
            allCourses.add(name);
            allCourses.add(url);

//            System.out.println(course_id);
//            System.out.println(name);
//            System.out.println(url);

//            Course course = new Course();
//            course.setId(Integer.parseInt(course_id));
//            course.setName(name);
//            course.setUrl(url);

//            courseRepository.save(course);

        }



//        List<Course> allCourses = courseRepository.findAll();
        model.addAttribute("allCourses", allCourses);


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