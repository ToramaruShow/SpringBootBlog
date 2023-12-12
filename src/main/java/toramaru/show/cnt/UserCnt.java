package toramaru.show.cnt;

import java.util.Objects;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import toramaru.show.model.BlogConfig;
import toramaru.show.model.user.LoginInfo;
import toramaru.show.model.user.LoginInfoKey;
import toramaru.show.service.LoginInfoService;

@Controller
@RequestMapping("/user")
//親　GetMappingでその続き (@RM+@GM /user/input)
public class UserCnt {
	@Value("${user.title}")
	private String[] title;
	@Value("${user.check.item}")
	private String[] userCheckItem;

	@Autowired
	private LoginInfoService service;
	@Autowired
	private HttpSession session;
	private ResourceBundle resource;

	//コンストラクタ
	public UserCnt() {
		resource = ResourceBundle.getBundle(BlogConfig.RESOURCE_NAME);
	}

	@GetMapping("")
	public String top() {
		//セッション情報がない場合はログイン画面へ
		if (Objects.isNull(session.getAttribute(BlogConfig.SESSION_LOGIN_INFO))) {
			return "redirect:/";
		}
		return "/user/user_top";
	}

	//ユーザ登録情報　入力の検査
	@GetMapping("/input")
	public String input(Model model) {
		model.addAttribute(new LoginInfo());
		model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
		return "user/user_regist_input";
	}

	@GetMapping(value = "/regist")
	public String regist(@Valid LoginInfo loginInfo, BindingResult result, Model model) {
		//バリテーション　入力検査
		if (result.hasErrors()) {
			model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
			return "user/user_regist_input";
		}
		//ユーザID重複していないか
		if (service.isRegistUserId(loginInfo.getUserId())) {//UserIDがtrueなら
			model.addAttribute("checkMsg", String.format(resource.getString("user.check.item.err"), userCheckItem[0]));//registinput.htmlの{checkMsg}にデータを渡す
			model.addAttribute("title", title[BlogConfig.STATE_INPUT]);
			return "/user/user_regist_input";//trueだと入力画面に戻される
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
			service.save(loginInfo);//ここをコメントにすると登録されない
			resultMsg = resource.getString("user.regist");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("title", title[BlogConfig.STATE_RESULT]);
		model.addAttribute("resultMsg", resultMsg);
		return "user/user_result";
	}

	@GetMapping("/cancel")
	public String cancel(Model model) {
		if (Objects.isNull(session.getAttribute(BlogConfig.SESSION_LOGIN_INFO))) {
			return "redirect:/";
		}
		LoginInfoKey sessionUser = (LoginInfoKey) session.getAttribute(BlogConfig.SESSION_LOGIN_INFO);
		String resultMsg = resource.getString("user.cancel.err");
		try {
			service.delete(sessionUser);//DBから削除
			resultMsg = resource.getString("user.cancel");
		} catch (Exception e) {
		}
		//セッション破棄
		session.invalidate();
		model.addAttribute("title", title[BlogConfig.STATE_RESULT]);
		model.addAttribute("resultMsg", resultMsg);
		return "/user/user_result";
	}
}
