package com.amazonas.questoes.ui.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonas.questoes.R;
import com.amazonas.questoes.model.Questao;
import com.amazonas.questoes.ui.recyclerview.adapter.listener.OnItemClickListenerQuestao;

import java.util.ArrayList;

public class ListaQuestoesAdapter extends RecyclerView.Adapter<ListaQuestoesAdapter.QuestaoViewHolder> {
    private OnItemClickListenerQuestao onItemClickListener;
    private ArrayList<Questao> questoes;
    private final Context context;

    public ListaQuestoesAdapter(Context context, ArrayList<Questao> questoes) {
        this.context = context;
        this.questoes = questoes;
    }

    public void setOnItemClickListener(OnItemClickListenerQuestao onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void atualiza(ArrayList<Questao> questoes) {
        this.questoes = questoes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_questao, parent, false);
        return new QuestaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestaoViewHolder holder, int position) {
        Questao questao = questoes.get(position);
        holder.vincula(questao);
    }

    @Override
    public int getItemCount() {
        if (questoes == null) return 0;
        return questoes.size();
    }

    public class QuestaoViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtBanca;
        private final TextView txtAno;
        private final TextView txtDificudade;
        private final TextView txtDescricao;
        private Questao questao;
        public QuestaoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBanca = itemView.findViewById(R.id.txtBancaItemQuestao);
            txtAno = itemView.findViewById(R.id.txtAnoItemQuestao);
            txtDificudade = itemView.findViewById(R.id.txtDificudadeItemQuestao);
            txtDescricao = itemView.findViewById(R.id.txtDescricaoItemQuestao);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(questao));
        }

        public void vincula(Questao questao) {
            this.questao = questao;
            preencheCampos(questao);
        }

        private void preencheCampos(Questao questao) {
            txtBanca.setText(questao.getBanca());
            txtAno.setText(String.valueOf(questao.getAno()));
            txtDificudade.setText(questao.getDificudade());
            txtDescricao.setText(questao.getDescricao());
        }
    }
}
