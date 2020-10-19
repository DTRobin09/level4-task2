package com.example.madlevel4task2.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

const val REQ_GAME_KEY = "req_game"
const val PERSON_CHOICE_KEY = "person_choice"
const val COMPUTER_CHOICE_KEY = "compuer_choice"
const val GAME_DATE_KEY = "game_date"
const val GAME_RESULT_KEY = "game_result_key"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {
    lateinit var personChoice: String
    lateinit var computerChoice: String
    lateinit var gameResult: String

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

        ivRock.setOnClickListener {
            randomChoice()
            personChoice = "rock"
            updateDB()
        }

        ivPaper.setOnClickListener {
            randomChoice()
            personChoice = "paper"
            updateDB()
        }

        ivScissors.setOnClickListener {
            randomChoice()
            personChoice = "scissors"
            updateDB()
        }
    }

    private fun randomChoice() {
        computerChoice = when ((1..3).random()) {
            1 -> "rock"
            2 -> "paper"
            3 -> "scissors"
            else -> {
                "rock"
            }
        }
    }

    private fun updateUI() {
        when (computerChoice) {
            "rock" -> {
                ivComputerChoice.setImageResource(R.drawable.rock)
                when (personChoice) {
                    "rock" -> {
                        tvMatchResult.text = getString(R.string.draw_message)

                        ivPersonChoice.setImageResource(R.drawable.rock)
                    }
                    "paper" -> {
                        tvMatchResult.text = getString(R.string.win_message)

                        ivPersonChoice.setImageResource(R.drawable.paper)
                    }
                    "scissors" -> {
                        tvMatchResult.text = getString(R.string.lose_message)

                        ivPersonChoice.setImageResource(R.drawable.scissors)
                    }
                }

            }
            "paper" -> {
                ivComputerChoice.setImageResource(R.drawable.paper)
                when (personChoice) {
                    "rock" -> {
                        tvMatchResult.text = getString(R.string.lose_message)

                        ivPersonChoice.setImageResource(R.drawable.rock)
                    }
                    "paper" -> {
                        tvMatchResult.text = getString(R.string.draw_message)

                        ivPersonChoice.setImageResource(R.drawable.paper)
                    }
                    "scissors" -> {
                        tvMatchResult.text = getString(R.string.win_message)

                        ivPersonChoice.setImageResource(R.drawable.scissors)
                    }
                }

            }
            "scissors" -> {
                ivComputerChoice.setImageResource(R.drawable.scissors)
                when (personChoice) {
                    "rock" -> {
                        tvMatchResult.text = getString(R.string.win_message)

                        ivPersonChoice.setImageResource(R.drawable.rock)
                    }
                    "paper" -> {
                        tvMatchResult.text = getString(R.string.lose_message)

                        ivPersonChoice.setImageResource(R.drawable.paper)
                    }
                    "scissors" -> {
                        tvMatchResult.text = getString(R.string.draw_message)

                        ivPersonChoice.setImageResource(R.drawable.scissors)
                    }
                }
            }
        }
        gameResult = tvMatchResult.text.toString()
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateDB() {
        val date = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())

        CoroutineScope(Dispatchers.Main).launch {
            val game = Game(computerChoice,personChoice,date,gameResult)
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
        updateUI()
    }
}