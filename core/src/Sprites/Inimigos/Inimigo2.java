package Sprites.Inimigos;

import Telas.TelaJogo2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Inimigo2 extends Sprite {
    protected World mapa;
    protected TelaJogo2 tela;
    public Body b2body;
    public Vector2 velocidade;

    public Inimigo2 (TelaJogo2 tela, float x, float y){
        this.mapa = tela.getWorld();
        this.tela = tela;
        setPosition(x, y);
        defineInimigo();
        velocidade = new Vector2(-1, -2);

        //põe os inimigos para dormir
        b2body.setActive(false);
    }

    protected abstract void defineInimigo();
    public abstract void atualiza(float tempo);
    public abstract void bateCabeca();

    public void reverteVelo (boolean x, boolean y){
        if (x)
            velocidade.x = -velocidade.x;
        if (y)
            velocidade.y = -velocidade.y;
    }

}