package platform.game.entities;

import platform.framework.render.Model;

import java.awt.*;

public class Wall extends Entity{

    public Wall(int posX, int posY,int width,int height) {
        super(posX, posY, new Model(width,height,Color.white));
        setHitbox(posX,posY,width,height);
    }

    @Override
    public void draw(int posX, int posY, Graphics graphics){
        graphics.setColor(Color.black);
        graphics.drawRect(posX,posY,getModel().width,getModel().height);
        graphics.setColor(getModel().getTexture());
        graphics.fillRect(posX+1, posY+1, getModel().width-2, getModel().height-2);
    }

}
