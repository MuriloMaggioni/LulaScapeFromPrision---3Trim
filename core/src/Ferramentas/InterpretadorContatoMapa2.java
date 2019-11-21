package Ferramentas;

import Sprites.Inimigos.Inimigo2;
import Sprites.Itens.Item2;
import Sprites.ObjetosTile.InteractiveTiledObject2;
import Sprites.Personagem2;
import com.badlogic.gdx.physics.box2d.*;
import game.LulaScape;

public class InterpretadorContatoMapa2 implements ContactListener {

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
                    ((InteractiveTiledObject2) fixB.getUserData()).batidaCabeca(((Personagem2) fixA.getUserData()));
                else
                    ((InteractiveTiledObject2) fixA.getUserData()).batidaCabeca(((Personagem2) fixB.getUserData()));
                break;
            case LulaScape.CABECA_INIMIGO_BIT | LulaScape.PERSO_BIT:
                if (fixA.getFilterData().categoryBits == LulaScape.CABECA_INIMIGO_BIT)
                    ((Inimigo2) fixA.getUserData()).bateCabeca();
                else
                    ((Inimigo2)fixB.getUserData()).bateCabeca();
                break;
            case LulaScape.INIMIGO_BIT | LulaScape.OBJETO_BIT:
                if (fixA.getFilterData().categoryBits == LulaScape.INIMIGO_BIT)
                    ((Inimigo2) fixA.getUserData()).reverteVelo(true, false);
                else
                    ((Inimigo2)fixB.getUserData()).reverteVelo(true, false);
                break;
            case LulaScape.PERSO_BIT | LulaScape.INIMIGO_BIT:
                if(fixA.getFilterData().categoryBits == LulaScape.PERSO_BIT)
                    ((Personagem2) fixA.getUserData()).bate();
                else
                    ((Personagem2) fixB.getUserData()).bate();
                break;
            case LulaScape.INIMIGO_BIT | LulaScape.INIMIGO_BIT:
                ((Inimigo2)fixA.getUserData()).reverteVelo(true,false);
                ((Inimigo2)fixB.getUserData()).reverteVelo(true,false);
                break;
            case LulaScape.POWER_UP_BIT | LulaScape.OBJETO_BIT:
                if (fixA.getFilterData().categoryBits == LulaScape.POWER_UP_BIT)
                    ((Item2) fixA.getUserData()).reverteVelo(true, false);
                else
                    ((Item2)fixB.getUserData()).reverteVelo(true, false);
                break;
            case LulaScape.POWER_UP_BIT | LulaScape.PERSO_BIT:
                if(fixA.getFilterData().categoryBits == LulaScape.POWER_UP_BIT)
                    ((Item2)fixA.getUserData()).usarItem((Personagem2) fixB.getUserData());
                else
                    ((Item2)fixB.getUserData()).usarItem((Personagem2) fixA.getUserData());
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
