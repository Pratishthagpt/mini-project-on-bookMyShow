package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    Auditorium save (Auditorium auditorium);

}
