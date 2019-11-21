package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.LulaScape;

public class Hud implements Disposable {
    public Stage stage;
    public Viewport viewport;

    private Integer tempoMundo;
    private float contador;
    private static Integer pontuacao;

    private Label decrescenteLabel;
    private static Label pontuacaoLabel;
    private Label tempoLabel;
    private Label levelLabel;
    private Label mapaLabel;
    private Label personagemLabel;

    public Hud (SpriteBatch sb){
        tempoMundo = 300;
        contador = 0;
        pontuacao = 0;

        viewport = new FitViewport(LulaScape.V_WIDTH, LulaScape.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        decrescenteLabel = new Label(String.format("%03d", tempoMundo), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pontuacaoLabel = new Label(String.format("%06d", pontuacao), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tempoLabel = new Label("TEMPO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        personagemLabel = new Label("PONTUAÇÃO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(personagemLabel).expandX().padTop(10);
        table.add(tempoLabel).expandX().padTop(10);
        table.row();
        table.add(pontuacaoLabel).expandX();
        table.add(decrescenteLabel).expandX();

        stage.addActor(table);
    }

    public void atualiza(float tempo){
        contador += tempo;
        if (contador >= 1){
            tempoMundo--;
           decrescenteLabel.setText(String.format("%03d", tempoMundo));
           contador = 0;
        }
    }

    public static void addPontuacao(int valor){
        pontuacao += valor;
        pontuacaoLabel.setText(String.format("%06d", pontuacao));
    }

    @Override
    public void dispose(){
        stage.dispose();
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public int getTempo(){
        return tempoMundo;
    }

}


