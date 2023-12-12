package toramaru.show.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toramaru.show.model.user.LoginInfo;
import toramaru.show.model.user.LoginInfoKey;
import toramaru.show.service.repository.LoginInfoRepository;
//ここらはDB！
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

	//メール重複してるか？
	public boolean isRegistUserEmail(String email) {
		if (repository.isResultUserEmail(email) == 0) {
			return false;
		}
		return true;
	}

	public LoginInfo save(LoginInfo loginInfo) {
		return repository.save(loginInfo);
	}

	public String findUser(LoginInfo loginInfo) {
		return repository.findUser(loginInfo.getEmail(), loginInfo.getPasswd());
	}

	public List<LoginInfo> getAllData() {
		return repository.findAll();
	}
	
	public void delete(LoginInfoKey infoKey) {
		repository.deleteById(infoKey);//logininfokeyにつながってれば勝手に消してくれる
	}
}
