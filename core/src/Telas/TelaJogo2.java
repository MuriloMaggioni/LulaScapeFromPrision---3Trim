package Telas;

import Ferramentas.B2MapaCriacao2;
import Ferramentas.InterpretadorContatoMapa2;
import Scenes.Hud;
import Sprites.Inimigos.Inimigo2;
import Sprites.Itens.Item2;
import Sprites.Itens.ItemDef;
import Sprites.Itens.Powerup;
import Sprites.Itens.Powerup2;
import Sprites.Personagem2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.LulaScape;

import java.util.concurrent.LinkedBlockingQueue;

public class TelaJogo2 implements Screen {
    private LulaScape game;
    private TextureAtlas atlas;
    public static boolean jaDestruido = false;

    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport viewport;

    //Mapa(TILED) Variaveis
    private TmxMapLoader mapCarreg;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderizador;

    //Box2D Variáveis
    private World mapa;
    private Box2DDebugRenderer b2dr;
    private B2MapaCriacao2 creator;

    private Personagem2 jogador;

    private Music music;

    //Cria powerUps
    private Array<Item2> itens;
    private LinkedBlockingQueue<ItemDef> itensSpawn;

    public TelaJogo2(LulaScape game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;

        gamecam = new OrthographicCamera(800, 480);
        viewport = new FitViewport(LulaScape.V_WIDTH / LulaScape.PPM, LulaScape.V_HEIGHT / LulaScape.PPM, gamecam);
        hud = new Hud(game.batch);

        //cria e recebe as variais do tiled(mapa)
        mapCarreg = new TmxMapLoader();
        map = mapCarreg.load("level2.tmx");
        renderizador = new OrthogonalTiledMapRenderer(map, 1 / LulaScape.PPM);

        //posiciona a camera inicial
        gamecam.position.set(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2, 0);

        mapa = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2MapaCriacao2(this);

        //cria o personagem no jogo
        jogador = new Personagem2(this);

        mapa.setContactListener(new InterpretadorContatoMapa2());

        //seta a música no game
        music = LulaScape.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        itens = new Array<Item2>();
        itensSpawn = new LinkedBlockingQueue<ItemDef>();

    }

    public void spawnItem(ItemDef id){
        itensSpawn.add(id);
    }

    public void criandoItens(){
        if (!itensSpawn.isEmpty()){
            ItemDef id = itensSpawn.poll();
            if (id.tipo == Powerup.class){
                itens.add(new Powerup2(this, id.posicao.x, id.posicao.y));
            }
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    //Define teclas de movimentação
    public void apertaTecla(float tempo) {
        if (jogador.estadoAtual != Personagem2.State.MORTO) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                if (jogador.estadoAtual != Personagem2.State.PULANDO){
                    jogador.b2body.applyLinearImpulse(new Vector2(0, 4f), jogador.b2body.getWorldCenter(), true);
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && jogador.b2body.getLinearVelocity().x <= 2) {
                jogador.b2body.applyLinearImpulse(new Vector2(0.1f, 0), jogador.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && jogador.b2body.getLinearVelocity().x >= -2) {
                jogador.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), jogador.b2body.getWorldCenter(), true);
            }
        }
    }

    public void atualiza(float tempo){
        apertaTecla(tempo);
        criandoItens();

        mapa.step(1/60f, 6, 2);

        jogador.atualiza(tempo);

        //acorda os inimigos
        for (Inimigo2 inimigo : creator.getInimigos()){
            inimigo.atualiza(tempo);
            if (inimigo.getX() < jogador.getX() + 448 / LulaScape.PPM){
                inimigo.b2body.setActive(true);
            }
        }

        for (Item2 item : itens)
            item.atualiza(tempo);

        hud.atualiza(tempo);

        //seta a camera na coordenada x do jogador
        if (jogador.estadoAtual != Personagem2.State.MORTO) {
            gamecam.position.x = jogador.b2body.getPosition().x;
        }
        //atualiza a camera a cada mudança
        gamecam.update();

        //renderiza só o que ta na camera
        renderizador.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        atualiza(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderizador.render();

        b2dr.render(mapa, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        //executa a contrução do PERSONAGEM
        jogador.draw(game.batch);

        //executa a construção dos INIMIGOS
        for (Inimigo2 i : creator.getInimigos())
            i.draw(game.batch);

        //executa a construção dos POWER-UPS
        for (Item2 item : itens)
            item.draw(game.batch);

        game.batch.end();

        //seta a constução da HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()){
            game.setScreen(new GameOver(game));
            gravaPontos();
            dispose();
        }

        if (termina()) {
            game.setScreen(new FinalGame(game));
            gravaPontos();
            dispose();
        }

        if (hud.getTempo() < 0){
            game.setScreen(new GameOver(game));
            gravaPontos();
            dispose();
        }


    }

    public boolean gameOver(){
        if (jogador.estadoAtual == Personagem2.State.MORTO &&
                jogador.getTempoEstado() > 3){
            return true;
        }
        return false;
    }

    public boolean termina(){
        if (jogador.estadoAtual == Personagem2.State.GANHOU &&
                jogador.getTempoEstado() > 3){
            return true;
        }
        return false;
    }

    public void gravaPontos() {
        FileHandle fh = Gdx.files.local("pontosFase2.txt");
        fh.writeString(Integer.toString(hud.getPontuacao()) + "\n", true);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return mapa;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderizador.dispose();
        mapa.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud hetHud(){
        return hud;
    }
}
