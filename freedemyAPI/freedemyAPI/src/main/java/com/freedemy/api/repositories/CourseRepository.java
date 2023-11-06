package com.freedemy.api.repositories;

import com.freedemy.api.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
    List<Course> findById(int id);
    List<Course> findAll();
}
