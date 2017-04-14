package br.com.fabiomiyasato.divideconta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.com.fabiomiyasato.divideconta.dao.ItemDAO;
import br.com.fabiomiyasato.divideconta.model.Item;

public class FechaContaActivity extends AppCompatActivity {

    TextView tvValorTotal;
    EditText etQuantidade;
    TextView tvResumo;
    Button btCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_conta);

        tvValorTotal = (TextView) findViewById(R.id.tvValorTotal);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        etQuantidade.setText("1");
        tvResumo = (TextView) findViewById(R.id.tvResumo);
        btCalcular = (Button) findViewById(R.id.btCalcular);

        calcularTotal();

        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularTotal();
            }
        });
    }

    public void calcularTotal() {
        ItemDAO itemDAO = new ItemDAO(this);
        StringBuilder sb= new StringBuilder();
        List<Item> itemList = itemDAO.getAll();

        double total = 0;
        for (Item g: itemList) {
            total+= g.getValor();
        }

        tvValorTotal.setText("R$"+total);

        int qtdPessoa = Integer.parseInt(etQuantidade.getText().toString());
        if(qtdPessoa == 0){
            qtdPessoa = 1;
        }
        tvResumo.setText("Divide value per person is R$"+total/qtdPessoa);
    }
}
