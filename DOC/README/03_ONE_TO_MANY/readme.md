## ONE TO MANY について 対象ブランチ(check/hibernate-one-to-many-mapping)※one-to-oneブランチからの派生です。

## 一方方向の通信イメージ

![WS000733.JPG](E:\Dropbox\MY_DESC\ピクチャ\Screenshot\WS000733.JPG)

## 今回の対象データベース
`review`

```
CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

リレーション関係を作成します。
```
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@JoinColumn(name="course_id")
private List<Review> reviews;
```


このように設定すると,DB上ではこのような変化となります。
```
CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKprox8elgnr8u5wrq1983degk` (`course_id`),
  CONSTRAINT `FKprox8elgnr8u5wrq1983degk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```


※気になる点
普段は`@JoinColumn`が記載してあるentity上で、カラムが追加されるのですが、今回は連携先のテーブルで作成されていた。

※補足
https://www.baeldung.com/jpa-join-column
ざっくり考えると,`@OneToMany`の方にはリレーションカラムはつかないと考えていい。なので今回の例では連携先の方でカラムが追加させられていた


## DBの追加自体はそんなに、難しくなかった
```
Course tmpCourse = new Course("Pacman - How To Score One Million Points");
tmpCourse.addReview(new Review("GREAT!!!"));
tmpCourse.addReview(new Review("COOL!!!"));
tmpCourse.addReview(new Review("AWESOMME!!!"));
this.courseRepository.save(tmpCourse);
```

## 双方向の通信はこんな感じ

![WS000731.JPG](.\img\WS000731.JPG)


## 今回対象のデータベース
`instructor`
```
CREATE TABLE `instructor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `instructor_detail_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5h8q2s9b2twvpdln31m1q70tw` (`instructor_detail_id`),
  CONSTRAINT `FK5h8q2s9b2twvpdln31m1q70tw` FOREIGN KEY (`instructor_detail_id`) REFERENCES `instructor_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8
```

`coures`
```
CREATE TABLE `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

## 外部キー制約をつけたいとします。

`course`
```
@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
@JoinColumn(name="instructor_id")
private Instructor instructor;
```

今回は1対多となります。`多`の方に`ManyToOne`となり、一つだけ紐づく方には`OneToMany`と書きます。今回の例だと`instructor`は`OneToMany`となります。

変数`instructor`とあるので,`instructor`テーブルのidに、紐づきます。また、`@JoinColumn`とあるので、`course`テーブルには、`instructor_id`が追加されます。見てみます。

### ※念の為、テーブル削除します。

`course`
```
CREATE TABLE `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `instructor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqk2yq2yk124dhlsilomy36qr9` (`instructor_id`),
  CONSTRAINT `FKqk2yq2yk124dhlsilomy36qr9` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```
変更されました。

`instructor`も設定する必要があります。
```
@OneToMany(mappedBy = "instructor",
cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
List<Course> course;
```


`instructor`
```
CREATE TABLE `instructor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `instructor_detail_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5h8q2s9b2twvpdln31m1q70tw` (`instructor_detail_id`),
  CONSTRAINT `FK5h8q2s9b2twvpdln31m1q70tw` FOREIGN KEY (`instructor_detail_id`) REFERENCES `instructor_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8
```



## DB更新の流れ(※ここがややこしい)
`Instructor instructor = this.instructorRepository.findById(1L).get();`

ここで対象のinstructorデータを取得。
```
    public void add(Course tmpCourse) {
        if(courses == null) {
            // TODO: 1.ここが関係してるかも
            courses = new ArrayList<>();
        }
        courses.add(tmpCourse);
        tmpCourse.setInstructor(this);
    }
```
ここで、`instructor`の`Couse<List> courses`にデータを挿入して、かつ`tmpCourse.setInstructor(this);`で`Course`Entittyに`instructor`の内容をいれる。

こうすると、`course.instructor_id`にもデータが挿入される。今回のように、tmpCourse.setInstructro(this);のようにしなくてはならない理由は、`@joinColumn`が別Entityにもっているからである。もし、`instructor`側に`@joinColumn`がある場合は、自動で挿入されるようになっている。
