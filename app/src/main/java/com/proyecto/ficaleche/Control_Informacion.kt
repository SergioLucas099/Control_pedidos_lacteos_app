package com.proyecto.ficaleche

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.time.format.DateTimeFormatter
import java.util.*

class Control_Informacion : AppCompatActivity() {
    private lateinit var BotonAtrasControlInfo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_informacion)

        BotonAtrasControlInfo = findViewById(R.id.BotonAtrasControlInfo)
        val Enero = findViewById<LinearLayout>(R.id.Enero)
        val Febrero = findViewById<LinearLayout>(R.id.Febrero)
        val Marzo = findViewById<LinearLayout>(R.id.Marzo)
        val Abril = findViewById<LinearLayout>(R.id.Abril)
        val Mayo = findViewById<LinearLayout>(R.id.Mayo)
        val Junio = findViewById<LinearLayout>(R.id.Junio)
        val Julio = findViewById<LinearLayout>(R.id.Julio)
        val Agosto = findViewById<LinearLayout>(R.id.Agosto)
        val Septiembre = findViewById<LinearLayout>(R.id.Septiembre)
        val Octubre = findViewById<LinearLayout>(R.id.Octubre)
        val Noviembre = findViewById<LinearLayout>(R.id.Noviembre)
        val Diciembre = findViewById<LinearLayout>(R.id.Diciembre)
        val Meses = findViewById<LinearLayout>(R.id.Meses)
        val ImgSinWifi = findViewById<ImageView>(R.id.ImgSinWifi)

        val con = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = con.activeNetworkInfo

        if(networkInfo!=null && networkInfo.isConnected){
            ImgSinWifi.visibility = View.INVISIBLE
            Meses.visibility = View.VISIBLE
        }else{
            ImgSinWifi.visibility = View.VISIBLE
            Meses.visibility = View.INVISIBLE
            BotonAtrasControlInfo.visibility = View.VISIBLE
        }

        Enero.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Enero")
            intent.putExtra("#Mes", "01")
            startActivity(intent)
        }

        Febrero.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Febrero")
            intent.putExtra("#Mes", "02")
            startActivity(intent)
        }

        Marzo.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Marzo")
            intent.putExtra("#Mes", "03")
            startActivity(intent)
        }

        Abril.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Abril")
            intent.putExtra("#Mes", "04")
            startActivity(intent)
        }

        Mayo.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Mayo")
            intent.putExtra("#Mes", "05")
            startActivity(intent)
        }

        Junio.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Junio")
            intent.putExtra("#Mes", "06")
            startActivity(intent)
        }

        Julio.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Julio")
            intent.putExtra("#Mes", "07")
            startActivity(intent)
        }

        Agosto.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Agosto")
            intent.putExtra("#Mes", "08")
            startActivity(intent)
        }

        Septiembre.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Septiembre")
            intent.putExtra("#Mes", "09")
            startActivity(intent)
        }

        Octubre.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Octubre")
            intent.putExtra("#Mes", "10")
            startActivity(intent)
        }

        Noviembre.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Noviembre")
            intent.putExtra("#Mes", "11")
            startActivity(intent)
        }

        Diciembre.setOnClickListener {
            val intent = Intent (this, Quincena::class.java)
            intent.putExtra("Mes", "Diciembre")
            intent.putExtra("#Mes", "12")
            startActivity(intent)
        }

        BotonAtrasControlInfo.setOnClickListener {
            val intent = Intent(this, AccionesAdministrador::class.java)
            startActivity(intent)
            finish()
        }
    }
}