package com.example.android_crud_php_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.AbsSavedState;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ListaFuncionarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText idFunc, nome, cargo,salario;
    private Button btn_atualizar, btn_apagar;
    private String id_func,nome_func,cargo_func,salario_func;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_funcionario);
        Intent intent = getIntent();
        id_func = intent.getStringExtra(Configuracao.FUNC_ID);
        nome_func = intent.getStringExtra(Configuracao.FUNC_NOME);
        cargo_func = intent.getStringExtra(Configuracao.FUNC_CARGO);
        salario_func = intent.getStringExtra(Configuracao.FUNC_SALARIO);

        idFunc = findViewById(R.id.idFunc);
        nome = findViewById(R.id.nome);
        cargo = findViewById(R.id.cargo);
        salario = findViewById(R.id.salario);

        btn_atualizar = findViewById(R.id.atualizar);
        btn_apagar = findViewById(R.id.apagar);

        btn_atualizar.setOnClickListener(this);
        btn_apagar.setOnClickListener(this);

        idFunc.setText(id_func);
        nome.setText(nome_func);
        cargo.setText(cargo_func);
        salario.setText(salario_func);

        getFuncionario();


    }

    private void getFuncionario(){
        class GetFuncionario extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show
                        (ListaFuncionarioActivity.this,"Buscando dados...",
                                "Espere por favor...",false,false);
            }

            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                mostraFuncionario(s);
            }


            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Configuracao.URL_GET_FUNCIONARIO, id_func);
                return s;
            }
        }
        GetFuncionario gf = new GetFuncionario();
        gf.execute();
    }

    private void mostraFuncionario(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Configuracao.TAG_JSON_ARRAY);
            JSONObject objeto = result.getJSONObject(0);
            String no = objeto.getString(Configuracao.TAG_NOME);
            String ca = objeto.getString(Configuracao.TAG_CARGO);
            String sal = objeto.getString(Configuracao.TAG_SALARIO);

            nome.setText(no);
            cargo.setText(ca);
            salario.setText(sal);

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void atualizaFuncionario(){
        final String n = nome.getText().toString().trim();
        final String c = cargo.getText().toString().trim();
        final String s = salario.getText().toString().trim();

        class AtualizaFuncionario extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            protected  void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(ListaFuncionarioActivity.this, "Atualizando...",
                        "Espere por favor...", false,false);
            }

            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ListaFuncionarioActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> map = new HashMap<>();
                map.put(Configuracao.KEY_FUNC_ID,id_func);
                map.put(Configuracao.KEY_FUNC_NOME,n);
                map.put(Configuracao.KEY_FUNC_CARGO,c);
                map.put(Configuracao.KEY_FUNC_SALARIO,s);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Configuracao.URL_ATUALIZAR_FUNCIONARIO, map);

                return s;
            }
        }

        AtualizaFuncionario af = new AtualizaFuncionario();
        af.execute();
    }

 private void apagaFuncionario(){
        class ApagaFuncionario extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(ListaFuncionarioActivity.this, "Apagando...",
                        "Espere por favor...", false,false);
            }

            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ListaFuncionarioActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Configuracao.URL_APAGAR_FUNCIONARIO,id_func);

                return s;
            }
        }
        ApagaFuncionario af = new ApagaFuncionario();
        af.execute();
 }

 private void confirmeApagaFuncionario(){
     AlertDialog.Builder alerta = new AlertDialog.Builder(this);
     alerta.setMessage("Você quer apagar o funcionário?");

     alerta.setPositiveButton("sim", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             apagaFuncionario();
             startActivity(new Intent(ListaFuncionarioActivity.this, ListarTodosFuncionariosActivity.class));
         }
     });

     alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {

         }
     });

     AlertDialog dialog = alerta.create();
     dialog.show();
 }

    @Override
    public void onClick(View v) {
        if(v == btn_atualizar){
            atualizaFuncionario();
        }
        if(v == btn_apagar){
            confirmeApagaFuncionario();
        }


    }
}
