package com.example.android_crud_php_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListarTodosFuncionariosActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos_funcionarios);

        listView = findViewById(R.id.lista);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void listarFuncionarios() {
        JSONObject objeto = null;
        ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
        try {
            objeto = new JSONObject(JSON_STRING);
            JSONArray result = objeto.getJSONArray(Configuracao.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Configuracao.TAG_ID);
                String nome = jo.getString(Configuracao.TAG_NOME);
                String cargo = jo.getString(Configuracao.TAG_CARGO);
                String salario = jo.getString(Configuracao.TAG_SALARIO);

                HashMap<String, String> funcionarios = new HashMap<>();
                funcionarios.put(Configuracao.TAG_ID, id);
                funcionarios.put(Configuracao.TAG_NOME, nome);
                funcionarios.put(Configuracao.TAG_CARGO, cargo);
                funcionarios.put(Configuracao.TAG_SALARIO, salario);
                lista.add(funcionarios);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adaptador = new SimpleAdapter(ListarTodosFuncionariosActivity.this,
                lista, R.layout.lista, new String[]{Configuracao.TAG_ID, Configuracao.TAG_NOME,
                Configuracao.TAG_CARGO, Configuracao.TAG_SALARIO},
                new int[]{R.id.id, R.id.nome, R.id.cargo, R.id.salario});
        listView.setAdapter(adaptador);
    }

    private void getJSON(){
        class GetJson extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(ListarTodosFuncionariosActivity.this,
                        "Buscando dados...", "Espere por favor...", false, false);

            }

            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                listarFuncionarios();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuracao.URL_GET_TODOS_FUNCIONARIOS);

                return s;
            }
        }
        GetJson gj = new GetJson();
        gj.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(ListarTodosFuncionariosActivity.this,ListaFuncionarioActivity.class);
        HashMap<String,String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
        String empId = map.get(Configuracao.KEY_FUNC_ID);
        String empNome = map.get(Configuracao.KEY_FUNC_NOME);
        String empCargo = map.get(Configuracao.KEY_FUNC_CARGO);
        String empSalario = map.get(Configuracao.KEY_FUNC_SALARIO);

        intent.putExtra(Configuracao.FUNC_ID,empId);
        intent.putExtra(Configuracao.FUNC_NOME, empNome);
        intent.putExtra(Configuracao.FUNC_CARGO,empCargo);
        intent.putExtra(Configuracao.FUNC_SALARIO, empSalario);
        startActivity(intent);

    }
}
