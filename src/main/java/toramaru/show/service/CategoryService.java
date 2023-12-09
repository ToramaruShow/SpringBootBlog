package toramaru.show.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import toramaru.show.model.Category;
import toramaru.show.service.repository.CategoryRepository;

@Service
public class CategoryService {
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

	private void setCaterogyNameList() {
		var categoryName = new ArrayList<String>();
		findAll().forEach(item -> categoryName.add(item.getName()));//itemにひとつづつ入れる
		categoryNameList = categoryName.toArray(new String[] {});
	}
}
