package com.proyecto.ficaleche

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.ficaleche.Adapter.UsuariosAdapter
import com.proyecto.ficaleche.Model.UsuariosModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AgregarUsuario.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var EditTextIngresoCedulaUsuario: EditText
private lateinit var EditTextIngresoNombreUsuario: EditText
private lateinit var TextViewIngresoRutaUsuario: TextView
private lateinit var reference: DatabaseReference

class AgregarUsuario : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_usuario, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AgregarUsuario.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AgregarUsuario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EditTextIngresoCedulaUsuario = view.findViewById(R.id.EditTextIngresoCedulaUsuario)
        EditTextIngresoNombreUsuario = view.findViewById(R.id.EditTextIngresoNombreUsuario)
        TextViewIngresoRutaUsuario = view.findViewById(R.id.TextViewIngresoRutaUsuario)

        reference = FirebaseDatabase.getInstance().getReference()

        val BotonAtras = view.findViewById<ImageView>(R.id.BotonAtrasAgregarUser)
        val items = listOf("Ruta 1","Ruta 2","Ruta 3")
        val adapter = context?.let { ArrayAdapter(it, R.layout.list_item,items) }
        val autoComplete: AutoCompleteTextView = view.findViewById(R.id.auto_complete_usuario)
        val BotonRegistro = view.findViewById<Button>(R.id.BotonRegistrarUsuario)

        BotonAtras.setOnClickListener {
            val intent = Intent(context, AccionesAdministrador::class.java)
            startActivity(intent)
        }

        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            val itemSelected = parent.getItemAtPosition(position)
            TextViewIngresoRutaUsuario.setText(itemSelected.toString())
        }

        BotonRegistro.setOnClickListener {
            SubirInformacion()
            val intent = Intent(context, ControlUsuarios::class.java)
            startActivity(intent)
        }
    }

    private fun SubirInformacion()
    {
        val Cedula = EditTextIngresoCedulaUsuario.text.toString()
        val Nombre = EditTextIngresoNombreUsuario.text.toString()
        val Ruta = TextViewIngresoRutaUsuario.text.toString()

        if(!Cedula.isEmpty())
        {
            if(!Nombre.isEmpty())
            {
                if(!Ruta.isEmpty())
                {
                    val map: MutableMap<String, Any> = HashMap()
                    map["Cedula"] = Cedula
                    map["Nombre"] = Nombre
                    map["Ruta"] = Ruta

                    reference.child("Usuarios").child(Cedula).setValue(map).addOnCompleteListener {
                        Toast.makeText(context, "Se ha creado un nuevo usuario", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(context, "Error al subir la información", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}