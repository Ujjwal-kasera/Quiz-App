package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

/** hmra jo activity file h use hm OnClickListener se inherit kr rhe to isse
* kya hoga hmre jitne bi textview,elements h page me sare clickable ho skte h
*/
class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener{

    private var mCurrentPosition:Int=1
    private var mQuestionList:ArrayList<Question>?= null
    private var mSelectedOptionPosition:Int=0
    private var mUsername:String?=null
    private var mCorrectAnswer:Int=0

    private var tvQuestion : TextView ?= null
    private var ivImage : ImageView ?= null
    private var progressBar : ProgressBar ?= null
    private var tvProgress : TextView ?= null
    private var tvOptionOne : TextView ?= null
    private var tvOptionTwo : TextView ?= null
    private var tvOptionThree : TextView ?= null
    private var tvOptionFour : TextView ?= null
    private var btnSkip : Button ?=null
    private var btnSubmit : Button ?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUsername= intent.getStringExtra(Constant.USER_NAME)
        tvQuestion=findViewById(R.id.tv_question)
        ivImage=findViewById(R.id.iv_image)
        progressBar=findViewById(R.id.progressBar)
        tvProgress=findViewById(R.id.tv_progress)
        tvOptionOne=findViewById(R.id.tv_option_one)
        tvOptionTwo=findViewById(R.id.tv_option_two)
        tvOptionThree=findViewById(R.id.tv_option_three)
        tvOptionFour=findViewById(R.id.tv_option_four)
        btnSkip=findViewById(R.id.btn_skip)
        btnSubmit=findViewById(R.id.btn_submit)
        mQuestionList = Constant.getQuestion()

