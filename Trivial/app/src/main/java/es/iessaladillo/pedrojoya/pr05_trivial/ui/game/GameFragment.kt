package es.iessaladillo.pedrojoya.pr05_trivial.ui.game


import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager

import es.iessaladillo.pedrojoya.pr05_trivial.R
import es.iessaladillo.pedrojoya.pr05_trivial.data.Question
import es.iessaladillo.pedrojoya.pr05_trivial.ui.game.game_over.GameOverFragment
import es.iessaladillo.pedrojoya.pr05_trivial.ui.game.game_won.GameWonFragment
import kotlinx.android.synthetic.main.game_fragment.*
import kotlin.properties.Delegates


class GameFragment : Fragment(R.layout.game_fragment) {

    private val viewModel: GameFragmentViewModel by viewModels()
    private lateinit var currentQuestion: Question
    private var indexAnswerSelected: Int = -1
    var numQuestions by Delegates.notNull<Int>()
    var check by Delegates.notNull<Boolean>()

    //Obtenemos los valores de preferences.xml
    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    companion object {
        fun newInstance() = GameFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        //Obtenemos el número de preguntas antes de modificar el appbar porque éste necesita saber cuántas preguntas hay
        getNumQuestions()
        setupAppBar()
    }

    private fun setupAppBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            //Para que aparezca el icono de atrás
            //Esto solo habilita el icono, pero no va hacia atrás.
            //Para que al pulsar vaya a atrás, hay que sobreescribir el método onSupportNavigateUp() EN LA ACTIVIDAD, indicándole que haga onBackPressed()
            setDisplayHomeAsUpEnabled(true)
            //Para que aparezca el título que quiero mostrar
            setTitle(getString(R.string.game_question_title, viewModel.currentQuestionIndex+1, numQuestions))
        }
    }

    //Al crearse el fragmento obtenemos la pregunta correspondiente y la pintamos.
    //También le damos funcionalidad al botón
    private fun setupViews() {
        currentQuestion = viewModel.questionList[viewModel.currentQuestionIndex]
        setQuestion(currentQuestion)
        btnSubmit.setOnClickListener {
            checkAnswer()
        }
    }

    //Mediante las preferencias obtenemos el número de preguntas que el usuario ha establecido
    private fun getNumQuestions() {
        numQuestions = settings.getInt(
            getString(R.string.seekbar),
            resources.getInteger(R.integer.num_questions)
        )
    }

    //Damos funcionalidad al botón
    private fun checkAnswer() {
        val optionSelectedId = radioGroup.checkedRadioButtonId
        if (optionSelectedId != -1) {
            validateAnswer(optionSelectedId)
        }
    }

    //se comprueba si la opción seleccionada es correcta
    private fun validateAnswer(optionSelectedId: Int) {
        val radioGroupViewSelected: View = requireActivity().findViewById(optionSelectedId)
        indexAnswerSelected = radioGroup.indexOfChild(radioGroupViewSelected)
        if (indexAnswerSelected + 1 == currentQuestion.correctAnswer) {
            correctAnswer()
        } else {
            navigateToGameOver()
        }
    }

    //Cuando una respuesta es correcta se comprueba si es la última pregunta.
    //Si lo es, se muestra la pantalla de victoria. Si no, se dibuja la siguiente pregunta
    private fun correctAnswer() {
        viewModel.currentQuestionIndex++
        if (viewModel.currentQuestionIndex > numQuestions - 1) {
            navigateToGameWon()
        } else {
            currentQuestion = viewModel.questionList[viewModel.currentQuestionIndex]
            nextQuestion()
        }
    }


    private fun navigateToGameWon() {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcContent, GameWonFragment.newInstance())

        }

    }

    private fun navigateToGameOver() {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcContent, GameOverFragment.newInstance())
        }
    }


    private fun nextQuestion() {
        radioGroup.clearCheck()
        setQuestion(currentQuestion)
        setupAppBar()
    }

    private fun setQuestion(question: Question) {
        txtQuestion.text = question.question
        rbtnAnswer1.text = question.answers[0]
        rbtnAnswer2.text = question.answers[1]
        rbtnAnswer3.text = question.answers[2]
        rbtnAnswer4.text = question.answers[3]
    }


}
