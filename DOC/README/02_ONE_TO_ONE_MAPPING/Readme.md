## 外部キー制約を付けたい場合

## 片方向(Uni-directional)
### ※念の為DBを削除してください
![WS000728.JPG](.\img\WS000728.JPG)


# 該当ブランチ名: (check/hibernate-one-to-one-mapping)
## 2つのテーブルが定義されてるとします。

`instructor`
```
CREATE TABLE `instructor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

`instructor_detail`
```
 CREATE TABLE `instructor_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `youtube_channel` varchar(255) DEFAULT NULL,
  `hobby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

## 外部キー制約をつけたいとします。

* entity上でOneToOneの設定するには下記のように記述してください

```
OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name="instructor_detail_id")
private InstructorDetail instructorDetail;
```

このように設定する。

変数で`InstructorDetail`を設定することで、Entittyの`InstructorDetail`クラスのIDとマッピングしてくれます。正確にいうとPRIMARY KEYとマッピングしてくれます。

`@JoinColumn`を記載することで、`instructor`テーブルに`instructor_detail_id`というカラムが追加されて、ここのIDが`instructor_detail`テーブルとのマッピングするIDとなってくれます。実際に上記設定後のカラムをみてみます。

## 変更後のDB
`instructor`
```
 CREATE TABLE `instructor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `instructor_detail_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5h8q2s9b2twvpdln31m1q70tw` (`instructor_detail_id`),
  CONSTRAINT `FK5h8q2s9b2twvpdln31m1q70tw` FOREIGN KEY (`instructor_detail_id`) REFERENCES `instructor_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

`instructor_detail`は何も変わらないので割愛します。

## データ登録時に,`instructor`と`instructor_detail`テーブル両方にデータを登録する

`instructor_detail`オブジェクト作成
```
InstructorDetail instructorDetail = new InstructorDetail();
instructorDetail.setHobby("game");
instructorDetail.setYoutubeChannel("kanekinfitness");
```

上記のオブジェクトを,`instructor`のentityで準備した`setInstructorDetail`にていれることができる
```
Instructor instructor = new Instructor();
instructor.setEmail("hogehoge@gmail.com");
instructor.setFirstName("hogehoge");
instructor.setLastName("fugafuga");
//ココ
instructor.setInstructorDetail(instructorDetail);
```

これでDBにて登録すると`this.instructorRepository.save(instructor);`

`instructor_detail`テーブルにも登録されるようになります。

### 結果
```
mysql> select * from instructor_detail;
1 row in set (0.00 sec)
+----+-------+-----------------+
| id | hobby | youtube_channel |
+----+-------+-----------------+
|  1 | game  | kanekinfitness  |
+----+-------+-----------------+
```

また、`id`に該当する部分、この例では`1`が`instructor`テーブルので`instructor_detail_id`テーブルに該当します。
```
mysql> select * from instructor;
2 rows in set (0.00 sec)

+----+-----------+--------------------+------------+----------------------+
| id | last_name | email              | first_name | instructor_detail_id |
+----+-----------+--------------------+------------+----------------------+
|  1 | NULL      | test@gmail.com     | hoge       |                 NULL |
|  2 | NULL      | hogehoge@gmail.com | hogehoge   |                    1 |
+----+-----------+--------------------+------------+----------------------+
```


# 削除パターン
* 今度は、削除してみます。

## 既存データ
```
mysql> select * from instructor;
2 rows in set (0.00 sec)
+----+--------------------+------------+-----------+----------------------+
| id | email              | first_name | last_name | instructor_detail_id |
+----+--------------------+------------+-----------+----------------------+
|  6 | hogehoge@gmail.com | hogehoge   | NULL      |                    5 |
|  7 | test@gmail.com     | testhoge   | NULL      |                    6 |
+----+--------------------+------------+-----------+----------------------+
```

```
mysql> select * from instructor_detail;
2 rows in set (0.00 sec)
+----+---------+-----------------+
| id | hobby   | youtube_channel |
+----+---------+-----------------+
|  5 | game    | kanekinfitness  |
|  6 | kintore | amekenfitness   |
+----+---------+-----------------+
(END)
```

## 実行コマンド、`this.instructorRepository.deleteById(6L);` 実行後

```
mysql> select * from instructor;
1 row in set (0.00 sec)
+----+----------------+------------+-----------+----------------------+
| id | email          | first_name | last_name | instructor_detail_id |
+----+----------------+------------+-----------+----------------------+
|  7 | test@gmail.com | testhoge   | NULL      |                    6 |
+----+----------------+------------+-----------+----------------------+
```

```
select * from instructor_detail;
1 row in set (0.00 sec)
+----+---------+-----------------+
| id | hobby   | youtube_channel |
+----+---------+-----------------+
|  6 | kintore | amekenfitness   |
+----+---------+-----------------+
(END)
```

同時に削除されることが確認できる。


## `instructor`が`instructor_detail`と紐づいていないときの挙動について調査

```
mysql> select * from instructor;
2 rows in set (0.00 sec)
+----+--------------------+------------+-----------+----------------------+
| id | email              | first_name | last_name | instructor_detail_id |
+----+--------------------+------------+-----------+----------------------+
|  7 | test@gmail.com     | testhoge   | NULL      |                    6 |
|  8 | fuwafuwa@gmail.com | fuwafuwa   | NULL      |                 NULL |
+----+--------------------+------------+-----------+----------------------+
(END)

```

```
mysql> select * from instructor_detail;
1 row in set (0.00 sec)
+----+---------+-----------------+
| id | hobby   | youtube_channel |
+----+---------+-----------------+
|  6 | kintore | amekenfitness   |
+----+---------+-----------------+
(END)
```

`this.instructorRepository.deleteById(8);`で無事削除されます。


## 両方向(bi-directional)

### ※念の為DBを削除してください
![WS000729.JPG](.\img\WS000729.JPG)

`instructor_detail`に`instructor`の内容をいれてあげます。
```
@OneToOne(mappedBy = "instructorDetail")
private Instructor instructor;
```

こうすると、`instructor`クラスの`instructorDetail`変数ととマッピングすることを知らせることができます。

* instuructorの内容が入ってることを確認できました。
![WS000730.JPG](.\img\WS000730.JPG)

## 削除方法について

`CascadeType.ALL`現在この設定により、`instructor_detail`を削除すると関連する`instructor`カラムも削除されてしまいます。

## `instructor_detail`のみを削除したい場合は`CascadeType`を修正します。
`cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})`
ここを`instructor_detail`の`instructor`の箇所の`onetoone`の箇所にいれ削除します。

### ※注意点
一度、`instructor`の関係を無関係にしてから削除します。ここは結構はまりますので注意してください

```
InstructorDetail detail = this.instructorDetailRepository.findById(2L).get();
detail.getInstructor().setInstructorDetail(null);
```

ここで削除可能となります。