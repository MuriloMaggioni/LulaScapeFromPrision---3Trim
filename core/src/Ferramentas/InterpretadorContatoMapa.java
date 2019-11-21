package Ferramentas;

import Sprites.Inimigos.Inimigo;
import Sprites.Itens.Item;
import Sprites.ObjetosTile.InteractiveTiledObject;
import Sprites.Personagem;
import com.badlogic.gdx.physics.box2d.*;
import game.LulaScape;

public class InterpretadorContatoMapa implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixB = contact.getFixtureB();
        Fixture fixA = contact.getFixtureA();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        /*
        if (fixA.getUserData() == "cabeca" || fixB.getUserData() == "cabeca"){
            Fixture cabeca = fixA.getUserData() == "cabeca" ? fixA : fixB;
            Fixture objeto =  cabeca == fixA ? fixB : fixA;
            if (objeto.getUserData() != null && InteractiveTiledObject.class.isAssignableFrom(objeto.getUserData().getClass())){
                ((InteractiveTiledObject) objeto.getUserData()).batidaCabeca();
            }
        }
        */

        switch (cDef){
            case LulaScape.PERSO_CABECA_BIT | LulaScape.TIJOLO_BIT:
            case LulaScape.PERSO_CABECA_BIT | LulaScape.MOEDA_BIT:
            case LulaScape.PERSO_CABECA_BIT | LulaScape.POWER_UP_BLOCO_BIT:
                if (fixA.getFilterData().categoryBits == LulaScape.PERSO_CABECA_BIT)
                    ((InteractiveTiledObject) fixB.getUserData()).batidaCabeca(((Personagem) fixA.getUserData()));
                else
                    ((InteractiveTiledObject) fixA.getUserData()).batidaCabeca(((Personagem) fixB.getUserData()));
                break;
            case LulaScape.CABECA_INIMIGO_BIT | LulaScape.PERSO_BIT:
                if (fixA.getFilterData().categoryBits == LulaScape.CABECA_INIMIGO_BIT)
                    ((Inimigo) fixA.getUserData()).bateCabeca();
                else
                    ((Inimigo)fixB.getUserData()).bateCabeca();
                break;
            case LulaScape.INIMIGO_BIT | LulaScape.OBJETO_BIT:
                if (fixA.getFilterData().categoryBits == LulaScape.INIMIGO_BIT)
                    ((Inimigo) fixA.getUserData()).reverteVelo(true, false);
                else
                    ((Inimigo)fixB.getUserData()).reverteVelo(true, false);
                break;
            case LulaScape.PERSO_BIT | LulaScape.INIMIGO_BIT:
                if(fixA.getFilterData().categoryBits == LulaScape.PERSO_BIT)
                    ((Personagem) fixA.getUserData()).bate();
                else
                    ((Personagem) fixB.getUserData()).bate();
                break;
            case LulaScape.INIMIGO_BIT | LulaScape.INIMIGO_BIT:
                ((Inimigo)fixA.getUserData()).reverteVelo(true,false);
                ((Inimigo)fixB.getUserData()).reverteVelo(true,false);
                break;
            case LulaScape.POWER_UP_BIT | LulaScape.OBJETO_BIT:
                if (fixA.getFilterData().categoryBits == LulaScape.POWER_UP_BIT)
                    ((Item) fixA.getUserData()).reverteVelo(true, false);
                else
                    ((Item)fixB.getUserData()).reverteVelo(true, false);
                break;
            case LulaScape.POWER_UP_BIT | LulaScape.PERSO_BIT:
                if(fixA.getFilterData().categoryBits == LulaScape.POWER_UP_BIT)
                    ((Item)fixA.getUserData()).usarItem((Personagem) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).usarItem((Personagem) fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
























