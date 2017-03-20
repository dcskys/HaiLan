package com.dc.hailan.utils.greendao.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.dc.hailan.BaseApplication;
import com.dc.hailan.R;
import com.dc.hailan.utils.greendao.GreenDaoManager;
import com.dc.hailan.utils.logger.L;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.greenrobot.greendao.query.Query;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


/**
 * demo   测试用 数据库
 */
public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private View addNoteButton;

    private NoteDao  mNoteDao;
    private Query<Note> noteQuery;

    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initView();

        mNoteDao= GreenDaoManager.getInstance().getSession().getNoteDao();

        noteQuery = mNoteDao.queryBuilder().orderAsc(NoteDao.Properties.Text).build();
        updateNotes();

    }


    private void updateNotes() {
        List<Note> notes = noteQuery.list();
        notesAdapter.setNotes(notes);
    }


    private void initView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        //noinspection ConstantConditions
        addNoteButton.setEnabled(false);

        editText = (EditText) findViewById(R.id.editTextNote);

        //编辑完之后点击软键盘上的回车键才会触发
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {//对应按键 完成
                    addNote();
                    return true;
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                boolean enable = s.length() != 0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void onAddButtonClick(View view) {
        addNote();
    }


    private void addNote() {

        String noteText = editText.getText().toString();
        editText.setText("");

         //用DateFormat类获取系统的当前时间的  MEDIUM 模式的日期为：2017年2月17日  下午19:43:39
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        note.setType(NoteType.TEXT);
        mNoteDao.insert(note);
        L.d("DaoExample"+ "Inserted new note, ID: " + note.getId());
        updateNotes();
    }


    NotesAdapter.NoteClickListener noteClickListener = new NotesAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(int position) {

            Note note = notesAdapter.getNote(position);
            Long noteId = note.getId();
            mNoteDao.deleteByKey(noteId); //删除操作

            L.e("DaoExample:"+"Deleted note, ID: " + noteId);
            updateNotes();
        }
    };



}
