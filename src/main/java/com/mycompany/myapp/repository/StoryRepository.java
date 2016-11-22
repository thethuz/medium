package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Story;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Story entity.
 */
@SuppressWarnings("unused")
public interface StoryRepository extends JpaRepository<Story,Long> {
  List<Story> findAllByAuthor(String author);
  List<Story> findAllByCategory(String category);
  //List<Story> findAllByDate(String date);
}
