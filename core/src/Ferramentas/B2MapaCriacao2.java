package Ferramentas;

import Sprites.Inimigos.Empresario2;
import Sprites.ObjetosTile.Moeda2;
import Sprites.ObjetosTile.PowerupBloco2;
import Sprites.ObjetosTile.Tijolo2;
import Telas.TelaJogo2;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.LulaScape;

public class B2MapaCriacao2 {
    private Array<Empresario2> inimigos;

    public Array<Empresario2> getInimigos() {
        return inimigos;
    }

    public B2MapaCriacao2(TelaJogo2 tela) {
        World mapa = tela.getWorld();
        TiledMap map = tela.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Body body;

        //chão
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / LulaScape.PPM, (rect.getY() + rect.getHeight() / 2) / LulaScape.PPM);

            body = mapa.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / LulaScape.PPM, rect.getHeight() / 2 / LulaScape.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //canos
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / LulaScape.PPM, (rect.getY() + rect.getHeight() / 2) / LulaScape.PPM);

            body = mapa.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / LulaScape.PPM, rect.getHeight() / 2 / LulaScape.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = LulaScape.OBJETO_BIT;

            body.createFixture(fdef);
        }

        //moedas
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Moeda2(tela, object);
        }

        //pedras
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Tijolo2(tela, object);
        }

        //Inimigo
        inimigos = new Array<Empresario2>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            inimigos.add(new Empresario2(tela, rect.getX() / LulaScape.PPM, rect.getY() / LulaScape.PPM));
        }

        //Powerups
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new PowerupBloco2(tela, object);
        }

        //FINAL
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / LulaScape.PPM, (rect.getY() + rect.getHeight() / 2) / LulaScape.PPM);

            body = mapa.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / LulaScape.PPM, rect.getHeight() / 2 / LulaScape.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


    }
}