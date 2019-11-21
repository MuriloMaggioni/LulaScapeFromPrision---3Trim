package Sprites.Inimigos;

import Scenes.Hud;
import Telas.TelaJogo;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import game.LulaScape;

public class Empresario extends Inimigo {
    private float tempoEstado;
    private Animation andarAnimation;
    private Array<TextureRegion> frames;
    private boolean setDestroi;
    private boolean destruido;


    public Empresario(TelaJogo tela, float x, float y) {
        super(tela, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i<2; i++){
            frames.add(new TextureRegion( tela.getAtlas(). findRegion("goomba"), i * 16, 0, 16, 16));
        }
        andarAnimation = new Animation(0.4f, frames);
        tempoEstado = 0;
        setBounds(getX(), getY(), 16 / LulaScape.PPM, 16 / LulaScape.PPM);
        setDestroi = false;
        destruido = false;
    }

    public void atualiza (float tempo) {
        tempoEstado += tempo;
        if (setDestroi && !destruido) {
            mapa.destroyBody(b2body);
            destruido = true;
            setRegion(new TextureRegion(tela.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
            tempoEstado = 0;
        } else if (!destruido) {
            b2body.setLinearVelocity(velocidade);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) andarAnimation.getKeyFrame(tempoEstado, true));
        }
    }
    @Override
    protected void defineInimigo() {
        BodyDef bdef = new BodyDef();

        //seta posição inicial do inimigo
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = mapa.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape cShape = new CircleShape();
        cShape.setRadius(6 / LulaScape.PPM);
        fdef.filter.categoryBits = LulaScape.INIMIGO_BIT;
        fdef .filter.maskBits = LulaScape.TERRENO_BIT |
                LulaScape.MOEDA_BIT |
                LulaScape.TIJOLO_BIT |
                LulaScape.INIMIGO_BIT |
                LulaScape.OBJETO_BIT |
                LulaScape.PERSO_BIT ;

        fdef.shape = cShape;
        b2body.createFixture(fdef).setUserData(this);

        //seta cabeça inimigo
        PolygonShape cabeca = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / LulaScape.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / LulaScape.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / LulaScape.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / LulaScape.PPM);
        cabeca.set(vertice);

        fdef.shape = cabeca;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = LulaScape.CABECA_INIMIGO_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw (Batch batch){
        if (!destruido || tempoEstado < 1){
            super.draw(batch);
        }
    }

    @Override
    public void bateCabeca() {
        setDestroi = true;
        LulaScape.manager.get("audio/sounds/stomp.wav", Sound.class).play();
        Hud.addPontuacao(150);
    }
}
