package com.example.android_crud_php_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nome, cargo, salario;
    private Button btn_adicionar, btn_listar;
    private String n,c,s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.nome);
        cargo = findViewById(R.id.cargo);
        salario = findViewById(R.id.salario);

        btn_adicionar = findViewById(R.id.adicionar);
        btn_listar = findViewById(R.id.listar);

        btn_adicionar.setOnClickListener(this);
        btn_listar.setOnClickListener(this);
    }

    private void adicionarFuncionario(){

        n = nome.getText().toString().trim();
        c = cargo.getText().toString().trim();
        s = salario.getText().toString().trim();

        class AdicionarFuncionario extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show
                        (MainActivity.this, "Adicionando..", "Espere por favor",
                                false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> parametros = new HashMap<>();
                parametros.put(Configuracao.KEY_FUNC_NOME, n);
                parametros.put(Configuracao.KEY_FUNC_CARGO, c);
                parametros.put(Configuracao.KEY_FUNC_SALARIO, s);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuracao.URL_ADICIONAR, parametros);

                return res;
            }

            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG ).show();
            }
        }

        AdicionarFuncionario ae = new AdicionarFuncionario();
        ae.execute();

    }

    @Override
    public void onClick(View v) {
        if(v == btn_adicionar){
            adicionarFuncionario();
        }
        if(v == btn_listar){
            startActivity(new Intent(this, ListarTodosFuncionariosActivity.class));
        }
    }
}
