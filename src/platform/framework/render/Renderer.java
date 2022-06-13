package platform.framework.render;

import java.awt.Graphics;

import platform.framework.DisplayManager;
import platform.game.entities.Camera;
import platform.game.entities.Entity;

public class Renderer {

	/**Renders an entity on screen
	 * @param entity - The entity to render
	 * @param camera - Needed to calculate render position
	 * @param graphics - Graphics object
	 */
	public static void renderEntity(Entity entity, Camera camera, Graphics graphics) {
		int posX = entity.getPosX() - camera.getPosX();
		int posY = entity.getPosY() - camera.getPosY();
		if(posX>0 && posX< DisplayManager.WIDTH && posY>0 && posY<DisplayManager.HEIGHT){
			entity.draw(posX, posY, graphics);
		}
	}

}
