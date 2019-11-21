package Sprites.Itens;

import Sprites.Personagem2;
import Telas.TelaJogo2;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import game.LulaScape;

public abstract class Item2 extends Sprite {
    protected TelaJogo2 tela;
    protected World mapa;
    protected Vector2 velocidade;
    protected boolean destruir;
    protected boolean destruido;
    protected Body body;


    public Item2(TelaJogo2 tela, float x, float y) {
        this.tela = tela;
        this.mapa = tela.getWorld();
        destruir = false;
        destruido = false;

        setPosition(x, y);
        setBounds(getX(), getY(), 16 / LulaScape.PPM, 16 / LulaScape.PPM);
        defineItem();
    }

    public abstract void defineItem();
    public abstract void usarItem(Personagem2 perso);

    public void atualiza (float tempo){
        if (destruir && !destruido){
            mapa.destroyBody(body);
            destruido = true;
        }
    }

    public void draw (Batch batch){
        if (!destruido)
            super.draw(batch);
    }

    public void destroi() {
        destruir = true;
    }

    public void reverteVelo (boolean x, boolean y){
        if (x)
            velocidade.x = -velocidade.x;
        if (y)
            velocidade.y = -velocidade.y;
    }
}
