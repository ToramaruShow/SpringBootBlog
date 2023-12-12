package toramaru.show.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.Getter;
import toramaru.show.model.Category;
import toramaru.show.service.repository.CategoryRepository;

@Service
public class CategoryService {
	private static final int NO_DELETE_NO = 0;
	@Autowired
	private CategoryRepository repository;
	@Getter
	private String[] categoryNameList;

	@PostConstruct
	private void init() {
		setCaterogyNameList();
	}

	public List<Category> findAll() {
		return repository.findAll();
	}

	//一件だけ抽出
	public Category getReferenceById(int id) {
		return repository.getReferenceById(id);
	}
	
	//登録・更新
	public Category save(Category category) {
		return repository.save(category);
	}
	
	@Transactional
	public void deleteById(int id) {
		if(id!=NO_DELETE_NO) {
			repository.updateBlogCategoryId(id);
			repository.deleteById(id);
		}
	}
	

	private void setCaterogyNameList() {
		var categoryName = new ArrayList<String>();
		findAll().forEach(item -> categoryName.add(item.getName()));//itemにひとつづつ入れる
		categoryNameList = categoryName.toArray(new String[] {});
	}
}
