package course.springdata.restmvc.dao;

import course.springdata.restmvc.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
