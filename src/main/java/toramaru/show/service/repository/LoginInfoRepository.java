package toramaru.show.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import toramaru.show.model.user.LoginInfo;
import toramaru.show.model.user.LoginInfoKey;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, LoginInfoKey> {
	//ユーザーID検索
	@Query(value = "select count(*) from blog_login_info"
			+ "\nwhere user_id= :user_id", nativeQuery = true)
	public int isResultUserId(@Param("user_id") String user_id);

	//ユーザーID検索
	@Query(value = "select count(*) from blog_login_info"
			+ "\nwhere email= :email", nativeQuery = true)
	public int isResultUserEmail(@Param("email") String email);

	@Query(value = "select user_id from blog_login_info"
			+ "\nwhere email= :email and passwd= :passwd", nativeQuery = true)
	public String findUser(@Param("email") String email, @Param("passwd") String passwd);
}
