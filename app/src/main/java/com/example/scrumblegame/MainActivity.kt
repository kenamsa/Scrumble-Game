package com.example.scrumblegame

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scrumblegame.ui.theme.ScrumbleGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrumbleGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScrumbleGame()
                }
            }
        }
    }
}

// Define your word list
val wordList = listOf("apple", "banana", "orange", "grape", "kiwi")

// Function to scramble the words
fun scrambleWord(word: String): String {
    return word.toCharArray().apply { shuffle() }.concatToString()
}

@Composable
fun ScrumbleGame() {
    // State variables to hold the current word and score
    var currentWord by remember { mutableStateOf(wordList.random()) }
    var score by remember { mutableStateOf(0) }
    var userGuess by remember { mutableStateOf("") }
    val context = LocalContext.current  // Get the current context

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val scrambledWord = scrambleWord(currentWord)

        // Text showing the word to unscramble
        Text(text = "Unscramble: $scrambledWord")

        TextField(
            value = userGuess,
            onValueChange = { userGuess = it },
            placeholder = { Text("Enter your guess") }
        )

        Button(onClick = {
            if (userGuess.equals(currentWord, true)) {
                score++
                Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Try Again!", Toast.LENGTH_SHORT).show()
            }
            userGuess = ""
            loadNextWord { currentWord = it }
        }) {
            Text(text = "Submit")
        }

        Text(text = "Score: $score")
    }
}

// Function to load the next word randomly
private fun loadNextWord(updateCurrentWord: (String) -> Unit) {
    updateCurrentWord(wordList.random())
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScrumbleGameTheme {
        ScrumbleGame()
    }
}
