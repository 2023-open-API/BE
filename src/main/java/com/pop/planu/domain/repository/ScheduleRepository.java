package com.pop.planu.domain.repository;

import com.pop.planu.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndMemberId( @Param("startDate") LocalDate startOfMonth, @Param("endDate")LocalDate endOfMonth, @Param("memberId") Long memberId);
    List<Schedule> findByStartDateGreaterThanEqualAndStartDateLessThanEqualAndMemberId(
            LocalDate startOfMonth, LocalDate endOfMonth, @Param("memberId") Long memberId);

    List<Schedule> findByEndDateGreaterThanEqualAndEndDateLessThanEqualAndMemberId(
            LocalDate startOfMonth, LocalDate endOfMonth, @Param("memberId") Long memberId);

}
