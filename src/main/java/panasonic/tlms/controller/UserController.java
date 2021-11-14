package panasonic.tlms.controller;

import com.google.gson.Gson;
import okhttp3.*;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import panasonic.tlms.beans.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

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

				System.out.println(userBean.getLogin_key());
				System.out.println(userBean.getUser_id());

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

				//response3 값을 String 형식
				String jsonData = response3.body().string();


				System.out.println(jsonData);




				return "main";
    }

    @GetMapping("/logout")
	public String logout() throws IOException {

    	OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("user_id","1")
				.addFormDataPart("next","example.com")
				.build();
		Request request = new Request.Builder()
				.url("https://panasonic1.talentlms.com/api/v1/userlogout")
				.method("POST", body)
				.addHeader("Authorization", "Basic RFRDejRxWVI1eE1RemNwQjVVbktQWTFpZHNzR0lwOg==")
				.addHeader("Cookie", "AWSALB=CE4KPSnahOIQsVk2SRvYATE5idXymivET9AJDAoi3RVkMCeZ8cscAhye8aOyZeUdPC05VHTGgQNxiYtjH89WIjJDbDxmB0gLfmnCQRtj74PfHsGmJZP+h5/kN+dkwyVhHnkZ51uksxbb9TCI7wjGpEGGEWmFPXvYBhT5gvQ27m462iy1be5cEv4C2NWUfA==; AWSALBCORS=CE4KPSnahOIQsVk2SRvYATE5idXymivET9AJDAoi3RVkMCeZ8cscAhye8aOyZeUdPC05VHTGgQNxiYtjH89WIjJDbDxmB0gLfmnCQRtj74PfHsGmJZP+h5/kN+dkwyVhHnkZ51uksxbb9TCI7wjGpEGGEWmFPXvYBhT5gvQ27m462iy1be5cEv4C2NWUfA==; PHPSESSID=elb~il6avdkqtemuo8ppsaudjeuum7")
				.build();
		Response response = client.newCall(request).execute();



		return "user/logout";

	}

    //그룹가입
	@GetMapping("/joinGroup")
	public String joinMember() throws IOException {
		return "redirect:https://panasonic1.talentlms.com/dashboard";

	}







}
