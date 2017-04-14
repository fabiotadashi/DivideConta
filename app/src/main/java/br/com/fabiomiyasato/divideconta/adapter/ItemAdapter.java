package br.com.fabiomiyasato.divideconta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.com.fabiomiyasato.divideconta.R;
import br.com.fabiomiyasato.divideconta.listener.OnItemClickListener;
import br.com.fabiomiyasato.divideconta.model.Item;


public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Item> data = Collections.emptyList();

    private OnItemClickListener clickListener;

    public ItemAdapter(Context context, List<Item> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gasto, parent, false);
        GastoItemHolder holder = new GastoItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GastoItemHolder myHolder = (GastoItemHolder) holder;
        Item current = data.get(position);
        myHolder.tvDescricao.setText(current.getDescricao());
        myHolder.tvValor.setText("R$"  + current.getValor());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public Item getItem(int position) {
        return data.get(position);
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }



    private class GastoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvDescricao;
        TextView tvValor;
        TextView tvTipo;


        private GastoItemHolder(View itemView) {
            super(itemView);
            tvDescricao = (TextView) itemView.findViewById(R.id.tvDescricao);
            tvValor = (TextView) itemView.findViewById(R.id.tvValor);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }

    }
}