package demos.springdata.jsondemo.dao;

import demos.springdata.jsondemo.model.Post;
import org.hibernate.HibernateException;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
}
