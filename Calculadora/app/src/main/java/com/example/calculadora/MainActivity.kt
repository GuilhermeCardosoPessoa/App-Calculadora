package com.example.calculadora

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    private lateinit var visor : TextView
    private var numeroAtual = ""
    private var numeroAnterior = ""
    private var operador = ""
    private var listinha = ""
    private var listona = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        visor = findViewById(R.id.visor)

        val botoesNumericos = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        botoesNumericos.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onNumeroClick(it) }
        }
        val botoesOperacoes = mapOf(
            R.id.btnAdd to "+",
            R.id.btnSub to "-",
            R.id.btnMul to "*",
            R.id.btnDiv to "/"
        )
        botoesOperacoes.forEach { (id, op) ->
            findViewById<Button>(id).setOnClickListener { onOperacaoClick(op) }
        }
        findViewById<Button>(R.id.btnDot).setOnClickListener { onDotClick() }
        val listViewProdutos = findViewById<ListView>(R.id.list_view_produtos)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        listViewProdutos.adapter = adapter
        findViewById<Button>(R.id.btnAC).setOnClickListener { onACClick() }
        findViewById<Button>(R.id.btnDEL).setOnClickListener { onDELClick() }
        findViewById<Button>(R.id.Apagar).setOnClickListener { adapter.clear() }
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            onEqualClick()
            if(listinha.isNotEmpty()) {
                var produto = listinha
                listona.add(0, produto)
                adapter.clear()
                adapter.addAll(listona)
            }
        }



    }
    private fun onNumeroClick(view: View){
        val numero = (view as Button).text.toString()
        numeroAtual += numero
        visor.text = numeroAtual
    }
    private fun onOperacaoClick(op: String) {
        if (numeroAtual.isNotEmpty()) {
            numeroAnterior = numeroAtual
            numeroAtual = ""
            operador = op
            visor.text = operador
        }
    }
    private fun onEqualClick() {
        if (numeroAnterior.isNotEmpty() && numeroAtual.isNotEmpty()){
            val num1 = numeroAnterior.toDouble()
            val num2 = numeroAtual.toDouble()
            var Soma = 0.0
            if (operador == "+"){
                Soma = num1 + num2;
            }
            if (operador == "-"){
                Soma = num1 - num2;
            }
            if (operador == "*"){
                Soma = num1 * num2;
            }
            if (operador == "/") {
                Soma = num1 / num2;
            }
            listinha = num1.toString() +" "+ operador + " " + num2.toString() + " = " + Soma.toString()
            visor.text = Soma.toString()

            numeroAnterior = ""
            numeroAtual = ""
            operador = ""
        }
    }
    private fun onDotClick() {
        if (!numeroAtual.contains(".")) {
            numeroAtual += "."
            visor.text = numeroAtual
        }
    }
    private fun onDELClick() {
        if (numeroAtual.isNotEmpty()) {
            numeroAtual = numeroAtual.dropLast(1)
            visor.text = numeroAtual
        }
    }
    private fun onACClick() {
        numeroAnterior = ""
        numeroAtual = ""
        operador = ""
        visor.text = ""
    }
}