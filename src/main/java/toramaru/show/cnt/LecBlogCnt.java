package toramaru.show.cnt;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import toramaru.show.model.Blog;
import toramaru.show.model.BlogConfig;
import toramaru.show.model.Category;
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

	@Value("#{${blog.io.state}}")
	private Map<String, String> ioState;

	@GetMapping("/select")
	public String select(Model model) {
		model.addAttribute("title", title[BlogConfig.STATE_SELECT]);
		model.addAttribute("blogList", blogService.findAll());
		return "/blog/blog_select";
	}

	@GetMapping("/search")
	public String search(@RequestParam("id") Integer id, Model model) {
		model.addAttribute(blogService.getReferenceById(id));
		//タイトル
		model.addAttribute("title", title[BlogConfig.STATE_UPDATE]);
		//カテゴリ
		model.addAttribute("categoryList", categoryService.getCategoryNameList());
		//ボタンセット
		model.addAttribute("btnModeUp", BlogConfig.MODE_UPDATE);
		model.addAttribute("btnModeDel", BlogConfig.MODE_DELETE);
		return "/blog/blog_input";
	}

	@GetMapping("/input")
	public String input(Model model) {
		model.addAttribute(new Blog());
		//タイトル
		model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
		//カテゴリ
		model.addAttribute("categoryList", categoryService.getCategoryNameList());
		//ボタンセット
		model.addAttribute("btnMode", BlogConfig.MODE_REGIST);
		return "/blog/blog_input";
	}

	@GetMapping("/confirm")
	public String confirm(@Valid Blog blog, BindingResult result, @RequestParam("btn") String btn, Model model) {
		if (result.hasErrors()) {
			//カテゴリを送る
			model.addAttribute("categoryList", categoryService.getCategoryNameList());
			if (Objects.equals(btn, BlogConfig.MODE_REGIST)) {
				//タイトル (INPUTにして)
				model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
				model.addAttribute("btnMode",BlogConfig.MODE_REGIST); //ここ忘れてる
				return "/blog/blog_input";
			}
			model.addAttribute("title", title[BlogConfig.STATE_UPDATE]);
			return "/blog/blog_input";
		}
		//ボタンの状態から処理したい内容を取得
		model.addAttribute("confirmMsg", ioState.get(btn));
		//カテゴリー名をセット
//		blog.setCategory(categoryService.getCategoryNameList()[blog.getCategoryId() - 1]);
		Category category =categoryService.getCategoryNameList().stream().filter(
				item->item.getId()==blog.getCategoryId()).findFirst().orElse(null);
		blog.setCategory(category.getName());
		model.addAttribute("title", title[BlogConfig.STATE_CONFIRM]);
		//ボタンセット
		model.addAttribute("btnBack", BlogConfig.MODE_PAGE_BACK);
		model.addAttribute("btnMode", btn);
		return "/blog/blog_confirm";
	}

	@GetMapping("/result")
	public String handling(@Valid Blog blog, BindingResult result, @RequestParam("btn") String btn,
			RedirectAttributes reAttributes, Model model, @Value("${blog.update.msg}") String updateStr,
			@Value("${blog.update.err}") String updateErr) {
		if (result.hasErrors() || Objects.equals(btn, BlogConfig.MODE_PAGE_BACK)) {
			model.addAttribute("categoryList", categoryService.getCategoryNameList());
			if (blog.getId() == 0) {
				model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
				model.addAttribute("btnMode", BlogConfig.MODE_REGIST);
				return "/blog/blog_input";
			}
			model.addAttribute("title", title[BlogConfig.STATE_UPDATE]);
			model.addAttribute("btnModeUp", BlogConfig.MODE_UPDATE);
			model.addAttribute("btnModeDel", BlogConfig.MODE_DELETE);
			return "/blog/blog_input";
		}
		String resultMsg = String.format(updateErr, blog.getTitle(), ioState.get(btn));
		//登録
		try {
			//登録・更新
			if (Objects.equals(btn, BlogConfig.MODE_REGIST) || Objects.equals(btn, BlogConfig.MODE_UPDATE)) {
				blogService.save(blog);
			}
			//削除
			if (Objects.equals(btn, BlogConfig.MODE_DELETE)) {
				blogService.deleteById(blog.getId());
			}
			resultMsg = String.format(updateStr, blog.getTitle(), ioState.get(btn));
		} catch (Exception e) {
			System.out.println("handling");
			e.printStackTrace();
		}
		//model.addAttribute("resultMsg", resultMsg);
		//リダイレクト先にデータを送る
		var modelMap = new ModelMap();
		modelMap.addAttribute("resultMsg", resultMsg);
		reAttributes.addFlashAttribute("modelMap", modelMap);
		//return select(model);
		return "redirect:/blog/resultShow";
	}

	@GetMapping("/resultShow")
	public String resultShow(@ModelAttribute("modelMap") ModelMap modelMap, Model model) {
		model.addAttribute("resultMsg", modelMap.getAttribute("resultMsg"));
		return select(model);
	}
}
