package com.example.demo.repo;

import java.util.List;

import com.example.demo.domain.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAll(Sort sort);
    @Query("SELECT p FROM Student p WHERE CONCAT(p.first, ' ', p.last, ' ', p.num, ' ', p.numgroup, ' ',  p.av, ' ') LIKE %?1%")
    List<Student> search(String keyword);

    List<Student> findByProblemstudentIsNotNull();

}
