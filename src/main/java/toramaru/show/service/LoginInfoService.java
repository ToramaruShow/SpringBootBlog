package toramaru.show.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toramaru.show.model.user.LoginInfo;
import toramaru.show.service.repository.LoginInfoRepository;

@Service
public class LoginInfoService {
	@Autowired
	private LoginInfoRepository repository;

	//ユーザーID重複してるか？
	public boolean isRegistUserId(String userId) {
		if (repository.isResultUserId(userId) == 0) {
			return false;
		}
		return true;
	}
	//ユーザーID重複してるか？
	public boolean isRegistUserEmail(String email) {
		if (repository.isResultUserEmail(email) == 0) {
			return false;
		}
		return true;
	}
	
	public LoginInfo save(LoginInfo logininfo) {
		return repository.save(logininfo);
	}
}
