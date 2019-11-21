package Sprites;

import Telas.TelaJogo2;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.LulaScape;

public class Personagem2 extends Sprite {
    public enum State {CAINDO, PULANDO, PARADO, CORRENDO, CRESCENDO, MORTO, GANHOU};
    public Personagem2.State estadoAtual;
    public Personagem2.State estadoAnterior;

    public World mapa;
    public Body b2body;

    private TextureRegion lugarPerso;
    private Animation correr;
    private TextureRegion pular;
    private TextureRegion bigPersoParado;
    private TextureRegion bigPersoPula;
    private TextureRegion mortePerso;
    private Animation bigPersoCorre;
    private Animation crescePerso;

    private boolean correrDireita;
    private float tempoEstado;
    private boolean persoGrande;
    private boolean animacaoCrescer;
    private boolean defineBigPerso;
    private boolean redefinePerso;
    private boolean taMortoo;


    public Personagem2 (TelaJogo2 tela){
        this.mapa = tela.getWorld();
        estadoAtual = Personagem2.State.PARADO;
        estadoAnterior = Personagem2.State.PARADO;
        tempoEstado = 0;
        correrDireita = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //define animação de corrida
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(tela.getAtlas().findRegion("little_mario"),  i * 16, 0, 16, 16));
        correr = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(tela.getAtlas().findRegion("big_mario"),  i * 16, 0, 16, 32));
        bigPersoCorre = new Animation(0.1f, frames);
        frames.clear();

        //seta os frames de crescer
        frames.add(new TextureRegion(tela.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(tela.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        frames.add(new TextureRegion(tela.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(tela.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        crescePerso = new Animation(0.2f, frames);

        //seta os frames de pulo
        pular = new TextureRegion(tela.getAtlas().findRegion("little_mario"), 80, 0, 16, 16);
        bigPersoPula = new TextureRegion(tela.getAtlas().findRegion("big_mario"), 80, 0, 16, 32);

        //seta os frames do personagem grande
        lugarPerso = new TextureRegion(tela.getAtlas().findRegion("little_mario"), 0, 0, 16, 16);
        bigPersoParado = new TextureRegion(tela.getAtlas().findRegion("big_mario"), 0, 0, 16, 32);

        //seta frames de morte
        mortePerso = new TextureRegion(tela.getAtlas().findRegion("little_mario"), 96, 0, 16, 16);


        definePerso();

        setBounds(0, 0, 16 / LulaScape.PPM, 16 / LulaScape.PPM);
        setRegion(lugarPerso);

    }

    public void atualiza(float tempo){

        //define a posicao inicial se é grande ou pequeno
        if (persoGrande)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 5.5f / LulaScape.PPM);
        else
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 0.15f / LulaScape.PPM);

        setRegion(getFrame(tempo));

        if (defineBigPerso)
            defineBigPersoo();
        if (redefinePerso)
            redefinePersoo();
    }

    public TextureRegion getFrame(float tempo){
        estadoAtual = getState();

        TextureRegion region;
        switch (estadoAtual){
            case MORTO:
                region = mortePerso;
                break;
            case CRESCENDO:
                region = (TextureRegion) crescePerso.getKeyFrame(tempoEstado);
                if (crescePerso.isAnimationFinished(tempoEstado))
                    animacaoCrescer = false;
                break;
            case PULANDO:
                region = persoGrande ? bigPersoPula : pular;
                break;
            case CORRENDO:
                region = persoGrande ? (TextureRegion) bigPersoCorre.getKeyFrame(tempoEstado, true) :
                        (TextureRegion) correr.getKeyFrame(tempoEstado, true);
                break;
            case CAINDO:
            case PARADO:
            default:
                region = persoGrande ? bigPersoParado : lugarPerso;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !correrDireita) && !region.isFlipX()){
            region.flip(true, false);
            correrDireita = false;
        }else if ((b2body.getLinearVelocity().x > 0 || correrDireita) && region.isFlipX()){
            region.flip(true, false);
            correrDireita = true;
        }

        tempoEstado = estadoAtual == estadoAnterior ? tempoEstado + tempo : 0;
        estadoAnterior = estadoAtual;
        return region;

    }

    public Personagem2.State getState() {
        if (b2body.getPosition().x == 3624 / LulaScape.PPM) {
            b2body.applyLinearImpulse(new Vector2(0.03f, 0), b2body.getWorldCenter(), true);
            return Personagem2.State.GANHOU;
        } else if (taMortoo || b2body.getPosition().y < -1) {
            return Personagem2.State.MORTO;
        }
        else if (animacaoCrescer){
            return Personagem2.State.CRESCENDO;
        }
        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && estadoAnterior == Personagem2.State.PULANDO)){
            return Personagem2.State.PULANDO;
        }
        else if (b2body.getLinearVelocity().y < 0){
            return Personagem2.State.CAINDO;
        }
        else if (b2body.getLinearVelocity().x != 0){
            return Personagem2.State.CORRENDO;
        }
        else{
            return Personagem2.State.PARADO;
        }
    }

    public float getTempoEstado(){
        return tempoEstado;
    }

    public void defineBigPersoo(){
        Vector2 posicao = b2body.getPosition();
        mapa.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(posicao.add(0, 10 / LulaScape.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = mapa.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape cShape = new CircleShape();
        cShape.setRadius(6 / LulaScape.PPM);
        fdef.filter.categoryBits = LulaScape.PERSO_BIT;
        fdef .filter.maskBits = LulaScape.TERRENO_BIT |
                LulaScape.MOEDA_BIT |
                LulaScape.TIJOLO_BIT |
                LulaScape.INIMIGO_BIT |
                LulaScape.OBJETO_BIT |
                LulaScape.CABECA_INIMIGO_BIT |
                LulaScape.POWER_UP_BIT |
                LulaScape.POWER_UP_BLOCO_BIT;

        fdef.shape = cShape;
        b2body.createFixture(fdef).setUserData(this);
        cShape.setPosition(new Vector2(0, -14 / LulaScape.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape cabeca = new EdgeShape();
        cabeca.set(new Vector2(-2 / LulaScape.PPM, 6 / LulaScape.PPM), new Vector2(2 / LulaScape.PPM, 6 / LulaScape.PPM));
        fdef.filter.categoryBits = LulaScape.PERSO_CABECA_BIT;
        fdef.shape = cabeca;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        defineBigPerso = false;
    }

    public void definePerso(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / LulaScape.PPM, 32 / LulaScape.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = mapa.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape cShape = new CircleShape();
        cShape.setRadius(6 / LulaScape.PPM);
        fdef.filter.categoryBits = LulaScape.PERSO_BIT;
        fdef .filter.maskBits = LulaScape.TERRENO_BIT |
                LulaScape.MOEDA_BIT |
                LulaScape.TIJOLO_BIT |
                LulaScape.INIMIGO_BIT |
                LulaScape.OBJETO_BIT |
                LulaScape.CABECA_INIMIGO_BIT |
                LulaScape.POWER_UP_BIT |
                LulaScape.POWER_UP_BLOCO_BIT;

        fdef.shape = cShape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape cabeca = new EdgeShape();
        cabeca.set(new Vector2(-2 / LulaScape.PPM, 6 / LulaScape.PPM), new Vector2(2 / LulaScape.PPM, 6 / LulaScape.PPM));
        fdef.filter.categoryBits = LulaScape.PERSO_CABECA_BIT;
        fdef.shape = cabeca;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void redefinePersoo(){
        Vector2 posicao = b2body.getPosition();
        mapa.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(posicao);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = mapa.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape cShape = new CircleShape();
        cShape.setRadius(6 / LulaScape.PPM);
        fdef.filter.categoryBits = LulaScape.PERSO_BIT;
        fdef .filter.maskBits = LulaScape.TERRENO_BIT |
                LulaScape.MOEDA_BIT |
                LulaScape.TIJOLO_BIT |
                LulaScape.INIMIGO_BIT |
                LulaScape.OBJETO_BIT |
                LulaScape.CABECA_INIMIGO_BIT |
                LulaScape.POWER_UP_BIT |
                LulaScape.POWER_UP_BLOCO_BIT;

        fdef.shape = cShape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape cabeca = new EdgeShape();
        cabeca.set(new Vector2(-2 / LulaScape.PPM, 6 / LulaScape.PPM), new Vector2(2 / LulaScape.PPM, 6 / LulaScape.PPM));
        fdef.filter.categoryBits = LulaScape.PERSO_CABECA_BIT;
        fdef.shape = cabeca;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        redefinePerso = false;

    }

    public void cresce() {
        if (!taGrande()) {
            animacaoCrescer = true;
            persoGrande = true;
            defineBigPerso = true;
            setBounds(getX(), getY(), getWidth(), getHeight() * 2);
            LulaScape.manager.get("audio/sounds/powerup.wav", Sound.class).play();
        }
    }

    public void bate(){
        if (taGrande()){
            persoGrande = false;
            redefinePerso = true;
            setBounds(getX(), getY(), getWidth(), getHeight() / 2);
            LulaScape.manager.load("audio/sounds/powerdown.wav", Sound.class);
        } else{
            LulaScape.manager.get("audio/music/mario_music.ogg", Music.class).stop();
            LulaScape.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            taMortoo = true;
            Filter filter = new Filter();
            filter.maskBits = LulaScape.NADA_BIT;
            for (Fixture fix : b2body.getFixtureList())
                fix.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0, 4), b2body.getWorldCenter(), true);

        }
    }

    public boolean taMorto(){
        return taMortoo;
    }

    public float getStateTempo(){
        return tempoEstado;
    }

    public boolean taGrande(){
        return persoGrande;

    }

}