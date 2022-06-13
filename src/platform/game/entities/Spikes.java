package platform.game.entities;

import platform.framework.render.Model;
import platform.game.utils.Direction;

import java.awt.*;

public class Spikes extends Entity{
    public Spikes(int posX, int posY) {
        super(posX, posY, new Model(50,20,Color.black));
        setHitbox(posX,posY,50,20);
    }


    @Override
    public void collisionWithPlayer(Player player, Direction direction) {
        player.shouldDie=true;
    }

    @Override
    public void draw(int posX, int posY, Graphics graphics){
        graphics.setColor(getModel().getTexture());
        graphics.fillRect(posX, posY, getModel().width, getModel().height);
    }
}
