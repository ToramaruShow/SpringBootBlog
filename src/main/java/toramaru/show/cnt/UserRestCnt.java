package toramaru.show.cnt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import toramaru.show.model.Category;
import toramaru.show.model.user.LoginInfo;
import toramaru.show.service.CategoryService;
import toramaru.show.service.LoginInfoService;

@RestController
public class UserRestCnt {
	@Autowired
	private LoginInfoService service;
	@Autowired
	private CategoryService cate;
	
	@GetMapping("/user/info")
	public List<LoginInfo> getAllData(){
		return service.getAllData();//データ全表示
	}
	
	@GetMapping("/test/cate")
	public List<Category> select(){
		var add = new Category(4,"Dummy!","Dummy?");
//		cate.save(add);
		cate.deleteById(4);
		return cate.findAll();//データ全表示
	}
}
