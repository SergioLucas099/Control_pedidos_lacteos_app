package com.proyecto.ficaleche.Adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.proyecto.ficaleche.InicioSesion
import com.proyecto.ficaleche.Model.UsuariosEliminadosModel
import com.proyecto.ficaleche.Model.UsuariosModel
import com.proyecto.ficaleche.R
import java.util.ArrayList

class UsuarioEliminadoAdapter (
    private val RegistroLista: ArrayList<UsuariosEliminadosModel>
        )
    : RecyclerView.Adapter<UsuarioEliminadoAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.usuarios_eliminados_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = RegistroLista[position]

        holder.TxtCedulaUsuarioEliminado.text = lista.Cedula
        holder.TxtNombreUsuarioEliminado.text = lista.Nombre
        holder.TxtRutaUsuarioEliminado.text = lista.Ruta

        val map: MutableMap<String, Any> = HashMap()
        map["Cedula"] = holder.TxtCedulaUsuarioEliminado.text
        map["Nombre"] = holder.TxtNombreUsuarioEliminado.text
        map["Ruta"] = holder.TxtRutaUsuarioEliminado.text

        holder.AgregarUsuarioEliminado.setOnClickListener {

            val builder = AlertDialog.Builder(holder.AgregarUsuarioEliminado.context)
            builder.setTitle("Advertencia")
            builder.setMessage("¿Desea restablecer este usuario?")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                FirebaseDatabase.getInstance().getReference()
                    .child("Usuarios Eliminados")
                    .child(holder.TxtCedulaUsuarioEliminado.text.toString())
                    .removeValue()

                FirebaseDatabase.getInstance().getReference()
                    .child("Usuarios")
                    .child(holder.TxtCedulaUsuarioEliminado.text.toString())
                    .setValue(map).addOnCompleteListener{
                        Toast.makeText(holder.AgregarUsuarioEliminado.context, "Se ha restablecido un usuario", Toast.LENGTH_SHORT).show()
                    }
            })
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(holder.AgregarUsuarioEliminado.context, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            })
            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return RegistroLista.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val TxtCedulaUsuarioEliminado : TextView = itemView.findViewById(R.id.TxtCedulaUsuarioEliminado)
        val TxtNombreUsuarioEliminado : TextView = itemView.findViewById(R.id.TxtNombreUsuarioEliminado)
        val TxtRutaUsuarioEliminado : TextView = itemView.findViewById(R.id.TxtRutaUsuarioEliminado)
        val AgregarUsuarioEliminado : ImageView = itemView.findViewById(R.id.AgregarUsuarioEliminado)
    }
}