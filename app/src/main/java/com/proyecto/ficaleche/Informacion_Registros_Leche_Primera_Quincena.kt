package com.proyecto.ficaleche

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter01
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter02
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter03
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter04
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter05
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter06
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter07
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter08
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter09
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter10
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter11
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter12
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter13
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter14
import com.proyecto.ficaleche.Adapter.RegistrosPrimeraQuincenaAdapter.RegistroAdapter15
import com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter.RegistroAdapter16
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel01
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel02
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel03
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel04
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel05
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel06
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel07
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel08
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel09
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel10
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel11
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel12
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel13
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel14
import com.proyecto.ficaleche.Model.RegistrosPrimeraQuincenaModel.RegistrosModel15
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel16
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

class Informacion_Registros_Leche_Primera_Quincena : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_registros_leche_primera_quincena)

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
        val IndicardorMesPrimeraQuincena = findViewById<TextView>(R.id.IndicardorMesPrimeraQuincena)
        val IndicadorQuincena1 = findViewById<TextView>(R.id.IndicadorQuincena1)
        val IndicadorRutaPrimeraQuincena = findViewById<TextView>(R.id.IndicadorRutaPrimeraQuincena)

        IndicardorMesPrimeraQuincena.setText(IndicadorMes)
        IndicadorQuincena1.setText(InfoDia)
        IndicadorRutaPrimeraQuincena.setText(Ruta)

        //Dia 01
        val Recyclerdatos01 = findViewById<RecyclerView>(R.id.Recyclerdatos01)
        val AvisoSinInfoDia01 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia01)
        val ListaRegistro01 : ArrayList<RegistrosModel01>

        Recyclerdatos01.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos01.setHasFixedSize(true)
        ListaRegistro01 = arrayListOf<RegistrosModel01>()
        Recyclerdatos01.visibility = View.GONE
        AvisoSinInfoDia01.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("01").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro01.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel01::class.java)
                            ListaRegistro01.add(data!!)
                        }
                        val Adapter = RegistroAdapter01(ListaRegistro01)
                        Recyclerdatos01.adapter = Adapter
                        Recyclerdatos01.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia01.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 02
        val Recyclerdatos02 = findViewById<RecyclerView>(R.id.Recyclerdatos02)
        val AvisoSinInfoDia02 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia02)
        val ListaRegistro02 : ArrayList<RegistrosModel02>

        Recyclerdatos02.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos02.setHasFixedSize(true)
        ListaRegistro02 = arrayListOf<RegistrosModel02>()
        Recyclerdatos02.visibility = View.GONE
        AvisoSinInfoDia02.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("02").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro02.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel02::class.java)
                            ListaRegistro02.add(data!!)
                        }
                        val Adapter = RegistroAdapter02(ListaRegistro02)
                        Recyclerdatos02.adapter = Adapter
                        Recyclerdatos02.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia02.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 03
        val Recyclerdatos03 = findViewById<RecyclerView>(R.id.Recyclerdatos03)
        val AvisoSinInfoDia03 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia03)
        val ListaRegistro03 : ArrayList<RegistrosModel03>

        Recyclerdatos03.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos03.setHasFixedSize(true)
        ListaRegistro03 = arrayListOf<RegistrosModel03>()
        Recyclerdatos03.visibility = View.GONE
        AvisoSinInfoDia03.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("03").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro03.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel03::class.java)
                            ListaRegistro03.add(data!!)
                        }
                        val Adapter = RegistroAdapter03(ListaRegistro03)
                        Recyclerdatos03.adapter = Adapter
                        Recyclerdatos03.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia03.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 04
        val Recyclerdatos04 = findViewById<RecyclerView>(R.id.Recyclerdatos04)
        val AvisoSinInfoDia04 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia04)
        val ListaRegistro04 : ArrayList<RegistrosModel04>

        Recyclerdatos04.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos04.setHasFixedSize(true)
        ListaRegistro04 = arrayListOf<RegistrosModel04>()
        Recyclerdatos04.visibility = View.GONE
        AvisoSinInfoDia04.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("04").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro04.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel04::class.java)
                            ListaRegistro04.add(data!!)
                        }
                        val Adapter = RegistroAdapter04(ListaRegistro04)
                        Recyclerdatos04.adapter = Adapter
                        Recyclerdatos04.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia04.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 05
        val Recyclerdatos05 = findViewById<RecyclerView>(R.id.Recyclerdatos05)
        val AvisoSinInfoDia05 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia05)
        val ListaRegistro05 : ArrayList<RegistrosModel05>

        Recyclerdatos05.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos05.setHasFixedSize(true)
        ListaRegistro05 = arrayListOf<RegistrosModel05>()
        Recyclerdatos05.visibility = View.GONE
        AvisoSinInfoDia05.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("05").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro05.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel05::class.java)
                            ListaRegistro05.add(data!!)
                        }
                        val Adapter = RegistroAdapter05(ListaRegistro05)
                        Recyclerdatos05.adapter = Adapter
                        Recyclerdatos05.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia05.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 06
        val Recyclerdatos06 = findViewById<RecyclerView>(R.id.Recyclerdatos06)
        val AvisoSinInfoDia06 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia06)
        val ListaRegistro06 : ArrayList<RegistrosModel06>

        Recyclerdatos06.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos06.setHasFixedSize(true)
        ListaRegistro06 = arrayListOf<RegistrosModel06>()
        Recyclerdatos06.visibility = View.GONE
        AvisoSinInfoDia06.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("06").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro06.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel06::class.java)
                            ListaRegistro06.add(data!!)
                        }
                        val Adapter = RegistroAdapter06(ListaRegistro06)
                        Recyclerdatos06.adapter = Adapter
                        Recyclerdatos06.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia06.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 07
        val Recyclerdatos07 = findViewById<RecyclerView>(R.id.Recyclerdatos07)
        val AvisoSinInfoDia07 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia07)
        val ListaRegistro07 : ArrayList<RegistrosModel07>

        Recyclerdatos07.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos07.setHasFixedSize(true)
        ListaRegistro07 = arrayListOf<RegistrosModel07>()
        Recyclerdatos07.visibility = View.GONE
        AvisoSinInfoDia07.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("07").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro07.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel07::class.java)
                            ListaRegistro07.add(data!!)
                        }
                        val Adapter = RegistroAdapter07(ListaRegistro07)
                        Recyclerdatos07.adapter = Adapter
                        Recyclerdatos07.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia07.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 08
        val Recyclerdatos08 = findViewById<RecyclerView>(R.id.Recyclerdatos08)
        val AvisoSinInfoDia08 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia08)
        val ListaRegistro08 : ArrayList<RegistrosModel08>

        Recyclerdatos08.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos08.setHasFixedSize(true)
        ListaRegistro08 = arrayListOf<RegistrosModel08>()
        Recyclerdatos08.visibility = View.GONE
        AvisoSinInfoDia08.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("08").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro08.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel08::class.java)
                            ListaRegistro08.add(data!!)
                        }
                        val Adapter = RegistroAdapter08(ListaRegistro08)
                        Recyclerdatos08.adapter = Adapter
                        Recyclerdatos08.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia08.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 09
        val Recyclerdatos09 = findViewById<RecyclerView>(R.id.Recyclerdatos09)
        val AvisoSinInfoDia09 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia09)
        val ListaRegistro09 : ArrayList<RegistrosModel09>

        Recyclerdatos09.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos09.setHasFixedSize(true)
        ListaRegistro09 = arrayListOf<RegistrosModel09>()
        Recyclerdatos09.visibility = View.GONE
        AvisoSinInfoDia09.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("09").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro09.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel09::class.java)
                            ListaRegistro09.add(data!!)
                        }
                        val Adapter = RegistroAdapter09(ListaRegistro09)
                        Recyclerdatos09.adapter = Adapter
                        Recyclerdatos09.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia09.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 10
        val Recyclerdatos10 = findViewById<RecyclerView>(R.id.Recyclerdatos10)
        val AvisoSinInfoDia10 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia10)
        val ListaRegistro10 : ArrayList<RegistrosModel10>

        Recyclerdatos10.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos10.setHasFixedSize(true)
        ListaRegistro10 = arrayListOf<RegistrosModel10>()
        Recyclerdatos10.visibility = View.GONE
        AvisoSinInfoDia10.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("10").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro10.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel10::class.java)
                            ListaRegistro10.add(data!!)
                        }
                        val Adapter = RegistroAdapter10(ListaRegistro10)
                        Recyclerdatos10.adapter = Adapter
                        Recyclerdatos10.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia10.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 11
        val Recyclerdatos11 = findViewById<RecyclerView>(R.id.Recyclerdatos11)
        val AvisoSinInfoDia11 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia11)
        val ListaRegistro11 : ArrayList<RegistrosModel11>

        Recyclerdatos11.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos11.setHasFixedSize(true)
        ListaRegistro11 = arrayListOf<RegistrosModel11>()
        Recyclerdatos11.visibility = View.GONE
        AvisoSinInfoDia11.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("11").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro11.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel11::class.java)
                            ListaRegistro11.add(data!!)
                        }
                        val Adapter = RegistroAdapter11(ListaRegistro11)
                        Recyclerdatos11.adapter = Adapter
                        Recyclerdatos11.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia11.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 12
        val Recyclerdatos12 = findViewById<RecyclerView>(R.id.Recyclerdatos12)
        val AvisoSinInfoDia12 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia12)
        val ListaRegistro12 : ArrayList<RegistrosModel12>

        Recyclerdatos12.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos12.setHasFixedSize(true)
        ListaRegistro12 = arrayListOf<RegistrosModel12>()
        Recyclerdatos12.visibility = View.GONE
        AvisoSinInfoDia12.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("12").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro12.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel12::class.java)
                            ListaRegistro12.add(data!!)
                        }
                        val Adapter = RegistroAdapter12(ListaRegistro12)
                        Recyclerdatos12.adapter = Adapter
                        Recyclerdatos12.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia12.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 13
        val Recyclerdatos13 = findViewById<RecyclerView>(R.id.Recyclerdatos13)
        val AvisoSinInfoDia13 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia13)
        val ListaRegistro13 : ArrayList<RegistrosModel13>

        Recyclerdatos13.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos13.setHasFixedSize(true)
        ListaRegistro13 = arrayListOf<RegistrosModel13>()
        Recyclerdatos13.visibility = View.GONE
        AvisoSinInfoDia13.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("13").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro13.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel13::class.java)
                            ListaRegistro13.add(data!!)
                        }
                        val Adapter = RegistroAdapter13(ListaRegistro13)
                        Recyclerdatos13.adapter = Adapter
                        Recyclerdatos13.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia13.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 14
        val Recyclerdatos14 = findViewById<RecyclerView>(R.id.Recyclerdatos14)
        val AvisoSinInfoDia14 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia14)
        val ListaRegistro14 : ArrayList<RegistrosModel14>

        Recyclerdatos14.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos14.setHasFixedSize(true)
        ListaRegistro14 = arrayListOf<RegistrosModel14>()
        Recyclerdatos14.visibility = View.GONE
        AvisoSinInfoDia14.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("14").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro14.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel14::class.java)
                            ListaRegistro14.add(data!!)
                        }
                        val Adapter = RegistroAdapter14(ListaRegistro14)
                        Recyclerdatos14.adapter = Adapter
                        Recyclerdatos14.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia14.visibility = View.VISIBLE
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //Dia 15
        val Recyclerdatos15 = findViewById<RecyclerView>(R.id.Recyclerdatos15)
        val AvisoSinInfoDia15 = findViewById<LinearLayout>(R.id.AvisoSinInfoDia15)
        val ListaRegistro15 : ArrayList<RegistrosModel15>

        Recyclerdatos15.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        Recyclerdatos15.setHasFixedSize(true)
        ListaRegistro15 = arrayListOf<RegistrosModel15>()
        Recyclerdatos15.visibility = View.GONE
        AvisoSinInfoDia15.visibility = View.INVISIBLE

        FirebaseDatabase.getInstance().getReference("Registro_Proveedor")
            .child(InfoMes.toString())
            .child(InfoDia.toString())
            .child(Ruta.toString())
            .child("15").addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ListaRegistro15.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(RegistrosModel15::class.java)
                            ListaRegistro15.add(data!!)
                        }
                        val Adapter = RegistroAdapter15(ListaRegistro15)
                        Recyclerdatos15.adapter = Adapter
                        Recyclerdatos15.visibility = View.VISIBLE
                    }else{
                        AvisoSinInfoDia15.visibility = View.VISIBLE
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
            //01
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
            cell.setCellValue("01")

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

            var posicion_persona01 = 5
            var numero_persona01 = 1
            var posicion_excel01 = 6

            for (i in 0 until ListaRegistro01.size) {

                row= sheet.createRow(posicion_persona01)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona01.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro01.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro01.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro01.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro01.get(i).Jornada_Recolecion_Tarde)

                posicion_persona01++
                numero_persona01++
                posicion_excel01++
            }

            row = sheet.createRow(posicion_persona01)
            sheet.addMergedRegion(CellRangeAddress(posicion_persona01,posicion_persona01,0,5))
            cell = row.createCell(0)
            cell.setCellValue("02")

            row = sheet.createRow(posicion_persona01+1)
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

            var posicion_persona02 = posicion_persona01+2
            var numero_persona02 = 1
            var posicion_excel02 = posicion_persona01+3

            for (i in 0 until ListaRegistro02.size) {

                row= sheet.createRow(posicion_persona02)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona02.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro02.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro02.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro02.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro02.get(i).Jornada_Recolecion_Tarde)

                posicion_persona02++
                numero_persona02++
                posicion_excel02++
            }

            row = sheet.createRow(posicion_persona02)
            sheet.addMergedRegion(CellRangeAddress(posicion_persona02,posicion_persona02,0,5))
            cell = row.createCell(0)
            cell.setCellValue("03")

            row = sheet.createRow(posicion_persona02+1)
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

            var posicion_persona03 = posicion_persona02+2
            var numero_persona03 = 1
            var posicion_excel03 = posicion_persona02+3

            for (i in 0 until ListaRegistro03.size) {

                row= sheet.createRow(posicion_persona03)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona03.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro03.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro03.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro03.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro03.get(i).Jornada_Recolecion_Tarde)

                posicion_persona03++
                numero_persona03++
                posicion_excel03++
            }

            row = sheet.createRow(posicion_persona03)
            sheet.addMergedRegion(CellRangeAddress(posicion_persona03,posicion_persona03,0,5))
            cell = row.createCell(0)
            cell.setCellValue("04")

            row = sheet.createRow(posicion_persona03+1)
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

            var posicion_persona04 = posicion_persona03+2
            var numero_persona04 = 1
            var posicion_excel04 = posicion_persona03+3

            for (i in 0 until ListaRegistro04.size) {

                row= sheet.createRow(posicion_persona04)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona04.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro04.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro04.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro04.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro04.get(i).Jornada_Recolecion_Tarde)

                posicion_persona04++
                numero_persona04++
                posicion_excel04++
            }

            row = sheet.createRow(posicion_excel04)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel04,posicion_excel04,0,5))
            cell = row.createCell(0)
            cell.setCellValue("05")

            row = sheet.createRow(posicion_excel04+1)
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

            var posicion_persona05 = posicion_excel04+2
            var numero_persona05 = 1
            var posicion_excel05 = posicion_excel04+3

            for (i in 0 until ListaRegistro05.size) {

                row= sheet.createRow(posicion_persona05)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona05.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro05.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro05.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro05.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro05.get(i).Jornada_Recolecion_Tarde)

                posicion_persona05++
                numero_persona05++
                posicion_excel05++
            }

            row = sheet.createRow(posicion_excel05)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel05,posicion_excel05,0,5))
            cell = row.createCell(0)
            cell.setCellValue("06")

            row = sheet.createRow(posicion_excel05+1)
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

            var posicion_persona06 = posicion_excel05+2
            var numero_persona06 = 1
            var posicion_excel06 = posicion_excel05+3

            for (i in 0 until ListaRegistro06.size) {

                row= sheet.createRow(posicion_persona06)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona06.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro06.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro06.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro06.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro06.get(i).Jornada_Recolecion_Tarde)

                posicion_persona06++
                numero_persona06++
                posicion_excel06++
            }

            //07
            row = sheet.createRow(posicion_excel06)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel06,posicion_excel06,0,5))
            cell = row.createCell(0)
            cell.setCellValue("07")

            row = sheet.createRow(posicion_excel06+1)
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

            var posicion_persona07 = posicion_excel06+2
            var numero_persona07 = 1
            var posicion_excel07 = posicion_excel06+3

            for (i in 0 until ListaRegistro07.size) {

                row= sheet.createRow(posicion_persona07)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona07.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro07.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro07.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro07.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro07.get(i).Jornada_Recolecion_Tarde)

                posicion_persona07++
                numero_persona07++
                posicion_excel07++
            }

            //08
            row = sheet.createRow(posicion_excel07)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel07,posicion_excel07,0,5))
            cell = row.createCell(0)
            cell.setCellValue("23")

            row = sheet.createRow(posicion_excel07+1)
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

            var posicion_persona08 = posicion_excel07+2
            var numero_persona08 = 1
            var posicion_excel08 = posicion_excel07+3

            for (i in 0 until ListaRegistro08.size) {

                row= sheet.createRow(posicion_persona08)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona08.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro08.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro08.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro08.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro08.get(i).Jornada_Recolecion_Tarde)

                posicion_persona08++
                numero_persona08++
                posicion_excel08++
            }

            //09
            row = sheet.createRow(posicion_persona08)
            sheet.addMergedRegion(CellRangeAddress(posicion_persona08,posicion_persona08,0,5))
            cell = row.createCell(0)
            cell.setCellValue("24")

            row = sheet.createRow(posicion_persona08+1)
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

            var posicion_persona09 = posicion_persona08+2
            var numero_persona09 = 1
            var posicion_excel09 = posicion_persona08+3

            for (i in 0 until ListaRegistro09.size) {

                row= sheet.createRow(posicion_persona09)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona09.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro09.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro09.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro09.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro09.get(i).Jornada_Recolecion_Tarde)

                posicion_persona09++
                numero_persona09++
                posicion_excel09++
            }

            //10
            row = sheet.createRow(posicion_persona09)
            sheet.addMergedRegion(CellRangeAddress(posicion_persona09,posicion_persona09,0,5))
            cell = row.createCell(0)
            cell.setCellValue("10")

            row = sheet.createRow(posicion_persona09+1)
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

            var posicion_persona10 = posicion_persona09+2
            var numero_persona10 = 1
            var posicion_excel10 = posicion_persona09+3

            for (i in 0 until ListaRegistro10.size) {

                row= sheet.createRow(posicion_persona10)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona10.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro10.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro10.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro10.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro10.get(i).Jornada_Recolecion_Tarde)

                posicion_persona10++
                numero_persona10++
                posicion_excel10++
            }

            //11
            row = sheet.createRow(posicion_excel10)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel10,posicion_excel10,0,5))
            cell = row.createCell(0)
            cell.setCellValue("11")

            row = sheet.createRow(posicion_excel10+1)
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

            var posicion_persona11 = posicion_excel10+2
            var numero_persona11 = 1
            var posicion_excel11 = posicion_excel10+3

            for (i in 0 until ListaRegistro11.size) {

                row= sheet.createRow(posicion_persona11)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona11.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro11.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro11.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro11.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro11.get(i).Jornada_Recolecion_Tarde)

                posicion_persona11++
                numero_persona11++
                posicion_excel11++
            }

            //12
            row = sheet.createRow(posicion_persona11)
            sheet.addMergedRegion(CellRangeAddress(posicion_persona11,posicion_persona11,0,5))
            cell = row.createCell(0)
            cell.setCellValue("12")

            row = sheet.createRow(posicion_persona11+1)
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

            var posicion_persona12 = posicion_persona11+2
            var numero_persona12 = 1
            var posicion_excel12 = posicion_persona11+3

            for (i in 0 until ListaRegistro12.size) {

                row= sheet.createRow(posicion_persona12)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona12.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro12.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro12.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro12.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro12.get(i).Jornada_Recolecion_Tarde)

                posicion_persona12++
                numero_persona12++
                posicion_excel12++
            }

            //13
            row = sheet.createRow(posicion_excel12)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel12,posicion_excel12,0,5))
            cell = row.createCell(0)
            cell.setCellValue("13")

            row = sheet.createRow(posicion_excel12+1)
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

            var posicion_persona13 = posicion_excel12+2
            var numero_persona13 = 1
            var posicion_excel13 = posicion_excel12+3

            for (i in 0 until ListaRegistro13.size) {

                row= sheet.createRow(posicion_persona13)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona13.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro13.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro13.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro13.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro13.get(i).Jornada_Recolecion_Tarde)

                posicion_persona13++
                numero_persona13++
                posicion_excel13++
            }

            //14
            row = sheet.createRow(posicion_excel13)
            sheet.addMergedRegion(CellRangeAddress(posicion_excel13,posicion_excel13,0,5))
            cell = row.createCell(0)
            cell.setCellValue("14")

            row = sheet.createRow(posicion_excel13+1)
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

            var posicion_persona14 = posicion_excel13+2
            var numero_persona14 = 1
            var posicion_excel14 = posicion_excel13+3

            for (i in 0 until ListaRegistro14.size) {

                row= sheet.createRow(posicion_persona14)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona14.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro14.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro14.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro14.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro14.get(i).Jornada_Recolecion_Tarde)

                posicion_persona14++
                numero_persona14++
                posicion_excel14++
            }

            //15
            row = sheet.createRow(posicion_persona14)
            sheet.addMergedRegion(CellRangeAddress(posicion_persona14,posicion_persona14,0,5))
            cell = row.createCell(0)
            cell.setCellValue("15")

            row = sheet.createRow(posicion_persona14+1)
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

            var posicion_persona15 = posicion_persona14+2
            var numero_persona15 = 1
            var posicion_excel15 = posicion_persona14+3

            for (i in 0 until ListaRegistro15.size) {

                row= sheet.createRow(posicion_persona15)
                cell = row.createCell(0)
                cell.setCellValue(numero_persona15.toString())

                cell = row.createCell(1)
                cell.setCellValue(ListaRegistro15.get(i).Cedula)

                cell = row.createCell(2)
                cell.setCellValue(ListaRegistro15.get(i).Nombre)

                cell = row.createCell(3)
                cell.setCellValue("")

                cell = row.createCell(4)
                cell.setCellValue(ListaRegistro15.get(i).Jornada_Recolecion_Mañana)

                cell = row.createCell(5)
                cell.setCellValue(ListaRegistro15.get(i).Jornada_Recolecion_Tarde)

                posicion_persona15++
                numero_persona15++
                posicion_excel15++
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