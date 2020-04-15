package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.database.Game
import com.example.madlevel4task2.database.MatchResult
import com.example.madlevel4task2.database.Move
import com.example.madlevel4task2.R
import kotlinx.android.synthetic.main.item_game.view.*
import java.util.*

class GameHistoryAdapter(private val playedGames: List<Game>) :
    RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            val timestamp = Calendar.getInstance().time
            itemView.tvMatchTime.text = timestamp.toString()
            when (game.matchResult) {
                MatchResult.DRAW -> {
                    itemView.tvWinLoseOrDraw.text = itemView.resources.getString(R.string.Draw)
                }
                MatchResult.WIN -> {
                    itemView.tvWinLoseOrDraw.text = itemView.resources.getString(R.string.Winner)
                }
                else -> {
                    itemView.tvWinLoseOrDraw.text = itemView.resources.getString(R.string.Loser)
                }
            }
            when (game.playerMove) {
                Move.ROCK -> itemView.ivPlayerPickedImage.setImageResource(
                    R.drawable.rock
                )
                Move.PAPER -> itemView.ivPlayerPickedImage.setImageResource(
                    R.drawable.paper
                )
                Move.SCISSORS -> itemView.ivPlayerPickedImage.setImageResource(
                    R.drawable.scissors
                )
            }
            when (game.botMove) {
                Move.ROCK -> itemView.ivComputerPickedImage.setImageResource(
                    R.drawable.rock
                )
                Move.PAPER -> itemView.ivComputerPickedImage.setImageResource(
                    R.drawable.paper
                )
                Move.SCISSORS -> itemView.ivComputerPickedImage.setImageResource(
                    R.drawable.scissors
                )
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }
    override fun getItemCount(): Int = playedGames.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playedGames[position])
    }
}