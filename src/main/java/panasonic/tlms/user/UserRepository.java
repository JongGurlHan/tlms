package panasonic.tlms.user;

import org.springframework.stereotype.Repository;
import panasonic.tlms.course.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private static final Map<String, String> userInfo = new HashMap<>();
    private static final List<String> userInfo2 = new ArrayList<>();


//    public Map<String, String> save(String user_id, String login_key){
//        userInfo.put(user_id, login_key);
//        return userInfo;
//    }
    public List<String> save(String value){
        userInfo2.add(value);
        return userInfo2;
    }


    public List<String> findAll(){
        return userInfo2;
    }
}