        /** QuizQuestionActivity File ko jo hmne OnClickListener se inherit kiya
        * to hm isme ye define kr rhe ki kaun se element click ho to kya hoga
        * aur kaun kaun se elements hmre clickable hoge
        */

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSkip?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        setQuestion()

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        defaultOptionsView()
        val question: Question = mQuestionList!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        ivImage?.setImageResource(question.image)
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        /** Agr hm last question pe hoge to ye
        * submit button ke text ko finish kr dega
        */
        if(mCurrentPosition==mQuestionList!!.size)
            btnSubmit?.text="Finish"
        else
            btnSubmit?.text="Submit"

    }

    private fun defaultOptionsView(){

        val options = ArrayList<TextView> ()
        tvOptionOne?.let {
            options.add(0,it)
        }
        tvOptionTwo?.let {
            options.add(1,it)
        }
        tvOptionThree?.let {
            options.add(2,it)
        }
        tvOptionFour?.let {
            options.add(3,it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8090"))
            option.typeface=Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv:TextView,selectOptionNum:Int){

        /** har bar jb bi hm kisi option ko click krege to ye defaultOptionView
        * phle click kie option ko default style pe set kr dega..
        * agr hm isko use nhi krte to jitna bi option ko hm
        * select krege sb selected style me set hote jaege
        */
        defaultOptionsView()
        mSelectedOptionPosition=selectOptionNum

        /** Change the colour of selected option
        to selected style as we have defined */

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    /** Upr hmne onCreate me define kiya h ki kaun kaun se element click ho skege
    * OnclickListener se to hmre isme ye define kiya h ki wo element
    * kya function perform kregeya unpe click hone ke bad kya hoga
    * Oncreate me hm phle jaise OnClickListener se ko define krete the
    * use me jaise sara code likhte the iske lie bi wase hi kr skte h
    * pr sare ke lie likhna shi lenghty hoga aur hektik bi hoga
    * aur to sare ke lie hmne ek hi kam krna to islie hm
    * sare ke ek me hi code likhte h
    */
    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option_one ->{
                tvOptionOne?.let {
                    selectedOptionView(it,1)
                }
            }
            R.id.tv_option_two ->{
                tvOptionTwo?.let {
                    selectedOptionView(it,2)
                }
            }
            R.id.tv_option_three ->{
                tvOptionThree?.let {
                    selectedOptionView(it,3)
                }
            }
            R.id.tv_option_four ->{
                tvOptionFour?.let {
                    selectedOptionView(it,4)
                }
            }

            /** jb bi skip button click hoga to kya hoga wo ye define krega */
            R.id.btn_skip->{
                /** Agr koi bi option select nhi hua h aur hm
                * click krte h to wo dusre question pe chla jaega
                */
                mCurrentPosition++
                /** Agr to question abhi baki h to sb reset kr ke nya question assign kr do..
                *  ya phr result wle interface pe chle jaoo
                */
                if(mCurrentPosition<=mQuestionList!!.size){
                    setQuestion()
                }else{
                    val intent = Intent(this,ResultActivity::class.java)
                    intent.putExtra(Constant.USER_NAME,mUsername)
                    intent.putExtra(Constant.TOTAL_QUESTION,mQuestionList!!.size)
                    intent.putExtra(Constant.CORRECT_ANSWER,mCorrectAnswer)
                    startActivity(intent)
                    finish()
                }
                /** Agr last question me hm h to submit ko finish me change kr do */

                if(mCurrentPosition == mQuestionList!!.size){
                    btnSubmit?.text="@string/finish"
                }
                /** jo Skip ko hmne next question kiya tha use phr se skip kr do */
                btnSkip?.text="Skip"
                mSelectedOptionPosition=0
            }

            // Jb bi submit button click hoga to kya hoga ye define krega
            R.id.btn_submit ->{
                /** Agr koi bi option select nhi hua h aur hm pe
                * click krte h to wo reminder de ga
                */
                if(mSelectedOptionPosition==0){
                    Toast.makeText(this,"Please Select a option",Toast.LENGTH_SHORT).show()
                }
                /** Agr option select kiya h user to age ab decide kro ki selected
                * option shi h ki nhi aur uske according option ke UI ko update kro
                */
                else{
                    val question= mQuestionList!![mCurrentPosition-1]
                    if(mSelectedOptionPosition != question.correctAnswer) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswer++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                    /** Agr lst question h to finish display kro do submit button me
                    * wrna go to next question display kro
                    */
                    if(mCurrentPosition == mQuestionList!!.size) {
                        btnSubmit?.text = "@string/finish"
                    }
                    /** next question me move krne ke lie user ko btane ke lie
                    skip button ko next question me kr do..
                    kyuki hmne submit me aisa code nhi likha jo next question pe lete jae
                    to next question ke lie skip btn hi dabana pdega pr hm uska nam
                    change kr dege
                    */
                    btnSkip?.text="Next Question"
                    /**  agr question ktm ho gya h to main activity me return kr jao
                        jo ki hme result page pe le jaega */
                    if(mCurrentPosition>mQuestionList!!.size){
                        val intent = Intent(this,ResultActivity::class.java)
                        intent.putExtra(Constant.USER_NAME,mUsername)
                        intent.putExtra(Constant.TOTAL_QUESTION,mQuestionList!!.size)
                        intent.putExtra(Constant.CORRECT_ANSWER,mCorrectAnswer)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            /** Initial submit button jime skip aur submit el hi btn se hota tha
            ek  hi submit button tha skip button nhi tha
            */
//            // Jb bi submit button click hoga to kya hoga ye define krega
//            R.id.btn_submit ->{
//                /* Agr koi bi option select nhi hua h aur hm pe
//                * click krte h to wo dusre question pe chla jaega
//                */
//                if(mSelectedOptionPosition==0){
//                    mCurrentPosition++
//                    /* Agr to question abhi baki h to sb reset kr ke nya question assign kr do..
//                    *  ya phr result wle interface pe chle jaoo
//                    */
//                    if(mCurrentPosition<=mQuestionList!!.size){
//                        setQuestion()
//                    }else{
//                        val intent = Intent(this,ResultActivity::class.java)
//                        intent.putExtra(Constant.USER_NAME,mUsername)
//                        intent.putExtra(Constant.TOTAL_QUESTION,mQuestionList!!.size)
//                        intent.putExtra(Constant.CORRECT_ANSWER,mCorrectAnswer)
//                        startActivity(intent)
//                        finish()
//                    }
//                }
//                /* Agr option select kiya h user to age ab decide kro ki selected
//                * option shi h ki nhi aur uske according option ke UI ko update kro
//                */
//                else{
//                    val question= mQuestionList!![mCurrentPosition-1]
//                    if(mSelectedOptionPosition != question.correctAnswer) {
//                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
//                    }else{
//                        mCorrectAnswer++
//                    }
//                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
//
//                    /*
//                    Agr lst question h to finish display kro do submit button me
//                    * wrna go to next question display kro
//                    */
//                    if(mCurrentPosition == mQuestionList!!.size){
//                        btnSubmit?.text="Finish"
//                    }
//                    else{
//                        btnSubmit?.text="Go to Next question"
//                    }
//
//                    mSelectedOptionPosition=0
//                }
//            }
        }
    }
    /** this function is used to assigned the style to passed option value
    * isme hm jo bi option number pass krege ye use us passed drawable
    * style me change kr dega
    */
    private fun answerView(answer:Int,drawable:Int){
        when(answer){
            1 -> tvOptionOne?.background=ContextCompat.getDrawable(this,drawable)
            2 -> tvOptionTwo?.background=ContextCompat.getDrawable(this,drawable)
            3 -> tvOptionThree?.background=ContextCompat.getDrawable(this,drawable)
            4 -> tvOptionFour?.background=ContextCompat.getDrawable(this,drawable)
        }
    }
}