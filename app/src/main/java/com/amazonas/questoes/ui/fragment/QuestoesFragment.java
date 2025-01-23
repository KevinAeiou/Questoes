package com.amazonas.questoes.ui.fragment;

import static android.view.View.GONE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.amazonas.questoes.databinding.FragmentQuestoesBinding;
import com.amazonas.questoes.model.Questao;
import com.amazonas.questoes.ui.fragment.QuestoesFragmentDirections.ActionQuestoesFragmentToQuestaoDetalhadaFragment;
import com.amazonas.questoes.ui.recyclerview.ListaQuestoesAdapter;
import com.amazonas.questoes.ui.viewModel.QuestaoViewModel;

import java.util.ArrayList;

public class QuestoesFragment extends Fragment {
    private FragmentQuestoesBinding binding;
    private ListaQuestoesAdapter adapterQuestoes;
    private RecyclerView recyclerQuestoes;
    private ArrayList<Questao> questoes;
    private SwipeRefreshLayout refreshQuestoes;
    private ProgressBar indicadorQuestoes;
    private QuestaoViewModel questaoViewModel;

    public QuestoesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestoesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializaComponentes();
        configuraRecyclerQuestoes();
        configuraRefreshQuestoes();
    }

    private void configuraRefreshQuestoes() {
        refreshQuestoes.setOnRefreshListener(this::pegaQuestoes);
    }

    private void pegaQuestoes() {
        questaoViewModel.pegaQuestoes().observe(this, questoes -> {
            if (questoes == null) return;
            refreshQuestoes.setRefreshing(false);
            indicadorQuestoes.setVisibility(GONE);
            adapterQuestoes.atualiza(questoes);
        });
    }

    private void configuraRecyclerQuestoes() {
        recyclerQuestoes.setHasFixedSize(true);
        recyclerQuestoes.setLayoutManager(new LinearLayoutManager(getContext()));
        configuraAdapter();
    }

    private void configuraAdapter() {
        adapterQuestoes = new ListaQuestoesAdapter(getContext(), questoes);
        recyclerQuestoes.setAdapter(adapterQuestoes);
        adapterQuestoes.setOnItemClickListener(questao -> {
            ActionQuestoesFragmentToQuestaoDetalhadaFragment acao = QuestoesFragmentDirections.actionQuestoesFragmentToQuestaoDetalhadaFragment(questao);
            Navigation.findNavController(binding.getRoot()).navigate(acao);
        });
    }

    private void inicializaComponentes() {
        questoes = new ArrayList<>();
        recyclerQuestoes = binding.recyclerViewFragmentoQuestoes;
        refreshQuestoes = binding.refreshFragmentoQuestoes;
        indicadorQuestoes = binding.indicadorProgressoFragmentoQuestoes;
        questaoViewModel = new ViewModelProvider(this).get(QuestaoViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        pegaQuestoes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}