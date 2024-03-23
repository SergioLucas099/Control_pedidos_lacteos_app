package com.proyecto.ficaleche

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
 * Use the [Agregar.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var lista : ArrayList<UsuariosModel>
private lateinit var reference: DatabaseReference
private lateinit var RecyclerUsuariosAgregados: RecyclerView
lateinit var adapter: UsuariosAdapter

class Agregar : Fragment() {
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
        return inflater.inflate(R.layout.fragment_agregar, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Agregar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Agregar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val BotonAtrasVerUsuariosAgregar = view.findViewById<ImageView>(R.id.BotonAtrasVerUsuariosAgregar)
        val BuscadorUsuariosAgregados = view.findViewById<SearchView>(R.id.BuscadorUsuariosAgregados)
        RecyclerUsuariosAgregados = view.findViewById(R.id.RecyclerUsuariosAgregados)

        BotonAtrasVerUsuariosAgregar.setOnClickListener {
            val intent = Intent(context, AccionesAdministrador::class.java)
            startActivity(intent)
        }

        RecyclerUsuariosAgregados.layoutManager = LinearLayoutManager(context)
        RecyclerUsuariosAgregados.setHasFixedSize(true)

        lista = arrayListOf<UsuariosModel>()

        RecyclerUsuariosAgregados.visibility = View.GONE

        reference = FirebaseDatabase.getInstance().getReference("Usuarios")

        reference.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()
                if (snapshot.exists()){
                    for (Snap in snapshot.children){
                        val data = Snap.getValue(UsuariosModel::class.java)
                        lista.add(data!!)
                    }
                    val Adapter = UsuariosAdapter(lista)
                    RecyclerUsuariosAgregados.adapter = Adapter
                    RecyclerUsuariosAgregados.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        BuscadorUsuariosAgregados.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                buscar(s)
                return true
            }
        })
    }

    private fun buscar(s: String){
        val milista = ArrayList<UsuariosModel>()
        for (obj in lista) {
            if (obj.Cedula?.lowercase()?.contains(s.lowercase()) == true){
                milista.add(obj)
            }
        }
        val adapter = UsuariosAdapter(milista)
        RecyclerUsuariosAgregados.setAdapter(adapter)
    }
}