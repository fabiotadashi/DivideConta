package br.com.fabiomiyasato.divideconta;

import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.fabiomiyasato.divideconta.dao.ItemDAO;
import br.com.fabiomiyasato.divideconta.model.Item;


public class NovoItemActivity extends AppCompatActivity {

    public final static int CODE_NOVO_ITEM = 1002;

    private TextInputLayout tilDescricao;
    private EditText etValor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        tilDescricao = (TextInputLayout) findViewById(R.id.tilDescricao);
        etValor = (EditText) findViewById(R.id.etValor);
    }

    public void cadastrar(View v) {
        ItemDAO itemDAO = new ItemDAO(this);
        Item item = new Item();
        item.setDescricao(tilDescricao.getEditText().getText().toString());
        item.setValor(Double.valueOf(etValor.getText().toString()));

        itemDAO.add(item);

        retornaParaTelaAnterior();
    }

    //retorna para tela de lista de torcedores
    public void retornaParaTelaAnterior() {
        Intent intentMessage=new Intent();
        setResult(CODE_NOVO_ITEM, intentMessage);
        finish();
    }
}
