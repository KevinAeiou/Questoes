package com.example.questoes.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.questoes.model.Questao;

import java.util.ArrayList;

public class QuestaoViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Questao>> questoesEncontradas;

    public QuestaoViewModel() {
        this.questoesEncontradas = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Questao>> pegaQuestoes() {
        ArrayList<Questao> questoes = new ArrayList<>();
        for (int x= 0; x< 10; x++) {
            Questao questao = new Questao();
            questao.setBanca("Teste");
            questao.setAno(2025);
            questao.setDificudade("Fácil");
            questao.setDescricao("Descrição teste curta");
            questoes.add(questao);
        }
        questoesEncontradas.setValue(questoes);
        return questoesEncontradas;
    }
}
