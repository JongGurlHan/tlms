package panasonic.tlms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import panasonic.tlms.course.Course;
import panasonic.tlms.course.CourseRepository;
import panasonic.tlms.user.User;
import panasonic.tlms.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String home(){
        return "redirect:user/login";
    }

    @GetMapping("/main")
    public String main(Model model, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {

        List<String> userInfo = userRepository.findAll();

        model.addAttribute("id", userInfo.get(0));
        model.addAttribute("login_key", userInfo.get(1));

        System.out.println(userInfo.get(0));
        System.out.println(userInfo.get(1));


        List<Course>allCourses = courseRepository.findAll();
        model.addAttribute("allCourses", allCourses);
        return "main";
    }
}
