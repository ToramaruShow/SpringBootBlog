package toramaru.show.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toramaru.show.model.Blog;
import toramaru.show.service.repository.BlogRepository;

@Service
public class BlogService {
	@Autowired
	private BlogRepository repository;

	//DBから一覧取得
	public List<Blog> findAll() {
		return repository.findAll();
	}

	public Blog save(Blog blog) {
		return repository.save(blog);
	}
}
