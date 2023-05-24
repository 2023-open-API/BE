package com.pop.planu.domain.repository;

import com.pop.planu.domain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByMemberIdAndDate(@Param("memberId") Long memberId, @Param("date")LocalDate date);

}
