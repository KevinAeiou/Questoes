package com.amazonas.questoes.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amazonas.questoes.model.Alternativa;
import com.amazonas.questoes.model.Questao;

import java.util.ArrayList;

public class QuestaoViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Questao>> questoesEncontradas;

    public QuestaoViewModel() {
        this.questoesEncontradas = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Questao>> pegaQuestoes() {
        ArrayList<Questao> questoes = new ArrayList<>();
        ArrayList<Alternativa> alternativas = new ArrayList<>();
        for (int y= 0; y< 4; y++) {
            Alternativa alternativa = new Alternativa();
            alternativa.setDescricao("Alternativa " + y);
            alternativas.add(alternativa);
        }
        for (int x= 0; x< 5; x++) {
            Questao questao = new Questao();
            questao.setBanca("CEBRASPE");
            questao.setAno(2025);
            questao.setDificudade("Fácil");
            questao.setDescricao("Descrição teste curta");
            questao.setAlternativas(alternativas);
            questao.setGabarito(alternativas.get(1).getId());
            questoes.add(questao);
        }
//        alternativas.clear();
//        Alternativa certo = new Alternativa();
//        certo.setDescricao("Certo");
//        alternativas.add(certo);
//        Alternativa errado = new Alternativa();
//        errado.setDescricao("Errado");
//        alternativas.add(errado);
//        Questao questao = new Questao();
//        questao.setBanca("Teste");
//        questao.setAno(2025);
//        questao.setDificudade("Fácil");
//        questao.setDescricao("Descrição teste curta");
//        questao.setAlternativas(alternativas);
//        questao.setGabarito(alternativas.get(1).getId());
//        ArrayList<Comentario> comentarios = new ArrayList<>();
//        Comentario comentario = new Comentario();
//        comentario.setDescricao("Comentário teste");
//        comentarios.add(comentario);
//        Comentario comentario2 = new Comentario();
//        comentario2.setDescricao("Comentário teste");
//        comentarios.add(comentario2);
//        questao.setComentarios(comentarios);
//        questoes.add(questao);

        questoesEncontradas.setValue(questoes);
        return questoesEncontradas;
    }
}
