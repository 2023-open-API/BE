package com.pop.planu.domain.repository;

import com.pop.planu.domain.entity.ApplicationCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationCourseRepository extends JpaRepository<ApplicationCourse, Long> {

    Optional<List<ApplicationCourse>> findAllByMemberId(Long memberId);
}
