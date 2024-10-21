package com.example.myapplication.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.myapplication.Greeting
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@SuppressLint("UnrememberedMutableState")
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

    var documentos =  mutableStateListOf<HashMap<String, String>>()

    db.collection("users").get().addOnSuccessListener {
        result ->
        for(document in result){
            val documento = hashMapOf(
                "nome" to document.data.get("nome").toString(),
                "telefone" to document.data.get("telefone").toString()
            )

            documentos.add(documento)
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Nome: ", modifier = Modifier.padding(10.dp), fontSize = TextUnit(value = 20.0F, TextUnitType.Sp))
        TextField(value = nome.value, onValueChange = {nome.value = it}, modifier = Modifier.fillMaxWidth())

        Text("Telefone: ", modifier = Modifier.padding(10.dp), fontSize = TextUnit(value = 20.0F, TextUnitType.Sp))
        TextField(value = telefone.value, onValueChange = {telefone.value = it}, modifier = Modifier.fillMaxWidth())

        Button(onClick = { Cadastrar(nome.value, telefone.value) }, modifier = Modifier.padding(20.dp)) {
            Text(text = "Cadastrar")
        }

        Spacer(modifier = Modifier.size(20.dp))

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Column(modifier = Modifier.weight(0.5f)) {
                    Text("Nome", fontWeight = FontWeight.Bold)
                }
                Column(modifier = Modifier.weight(0.5f)) {
                    Text("Telefone", fontWeight = FontWeight.Bold)
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(documentos) {
                        documento ->
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column (modifier = Modifier.weight(0.5f)) {
                            Text(documento.get("nome") ?: "")
                        }
                        Column (modifier = Modifier.weight(0.5f)) {
                            Text(documento.get("telefone") ?: "")
                        }
                    }
                }
            }

        }
    }

}

fun Cadastrar(nome: String, telefone: String){
    var user = hashMapOf(
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    var lista = mutableListOf<String>()
    lista.add("ola")
    lista.add("ola")
    MyApplicationTheme {
        Column(
            modifier = Modifier.padding(20.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {

                Column (modifier = Modifier.weight(0.5f)) {
                    Text("Nome", fontWeight = FontWeight.Bold)
                }

                Column (modifier = Modifier.weight(0.5f)) {
                    Text("Telefone", fontWeight = FontWeight.Bold)
                }

            }

            Spacer(modifier = Modifier.size(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier =  Modifier.weight(0.5f)) {
                    Text("ola")
                }
                Column(modifier =  Modifier.weight(0.5f)) {
                    Text("ola")
                }
            }
        }
    }
}