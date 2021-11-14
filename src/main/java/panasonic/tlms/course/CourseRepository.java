package panasonic.tlms.course;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourseRepository {

    private static final Map<Long, Course> courseList = new HashMap<>();
    private static long sequence = 0L;

    public Course save(Course course){
        course.setTemp_id(++sequence);
        courseList.put(course.getTemp_id(), course);
        return course;
    }

    public Course findById(Long id){
        return courseList.get(id);
    }

    public List<Course> findAll(){
        return new ArrayList<>(courseList.values());
    }
}
