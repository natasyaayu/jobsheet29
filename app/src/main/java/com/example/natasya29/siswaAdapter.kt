package com.example.natasya29

import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.net.URL
import java.util.concurrent.Executors

class siswaAdapter(private val data: ArrayList<Siswa>?): RecyclerView.Adapter<siswaAdapter.siswaViewHolder>() {

    class siswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nama = itemView.findViewById<TextView>(R.id.namaSiswa)
        private val nis = itemView.findViewById<TextView>(R.id.nisSiswa)
        private val jk = itemView.findViewById<TextView>(R.id.jkSiswa)
        private val kelas = itemView.findViewById<TextView>(R.id.kelasSiswa)
        private val editbtn = itemView.findViewById<TextView>(R.id.btnEdit)
        private val hapusbtn = itemView.findViewById<TextView>(R.id.btnHapus)
        fun bind(get: Siswa) {
            nama.text = get.nama
            nis.text = get.nis
            jk.text = get.jk
            kelas.text = get.kelas

            editbtn.setOnClickListener() {
                val intent = Intent(itemView.context, edit::class.java)
                intent.putExtra("nama", get.nama)
                intent.putExtra("nis", get.nis)
                intent.putExtra("jk", get.jk)
                intent.putExtra("kelas", get.kelas)
                itemView.context.startActivity(intent)
            }

            hapusbtn.setOnClickListener() {
                val dialogBuilder = AlertDialog.Builder(itemView.context)
                dialogBuilder.setTitle("Hapus Data")
                dialogBuilder.setMessage("Hapus Siswa "+get.nama)
                dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener {_, _ ->
                    val executor = Executors.newSingleThreadExecutor()
                    val handler = Handler(Looper.getMainLooper())

                    executor.execute {
                        val url = URL( "http://192.168.147.20/server_siswa.php?nis" + get.nis).readText()

                        handler.post {
                            Toast.makeText(itemView.context, url, Toast.LENGTH_SHORT).show()

                        }
                    }
                })
                dialogBuilder.setNegativeButton("Cancl", DialogInterface.OnClickListener { _, _ ->

                })
                dialogBuilder.create()
                dialogBuilder.show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): siswaAdapter.siswaViewHolder {
        return siswaAdapter.siswaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_list_siswa, parent, false)
        )
    }

    override fun onBindViewHolder(holder: siswaAdapter.siswaViewHolder, position: Int) {
        holder.bind(data?.get(position) ?: Siswa("", "", "",  ""))
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0

    }
}