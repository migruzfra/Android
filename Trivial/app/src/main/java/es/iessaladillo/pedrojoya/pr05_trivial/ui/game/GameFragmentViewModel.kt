package es.iessaladillo.pedrojoya.pr05_trivial.ui.game

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr05_trivial.data.*


class GameFragmentViewModel: ViewModel() {

    //Lista barajada con todas las preguntas
    val questionList: List<Question> = listOf(question1, question2, question3, question4, question5,
        question6, question7, question8, question9, question10, question11, question12, question13,
        question14, question15, question16, question17, question18, question19, question20).shuffled()

    var currentQuestionIndex: Int = 0
}