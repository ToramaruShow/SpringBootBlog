package toramaru.show.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import toramaru.show.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

}
