package com.tutorial.tutorialspringboot.Repositories;

import com.tutorial.tutorialspringboot.Entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
