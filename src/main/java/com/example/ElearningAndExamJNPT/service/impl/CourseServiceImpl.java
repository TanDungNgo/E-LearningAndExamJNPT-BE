package com.example.ElearningAndExamJNPT.service.impl;

import com.example.ElearningAndExamJNPT.entity.Course;
import com.example.ElearningAndExamJNPT.repository.ICourseRepository;
import com.example.ElearningAndExamJNPT.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private ICourseRepository courseRepository;
    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course save(Course entity) {
        return courseRepository.save(entity);
    }

    @Override
    public Course update(Course entity) {
        return courseRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
    @Override
    public List<Course> searchCourses(String query) {
        return courseRepository.searchCourses(query);
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
}
