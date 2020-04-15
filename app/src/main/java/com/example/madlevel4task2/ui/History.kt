package com.example.madlevel4task2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.*
import com.example.madlevel4task2.database.Game
import com.example.madlevel4task2.model.GameRepository
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class History : AppCompatActivity() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private var listOFGames = arrayListOf<Game>()
    private var gameHistoryAdapter =
        GameHistoryAdapter(listOFGames)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbarHistory)
        gameRepository = GameRepository(this)

        initViews()
    }
    private fun initViews() {
        supportActionBar?.title = "Game History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rvHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHistory.adapter = gameHistoryAdapter
        rvHistory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        getGames()
    }

    private fun deleteHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGamesFromHistory()
            }
        }
        getGames()
    }
    private fun getGames() {
        mainScope.launch {
            val gameHistory = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            if (listOFGames.isEmpty()) {
                listOFGames.addAll(gameHistory)
                gameHistoryAdapter.notifyDataSetChanged()
            } else {
                listOFGames.clear()
                listOFGames.addAll(gameHistory)
                gameHistoryAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ic_delete_white -> {
                deleteHistory()
                true
            }
            android.R.id.home -> {
                var intent = Intent(this, MainActivity::class.java)
                startActivityForResult(intent,
                    HISTORY_DATA
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val HISTORY_DATA = 100
    }
}