package Sprites.ObjetosTile;

import Scenes.Hud;
import Sprites.Personagem;
import Telas.TelaJogo;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import game.LulaScape;

public class Moeda extends InteractiveTiledObject {
    private static TiledMapTileSet tileSet;
    private final int PEGA_MOEDA = 28;

    public Moeda(TelaJogo tela, MapObject object){
        super(tela, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(LulaScape.MOEDA_BIT);
    }

    @Override
    public void batidaCabeca(Personagem perso) {
        if (getCell().getTile().getId() == PEGA_MOEDA){
            LulaScape.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }else {
                LulaScape.manager.get("audio/sounds/coin.wav", Sound.class).play();
        }
        getCell().setTile(tileSet.getTile(PEGA_MOEDA));
        Hud.addPontuacao(100);
    }

}
