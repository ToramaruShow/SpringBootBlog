package toramaru.show.cnt;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import toramaru.show.model.Blog;
import toramaru.show.model.BlogConfig;
import toramaru.show.service.BlogService;
import toramaru.show.service.CategoryService;

@Controller
@RequestMapping("/blog")
public class LecBlogCnt {

	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;

	@Value("${blog.title}")
	private String[] title;

	@GetMapping("/select")
	public String select(Model model) {
		model.addAttribute("title", title[BlogConfig.STATE_SELECT]);
		model.addAttribute("blogList", blogService.findAll());
		return "/blog/blog_select";
	}

	@GetMapping("/input")
	public String input(Model model) {
		model.addAttribute(new Blog());
		model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
		model.addAttribute("categoryList", categoryService.getCategoryNameList());
		return "/blog/blog_input";
	}

	@GetMapping("/confirm")
	public String confirm(@Valid Blog blog, BindingResult result, Model model) {
		if (result.hasErrors()) {
			//カテゴリを送る
			model.addAttribute("categoryList", categoryService.getCategoryNameList());
			//タイトルをインプットにして
			model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
			return "/blog/blog_input";
		}
		//カテゴリー名をセット
		blog.setCategory(categoryService.getCategoryNameList()[blog.getCategoryId() - 1]);
		model.addAttribute("title", title[BlogConfig.STATE_CONFIRM]);
		//ボタンセット
		model.addAttribute("btnBack", BlogConfig.MODE_PAGE_BACK);
		model.addAttribute("btnMode", BlogConfig.MODE_REGIST);
		return "/blog/blog_confirm";
	}

	@GetMapping("/result")
	public String handling(@Valid Blog blog, BindingResult result, @RequestParam("btn") String btn, Model model,
			@Value("#{${blog.io.state}}") Map<String, String> ioState, @Value("${blog.update.msg}") String updateStr,
			@Value("${blog.update.err}") String updateErr) {
		if (result.hasErrors() || Objects.equals(btn, BlogConfig.MODE_PAGE_BACK)) {
			model.addAttribute("categoryList", categoryService.getCategoryNameList());
			model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
			return "/blog/blog_input";
		}
		String resultMsg = String.format(updateErr, blog.getTitle(), ioState.get(BlogConfig.MODE_REGIST));
		//登録
		try {
			blogService.save(blog);
			resultMsg = String.format(updateStr, blog.getTitle(), ioState.get(BlogConfig.MODE_REGIST));
		} catch (Exception e) {
			System.out.println("handling");
			e.printStackTrace();
		}
		model.addAttribute("resultMsg", resultMsg);
		return select(model);
	}
}
