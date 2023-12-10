package toramaru.show.cnt;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import toramaru.show.model.BlogConfig;
import toramaru.show.model.user.LoginInfo;
import toramaru.show.service.LoginInfoService;

@Controller
@RequestMapping("/user")
public class UserCnt {
	@Value("${user.title}")
	private String[] title;
	@Value("${user.check.item}")
	private String[] userCheckItem;

	@Autowired
	private LoginInfoService service;
	private ResourceBundle resource;

	//コンストラクタ
	public UserCnt() {
		resource = ResourceBundle.getBundle(BlogConfig.RESOURCE_NAME);
	}

	//ユーザ登録情報　入力
	@GetMapping("/input")
	public String input(Model model) {
		model.addAttribute(new LoginInfo());
		model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
		return "user/user_regist_input";
	}

	@GetMapping(value = "/regist")
	public String regist(@Valid LoginInfo loginInfo, BindingResult result, Model model) {
		//入力検査
		if (result.hasErrors()) {
			model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
			return "user/user_regist_input";
		}
		//ユーザID重複
		if (service.isRegistUserId(loginInfo.getUserId())) {
			model.addAttribute("checkMsg", String.format(resource.getString("user.check.item.err"), userCheckItem[0]));
			model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
			return "/user/user_regist_input";
		}
		//メール重複
		if (service.isRegistUserEmail(loginInfo.getEmail())) {
			model.addAttribute("checkMsg", String.format(resource.getString("user.check.item.err"), userCheckItem[1]));
			model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
			return "user/user_regist_input";
		}
		//登録
		String resultMsg = resource.getString("user.regist.err");
		try {
			service.save(loginInfo);
			resultMsg = resource.getString("user.regist");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("title", title[BlogConfig.STATE_RESULT]);
		model.addAttribute("resultMsg", resultMsg);
		return "user/user_result";
	}
}
