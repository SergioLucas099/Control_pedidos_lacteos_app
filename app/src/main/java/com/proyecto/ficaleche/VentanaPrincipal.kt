package com.proyecto.ficaleche

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.proyecto.ficaleche.includes.MiToolbar
import java.security.AccessController.getContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class VentanaPrincipal : AppCompatActivity() {

    private lateinit var reference: DatabaseReference

    private lateinit var EditTextIngresoNombre: EditText
    private lateinit var TextIngresoRuta: AutoCompleteTextView
    private lateinit var EditTextIngresoCedula: EditText
    private lateinit var EditTextCantidadLitrosMañana: EditText
    private lateinit var EditTextCantidadLitrosTarde: EditText
    private lateinit var EditTextObservaciones: EditText
    private lateinit var TextViewCedula: TextView
    private lateinit var AvisoCedula: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_principal)

        MiToolbar.show(this, "Orlando Pulido y Yenny Yaya", false)

        val items = listOf("Ruta 1","Ruta 2","Ruta 3")
        val Boton = findViewById<Button>(R.id.BotonCrearAdministrador)
        val SeleccionarCedula = findViewById<LinearLayout>(R.id.BuscarCedula)
        val adapter = ArrayAdapter(this, R.layout.list_item,items)

        EditTextIngresoNombre = findViewById(R.id.EditTextIngresoNombre)
        TextIngresoRuta = findViewById(R.id.TextIngresoRuta)
        EditTextIngresoCedula = findViewById(R.id.EditTextIngresoCedula)
        EditTextCantidadLitrosMañana = findViewById(R.id.EditTextCantidadLitrosMañana)
        EditTextCantidadLitrosTarde = findViewById(R.id.EditTextCantidadLitrosTarde)
        EditTextObservaciones = findViewById(R.id.EditTextObservaciones)
        TextViewCedula = findViewById(R.id.TextViewCedula)
        AvisoCedula = findViewById(R.id.AvisoCedula)

        reference = FirebaseDatabase.getInstance().getReference()

        TextIngresoRuta.setAdapter(adapter)

        TextIngresoRuta.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            val itemSelected = parent.getItemAtPosition(position)
            Toast.makeText(this,"Item: $itemSelected",Toast.LENGTH_SHORT).show()
        }

        val con = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = con.activeNetworkInfo

        SeleccionarCedula.setOnClickListener {
            if(networkInfo!=null && networkInfo.isConnected){
                CargarDatos()
            }else{
                Toast.makeText(this,"No hay conexión a internet\nVerifique el acceso a internet e intente nuevamente",Toast.LENGTH_LONG).show()
            }
        }

        Boton.setOnClickListener {
            if(networkInfo!=null && networkInfo.isConnected){
                SubirInformacion()
            }else{
                Toast.makeText(this,"No hay conexión a internet\nVerifique el acceso a internet e intente nuevamente",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.InicioSesion) {
            //auth.signOut()
            val intent = Intent(this@VentanaPrincipal, InicioSesion::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val preferences: SharedPreferences
        preferences = getSharedPreferences("typeUser", MODE_PRIVATE)
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            val user = preferences.getString("user", "")
            if (user.equals("Administrador")){
                val intent = Intent(this, AccionesAdministrador::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }

    private fun CargarDatos(){
        TextViewCedula.setText(EditTextIngresoCedula.text.toString())
        if(!TextViewCedula.text.toString().equals("")){
            reference.child("Usuarios").child(TextViewCedula.text.toString()).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val Nombre = snapshot.child("Nombre").getValue().toString()
                        val Ruta = snapshot.child("Ruta").value.toString()
                        EditTextIngresoNombre.setText(Nombre)
                        TextIngresoRuta.setText(Ruta)
                        AvisoCedula.setText("")
                    }else{
                        AvisoCedula.setText("La cedula registrada no se encuentra en la base de datos")
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }else{
            Toast.makeText(this,"Ingrese una cedula para continuar",Toast.LENGTH_LONG).show()
        }
    }

    private fun SubirInformacion(){
        val Id = reference.push().key!!
        val Cedula = TextViewCedula.text.toString()
        val Nombre = EditTextIngresoNombre.text.toString()
        val Ruta = TextIngresoRuta.text.toString()
        val Jornada_Recolecion_Mañana = EditTextCantidadLitrosMañana.text.toString()
        val Jornada_Recolecion_Tarde = EditTextCantidadLitrosTarde.text.toString()
        val Observaciones = EditTextObservaciones.text.toString()
        val Formato = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val FormatoDia = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd", Locale.ENGLISH)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val FormatoMes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("MM", Locale.ENGLISH)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val Fecha = LocalDateTime.now().format(Formato)
        val Dia = LocalDateTime.now().format(FormatoDia)
        val Mes = LocalDateTime.now().format(FormatoMes)

        if(Cedula != "" && Nombre != "" && Ruta != "" && Jornada_Recolecion_Mañana != "" || Jornada_Recolecion_Tarde != ""){
            if(Jornada_Recolecion_Mañana != ""){
                val map: MutableMap<String, Any> = HashMap()
                map["Cedula"] = Cedula
                map["Nombre"] = Nombre
                map["Ruta"] = Ruta
                map["Fecha"] = Fecha.toString()
                map["Observaciones"] = Observaciones
                map["Jornada_Recolecion_Mañana"] = Jornada_Recolecion_Mañana
                map["Jornada_Recolecion_Tarde"] = Jornada_Recolecion_Tarde

                if(Mes.toString()=="01"){
                    map["Mes"] = "Enero"
                    //Borrar registro mes pasado
                    reference
                        .child("Registro_Proveedor")
                        .child("11")
                        .removeValue()
                }
                if(Mes.toString()=="02"){
                    map["Mes"] = "Febrero"
                    reference
                        .child("Registro_Proveedor")
                        .child("12")
                        .removeValue()
                }
                if(Mes.toString()=="03"){
                    map["Mes"] = "Marzo"
                    reference
                        .child("Registro_Proveedor")
                        .child("01")
                        .removeValue()
                }
                if(Mes.toString()=="04"){
                    map["Mes"] = "Abril"
                    reference
                        .child("Registro_Proveedor")
                        .child("02")
                        .removeValue()
                }
                if(Mes.toString()=="05"){
                    map["Mes"] = "Mayo"
                    reference
                        .child("Registro_Proveedor")
                        .child("03")
                        .removeValue()
                }
                if(Mes.toString()=="06"){
                    map["Mes"] = "Junio"
                    reference
                        .child("Registro_Proveedor")
                        .child("04")
                        .removeValue()
                }
                if(Mes.toString()=="07"){
                    map["Mes"] = "Julio"
                    reference
                        .child("Registro_Proveedor")
                        .child("05")
                        .removeValue()
                }
                if(Mes.toString()=="08"){
                    map["Mes"] = "Agosto"
                    reference
                        .child("Registro_Proveedor")
                        .child("06")
                        .removeValue()
                }
                if(Mes.toString()=="09"){
                    map["Mes"] = "Septiembre"
                    reference
                        .child("Registro_Proveedor")
                        .child("07")
                        .removeValue()
                }
                if(Mes.toString()=="10"){
                    map["Mes"] = "Octubre"
                    reference
                        .child("Registro_Proveedor")
                        .child("08")
                        .removeValue()
                }
                if(Mes.toString()=="11"){
                    map["Mes"] = "Noviembre"
                    reference
                        .child("Registro_Proveedor")
                        .child("09")
                        .removeValue()
                }
                if(Mes.toString()=="12"){
                    map["Mes"] = "Diciembre"
                    reference
                        .child("Registro_Proveedor")
                        .child("10")
                        .removeValue()
                }

                if(Integer.parseInt(Dia)<=15){
                    reference.child("Registro_Proveedor")
                        .child(Mes)
                        .child("Primera Quincena")
                        .child(Ruta)
                        .child(Dia)
                        .child(Nombre)
                        .setValue(map).addOnCompleteListener {
                            Toast.makeText(this, "Se ha creado un nuevo registro de leche", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this, "$Fecha", Toast.LENGTH_SHORT).show()
                            TextViewCedula.setText("")
                            EditTextIngresoNombre.setText("")
                            TextIngresoRuta.setText("")
                            EditTextObservaciones.setText("")
                            EditTextCantidadLitrosMañana.setText("")
                            EditTextCantidadLitrosTarde.setText("")
                        }.addOnFailureListener{
                            Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    reference.child("Registro_Proveedor")
                        .child(Mes)
                        .child("Segunda Quincena")
                        .child(Ruta)
                        .child(Dia)
                        .child(Nombre)
                        .setValue(map).addOnCompleteListener {
                            Toast.makeText(this, "Se ha creado un nuevo registro de leche", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this, "$Fecha", Toast.LENGTH_SHORT).show()
                            EditTextIngresoCedula.setText("")
                            TextViewCedula.setText("")
                            EditTextIngresoNombre.setText("")
                            TextIngresoRuta.setText("")
                            EditTextObservaciones.setText("")
                            EditTextCantidadLitrosMañana.setText("")
                            EditTextCantidadLitrosTarde.setText("")
                        }.addOnFailureListener{
                            Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                        }
                }
            }else{
                val map1: MutableMap<String, Any> = HashMap()
                map1["Cedula"] = Cedula
                map1["Nombre"] = Nombre
                map1["Ruta"] = Ruta
                map1["Fecha"] = Fecha.toString()
                map1["Observaciones"] = Observaciones
                map1["Jornada_Recolecion_Tarde"] = Jornada_Recolecion_Tarde

                if(Mes.toString()=="01"){
                    map1["Mes"] = "Enero"
                    //Borrar registro mes pasado
                    reference
                        .child("Registro_Proveedor")
                        .child("11")
                        .removeValue()
                }
                if(Mes.toString()=="02"){
                    map1["Mes"] = "Febrero"
                    reference
                        .child("Registro_Proveedor")
                        .child("12")
                        .removeValue()
                }
                if(Mes.toString()=="03"){
                    map1["Mes"] = "Marzo"
                    reference
                        .child("Registro_Proveedor")
                        .child("01")
                        .removeValue()
                }
                if(Mes.toString()=="04"){
                    map1["Mes"] = "Abril"
                    reference
                        .child("Registro_Proveedor")
                        .child("02")
                        .removeValue()
                }
                if(Mes.toString()=="05"){
                    map1["Mes"] = "Mayo"
                    reference
                        .child("Registro_Proveedor")
                        .child("03")
                        .removeValue()
                }
                if(Mes.toString()=="06"){
                    map1["Mes"] = "Junio"
                    reference
                        .child("Registro_Proveedor")
                        .child("04")
                        .removeValue()
                }
                if(Mes.toString()=="07"){
                    map1["Mes"] = "Julio"
                    reference
                        .child("Registro_Proveedor")
                        .child("05")
                        .removeValue()
                }
                if(Mes.toString()=="08"){
                    map1["Mes"] = "Agosto"
                    reference
                        .child("Registro_Proveedor")
                        .child("06")
                        .removeValue()
                }
                if(Mes.toString()=="09"){
                    map1["Mes"] = "Septiembre"
                    reference
                        .child("Registro_Proveedor")
                        .child("07")
                        .removeValue()
                }
                if(Mes.toString()=="10"){
                    map1["Mes"] = "Octubre"
                    reference
                        .child("Registro_Proveedor")
                        .child("08")
                        .removeValue()
                }
                if(Mes.toString()=="11"){
                    map1["Mes"] = "Noviembre"
                    reference
                        .child("Registro_Proveedor")
                        .child("09")
                        .removeValue()
                }
                if(Mes.toString()=="12"){
                    map1["Mes"] = "Diciembre"
                    reference
                        .child("Registro_Proveedor")
                        .child("10")
                        .removeValue()
                }

                if(Integer.parseInt(Dia)<=15){
                    reference.child("Registro_Proveedor")
                        .child(Mes)
                        .child("Primera Quincena")
                        .child(Ruta)
                        .child(Dia)
                        .child(Nombre)
                        .updateChildren(map1).addOnCompleteListener {
                            Toast.makeText(this, "Se ha creado un nuevo registro de leche", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this, "$Fecha", Toast.LENGTH_SHORT).show()
                            TextViewCedula.setText("")
                            EditTextIngresoNombre.setText("")
                            TextIngresoRuta.setText("")
                            EditTextObservaciones.setText("")
                            EditTextCantidadLitrosMañana.setText("")
                            EditTextCantidadLitrosTarde.setText("")
                        }.addOnFailureListener{
                            Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    reference.child("Registro_Proveedor")
                        .child(Mes)
                        .child("Segunda Quincena")
                        .child(Ruta)
                        .child(Dia)
                        .child(Nombre)
                        .updateChildren(map1).addOnCompleteListener {
                            Toast.makeText(this, "Se ha creado un nuevo registro de leche", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this, "$Fecha", Toast.LENGTH_SHORT).show()
                            EditTextIngresoCedula.setText("")
                            TextViewCedula.setText("")
                            EditTextIngresoNombre.setText("")
                            TextIngresoRuta.setText("")
                            EditTextObservaciones.setText("")
                            EditTextCantidadLitrosMañana.setText("")
                            EditTextCantidadLitrosTarde.setText("")
                        }.addOnFailureListener{
                            Toast.makeText(this, "Error al subir la información", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }else{
            Toast.makeText(this, "Ingrese los datos requeridos para continuar", Toast.LENGTH_SHORT).show()
        }
    }
}