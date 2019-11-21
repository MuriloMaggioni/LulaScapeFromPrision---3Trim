package Ferramentas;

import Sprites.Inimigos.Empresario;
import Sprites.ObjetosTile.Moeda;
import Sprites.ObjetosTile.PowerupBloco;
import Sprites.ObjetosTile.Tijolo;
import Telas.TelaJogo;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.LulaScape;

public class B2MapaCriacao {
    private Array<Empresario> inimigos;

    public Array<Empresario> getInimigos() {
        return inimigos;
    }

    public B2MapaCriacao(TelaJogo tela) {
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

            new Moeda(tela, object);
        }

        //pedras
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Tijolo(tela, object);
        }

        //Inimigo
        inimigos = new Array<Empresario>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            inimigos.add(new Empresario(tela, rect.getX() / LulaScape.PPM, rect.getY() / LulaScape.PPM));
        }

        //Powerups
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new PowerupBloco(tela, object);
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
