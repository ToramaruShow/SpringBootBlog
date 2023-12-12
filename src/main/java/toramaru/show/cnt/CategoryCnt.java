package toramaru.show.cnt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class CategoryCnt {
	@Value("${blog.category.title}")
	private String title;

	@GetMapping("/cate")
	public String select(Model model) {
		model.addAttribute("title", title);
		return "blog/category/category_cnt";
	}
}