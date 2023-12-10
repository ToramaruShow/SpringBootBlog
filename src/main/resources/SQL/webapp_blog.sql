use webapp;

#テーブル：　カテゴリ
CREATE TABLE IF NOT EXISTS category(
    id INT(3) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    description TEXT
);

#テーブル：　ブログ
CREATE TABLE IF NOT EXISTS blog(
    id INT(3) AUTO_INCREMENT PRIMARY KEY,
    category_id INT (3) DEFAULT 1,
    title VARCHAR(30) NOT NULL,
    body TEXT,
    regist_date DATETIME NOT NULL DEFAULT now(),
    update_date DATETIME,
    FOREIGN KEY ctg_fk(category_id) REFERENCES category(id)
);