package toramaru.show.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	public Blog getReferenceById(int id) {
		return repository.getReferenceById(id);
	}
	//セーブ
	public Blog save(Blog blog) {
		//日付取得
		LocalDateTime nowTime = LocalDateTime.now();
		blog.setUpdateDate(nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return repository.save(blog);
	}
	//ID使って削除
	public void deleteById(int id) {
		repository.deleteById(id);
	}
}
