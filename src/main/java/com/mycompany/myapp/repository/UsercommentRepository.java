package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Usercomment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Usercomment entity.
 */
@SuppressWarnings("unused")
public interface UsercommentRepository extends JpaRepository<Usercomment,Long> {
  List<Usercomment> findAllByStoryID(int storyid);

}
