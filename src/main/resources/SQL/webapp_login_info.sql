use webapp;

CREATE TABLE IF NOT EXISTS blog_login_info(
    user_id CHAR(20) NOT NULL DEFAULT '' UNIQUE,
    email CHAR(30) NOT NULL UNIQUE,
    passwd VARCHAR(32) NOT NULL DEFAULT '',
    regist_date DATETIME NOT NULL DEFAULT now(),
    update_date DATETIME,
    PRIMARY KEY (user_id, email)
);