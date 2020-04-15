package com.example.madlevel4task2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.madlevel4task2.*
import com.example.madlevel4task2.database.Game
import com.example.madlevel4task2.database.MatchResult
import com.example.madlevel4task2.database.Move
import com.example.madlevel4task2.model.GameRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarMain)
        gameRepository = GameRepository(this)
        initViews()
    }
    private fun initViews() {
        updateStats()
        ivPaperImage.setOnClickListener { createGame(Move.PAPER) }
        ivRockImage.setOnClickListener { createGame(Move.ROCK) }
        ivScissorsImage.setOnClickListener { createGame(Move.SCISSORS) }
    }

    private fun createGame(playerMove: Move) {
        mainScope.launch {
            val botMove = (0..2).random().toEnum<Move>()

            val game = Game(
                date = Calendar.getInstance().time,
                playerMove = playerMove,
                botMove = botMove
            )
            game.matchResult = winLoseCheck(playerMove, botMove)
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
                Log.i("database", "added game to database: $game")
            }
            updateGame(game)
            updateStats()
        }
    }

    private fun winLoseCheck(playerMove: Move, botMove: Move): MatchResult {
        if (playerMove == botMove) return MatchResult.DRAW

        return if (playerMove == Move.SCISSORS && botMove == Move.ROCK) {
            MatchResult.LOSE
        } else if (playerMove == Move.ROCK && botMove == Move.PAPER) {
            MatchResult.LOSE
        } else if (playerMove == Move.PAPER && botMove == Move.SCISSORS) {
            MatchResult.LOSE
        } else {
            MatchResult.WIN
        }
    }

    private fun updateGame(game: Game) {
        when (game.playerMove) {
            Move.SCISSORS -> ivPicked.setImageResource(
                R.drawable.scissors
            )
            Move.PAPER -> ivPicked.setImageResource(
                R.drawable.paper
            )
            Move.ROCK -> ivPicked.setImageResource(
                R.drawable.rock
            )
        }
        when (game.botMove) {
            Move.SCISSORS -> ivComputerPicked.setImageResource(
                R.drawable.scissors
            )
            Move.PAPER -> ivComputerPicked.setImageResource(
                R.drawable.paper
            )
            Move.ROCK -> ivComputerPicked.setImageResource(
                R.drawable.rock
            )
        }
        when (game.matchResult) {
            MatchResult.WIN -> tvWinLose.text = this.resources.getString(
                R.string.Winner
            )
            MatchResult.LOSE -> tvWinLose.text = this.resources.getString(
                R.string.Loser
            )
            MatchResult.DRAW -> tvWinLose.text = this.resources.getString(
                R.string.Draw
            )
        }
    }
    private fun updateStats() {
        mainScope.launch {
            val gameWins = withContext(Dispatchers.IO) {
                gameRepository.getWins()
            }
            val gameLoses = withContext(Dispatchers.IO) {
                gameRepository.getLose()
            }
            val gameDraws = withContext(Dispatchers.IO) {
                gameRepository.getDraws()
            }
            tvStats.text = getString(R.string.stat_show, gameWins, gameLoses,gameDraws)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ic_history_white -> {
                val intent = Intent(this, History::class.java)
                startActivityForResult(intent,
                    DATA
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private inline fun <reified Move : Enum<Move>> Int.toEnum(): Move = enumValues<Move>()[this]
    companion object {
        const val DATA = 100
    }
}
