package com.amazonas.questoes.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonas.questoes.databinding.FragmentQuestaoDetalhadaBinding;
import com.amazonas.questoes.model.Questao;
import com.amazonas.questoes.ui.recyclerview.adapter.QuestaoDetalhadaAdapter;
import com.amazonas.questoes.ui.viewModel.QuestaoViewModel;

import java.util.ArrayList;

public class QuestaoDetalhadaFragment extends Fragment {
    private FragmentQuestaoDetalhadaBinding binding;
    private ViewPager2 viewPagerQuestao;
    private QuestaoDetalhadaAdapter pagerAdapter;
    private ArrayList<Questao> questoes;
    private QuestaoViewModel questaoViewModel;

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
        configuraViewPagerAdapter();
        int posicaoQuestao = QuestaoDetalhadaFragmentArgs.fromBundle(getArguments()).getPosicaoQuestao();
        questaoViewModel.pegaQuestoes().observe(getViewLifecycleOwner(), resultadoQuestoes -> {
            questoes = resultadoQuestoes;
            pagerAdapter.atualiza(questoes);
            viewPagerQuestao.setCurrentItem(posicaoQuestao);
        });
    }

    private void configuraViewPagerAdapter() {
        pagerAdapter = new QuestaoDetalhadaAdapter(questoes, getContext());
        viewPagerQuestao.setAdapter(pagerAdapter);
    }

    private void inicializaComponentes() {
        viewPagerQuestao = binding.viewPagerFragmentoQuestao;
        questoes = new ArrayList<>();
        questaoViewModel = new ViewModelProvider(this).get(QuestaoViewModel.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}