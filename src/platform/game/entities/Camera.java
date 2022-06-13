package platform.game.entities;

import platform.framework.DisplayManager;

public class Camera  {

	protected int posX;
	protected int posY;


	public Camera(Entity player) {
		this.posX=player.posX+player.getModel().width/2 - DisplayManager.WIDTH/2;
		this.posY=player.posY+player.getModel().height/2 - DisplayManager.HEIGHT/2;
	}
	
	public void setPosition(Player player) {
		this.posX = player.posX+player.getModel().width/2 - DisplayManager.WIDTH/2;
		this.posY = player.posY+player.getModel().height/2 - DisplayManager.HEIGHT/2;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
}
