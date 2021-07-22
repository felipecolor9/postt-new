package com.lipsoft.postt.repository;

import com.lipsoft.postt.model.Postit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostitRepository extends JpaRepository<Postit, Long> {
}
