package Sprites.ObjetosTile;

import Scenes.Hud;
import Sprites.Itens.ItemDef;
import Sprites.Itens.Powerup;
import Sprites.Personagem;
import Telas.TelaJogo;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import game.LulaScape;

public class PowerupBloco extends InteractiveTiledObject {
    private static TiledMapTileSet tileSet;
    private final int PEGA_POWER = 28;

    public PowerupBloco(TelaJogo tela, MapObject object){
        super(tela, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(LulaScape.POWER_UP_BLOCO_BIT);
    }

    @Override
    public void batidaCabeca(Personagem perso) {
        if (getCell().getTile().getId() == PEGA_POWER){
            LulaScape.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }else {
            tela.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / LulaScape.PPM), Powerup.class));
            LulaScape.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
        }
        getCell().setTile(tileSet.getTile(PEGA_POWER));
        Hud.addPontuacao(100);
    }
}

