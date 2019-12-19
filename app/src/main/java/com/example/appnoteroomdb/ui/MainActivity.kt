package com.example.appnoteroomdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnoteroomdb.*
import com.example.appnoteroomdb.adapter.NoteListAdapter
import com.example.appnoteroomdb.data.Note
import com.example.appnoteroomdb.data.NoteRoomDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private var noteDB: NoteRoomDatabase? = null
    private var adapter: NoteListAdapter? = null

    private lateinit var mJob: Job

    //Sử dụng coroutines đề loại bỏ callbacks,
    //coroutines sẽ giúp bạn chuyển cấu trúc code CallBack thành code tuần tự
    //callbacks se lam cho ung dung bi cham di
    //Để xử lý task tốn nhiều thời gian mà không ảnh hưởng đến Main Thread thì đa số Dev đều dùng CallBack Pattern
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mJob = Job()

        noteDB =
            NoteRoomDatabase.getDatabase(this)
        //!! se ném ngoại lệ khi giá trị của noteDbB là null
        adapter =
            NoteListAdapter(this, noteDB!!)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        button_new_note.setOnClickListener {
            val intent: Intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)
        }





    }

    override fun onResume() {
        super.onResume()
        getAllNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }

    //get all notes
    fun getAllNotes(){
        launch {
            val listNotes: List<Note>? = noteDB?.noteDao()?.getAllNotes()
            if (listNotes != null){
                adapter?.setNotes(listNotes)
            }
        }
    }


}
