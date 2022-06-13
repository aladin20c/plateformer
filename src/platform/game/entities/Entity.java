package platform.game.entities;

import platform.framework.render.Model;
import platform.game.utils.Direction;

import java.awt.*;

public class Entity {

	private Model model;
	protected int posX;
	protected int posY;
	private Rectangle hitbox;
	
	public Entity(int posX, int posY, Model model) {
		this.posX = posX;
		this.posY = posY;
		this.model = model;
	}

	public boolean xMouvementCollision(Rectangle hitbox,Rectangle block,double SpeedX){
		return hitbox.x + hitbox.width + SpeedX > block.x &&
				hitbox.x + SpeedX < block.x + block.width &&
				hitbox.y + hitbox.height > block.y &&
				hitbox.y < block.y + block.height;
	}
	public boolean yMouvementCollision(Rectangle hitbox,Rectangle block,double SpeedY){
		return hitbox.x + hitbox.width> block.x &&
				hitbox.x< block.x + block.width &&
				hitbox.y + hitbox.height + SpeedY > block.y &&
				hitbox.y + SpeedY < block.y + block.height;
	}
	
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	protected void setModel(Model model) {this.model = model;}
	public Model getModel() {
		model.x = posX;
		model.y = posY;
		return model;
	}


	public void draw(int posX, int posY, Graphics graphics){}


	public Rectangle getHitbox() {return hitbox;}
	public void setHitbox(int x, int y, int width, int height) {
		this.hitbox = new Rectangle(x,y,width,height);
	}
	public void updateHitbox( int x,int y) {
		this.hitbox.x = x;
		this.hitbox.y = y;
	}

	public void collisionWithPlayer(Player player, Direction direction){}


	public void setPosY(int posY) {
		this.posY = posY;
	}
}
