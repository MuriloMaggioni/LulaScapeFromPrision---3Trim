package Sprites.Itens;

import com.badlogic.gdx.math.Vector2;

public class ItemDef {
    public Vector2 posicao;
    public Class<?> tipo;

    public ItemDef(Vector2 posicao, Class<?> tipo){
        this.posicao = posicao;
        this.tipo = tipo;
    }
}
