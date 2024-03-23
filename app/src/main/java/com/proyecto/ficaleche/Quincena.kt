package com.proyecto.ficaleche

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class Quincena : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quincena)

        val BotonAtrasQuincena = findViewById<ImageView>(R.id.BotonAtrasQuincena)
        val IndicadorMes = findViewById<TextView>(R.id.IndicadorMes)
        val Quincena_1 = findViewById<LinearLayout>(R.id.Quincena_1)
        val Quincena_2 = findViewById<LinearLayout>(R.id.Quincena_2)

        val InfoMes : String? = intent.getStringExtra("Mes")
        val NMes : String? = intent.getStringExtra("#Mes")

        IndicadorMes.setText(InfoMes)

        BotonAtrasQuincena.setOnClickListener {
            val intent = Intent (this, Control_Informacion::class.java)
            startActivity(intent)
            finish()
        }

        Quincena_1.setOnClickListener {
            val intent = Intent (this, IndicardorRuta::class.java)
            intent.putExtra("Mes", NMes)
            intent.putExtra("IndicadorMes", InfoMes)
            intent.putExtra("Dia", "Primera Quincena")
            startActivity(intent)
        }

        Quincena_2.setOnClickListener {
            val intent = Intent (this, IndicardorRuta::class.java)
            intent.putExtra("Mes", NMes)
            intent.putExtra("IndicadorMes", InfoMes)
            intent.putExtra("Dia", "Segunda Quincena")
            startActivity(intent)
        }
    }
}