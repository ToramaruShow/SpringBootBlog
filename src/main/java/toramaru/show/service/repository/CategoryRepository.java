package toramaru.show.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import toramaru.show.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
