package com.proyecto.ficaleche

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter
import java.util.*

class DetallesRegistro : AppCompatActivity() {

    private val stringFilePath =
        Environment.getExternalStorageDirectory().path + "/Download/Document.pdf"
    private val file = File(stringFilePath)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_registro)

        //val BotonAtrasDetallesRegistro = findViewById<ImageView>(R.id.BotonAtrasDetallesRegistro)
        val GenerarPdf = findViewById<LinearLayout>(R.id.GenerarPdf)
        val Cedula = findViewById<TextView>(R.id.Cedula)
        val Fecha = findViewById<TextView>(R.id.Fecha)
        val Jornada_Recolecion_M = findViewById<TextView>(R.id.Jornada_Recolecion_M)
        val Jornada_Recolecion_T = findViewById<TextView>(R.id.Jornada_Recolecion_T)
        val Mes = findViewById<TextView>(R.id.Mes)
        val Nombre = findViewById<TextView>(R.id.Nombre)
        val Observaciones = findViewById<TextView>(R.id.Observaciones)
        val Ruta = findViewById<TextView>(R.id.Ruta)

        val StringCedula: String? = intent.getStringExtra("Cedula")
        val StringFecha: String? = intent.getStringExtra("Fecha")
        val Jornada_Recolecion_Mañana: String? = intent.getStringExtra("Jornada_Recolecion_Mañana")
        val Jornada_Recolecion_tarde: String? = intent.getStringExtra("Jornada_Recolecion_tarde")
        val StringMes: String? = intent.getStringExtra("Mes")
        val StringNombre: String? = intent.getStringExtra("Nombre")
        val StringObservaciones: String? = intent.getStringExtra("Observaciones")
        val StringRuta: String? = intent.getStringExtra("Ruta")

        Cedula.setText(StringCedula)
        Fecha.setText(StringFecha)
        Jornada_Recolecion_M.setText(Jornada_Recolecion_Mañana)
        Jornada_Recolecion_T.setText(Jornada_Recolecion_tarde)
        Mes.setText(StringMes)
        Nombre.setText(StringNombre)
        Observaciones.setText(StringObservaciones)
        Ruta.setText(StringRuta)

        GenerarPdf.setOnClickListener {
            var pdfDocument = PdfDocument()
            var paint = Paint()
            var fecha = Paint()
            var nombre = Paint()
            var cedula = Paint()
            var jornada = Paint()
            var cantidad = Paint()
            var ruta = Paint()
            var observaciones = Paint()

            var paginaInfo = PdfDocument.PageInfo.Builder(816, 1054, 1).create()
            var pagina1 = pdfDocument.startPage(paginaInfo)

            var canvas = pagina1.canvas

            var bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
            var bitmapEscala = Bitmap.createScaledBitmap(bitmap, 200,200, false)
            canvas.drawBitmap(bitmapEscala, 368f, 20f, paint)

            fecha.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            fecha.textSize = 20f
            canvas.drawText(Fecha.text.toString(), 10f, 250f, fecha)

            nombre.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            nombre.textSize = 20f
            canvas.drawText(Nombre.text.toString(), 10f, 350f, nombre)

            cedula.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            cedula.textSize = 20f
            canvas.drawText(Cedula.text.toString(), 10f, 450f, cedula)

            jornada.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            jornada.textSize = 20f
            canvas.drawText(Jornada_Recolecion_M.text.toString(), 10f, 550f, jornada)

            jornada.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            jornada.textSize = 20f
            canvas.drawText(Jornada_Recolecion_T.text.toString(), 10f, 650f, jornada)

            ruta.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            ruta.textSize = 20f
            canvas.drawText(Ruta.text.toString(), 10f, 750f, ruta)

            observaciones.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            observaciones.textSize = 20f
            canvas.drawText(Observaciones.text.toString(), 10f, 850f, observaciones)

            pdfDocument.finishPage(pagina1)

            try {
                pdfDocument.writeTo(FileOutputStream(file))
                Toast.makeText(this,"El archivo Document.pdf a sido creado en la carpeta descargas", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            pdfDocument.close()

        }
    }
}