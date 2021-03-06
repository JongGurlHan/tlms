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
import org.springframework.web.bind.annotation.RequestMapping;
import panasonic.tlms.user.User;
import panasonic.tlms.course.Course;
import panasonic.tlms.course.CourseRepository;
import panasonic.tlms.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final CourseRepository courseRepository;
	private final UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }


    @PostMapping("/login")
    public String login(Model model, HttpServletRequest  request, HttpServletResponse httpServletResponse) throws IOException {

			String id = request.getParameter("user_id");
			String user_pw = request.getParameter("user_pw");

			OkHttpClient client = new OkHttpClient().newBuilder()
					.build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("login", id)
					.addFormDataPart("password",user_pw)
					.build();
			Request request2 = new Request.Builder()
					.url("https://panasonic1.talentlms.com/api/v1/userlogin")
					.method("POST", body)
					.addHeader("Authorization", "Basic RFRDejRxWVI1eE1RemNwQjVVbktQWTFpZHNzR0lwOg==")
					.addHeader("Cookie", "AWSALB=HNXZ1v2IPVbC6Bhh9BxoJDgq1MteFGnG1xsSeho0O9ZAaWpo7VSLNlgmgGQM5XLpkMAQvCpMEBQ2pe6u2Jx6QhA4FACFdLyM2LAKoPiYxh5eaB1b/dGKBmcL8WFrRK4OWbk60U7KXEr70LzKwXRVXRAi0DSOZ7JMBkHDilUZ92uevakYXqOc6+p6DX7ppA==; AWSALBCORS=HNXZ1v2IPVbC6Bhh9BxoJDgq1MteFGnG1xsSeho0O9ZAaWpo7VSLNlgmgGQM5XLpkMAQvCpMEBQ2pe6u2Jx6QhA4FACFdLyM2LAKoPiYxh5eaB1b/dGKBmcL8WFrRK4OWbk60U7KXEr70LzKwXRVXRAi0DSOZ7JMBkHDilUZ92uevakYXqOc6+p6DX7ppA==; PHPSESSID=elb~sgpmkin8r2cq3o41ru79ps2du0")
					.build();
			Response response2 = client.newCall(request2).execute();

			System.out.println(response2);

			Gson gson = new Gson();
			User user = gson.fromJson(response2.body().string(), User.class);

			
			System.out.println(user);


			model.addAttribute("id", user.getUser_id());
			model.addAttribute("login_key", user.getLogin_key());


			userRepository.save(user.getUser_id() );
			userRepository.save(user.getLogin_key() );



			//????????????
			OkHttpClient client2 = new OkHttpClient().newBuilder()
					.build();
			Request request3 = new Request.Builder()
					.url("https://panasonic1.talentlms.com/api/v1/users/id:"+ user.getUser_id())
					.method("GET", null)
					.addHeader("Authorization", "Basic RFRDejRxWVI1eE1RemNwQjVVbktQWTFpZHNzR0lwOg==")
					.addHeader("Cookie", "AWSALB=BMrb5JNU1MJMwLUZDnqSqyGhxcUQwfYVos2nthPQ8EU+41COg65UeDedxNZeQsw0gfeqPKKhI2VTDxw2HIiqqPe6T+Oi57IY/EtEOKraj9oPqNn2QFF1f0Dtr9rFhVWKfu1vfkBCTNIVRxXnGKDXBYFWXNtcBP+YYLb5PSTukcAb+6z2cWgqaD4XnwfJhw==; AWSALBCORS=BMrb5JNU1MJMwLUZDnqSqyGhxcUQwfYVos2nthPQ8EU+41COg65UeDedxNZeQsw0gfeqPKKhI2VTDxw2HIiqqPe6T+Oi57IY/EtEOKraj9oPqNn2QFF1f0Dtr9rFhVWKfu1vfkBCTNIVRxXnGKDXBYFWXNtcBP+YYLb5PSTukcAb+6z2cWgqaD4XnwfJhw==; PHPSESSID=elb~qdoi8nqik68t0jrc2hnkkma6om")
					.build();

			Response response3 = client.newCall(request3).execute();

			//Response??? -> String???
			String userString = response3.body().string();
			//System.out.println(user);

			// String??? ->json???

			JSONObject userJson = new JSONObject(userString);
			//System.out.println(user2);

			//json?????? ??????????????????
		  	try{
				JSONArray courses = userJson.getJSONArray("courses");
				System.out.println(courses);


				for(int i =0; i<courses.length(); i++){
					JSONObject object  = courses.getJSONObject(i);
					String course_id = object.getString("id");
					String name = object.getString("name");
					String url = object.getString("last_accessed_unit_url");

					Course course = new Course();
					course.setId(Integer.parseInt(course_id));
					course.setName(name);
					course.setUrl(url);

					courseRepository.save(course);

				}

			}catch (Exception e){
				System.out.println(e);
			}


			return "redirect:/main";
    }

    @GetMapping("/logout")
	public String logout(Model model) throws IOException {

		List<String> userInfo = userRepository.findAll();


		String id = userInfo.get(0);

    	OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("user_id", id)
				.addFormDataPart("next","http://115.85.181.61:8030/user/login")
				.build();
		Request request = new Request.Builder()
				.url("https://panasonic1.talentlms.com/api/v1/userlogout")
				.method("POST", body)
				.addHeader("Authorization", "Basic RFRDejRxWVI1eE1RemNwQjVVbktQWTFpZHNzR0lwOg==")
				.addHeader("Cookie", "AWSALB=CE4KPSnahOIQsVk2SRvYATE5idXymivET9AJDAoi3RVkMCeZ8cscAhye8aOyZeUdPC05VHTGgQNxiYtjH89WIjJDbDxmB0gLfmnCQRtj74PfHsGmJZP+h5/kN+dkwyVhHnkZ51uksxbb9TCI7wjGpEGGEWmFPXvYBhT5gvQ27m462iy1be5cEv4C2NWUfA==; AWSALBCORS=CE4KPSnahOIQsVk2SRvYATE5idXymivET9AJDAoi3RVkMCeZ8cscAhye8aOyZeUdPC05VHTGgQNxiYtjH89WIjJDbDxmB0gLfmnCQRtj74PfHsGmJZP+h5/kN+dkwyVhHnkZ51uksxbb9TCI7wjGpEGGEWmFPXvYBhT5gvQ27m462iy1be5cEv4C2NWUfA==; PHPSESSID=elb~il6avdkqtemuo8ppsaudjeuum7")
				.build();
		Response logout_response = client.newCall(request).execute();

		System.out.println("????????????" +id);

		String resString = logout_response.body().string();
		JSONObject logoutJson = new JSONObject(resString);

		String logout_url = (String) logoutJson.get("redirect_url");

		model.addAttribute(logout_url);

		System.out.println("logout_url: " + logout_url);






		return "user/logout";

	}

    //????????????
	@GetMapping("/joinGroup")
	public String joinMember() throws IOException {
		return "redirect:https://panasonic1.talentlms.com/dashboard";

	}







}
