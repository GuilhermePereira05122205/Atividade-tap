package com.example.myapplication.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(){

    var nome = remember {
        mutableStateOf("")
    }
    var telefone = remember {
        mutableStateOf("")
    }

    val db = Firebase.firestore

    var documentos: List<QueryDocumentSnapshot> = listOf()

    documentos = db.collection("users")
        .get()
        .result.toList()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Nome: ")
        TextField(value = nome.value, onValueChange = {nome.value = it})

        Text("Telefone: ")
        TextField(value = telefone.value, onValueChange = {telefone.value = it})

        Button(onClick = { Cadastrar(nome.value, telefone.value) }) {
            Text(text = "Cadastrar")
        }
    }

    LazyColumn {
       items(documentos){ documentos2 ->
        Text(text = "Nome: ${documentos2.id}")
           Text(text = "Nome: ${documentos2.id}")
       }
    }


}

fun Cadastrar(nome: String, telefone: String){
    val user = hashMapOf(
        "nome" to nome,
        "telefone" to telefone
    )

    val db = Firebase.firestore
    db.collection("users").add(user).addOnSuccessListener {
        Log.w("success", "Sucesso ao cadastar o usuario")
    }.addOnFailureListener {
        Log.w("error", "error ao cadastar o usuario")
    }
}