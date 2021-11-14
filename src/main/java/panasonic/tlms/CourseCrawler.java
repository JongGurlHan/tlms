package panasonic.tlms;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class CourseCrawler {

    @PostConstruct
    public void init() throws IOException {
//        String url = "https://panasonic1.talentlms.com/dashboard";
//        Document doc = Jsoup.connect(url).get();
//
//        Elements course_title = doc.select("tl-formatted-course-name");
//        Elements imgUrl = doc.select("img.avatar-square");
//        System.out.println(course_title);
//        System.out.println(imgUrl);




    }
}
