package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    private val tag = "QuizActivity"
    private val questions = listOf(
        R.string.q1 to listOf(R.string.q1a, R.string.q1b, R.string.q1c),
        R.string.q2 to listOf(R.string.q2a, R.string.q2b, R.string.q2c),
        R.string.q3 to listOf(R.string.q3a, R.string.q3b, R.string.q3c),
        R.string.q4 to listOf(R.string.q4a, R.string.q4b, R.string.q4c),
        R.string.q5 to listOf(R.string.q5a, R.string.q5b, R.string.q5c)
    )
    private val correct = listOf(1, 0, 2, 1, 0)
    private var index = 0
    private var score = 0
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Log.d(tag, "onCreate")
        name = intent.getStringExtra("NAME") ?: getString(R.string.guest)
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("INDEX")
            score = savedInstanceState.getInt("SCORE")
        }
        showQuestion()
    }
    private fun showQuestion() {
        findViewById<TextView>(R.id.progressText).text = getString(R.string.progress, index + 1, questions.size)
        findViewById<TextView>(R.id.questionText).setText(questions[index].first)
        val ids = listOf(R.id.answer1, R.id.answer2, R.id.answer3)
        ids.forEachIndexed { answerIndex, id ->
            findViewById<Button>(id).apply {
                setText(questions[index].second[answerIndex])
                setOnClickListener { selectAnswer(answerIndex) }
            }
        }
    }
    private fun selectAnswer(answer: Int) {
        if (answer == correct[index]) score++
        Log.i(tag, "Question ${index + 1}, answer $answer, score $score")
        index++
        if (index == questions.size) {
            Log.i(tag, "Quiz completed")
            startActivity(Intent(this, ResultActivity::class.java)
                .putExtra("NAME", name).putExtra("SCORE", score)
                .putExtra("TOTAL", questions.size))
            finish()
        } else showQuestion()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("INDEX", index)
        outState.putInt("SCORE", score)
        super.onSaveInstanceState(outState)
    }
    override fun onStart() { super.onStart(); Log.d(tag, "onStart") }
    override fun onResume() { super.onResume(); Log.d(tag, "onResume") }
    override fun onPause() { super.onPause(); Log.d(tag, "onPause") }
    override fun onStop() { super.onStop(); Log.d(tag, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(tag, "onDestroy") }
}
