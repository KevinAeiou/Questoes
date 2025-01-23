package com.amazonas.questoes.ui.fragment;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amazonas.questoes.R;
import com.amazonas.questoes.databinding.FragmentQuestaoDetalhadaBinding;
import com.amazonas.questoes.model.Comentario;
import com.amazonas.questoes.model.Questao;
import com.google.android.material.snackbar.Snackbar;

public class QuestaoDetalhadaFragment extends Fragment {
    private FragmentQuestaoDetalhadaBinding binding;
    private TextView txtBancaQuestao, txtAnoQuestao, txtDificudadeQuestao, txtDescricaoQuestao;
    private Questao questao;
    private Button alternativa1Questao, alternativa2Questao, alternativa3Questao, alternativa4Questao;
    private Button botaoResponderQuestao;
    private RadioGroup grupoBotaoAlternativaQuestao;
    private String idAlternativa;

    public QuestaoDetalhadaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestaoDetalhadaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializaComponentes();
        recebeQuestao();
        preencheCampos();
        configuraBotoesAlternativas();
        configuraBotaoResponderQuestao();
    }

    private void configuraBotaoResponderQuestao() {
        botaoResponderQuestao.setOnClickListener(v -> {
            if (idAlternativa == null){
                Snackbar.make(binding.getRoot(), "Escolha uma altenativa...", Snackbar.LENGTH_LONG).setAnchorView(botaoResponderQuestao).show();
                return;
            }
            verificaGabarito();
            configuraComponentes();
        });
    }

    private void configuraComponentes() {
        botaoResponderQuestao.setEnabled(false);
        if (questao.getComentarios() == null || questao.getComentarios().isEmpty()) return;
        ArrayAdapter<Comentario> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, questao.getComentarios());
        binding.listaComentariosQuestao.setAdapter(adapter);
    }

    private void recebeQuestao() {
        questao = QuestaoDetalhadaFragmentArgs.fromBundle(getArguments()).getQuestao();
    }

    private void preencheCampos() {
        txtBancaQuestao.setText(questao.getBanca());
        txtAnoQuestao.setText(String.valueOf(questao.getAno()));
        txtDificudadeQuestao.setText(questao.getDificudade());
        txtDescricaoQuestao.setText(questao.getDescricao());
    }

    private void inicializaComponentes() {
        idAlternativa = null;
        txtBancaQuestao = binding.txtBancaFragmentoQuestao;
        txtAnoQuestao = binding.txtAnoFragmentoQuestao;
        txtDificudadeQuestao = binding.txtDificudadeFragmentoQuestao;
        txtDescricaoQuestao = binding.txtDescricaoFragmentoQuestao;
        alternativa1Questao = binding.botaoFragmentoQuestao1;
        alternativa2Questao = binding.botaoFragmentoQuestao2;
        alternativa3Questao = binding.botaoFragmentoQuestao3;
        alternativa4Questao = binding.botaoFragmentoQuestao4;
        grupoBotaoAlternativaQuestao = binding.grupoBotoesFragmentoQuestao;
        botaoResponderQuestao = binding.botaoResponderQuestao;
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

    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    private void verificaGabarito() {
        RadioButton alternativaSelecionada = grupoBotaoAlternativaQuestao.findViewById(grupoBotaoAlternativaQuestao.getCheckedRadioButtonId());
        if (questao.acertou(idAlternativa)) {
            alternativaSelecionada.setBackground(requireContext().getDrawable(R.drawable.botao_alternativa_certa));
            Snackbar.make(binding.getRoot(), "Acertou!", Snackbar.LENGTH_LONG).setAnchorView(botaoResponderQuestao).show();
            return;
        }
        alternativaSelecionada.setBackground(requireContext().getDrawable(R.drawable.botao_alternativa_errada));
        Snackbar.make(binding.getRoot(), "Errou!", Snackbar.LENGTH_LONG).setAnchorView(botaoResponderQuestao).show();
        String gabarito = questao.getGabarito();
        if (alternativa1Questao.getTag().equals(gabarito)) {
            alternativa1Questao.setBackground(requireContext().getDrawable(R.drawable.botao_alternativa_certa));
            return;
        }
        if (alternativa2Questao.getTag().equals(gabarito)) {
            alternativa2Questao.setBackground(requireContext().getDrawable(R.drawable.botao_alternativa_certa));
            return;
        }
        if (alternativa3Questao.getTag().equals(gabarito)) {
            alternativa3Questao.setBackground(requireContext().getDrawable(R.drawable.botao_alternativa_certa));
            return;
        }
        if (alternativa4Questao.getTag().equals(gabarito)) {
            alternativa4Questao.setBackground(requireContext().getDrawable(R.drawable.botao_alternativa_certa));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}