package com.proyecto.ficaleche.Adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.proyecto.ficaleche.Model.UsuariosModel
import com.proyecto.ficaleche.R
import java.util.ArrayList

class UsuariosAdapter (
    private val RegistroLista: ArrayList<UsuariosModel>
)
    : RecyclerView.Adapter<UsuariosAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.usuarios_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = RegistroLista[position]

        holder.TxtCedulaUsuario.text = lista.Cedula
        holder.TxtNombreUsuario.text = lista.Nombre
        holder.TxtRutaUsuario.text = lista.Ruta

        val map: MutableMap<String, Any> = HashMap()
        map["Cedula"] = holder.TxtCedulaUsuario.text
        map["Nombre"] = holder.TxtNombreUsuario.text
        map["Ruta"] = holder.TxtRutaUsuario.text

        holder.EliminarUsuario.setOnClickListener {

            val builder = AlertDialog.Builder(holder.EliminarUsuario.context)
            builder.setTitle("Advertencia")
            builder.setMessage("¿Desea eliminar este usuario?")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                FirebaseDatabase.getInstance().getReference()
                    .child("Usuarios")
                    .child(holder.TxtCedulaUsuario.text.toString())
                    .removeValue()

                FirebaseDatabase.getInstance().getReference()
                    .child("Usuarios Eliminados")
                    .child(holder.TxtCedulaUsuario.text.toString())
                    .setValue(map).addOnCompleteListener{
                        Toast.makeText(holder.EliminarUsuario.context, "Se ha eliminado un usuario", Toast.LENGTH_SHORT).show()
                    }
            })
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(holder.EliminarUsuario.context, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            })
            builder.show()
        }

    }

    override fun getItemCount(): Int {
        return RegistroLista.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val TxtCedulaUsuario : TextView = itemView.findViewById(R.id.TxtCedulaUsuario)
        val TxtNombreUsuario : TextView = itemView.findViewById(R.id.TxtNombreUsuario)
        val TxtRutaUsuario : TextView = itemView.findViewById(R.id.TxtRutaUsuario)
        val EliminarUsuario : ImageView = itemView.findViewById(R.id.EliminarUsuario)
    }
}