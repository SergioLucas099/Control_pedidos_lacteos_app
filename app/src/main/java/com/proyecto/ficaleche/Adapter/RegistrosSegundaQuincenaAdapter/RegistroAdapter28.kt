package com.proyecto.ficaleche.Adapter.RegistrosSegundaQuincenaAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.ficaleche.DetallesRegistro
import com.proyecto.ficaleche.Model.RegistrosSegundaQuincenaModel.RegistrosModel28
import com.proyecto.ficaleche.R
import java.util.ArrayList

class RegistroAdapter28 (
    private val RegistroLista: ArrayList<RegistrosModel28>
)
    : RecyclerView.Adapter<RegistroAdapter28.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.registro_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = RegistroLista[position]

        holder.TxtFecha.text = lista.Fecha
        holder.TxtNombre.text = lista.Nombre

        holder.Seleccionar.setOnClickListener {
            val intent = Intent(holder.Seleccionar.context, DetallesRegistro::class.java)
            intent.putExtra("Cedula", lista.Cedula)
            intent.putExtra("Fecha", lista.Fecha)
            intent.putExtra("Jornada_Recolecion_Mañana", lista.Jornada_Recolecion_Mañana)
            intent.putExtra("Jornada_Recolecion_tarde", lista.Jornada_Recolecion_Tarde)
            intent.putExtra("Mes", lista.Mes)
            intent.putExtra("Nombre", lista.Nombre)
            intent.putExtra("Observaciones", lista.Observaciones)
            intent.putExtra("Ruta", lista.Ruta)

            holder.Seleccionar.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return RegistroLista.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val TxtFecha : TextView = itemView.findViewById(R.id.TxtFecha)
        val TxtNombre : TextView = itemView.findViewById(R.id.TxtNombre)
        val Seleccionar : LinearLayout = itemView.findViewById(R.id.Seleccionar)
    }
}