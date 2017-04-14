package br.com.fabiomiyasato.divideconta;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.fabiomiyasato.divideconta.dao.ItemDAO;
import br.com.fabiomiyasato.divideconta.model.Item;

public class DetalheActivity extends AppCompatActivity {

    public final static int CODE_ATUALIZAR_ITEM = 1003;
    public final static int CODE_DELETE_ITEM = 1004;

    private int itemId = 0;

    private TextInputLayout tilDescricao;
    private EditText etValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        tilDescricao = (TextInputLayout) findViewById(R.id.tilEditDescricao);
        etValor = (EditText) findViewById(R.id.etEditValor);


        ItemDAO itemDAO = new ItemDAO(this);
        if(getIntent() != null) {
            itemId = getIntent().getIntExtra("id",0);
            Item gasto = itemDAO.getBy(itemId);
            tilDescricao.getEditText().setText(gasto.getDescricao());
            etValor.setText(String.valueOf(gasto.getValor()));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDelete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletar();
            }
        });

    }

    public void atualizar(View v) {
        ItemDAO itemDAO = new ItemDAO(this);
        Item item = new Item();
        item.setId(itemId);
        item.setDescricao(tilDescricao.getEditText().getText().toString());
        item.setValor(Double.valueOf(etValor.getText().toString()));

        itemDAO.atualizar(item);

        retornaParaTelaAnterior(CODE_ATUALIZAR_ITEM);
    }


    public void deletar() {
        ItemDAO itemDAO = new ItemDAO(this);
        itemDAO.delete(itemId);

        retornaParaTelaAnterior(CODE_DELETE_ITEM);
    }

    //retorna para tela de lista de torcedores
    public void retornaParaTelaAnterior(int code) {
        Intent intentMessage=new Intent();
        setResult(code, intentMessage);
        finish();
    }
}
