package toramaru.show.model.user;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginInfoKey implements Serializable{
	private String userId;
	private String email;
}
