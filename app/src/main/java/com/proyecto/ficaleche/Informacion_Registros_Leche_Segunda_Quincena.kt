package com.proyecto.ficaleche

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter16
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter17
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter18
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter19
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter20
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter21
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter22
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter23
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter24
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter25
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter26
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter27
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter28
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter29
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter30
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter31
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel16
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel17
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel18
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel19
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel20
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel21
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel22
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel23
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel24
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel25
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel26
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel27
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel28
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel29
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel30
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel31
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class Informacion_Registros_Leche_Segunda_Quincena : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_registros_leche_segunda_quincena)

        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), PackageManager.PERMISSION_GRANTED
        )

        val Formato = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val FechaActual = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().format(Formato)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val InfoMes : String? = intent.getStringExtra("Mes")//Numero mes
        val IndicadorMes : String? = intent.getStringExtra("IndicadorMes")//Nombre Mes
        val InfoDia : String? = intent.getStringExtra("Dia")//Primera o Segunda Quincena
        val Ruta : String? = intent.getStringExtra("Ruta")//Ruta Selecionada

        val stringFilePath =
            Environment.getExternalStorageDirectory().path + "/Download/$FechaActual $Ruta.xls"
        val file = File(stringFilePath)

        val hssfWorkbook = HSSFWorkbook()
        val sheet = hssfWorkbook.createSheet("PROVEEDORES")

        var row: Row? = null
        var cell: Cell? = null

        val CrearExcelSegundaQuincena = findViewById<LinearLayout>(R.id.CrearExcelSegundaQuincena)
        val IndicardorMes = findViewById<TextView>(R.id.IndicardorMes)
        val IndicadorQuincena = findViewById<TextView>(R.id.IndicadorQuincena)
        val IndicadorRuta = findViewById<TextView>(R.id.IndicadorRuta)

        IndicardorMes.setText(IndicadorMes)
        IndicadorQuincena.setText(InfoDia)
        IndicadorRuta.setText(Ruta)

        //Dia 16
        val Recyclerdatos16 = findViewById<RecyclerView>(R.id.Recyclerdatos16)
        val AvisoSinInfoDia16 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia16)
        val ListaRegistro16 : ArrayList<RegistrosModel16>

        Recyclerdatos16.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos16.setHasFixedSize(true)
        ListaRegistro16 = arrayListOf<RegistrosModel16>()
        Recyclerdatos16.visibility = View.GONE
        AvisoSinInfoDia16.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("16").addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ListaRegistro16.clear()
                if (snapshot.exists()){
                    for (Snap in snapshot.children){
                        val data = Snap.getValue(RegistrosModel16::class.java)
                        ListaRegistro16.add(data!!)
                    }
                    val Adapter = RegistroAdapter16(ListaRegistro16)
                    Recyclerdatos16.adapter = Adapter
                    Recyclerdatos16.visibility = View.VISIBLE
                }else{
                    AvisoSinInfoDia16.visibility = View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //Dia 17
        val Recyclerdatos17 = findViewById<RecyclerView>(R.id.Recyclerdatos17)
        val AvisoSinInfoDia17 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia17)
        val ListaRegistro17 : ArrayList<RegistrosModel17>

        Recyclerdatos17.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos17.setHasFixedSize(true)
        ListaRegistro17 = arrayListOf<RegistrosModel17>()
        Recyclerdatos17.visibility = View.GONE
        AvisoSinInfoDia17.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("17").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro17.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel17::class.java)
                            ListaRegistro17.add(data!!)
                        }
                        val Adapter = RegistroAdapter17(ListaRegistro17)
                        Recyclerdatos17.adapter = Adapter
                        Recyclerdatos17.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia17.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 18
        val Recyclerdatos18 = findViewById<RecyclerView>(R.id.Recyclerdatos18)
        val AvisoSinInfoDia18 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia18)
        val ListaRegistro18 : ArrayList<RegistrosModel18>

        Recyclerdatos18.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos18.setHasFixedSize(true)
        ListaRegistro18 = arrayListOf<RegistrosModel18>()
        Recyclerdatos18.visibility = View.GONE
        AvisoSinInfoDia18.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("18").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro18.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel18::class.java)
                            ListaRegistro18.add(data!!)
                        }
                        val Adapter = RegistroAdapter18(ListaRegistro18)
                        Recyclerdatos18.adapter = Adapter
                        Recyclerdatos18.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia18.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 19
        val Recyclerdatos19 = findViewById<RecyclerView>(R.id.Recyclerdatos19)
        val AvisoSinInfoDia19 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia19)
        val ListaRegistro19 : ArrayList<RegistrosModel19>

        Recyclerdatos19.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos19.setHasFixedSize(true)
        ListaRegistro19 = arrayListOf<RegistrosModel19>()
        Recyclerdatos19.visibility = View.GONE
        AvisoSinInfoDia19.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("19").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro19.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel19::class.java)
                            ListaRegistro19.add(data!!)
                        }
                        val Adapter = RegistroAdapter19(ListaRegistro19)
                        Recyclerdatos19.adapter = Adapter
                        Recyclerdatos19.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia19.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 20
        val Recyclerdatos20 = findViewById<RecyclerView>(R.id.Recyclerdatos20)
        val AvisoSinInfoDia20 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia20)
        val ListaRegistro20 : ArrayList<RegistrosModel20>

        Recyclerdatos20.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos20.setHasFixedSize(true)
        ListaRegistro20 = arrayListOf<RegistrosModel20>()
        Recyclerdatos20.visibility = View.GONE
        AvisoSinInfoDia20.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("20").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro20.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel20::class.java)
                            ListaRegistro20.add(data!!)
                        }
                        val Adapter = RegistroAdapter20(ListaRegistro20)
                        Recyclerdatos20.adapter = Adapter
                        Recyclerdatos20.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia20.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 21
        val Recyclerdatos21 = findViewById<RecyclerView>(R.id.Recyclerdatos21)
        val AvisoSinInfoDia21 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia21)
        val ListaRegistro21 : ArrayList<RegistrosModel21>

        Recyclerdatos21.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos21.setHasFixedSize(true)
        ListaRegistro21 = arrayListOf<RegistrosModel21>()
        Recyclerdatos21.visibility = View.GONE
        AvisoSinInfoDia21.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("21").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro21.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel21::class.java)
                            ListaRegistro21.add(data!!)
                        }
                        val Adapter = RegistroAdapter21(ListaRegistro21)
                        Recyclerdatos21.adapter = Adapter
                        Recyclerdatos21.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia21.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 22
        val Recyclerdatos22 = findViewById<RecyclerView>(R.id.Recyclerdatos22)
        val AvisoSinInfoDia22 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia22)
        val ListaRegistro22 : ArrayList<RegistrosModel22>

        Recyclerdatos22.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos22.setHasFixedSize(true)
        ListaRegistro22 = arrayListOf<RegistrosModel22>()
        Recyclerdatos22.visibility = View.GONE
        AvisoSinInfoDia22.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("22").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro22.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel22::class.java)
                            ListaRegistro22.add(data!!)
                        }
                        val Adapter = RegistroAdapter22(ListaRegistro22)
                        Recyclerdatos22.adapter = Adapter
                        Recyclerdatos22.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia22.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 23
        val Recyclerdatos23 = findViewById<RecyclerView>(R.id.Recyclerdatos23)
        val AvisoSinInfoDia23 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia23)
        val ListaRegistro23 : ArrayList<RegistrosModel23>

        Recyclerdatos23.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos23.setHasFixedSize(true)
        ListaRegistro23 = arrayListOf<RegistrosModel23>()
        Recyclerdatos23.visibility = View.GONE
        AvisoSinInfoDia23.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("23").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro23.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel23::class.java)
                            ListaRegistro23.add(data!!)
                        }
                        val Adapter = RegistroAdapter23(ListaRegistro23)
                        Recyclerdatos23.adapter = Adapter
                        Recyclerdatos23.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia23.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 24
        val Recyclerdatos24 = findViewById<RecyclerView>(R.id.Recyclerdatos24)
        val AvisoSinInfoDia24 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia24)
        val ListaRegistro24 : ArrayList<RegistrosModel24>

        Recyclerdatos24.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos24.setHasFixedSize(true)
        ListaRegistro24 = arrayListOf<RegistrosModel24>()
        Recyclerdatos24.visibility = View.GONE
        AvisoSinInfoDia24.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("24").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro24.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel24::class.java)
                            ListaRegistro24.add(data!!)
                        }
                        val Adapter = RegistroAdapter24(ListaRegistro24)
                        Recyclerdatos24.adapter = Adapter
                        Recyclerdatos24.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia24.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 25
        val Recyclerdatos25 = findViewById<RecyclerView>(R.id.Recyclerdatos25)
        val AvisoSinInfoDia25 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia25)
        val ListaRegistro25 : ArrayList<RegistrosModel25>

        Recyclerdatos25.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos25.setHasFixedSize(true)
        ListaRegistro25 = arrayListOf<RegistrosModel25>()
        Recyclerdatos25.visibility = View.GONE
        AvisoSinInfoDia25.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("25").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro25.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel25::class.java)
                            ListaRegistro25.add(data!!)
                        }
                        val Adapter = RegistroAdapter25(ListaRegistro25)
                        Recyclerdatos25.adapter = Adapter
                        Recyclerdatos25.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia25.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 26
        val Recyclerdatos26 = findViewById<RecyclerView>(R.id.Recyclerdatos26)
        val AvisoSinInfoDia26 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia26)
        val ListaRegistro26 : ArrayList<RegistrosModel26>

        Recyclerdatos26.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos26.setHasFixedSize(true)
        ListaRegistro26 = arrayListOf<RegistrosModel26>()
        Recyclerdatos26.visibility = View.GONE
        AvisoSinInfoDia26.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("26").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro26.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel26::class.java)
                            ListaRegistro26.add(data!!)
                        }
                        val Adapter = RegistroAdapter26(ListaRegistro26)
                        Recyclerdatos26.adapter = Adapter
                        Recyclerdatos26.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia26.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 27
        val Recyclerdatos27 = findViewById<RecyclerView>(R.id.Recyclerdatos27)
        val AvisoSinInfoDia27 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia27)
        val ListaRegistro27 : ArrayList<RegistrosModel27>

        Recyclerdatos27.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos27.setHasFixedSize(true)
        ListaRegistro27 = arrayListOf<RegistrosModel27>()
        Recyclerdatos27.visibility = View.GONE
        AvisoSinInfoDia27.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("27").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro27.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel27::class.java)
                            ListaRegistro27.add(data!!)
                        }
                        val Adapter = RegistroAdapter27(ListaRegistro27)
                        Recyclerdatos27.adapter = Adapter
                        Recyclerdatos27.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia27.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 28
        val Recyclerdatos28 = findViewById<RecyclerView>(R.id.Recyclerdatos28)
        val AvisoSinInfoDia28 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia28)
        val ListaRegistro28 : ArrayList<RegistrosModel28>

        Recyclerdatos28.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos28.setHasFixedSize(true)
        ListaRegistro28 = arrayListOf<RegistrosModel28>()
        Recyclerdatos28.visibility = View.GONE
        AvisoSinInfoDia28.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("28").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro28.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel28::class.java)
                            ListaRegistro28.add(data!!)
                        }
                        val Adapter = RegistroAdapter28(ListaRegistro28)
                        Recyclerdatos28.adapter = Adapter
                        Recyclerdatos28.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia28.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 29
        val Recyclerdatos29 = findViewById<RecyclerView>(R.id.Recyclerdatos29)
        val AvisoSinInfoDia29 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia29)
        val ListaRegistro29 : ArrayList<RegistrosModel29>

        Recyclerdatos29.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos29.setHasFixedSize(true)
        ListaRegistro29 = arrayListOf<RegistrosModel29>()
        Recyclerdatos29.visibility = View.GONE
        AvisoSinInfoDia29.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("29").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro29.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel29::class.java)
                            ListaRegistro29.add(data!!)
                        }
                        val Adapter = RegistroAdapter29(ListaRegistro29)
                        Recyclerdatos29.adapter = Adapter
                        Recyclerdatos29.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia29.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 30
        val Recyclerdatos30 = findViewById<RecyclerView>(R.id.Recyclerdatos30)
        val AvisoSinInfoDia30 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia30)
        val ListaRegistro30 : ArrayList<RegistrosModel30>

        Recyclerdatos30.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos30.setHasFixedSize(true)
        ListaRegistro30 = arrayListOf<RegistrosModel30>()
        Recyclerdatos30.visibility = View.GONE
        AvisoSinInfoDia30.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("30").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro30.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel30::class.java)
                            ListaRegistro30.add(data!!)
                        }
                        val Adapter = RegistroAdapter30(ListaRegistro30)
                        Recyclerdatos30.adapter = Adapter
                        Recyclerdatos30.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia30.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 31
        val Recyclerdatos31 = findViewById<RecyclerView>(R.id.Recyclerdatos31)
        val AvisoSinInfoDia31 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia31)
        val ListaRegistro31 : ArrayList<RegistrosModel31>

        Recyclerdatos31.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos31.setHasFixedSize(true)
        ListaRegistro31 = arrayListOf<RegistrosModel31>()
        Recyclerdatos31.visibility = View.GONE
        AvisoSinInfoDia31.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("31").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro31.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel31::class.java)
                            ListaRegistro31.add(data!!)
                        }
                        val Adapter = RegistroAdapter31(ListaRegistro31)
                        Recyclerdatos16.adapter = Adapter
                        Recyclerdatos16.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia31.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        CrearExcelSegundaQuincena.setOnClickListener {

            var row: Row? = null
            var cell: Cell? = null

            //Tomar timpo
            val FormatoAnio = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val AnioActual = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now().format(FormatoAnio)
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            val font: Font = hssfWorkbook.createFont()
            font.bold = true
            font.fontHeight = 200
            font.color = IndexedColors.BLACK.index
            font.fontName = "Arial"

            //TITULO 1 -> ORLANDO PULIDO
            row = sheet.createRow(0)
            //16
            sheet.addMergedRegion(CellRangeAddress(0,0,0,5))
            cell = row.createCell(0)
            cell.setCellValue("ORLANDO PULIDO")
            cell.cellStyle.setAlignment(HorizontalAlignment.CENTER)
            cell.cellStyle.setVerticalAlignment(VerticalAlignment.CENTER)
            cell.cellStyle.setFont(font)

            //TITULO 2 -> INDICADOR MES
            row = sheet.createRow(1)
            sheet.addMergedRegion(CellRangeAddress(1,1,0,5))
            cell = row.createCell(0)
            cell.setCellValue(InfoDia+" Mes "+IndicadorMes +" "+AnioActual)

            //TITULO 3 -> INDICADOR RUTA
            row = sheet.createRow(2)
            sheet.addMergedRegion(CellRangeAddress(2,2,0,5))
            cell = row.createCell(0)
            cell.setCellValue(Ruta)

            //TITULO 4 -> INDICADOR DIA
            row = sheet.createRow(3)
            sheet.addMergedRegion(CellRangeAddress(3,3,0,5))
            cell = row.createCell(0)
            cell.setCellValue("16")

            //TITULO 5 -> DATOS DEL DÍA
            row = sheet.createRow(4)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona16 = 5
            var numero_persona16 = 1
            var posicion_excel16 = 6

            for (i in 0 until ListaRegistro16.size) {

                row= sheet.createRow(posicion_persona16)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona16.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro16.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro16.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro16.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro16.get(i).Jornada_Recolecion_Tarde)

                posicion_persona16++
                numero_persona16++
                posicion_excel16++
            }

            row = sheet.createRow(posicion_excel16)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel16,posicion_excel16,0,5))
            cell = row.createCell(0)
            cell.setCellValue("17")

            row = sheet.createRow(posicion_excel16+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona17 = posicion_excel16+2
            var numero_persona17 = 1
            var posicion_excel17 = posicion_excel16+3

            for (i in 0 until ListaRegistro17.size) {

                row= sheet.createRow(posicion_persona17)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona17.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro17.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro17.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro17.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro17.get(i).Jornada_Recolecion_Tarde)

                posicion_persona17++
                numero_persona17++
                posicion_excel17++
            }

            row = sheet.createRow(posicion_excel17)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel17,posicion_excel17,0,5))
            cell = row.createCell(0)
            cell.setCellValue("18")

            row = sheet.createRow(posicion_excel17+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona18 = posicion_excel17+2
            var numero_persona18 = 1
            var posicion_excel18 = posicion_excel17+3

            for (i in 0 until ListaRegistro18.size) {

                row= sheet.createRow(posicion_persona18)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona18.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro18.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro18.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro18.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro18.get(i).Jornada_Recolecion_Tarde)

                posicion_persona18++
                numero_persona18++
                posicion_excel18++
            }

            row = sheet.createRow(posicion_excel18)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel18,posicion_excel18,0,5))
            cell = row.createCell(0)
            cell.setCellValue("19")

            row = sheet.createRow(posicion_excel18+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona19 = posicion_excel18+2
            var numero_persona19 = 1
            var posicion_excel19 = posicion_excel18+3

            for (i in 0 until ListaRegistro19.size) {

                row= sheet.createRow(posicion_persona18)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona18.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro19.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro19.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro19.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro19.get(i).Jornada_Recolecion_Tarde)

                posicion_persona19++
                numero_persona19++
                posicion_excel19++
            }

            row = sheet.createRow(posicion_excel19)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel19,posicion_excel19,0,5))
            cell = row.createCell(0)
            cell.setCellValue("20")

            row = sheet.createRow(posicion_excel19+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona20 = posicion_excel19+2
            var numero_persona20 = 1
            var posicion_excel20 = posicion_excel19+3

            for (i in 0 until ListaRegistro20.size) {

                row= sheet.createRow(posicion_persona20)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona20.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro20.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro20.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro20.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro20.get(i).Jornada_Recolecion_Tarde)

                posicion_persona20++
                numero_persona20++
                posicion_excel20++
            }

            row = sheet.createRow(posicion_excel20)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel20,posicion_excel20,0,5))
            cell = row.createCell(0)
            cell.setCellValue("21")

            row = sheet.createRow(posicion_excel20+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona21 = posicion_excel20+2
            var numero_persona21 = 1
            var posicion_excel21 = posicion_excel20+3

            for (i in 0 until ListaRegistro21.size) {

                row= sheet.createRow(posicion_persona21)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona21.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro21.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro21.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro21.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro21.get(i).Jornada_Recolecion_Tarde)

                posicion_persona21++
                numero_persona21++
                posicion_excel21++
            }

            //22
            row = sheet.createRow(posicion_excel21)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel21,posicion_excel21,0,5))
            cell = row.createCell(0)
            cell.setCellValue("22")

            row = sheet.createRow(posicion_excel21+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona22 = posicion_excel21+2
            var numero_persona22 = 1
            var posicion_excel22 = posicion_excel21+3

            for (i in 0 until ListaRegistro22.size) {

                row= sheet.createRow(posicion_persona22)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona22.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro22.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro22.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro22.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro22.get(i).Jornada_Recolecion_Tarde)

                posicion_persona22++
                numero_persona22++
                posicion_excel22++
            }

            //23
            row = sheet.createRow(posicion_excel22)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel22,posicion_excel22,0,5))
            cell = row.createCell(0)
            cell.setCellValue("23")

            row = sheet.createRow(posicion_excel22+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona23 = posicion_excel22+2
            var numero_persona23 = 1
            var posicion_excel23 = posicion_excel22+3

            for (i in 0 until ListaRegistro23.size) {

                row= sheet.createRow(posicion_persona23)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona23.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro23.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro23.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro23.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro23.get(i).Jornada_Recolecion_Tarde)

                posicion_persona23++
                numero_persona23++
                posicion_excel23++
            }

            //24
            row = sheet.createRow(posicion_excel23)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel23,posicion_excel23,0,5))
            cell = row.createCell(0)
            cell.setCellValue("24")

            row = sheet.createRow(posicion_excel23+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona24 = posicion_excel23+2
            var numero_persona24 = 1
            var posicion_excel24 = posicion_excel23+3

            for (i in 0 until ListaRegistro24.size) {

                row= sheet.createRow(posicion_persona24)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona24.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro24.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro24.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro24.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro24.get(i).Jornada_Recolecion_Tarde)

                posicion_persona24++
                numero_persona24++
                posicion_excel24++
            }

            //25
            row = sheet.createRow(posicion_excel24)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel24,posicion_excel24,0,5))
            cell = row.createCell(0)
            cell.setCellValue("25")

            row = sheet.createRow(posicion_excel24+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona25 = posicion_excel24+2
            var numero_persona25 = 1
            var posicion_excel25 = posicion_excel24+3

            for (i in 0 until ListaRegistro25.size) {

                row= sheet.createRow(posicion_persona25)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona25.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro25.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro25.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro25.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro25.get(i).Jornada_Recolecion_Tarde)

                posicion_persona25++
                numero_persona25++
                posicion_excel25++
            }

            //26
            row = sheet.createRow(posicion_excel25)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel25,posicion_excel25,0,5))
            cell = row.createCell(0)
            cell.setCellValue("26")

            row = sheet.createRow(posicion_excel25+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona26 = posicion_excel25+2
            var numero_persona26 = 1
            var posicion_excel26 = posicion_excel25+3

            for (i in 0 until ListaRegistro26.size) {

                row= sheet.createRow(posicion_persona26)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona26.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro26.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro26.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro26.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro26.get(i).Jornada_Recolecion_Tarde)

                posicion_persona26++
                numero_persona26++
                posicion_excel26++
            }

            //27
            row = sheet.createRow(posicion_excel26)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel26,posicion_excel26,0,5))
            cell = row.createCell(0)
            cell.setCellValue("27")

            row = sheet.createRow(posicion_excel26+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona27 = posicion_excel26+2
            var numero_persona27 = 1
            var posicion_excel27 = posicion_excel26+3

            for (i in 0 until ListaRegistro27.size) {

                row= sheet.createRow(posicion_persona27)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona27.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro27.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro27.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro27.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro27.get(i).Jornada_Recolecion_Tarde)

                posicion_persona27++
                numero_persona27++
                posicion_excel27++
            }

            //28
            row = sheet.createRow(posicion_excel27)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel27,posicion_excel27,0,5))
            cell = row.createCell(0)
            cell.setCellValue("28")

            row = sheet.createRow(posicion_excel27+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona28 = posicion_excel27+2
            var numero_persona28 = 1
            var posicion_excel28 = posicion_excel27+3

            for (i in 0 until ListaRegistro28.size) {

                row= sheet.createRow(posicion_persona28)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona28.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro28.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro28.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro28.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro28.get(i).Jornada_Recolecion_Tarde)

                posicion_persona28++
                numero_persona28++
                posicion_excel28++
            }

            //29
            row = sheet.createRow(posicion_excel28)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel28,posicion_excel28,0,5))
            cell = row.createCell(0)
            cell.setCellValue("29")

            row = sheet.createRow(posicion_excel28+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona29 = posicion_excel28+2
            var numero_persona29 = 1
            var posicion_excel29 = posicion_excel28+3

            for (i in 0 until ListaRegistro29.size) {

                row= sheet.createRow(posicion_persona29)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona29.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro29.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro29.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro29.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro29.get(i).Jornada_Recolecion_Tarde)

                posicion_persona29++
                numero_persona29++
                posicion_excel29++
            }

            //30
            row = sheet.createRow(posicion_excel29)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel29,posicion_excel29,0,5))
            cell = row.createCell(0)
            cell.setCellValue("30")

            row = sheet.createRow(posicion_excel29+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona30 = posicion_excel29+2
            var numero_persona30 = 1
            var posicion_excel30 = posicion_excel29+3

            for (i in 0 until ListaRegistro30.size) {

                row= sheet.createRow(posicion_persona22)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona30.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro30.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro30.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro30.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro30.get(i).Jornada_Recolecion_Tarde)

                posicion_persona30++
                numero_persona30++
                posicion_excel30++
            }

            //31
            row = sheet.createRow(posicion_excel30)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel30,posicion_excel30,0,5))
            cell = row.createCell(0)
            cell.setCellValue("31")

            row = sheet.createRow(posicion_excel30+1)
            cell = row.createCell(0)
            cell.setCellValue("#")
            cell = row.createCell(1)
            cell.setCellValue("Cedula")
            cell = row.createCell(2)
            cell.setCellValue("Nombre")
            cell = row.createCell(3)
            cell.setCellValue("Precio LT")
            cell = row.createCell(4)
            cell.setCellValue("M")
            cell = row.createCell(5)
            cell.setCellValue("T")

            var posicion_persona31 = posicion_excel30+2
            var numero_persona31 = 1
            var posicion_excel31 = posicion_excel30+3

            for (i in 0 until ListaRegistro31.size) {

                row= sheet.createRow(posicion_persona31)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona31.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro31.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro31.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro31.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro31.get(i).Jornada_Recolecion_Tarde)

                posicion_persona31++
                numero_persona31++
                posicion_excel31++
            }


            try{
                if (!file.exists()) {
                    file.createNewFile()
                }
                val fileOutputStream: FileOutputStream = FileOutputStream(file)
                hssfWorkbook.write(fileOutputStream)
                if (fileOutputStream != null) {
                    fileOutputStream.flush()
                    fileOutputStream.close()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }

            Toast.makeText(this,"El archivo $FechaActual $Ruta.xls a sido creado en la carpeta Descargas", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}