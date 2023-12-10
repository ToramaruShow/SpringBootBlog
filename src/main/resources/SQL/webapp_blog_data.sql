use webapp;

INSERT INTO category(name, description) values('なんでも','とりあえず用'),('ねこ','猫の話題'),('たべもの','食べ物の話題');

INSERT INTO blog(category_id,title,body) values(3,'今日の昼','今日は親子丼'),(2,'うちの黒猫','かわいい'),(2,'猫の秘密','内緒で餌をもらっている');
#2とか3は、カテゴリーの番号　２だったらカテゴリーの「ねこ、猫の話題」になる