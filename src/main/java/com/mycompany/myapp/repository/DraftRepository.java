package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Draft;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Draft entity.
 */
@SuppressWarnings("unused")
public interface DraftRepository extends JpaRepository<Draft,Long> {

}
