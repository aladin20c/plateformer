package platform.game.entities;

import platform.framework.render.Model;
import platform.game.utils.Direction;

import java.awt.*;

public class Trampoline extends Entity{

    public Trampoline(int posX, int posY) {
        super(posX, posY, new Model(50,20,new Color(236, 172, 172)));
        setHitbox(posX,posY,50,20);
    }


    @Override
    public void collisionWithPlayer(Player player, Direction direction) {
        if(direction==Direction.DOWN){
            player.fallSpeed-=15;
        }
    }

    @Override
    public void draw(int posX, int posY, Graphics graphics){
        graphics.setColor(getModel().getTexture());
        graphics.fillRect(posX, posY, getModel().width, getModel().height);
    }

}
