package com.freedemy.api.rest.controllers;


import com.freedemy.api.models.Course;
import com.freedemy.api.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursesController {

    @Autowired
    CourseRepository courseRepository;
    @GetMapping(value = "/courses", produces = "application/json")
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @GetMapping(value = "/courses/{id}", produces = "application/json")
    public List<Course> getCourse(@PathVariable("id") int id){
        List<Course> courses = courseRepository.findById(id);
        return courses;
    }

    @PostMapping(value = "/addcourse", consumes = "application/json")
    public ResponseEntity<String> addCourse(@RequestBody Course course){
        Course savedCourse = null;
        ResponseEntity response = null;
        try {
            savedCourse = courseRepository.save(course);
            if(savedCourse!=null){
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Course successfully added");
            }
        }catch (Exception e){
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception occured: "+e);
        }

        return response;
    }

    @DeleteMapping(value = "/course/{id}", produces = "application/json")
    public String deleteCourse(@PathVariable("id") int id){
        return "This will delete the mentioned course";
    }

    @PutMapping(value = "/course/updatecourse", consumes = "application/json")
    public String updateCourse(){
        return "This will update existing course";
    }
}
