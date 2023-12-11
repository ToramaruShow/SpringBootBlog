package toramaru.show.cnt;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import toramaru.show.model.user.LoginInfo;
import toramaru.show.service.LoginInfoService;

@Controller
//コントロールするために 上の@を忘れないで
public class LoginCnt {
	@Autowired
	LoginInfoService service;
	@Autowired
	private HttpSession session;
	@Value("${login.title}") //propetiesから引っ張るやつ
	private String[] title; //一応配列で

	@GetMapping("/")
	public String top(Model model) {
		model.addAttribute("title", title[0]);//配列なので０突っ込む
		model.addAttribute(new LoginInfo());
		return "/login/blog_login";
	}

	@GetMapping("/login")
	public String login(@Valid LoginInfo loginInfo, BindingResult result, Model model,
			@Value("${login.err}") String loginErr) {//@Valueってpropertiesから引っ張るもの？
		//入力チェック
		if (result.hasErrors()) {
			if (result.hasFieldErrors("email") || result.hasFieldErrors("passwd")) {//emailなどはフィールド名と合わせる
				//				model.addAttribute("${checkMsgLogin}", loginErr); やっぱいらなかった
				model.addAttribute("title", title[0]);
				return "/login/blog_login";
			}
		}
		//DBチェック
		String user_id = service.findUser(loginInfo);
		if (Objects.isNull(user_id)) {
			model.addAttribute("checkMsgLogin", loginErr);
			model.addAttribute("title", title[0]);
			return "/login/blog_login";
		}
		//セッション
		session.setAttribute("userInfo", user_id);
//		model.addAttribute("userInfo", user_id);
		return "redirect:/user";//リダイレクトでURLの後ろにある情報を消す
	}
}
//		//		model.addAttribute("loginInfo",loginInfo); 必要かは不明
//		model.addAttribute("userInfo", user_id);
//		return "/user/user_top";
//	} リダイレクト使わないバージョン
