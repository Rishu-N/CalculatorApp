package com.rishu.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput=findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric=true
        lastDot=false

    }

    fun onClear(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            //here it takes the place of the value in tvInput
            if (lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            //to solve the crashing problem
            var prefix = ""

            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)  //meansthat we are starting from index =1
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")  //if we start with a -ve number and sunstract something
                    //it crashes because we are trying to split at  - and it doesnt know what to do

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text = removeDot((one.toDouble() - two.toDouble()).toString())

                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")  //if we start with a -ve number and sunstract something
                    //it crashes because we are trying to split at  - and it doesnt know what to do

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text =removeDot( (one.toDouble() + two.toDouble()).toString())
                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")  //if we start with a -ve number and sunstract something
                    //it crashes because we are trying to split at  - and it doesnt know what to do

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text = removeDot((one.toDouble() / two.toDouble()).toString())
                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")  //if we start with a -ve number and sunstract something
                    //it crashes because we are trying to split at  - and it doesnt know what to do

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text = removeDot((one.toDouble() * two.toDouble()).toString())
                }





            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeDot(result : String) : String{  //split or substring can also be used
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length-2)
        }

        return value
    }

    private fun isOperatorAdded(value : String) : Boolean{ //makes sure that only 1 operator is allowed to be put in
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")  || value.contains("*") || value.contains("+") ||value.contains("-")
        }
    }
}