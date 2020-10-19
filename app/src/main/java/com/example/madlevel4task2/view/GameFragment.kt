package com.example.madlevel4task2.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameResult
import com.example.madlevel4task2.model.Move
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.ivComputerChoice
import kotlinx.android.synthetic.main.fragment_game.ivPersonChoice
import kotlinx.android.synthetic.main.fragment_game.tvMatchResult
import kotlinx.android.synthetic.main.game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {
    private var games = mutableListOf<Game>()
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var gameRepository: GameRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        initViews()
    }

    private fun initViews() {
        ivRock.setOnClickListener { playGame(Move.ROCK) }
        ivPaper.setOnClickListener { playGame(Move.PAPER) }
        ivScissors.setOnClickListener { playGame(Move.SCISSORS) }
    }

    private fun playGame(playerMove: Move) {
        val cpuMove = Move.values().random()
        val gameResult = getGameResult(playerMove, cpuMove)

        ivPersonChoice.setImageResource(playerMove.drawableId)
        ivComputerChoice.setImageResource(cpuMove.drawableId)
        tvMatchResult.setText(gameResult.message)
        mainScope.launch {
            val game = Game(
                cpuMove.toString(),
                playerMove.toString(),
                SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()),
                getGameResult(playerMove, cpuMove).toString()
            )
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }

    private fun getGameResult(playerMove: Move, cpuMove: Move): GameResult {
        return when(playerMove) {
            Move.ROCK -> when(cpuMove) {
                Move.ROCK -> GameResult.DRAW
                Move.PAPER -> GameResult.LOSE
                Move.SCISSORS -> GameResult.WIN
            }
            Move.PAPER -> when(cpuMove) {
                Move.ROCK -> GameResult.WIN
                Move.PAPER -> GameResult.DRAW
                Move.SCISSORS -> GameResult.LOSE
            }
            Move.SCISSORS -> when(cpuMove) {
                Move.ROCK -> GameResult.LOSE
                Move.PAPER -> GameResult.WIN
                Move.SCISSORS -> GameResult.DRAW
            }
        }
    }
}