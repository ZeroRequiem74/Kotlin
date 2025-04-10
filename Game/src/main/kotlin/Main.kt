import java.io.File
import kotlin.random.Random

// Data class to represent a player
data class Player(val name: String, var score: Int = 0)

// HangmanGame class encapsulates game logic
class HangmanGame(private val wordToGuess: String, private val maxAttempts: Int = 6) {
    private val guessedLetters = mutableSetOf<Char>()
    private var remainingAttempts = maxAttempts

    fun play() {
        println("Welcome to Hangman!")

        while (remainingAttempts > 0 && !isWordGuessed()) {
            println("Word: ${getMaskedWord()}")
            println("Guessed Letters: $guessedLetters")
            println("Attempts Left: $remainingAttempts")
            print("Guess a letter: ")

            val input = readLine()?.lowercase()?.getOrNull(0)

            when {
                input == null || !input.isLetter() -> {
                    println("Invalid input. Please enter a letter.")
                }
                guessedLetters.contains(input) -> {
                    println("You already guessed '$input'. Try a different letter.")
                }
                wordToGuess.contains(input) -> {
                    println("Good job! '$input' is in the word.")
                    guessedLetters.add(input)
                }
                else -> {
                    println("Sorry! '$input' is not in the word.")
                    guessedLetters.add(input)
                    remainingAttempts--
                }
            }
            println()
        }

        if (isWordGuessed()) {
            println("Congratulations! You've guessed the word: $wordToGuess")
        } else {
            println("Game Over! The word was: $wordToGuess")
        }
    }

    private fun getMaskedWord(): String {
        return wordToGuess.map { if (guessedLetters.contains(it)) it else '_' }.joinToString(" ")
    }

    private fun isWordGuessed(): Boolean {
        return wordToGuess.all { guessedLetters.contains(it) }
    }
}

fun main() {
    // Reading the words from a file (words.txt)
    val wordList = File("C:/Hangman/Kotlin/Game/src/main/resources/words.txt").readLines()

    if (wordList.isEmpty()) {
        println("The word list file is empty. Please add words to 'words.txt'.")
        return
    }

    // Select a random word from the list
    val randomWord = wordList[Random.nextInt(wordList.size)]
    val player = Player(name = "Player 1")

    println("Starting game for ${player.name}")
    val game = HangmanGame(randomWord)
    game.play()

    // Use of when to update score
    when {
        randomWord.length <= 5 -> player.score += 5
        randomWord.length in 6..8 -> player.score += 10
        else -> player.score += 15
    }

    println("${player.name}'s Score: ${player.score}")
}
