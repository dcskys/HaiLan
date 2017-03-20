package com.dc.hailan.utils.greendao.demo;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dc on 2017/3/14.
 *
   测试 类    测试  数据库
 */

@Entity(indexes = { //索引
        @Index(value = "text, date DESC", unique = true)
})
public class Note {

    @Id
    private Long id;

    @NotNull
    private String text;

    private String comment;

   private Date date ;

   //指定自定义类型
   @Convert(converter = NoteTypeConverter.class, columnType = String.class)
   private NoteType type;


   @Generated(hash = 59778150)
   public Note(Long id, @NotNull String text, String comment, Date date,
         NoteType type) {
      this.id = id;
      this.text = text;
      this.comment = comment;
      this.date = date;
      this.type = type;
   }

   @Generated(hash = 1272611929)
   public Note() {
   }


   public String getComment() {
      return this.comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public NoteType getType() {
      return this.type;
   }

   public void setType(NoteType type) {
      this.type = type;
   }

   public Date getDate() {
      return this.date;
   }

   public void setDate(Date date) {
      this.date = date;
   }



}


