package com.example.appnoteroomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NewNoteActivity : AppCompatActivity(), CoroutineScope {

    //Sử dụng coroutines đề loại bỏ callbacks,
    //coroutines sẽ giúp bạn chuyển cấu trúc code CallBack thành code tuần tự
    //callbacks se lam cho ung dung bi cham di
    //Để xử lý task tốn nhiều thời gian mà không ảnh hưởng đến Main Thread thì đa số Dev đều dùng CallBack Pattern

    private var noteDB : NoteRoomDatabase ?= null
    private lateinit var mJob: Job

    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        mJob = Job()
        noteDB = NoteRoomDatabase.getDatabase(this)
        button_save.setOnClickListener {
            launch {
                //save note into database
                val strContent: String = content_note.text.toString()
                noteDB?.noteDao()?.insert(Note(content = strContent))

                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}
