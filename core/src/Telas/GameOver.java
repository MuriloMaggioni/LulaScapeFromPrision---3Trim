package Telas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.LulaScape;


public class GameOver implements Screen {

    private SpriteBatch spriteBatch;
    private Viewport viewport;

    private Game game;

    private Animation animation;
    private float animationTime = 0;

    public GameOver (Game game){
        this.game = game;
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(LulaScape.V_WIDTH, LulaScape.V_HEIGHT, new OrthographicCamera(800, 480));

        float width_proporcao = 800 / Gdx.graphics.getWidth();

        Texture texture = new Texture("gameOver.gif");
        Sprite sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth() / width_proporcao, sprite.getHeight() / width_proporcao);
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion region1 = new TextureRegion(sprite);

        animation = new Animation(1.0f, region1);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        animationTime += Gdx.graphics.getDeltaTime();
        TextureRegion animationFrame = (TextureRegion) animation.getKeyFrame(animationTime, true);

        spriteBatch.begin();
        spriteBatch.draw(animationFrame, 0, 0);
        spriteBatch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new Menu((LulaScape) game));
            dispose();
        }
    }

    @Override
    public void resize(int i, int i1) {

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
        spriteBatch.dispose();
    }
}