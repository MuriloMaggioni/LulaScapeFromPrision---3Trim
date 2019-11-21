package Sprites.Itens;

import Sprites.Personagem;
import Telas.TelaJogo;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import game.LulaScape;

public class Powerup extends Item {

    public Powerup(TelaJogo tela, float x, float y) {
        super(tela, x, y);
        setRegion(tela.getAtlas().findRegion("mushroom"), 0, 0, 16, 16);
        velocidade = new Vector2(0.7f, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();

        //seta posição inicial do item
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = mapa.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape cShape = new CircleShape();
        cShape.setRadius(6 / LulaScape.PPM);
        fdef.filter.categoryBits = LulaScape.POWER_UP_BIT;
        fdef.filter.maskBits = LulaScape.PERSO_BIT |
                LulaScape.OBJETO_BIT |
                LulaScape.TERRENO_BIT |
                LulaScape.MOEDA_BIT |
                LulaScape.TIJOLO_BIT;

        fdef.shape = cShape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void usarItem(Personagem perso) {
        destroi();
        perso.cresce();
    }

    @Override
    public void atualiza(float tempo) {
        super.atualiza(tempo);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        velocidade.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocidade);
    }
}
