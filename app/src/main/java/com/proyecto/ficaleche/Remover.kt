package com.proyecto.ficaleche

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.ficaleche.Adapter.UsuarioEliminadoAdapter
import com.proyecto.ficaleche.Model.UsuariosEliminadosModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Remover.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var listaE : ArrayList<UsuariosEliminadosModel>
private lateinit var referenceE: DatabaseReference
private lateinit var RecyclerUsuariosEliminados: RecyclerView
lateinit var adapterE: UsuarioEliminadoAdapter

class Remover : Fragment() {
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
        return inflater.inflate(R.layout.fragment_remover, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Remover.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Remover().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val BotonAtrasVerUsuariosRemovidos = view.findViewById<ImageView>(R.id.BotonAtrasVerUsuariosRemovidos)
        val BuscadorUsuariosEliminados = view.findViewById<SearchView>(R.id.BuscadorUsuariosEliminados)
        RecyclerUsuariosEliminados = view.findViewById(R.id.RecyclerUsuariosEliminados)

        BotonAtrasVerUsuariosRemovidos.setOnClickListener {
            val intent = Intent(context, AccionesAdministrador::class.java)
            startActivity(intent)
        }

        RecyclerUsuariosEliminados.layoutManager = LinearLayoutManager(context)
        RecyclerUsuariosEliminados.setHasFixedSize(true)

        listaE = arrayListOf<UsuariosEliminadosModel>()

        RecyclerUsuariosEliminados.visibility = View.GONE

        referenceE = FirebaseDatabase.getInstance().getReference("Usuarios Eliminados")

        referenceE.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaE.clear()
                if (snapshot.exists()){
                    for (Snap in snapshot.children){
                        val data = Snap.getValue(UsuariosEliminadosModel::class.java)
                        listaE.add(data!!)
                    }
                    val adapterE = UsuarioEliminadoAdapter(listaE)
                    RecyclerUsuariosEliminados.adapter = adapterE
                    RecyclerUsuariosEliminados.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        BuscadorUsuariosEliminados.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        val milista = ArrayList<UsuariosEliminadosModel>()
        for (obj in listaE) {
            if (obj.Cedula?.lowercase()?.contains(s.lowercase()) == true){
                milista.add(obj)
            }
        }
        val adapter = UsuarioEliminadoAdapter(milista)
        RecyclerUsuariosEliminados.setAdapter(adapter)
    }
}