package com.dc.hailan;

import android.content.Context;
import android.content.Intent;

import com.dc.hailan.utils.greendao.demo.NoteActivity;

/**
 * Created by zcits on 2017/3/14.
 */

public class dbText {

    public static  void  startNoteActivity(Context context){
        Intent i  = new Intent(context, NoteActivity.class);
        context.startActivity(i);
    }



}
