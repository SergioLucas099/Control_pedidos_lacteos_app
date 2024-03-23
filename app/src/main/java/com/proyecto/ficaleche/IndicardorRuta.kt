package com.proyecto.ficaleche

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class IndicardorRuta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indicardor_ruta)

        val BotonAtrasRuta = findViewById<ImageView>(R.id.BotonAtrasRuta)
        val Ruta1 = findViewById<LinearLayout>(R.id.Ruta1)
        val Ruta2 = findViewById<LinearLayout>(R.id.Ruta2)
        val Ruta3 = findViewById<LinearLayout>(R.id.Ruta3)
        val MesRuta = findViewById<TextView>(R.id.MesRuta)
        val MesQuincena = findViewById<TextView>(R.id.MesQuincena)

        val InfoMes : String? = intent.getStringExtra("Mes") //Numero mes
        val IndicadorMes : String? = intent.getStringExtra("IndicadorMes") //Nombre Mes
        val InfoDia : String? = intent.getStringExtra("Dia") //Primera o Segunda Quincena

        MesRuta.setText(IndicadorMes)
        MesQuincena.setText(InfoDia)

        BotonAtrasRuta.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", IndicadorMes)
            startActivity(intent)
            finish()
        }

        Ruta1.setOnClickListener {
            if(InfoDia.equals("Primera Quincena")){
                val intent = Intent (this, Informacion_Registros_Leche_Primera_Quincena::class.java)
                intent.putExtra("Mes", InfoMes)
                intent.putExtra("IndicadorMes", IndicadorMes)
                intent.putExtra("Dia", InfoDia)
                intent.putExtra("Ruta", "Ruta 1")
                startActivity(intent)
            }else{
                val intent = Intent (this, Informacion_Registros_Leche_Segunda_Quincena::class.java)
                intent.putExtra("Mes", InfoMes)
                intent.putExtra("IndicadorMes", IndicadorMes)
                intent.putExtra("Dia", InfoDia)
                intent.putExtra("Ruta", "Ruta 1")
                startActivity(intent)
            }
        }

        Ruta2.setOnClickListener {
            if (InfoDia.equals("Primera Quincena")) {
                val intent = Intent(this, Informacion_Registros_Leche_Primera_Quincena::class.java)
                intent.putExtra("Mes", InfoMes)
                intent.putExtra("IndicadorMes", IndicadorMes)
                intent.putExtra("Dia", InfoDia)
                intent.putExtra("Ruta", "Ruta 2")
                startActivity(intent)
            } else {
                val intent = Intent(this, Informacion_Registros_Leche_Segunda_Quincena::class.java)
                intent.putExtra("Mes", InfoMes)
                intent.putExtra("IndicadorMes", IndicadorMes)
                intent.putExtra("Dia", InfoDia)
                intent.putExtra("Ruta", "Ruta 2")
                startActivity(intent)
            }
        }

        Ruta3.setOnClickListener {
            if (InfoDia.equals("Primera Quincena")) {
                val intent = Intent(this, Informacion_Registros_Leche_Primera_Quincena::class.java)
                intent.putExtra("Mes", InfoMes)
                intent.putExtra("IndicadorMes", IndicadorMes)
                intent.putExtra("Dia", InfoDia)
                intent.putExtra("Ruta", "Ruta 3")
                startActivity(intent)
            } else {
                val intent = Intent(this, Informacion_Registros_Leche_Segunda_Quincena::class.java)
                intent.putExtra("Mes", InfoMes)
                intent.putExtra("IndicadorMes", IndicadorMes)
                intent.putExtra("Dia", InfoDia)
                intent.putExtra("Ruta", "Ruta 3")
                startActivity(intent)
            }
        }
    }
}