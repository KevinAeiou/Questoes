package com.amazonas.questoes.ui.recyclerview.adapter;

import static android.view.View.GONE;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonas.questoes.R;
import com.amazonas.questoes.model.Comentario;
import com.amazonas.questoes.model.Questao;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class QuestaoDetalhadaAdapter extends RecyclerView.Adapter<QuestaoDetalhadaAdapter.ViewHolder> {
    private ArrayList<Questao> questoes;
    private static Context context;
    public QuestaoDetalhadaAdapter(ArrayList<Questao> questoes, Context context) {
        this.questoes = questoes;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_questao_detalhada, parent, false);
        return new ViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Questao questao = questoes.get(position);
        holder.vincula(questao);
    }

    @Override
    public int getItemCount() {
        if (questoes == null) return 0;
        return questoes.size();
    }

    public void atualiza(ArrayList<Questao> questoes) {
        this.questoes = questoes;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBanca;
        private TextView txtAno;
        private TextView txtDificudade;
        private TextView txtDescricao;
        private RadioButton alternativa1Questao, alternativa2Questao, alternativa3Questao, alternativa4Questao;
        private Button botaoResponderQuestao;
        private RadioGroup grupoBotaoAlternativaQuestao;
        private ListView listaComentariosQuestao;
        private String idAlternativa = null;
        private Questao questao;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inicializaComponetes(itemView);
            configuraGrupoBotoes();
            verificaBotaoResponder();
        }

        private void verificaBotaoResponder() {
            botaoResponderQuestao.setOnClickListener(view -> {
                if (idAlternativa == null) {
                    Snackbar.make(view, "Selecione uma alternativa para continuar...", Snackbar.LENGTH_LONG).setAnchorView(botaoResponderQuestao).show();
                    return;
                }
                verificaGabarito(view);
                configuraComponentes();
            });
        }

        private void configuraGrupoBotoes() {
            grupoBotaoAlternativaQuestao.setOnCheckedChangeListener((group, checkedId) -> idAlternativa = group.findViewById(checkedId).getTag().toString());
        }

        private void inicializaComponetes(@NonNull View itemView) {
            txtBanca = itemView.findViewById(R.id.txtBancaItemQuestaoDetalhada);
            txtAno = itemView.findViewById(R.id.txtAnoItemQuestaoDetalhada);
            txtDificudade = itemView.findViewById(R.id.txtDificudadeItemQuestaoDetalhada);
            txtDescricao = itemView.findViewById(R.id.txtDescricaoItemQuestaoDetalhada);
            alternativa1Questao = itemView.findViewById(R.id.botao1ItemQuestaoDetalhada);
            alternativa2Questao = itemView.findViewById(R.id.botao2ItemQuestaoDetalhada);
            alternativa3Questao = itemView.findViewById(R.id.botao3ItemQuestaoDetalhada);
            alternativa4Questao = itemView.findViewById(R.id.botao4ItemQuestaoDetalhada);
            botaoResponderQuestao = itemView.findViewById(R.id.botaoResponderItemQuestaoDetalhada);
            grupoBotaoAlternativaQuestao = itemView.findViewById(R.id.grupoBotoesItemQuestaoDetalhada);
            listaComentariosQuestao = itemView.findViewById(R.id.listaComentariosQuestaoDetalhada);
        }

        @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
        private void verificaGabarito(View view) {
            RadioButton alternativaSelecionada = grupoBotaoAlternativaQuestao.findViewById(grupoBotaoAlternativaQuestao.getCheckedRadioButtonId());
            if (questao.acertou(idAlternativa)) {
                alternativaSelecionada.setBackground(context.getDrawable(R.drawable.botao_alternativa_certa));
                Snackbar.make(view, "Acertou!", Snackbar.LENGTH_LONG).setAnchorView(botaoResponderQuestao).show();
                return;
            }
            alternativaSelecionada.setBackground(context.getDrawable(R.drawable.botao_alternativa_errada));
            Snackbar.make(view, "Errou!", Snackbar.LENGTH_LONG).setAnchorView(botaoResponderQuestao).show();
            String gabarito = questao.getGabarito();
            if (alternativa1Questao.getTag().equals(gabarito)) {
                alternativa1Questao.setBackground(context.getDrawable(R.drawable.botao_alternativa_certa));
                return;
            }
            if (alternativa2Questao.getTag().equals(gabarito)) {
                alternativa2Questao.setBackground(context.getDrawable(R.drawable.botao_alternativa_certa));
                return;
            }
            if (alternativa3Questao.getTag().equals(gabarito)) {
                alternativa3Questao.setBackground(context.getDrawable(R.drawable.botao_alternativa_certa));
                return;
            }
            if (alternativa4Questao.getTag().equals(gabarito)) {
                alternativa4Questao.setBackground(context.getDrawable(R.drawable.botao_alternativa_certa));
            }

        }

        private void configuraComponentes() {
            botaoResponderQuestao.setEnabled(false);
            if (questao.getComentarios() == null || questao.getComentarios().isEmpty()) return;
            ArrayAdapter<Comentario> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, questao.getComentarios());
            listaComentariosQuestao.setAdapter(adapter);
        }

        public void vincula(Questao questao) {
            this.questao = questao;
            preencheCampos();
            configuraBotoesAlternativas();
        }

        private void preencheCampos() {
            txtBanca.setText(questao.getBanca());
            txtAno.setText(String.valueOf(questao.getAno()));
            txtDificudade.setText(questao.getDificudade());
            txtDescricao.setText(questao.getDescricao());
        }


        private void configuraBotoesAlternativas() {
            grupoBotaoAlternativaQuestao.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton botao = group.findViewById(checkedId);
                idAlternativa = botao.getTag().toString();
            });
            if (questao.getAlternativas().size() == 2) {
                alternativa1Questao.setText(questao.getAlternativas().get(0).getDescricao());
                alternativa1Questao.setTag(questao.getAlternativas().get(0).getId());
                alternativa2Questao.setText(questao.getAlternativas().get(1).getDescricao());
                alternativa2Questao.setTag(questao.getAlternativas().get(1).getId());
                alternativa3Questao.setVisibility(GONE);
                alternativa4Questao.setVisibility(GONE);
                return;
            }
            if (questao.getAlternativas().size() == 4) {
                alternativa1Questao.setText(questao.getAlternativas().get(0).getDescricao());
                alternativa1Questao.setTag(questao.getAlternativas().get(0).getId());
                alternativa2Questao.setText(questao.getAlternativas().get(1).getDescricao());
                alternativa2Questao.setTag(questao.getAlternativas().get(1).getId());
                alternativa3Questao.setText(questao.getAlternativas().get(2).getDescricao());
                alternativa3Questao.setTag(questao.getAlternativas().get(2).getId());
                alternativa4Questao.setText(questao.getAlternativas().get(3).getDescricao());
                alternativa4Questao.setTag(questao.getAlternativas().get(3).getId());
            }
        }
    }
}
