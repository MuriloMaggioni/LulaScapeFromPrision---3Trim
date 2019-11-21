package Sprites.ObjetosTile;

import Scenes.Hud;
import Sprites.Personagem;
import Telas.TelaJogo;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import game.LulaScape;

public class Tijolo extends InteractiveTiledObject {
    public Tijolo(TelaJogo tela, MapObject object) {
        super(tela, object);
        fixture.setUserData(this);
        setCategoryFilter(LulaScape.TIJOLO_BIT);
    }

    @Override
    public void batidaCabeca(Personagem perso) {
        if (perso.taGrande()) {
        LulaScape.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        setCategoryFilter(LulaScape.QUEBRADO_BIT);
        getCell().setTile(null);
        Hud.addPontuacao(200);
    } else
        LulaScape.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }
}

