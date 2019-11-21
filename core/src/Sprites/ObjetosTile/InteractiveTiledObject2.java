package Sprites.ObjetosTile;

import Sprites.Personagem2;
import Telas.TelaJogo2;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import game.LulaScape;

public abstract class InteractiveTiledObject2 {
    protected World mapa;
    protected TiledMap map;
    protected Rectangle rect;
    protected Body body;
    protected TelaJogo2 tela;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTiledObject2(TelaJogo2 tela, MapObject object){
        this.object = object;
        this.tela = tela;
        this.mapa = tela.getWorld();
        this.map = tela.getMap();
        this.rect = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) / LulaScape.PPM, (rect.getY() + rect.getHeight() / 2) / LulaScape.PPM);

        body = mapa.createBody(bdef);

        shape.setAsBox((rect.getWidth() / 2) / LulaScape.PPM, (rect.getHeight() / 2) / LulaScape.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void batidaCabeca (Personagem2 perso);

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x * LulaScape.PPM / 16),
                (int) (body.getPosition().y * LulaScape.PPM / 16));
    }

}
