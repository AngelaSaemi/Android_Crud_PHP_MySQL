package com.example.android_crud_php_mysql;


public class Configuracao {

    //Endereços dos scripts para fazer o CRUD
    public static final String URL_ADICIONAR="https://conexaophp.000webhostapp.com/adicionaFuncionario.php";
    public static final String URL_GET_TODOS_FUNCIONARIOS = "https://conexaophp.000webhostapp.com/listaTodosFuncionarios.php";
    public static final String URL_GET_FUNCIONARIO = "https://conexaophp.000webhostapp.com/listaFuncionario.php?id=";
    public static final String URL_ATUALIZAR_FUNCIONARIO = "https://conexaophp.000webhostapp.com/atualizaFuncionario.php";
    public static final String URL_APAGAR_FUNCIONARIO = "https://conexaophp.000webhostapp.com/apagaFuncionario.php?id=";

    //Constantes chave que serão utilizados para enviar dados para os scripts php
    public static final String KEY_FUNC_ID = "id";
    public static final String KEY_FUNC_NOME= "nome";
    public static final String KEY_FUNC_CARGO = "cargo";
    public static final String KEY_FUNC_SALARIO = "salario";

    //Tags JSON
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NOME = "nome";
    public static final String TAG_CARGO = "cargo";
    public static final String TAG_SALARIO = "salario";

    //Id do funcionario que será passado via intent
    public static final String FUNC_ID = "emp_id";
    public static final String FUNC_NOME = "emp_nome";
    public static final String FUNC_CARGO = "emp_cargo";
    public static final String FUNC_SALARIO = "emp_salario";


}
