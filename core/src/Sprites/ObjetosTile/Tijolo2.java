package Sprites.ObjetosTile;

import Scenes.Hud;
import Sprites.Personagem2;
import Telas.TelaJogo2;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import game.LulaScape;

public class Tijolo2 extends InteractiveTiledObject2 {
    public Tijolo2(TelaJogo2 tela, MapObject object) {
        super(tela, object);
        fixture.setUserData(this);
        setCategoryFilter(LulaScape.TIJOLO_BIT);
    }

    @Override
    public void batidaCabeca(Personagem2 perso) {
        if (perso.taGrande()) {
            LulaScape.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
            setCategoryFilter(LulaScape.QUEBRADO_BIT);
            getCell().setTile(null);
            Hud.addPontuacao(200);
        } else
            LulaScape.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }
}
