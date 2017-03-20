package com.dc.hailan.utils.greendao.enity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by dc on 2017/3/14.
 * TODO  demo
 * GreenDao 数据库实体
 */

/*
* @Entity(
*
        // 告知GreenDao当前实体属于哪个schema
        schema = "myschema",

        // 标记一个实体处于活动状态，活动实体有更新、删除和刷新方法
        active = true,

        // 在数据中使用的别名，默认使用的是实体的类名
        nameInDb = "AWESOME_USERS",

        // 定义索引，可以跨越多个列
        indexes = {
                @Index(value = "name DESC", unique = true)
        },

        //标记创建数据库表
        createInDb = false,

        // Whether an all properties constructor should be generated.
        // A no-args constructor is always required.
        generateConstructors = true,

        // Whether getters and setters for properties should be generated if missing.
        generateGettersSetters = true
)
*
*
*
* */
@Entity
public class User {

    //@Id(autoincrement = true)设置自增长
    @Id(autoincrement = true)
    private Long id;  //必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的

    private String name;

    private int age;

    @Property(nameInDb = "NICKNAME")
    private String  nickname;   //nameInDb 在数据中使用的别名，默认使用的是实体的类名

    @Transient
    private int tempUsageCount; //添加次标记之后不会生成数据库表的列

    @NotNull
    private int repos;//设置数据库表当前列不能为空


    @Index(unique = true)
    private String key; //使用@Index创建一个索引，，也可以通过unique给索引添加约束@Unique：向数据库列添加了一个唯一的约束



     @Generated(hash = 1408246918)
    public User(Long id, String name, int age, String nickname, int repos, String key) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.nickname = nickname;
        this.repos = repos;
        this.key = key;
    }

    @Generated(hash = 586692638)
    public User() {
    }


    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getRepos() {
        return this.repos;
    }

    public void setRepos(int repos) {
        this.repos = repos;
    }

}
