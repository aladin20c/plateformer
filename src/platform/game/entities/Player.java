package platform.game.entities;

import java.awt.*;
import java.util.ArrayList;

import platform.framework.gamestates.GameStateManager;
import platform.framework.render.Model;
import platform.game.utils.Direction;

public class Player extends Entity {

	protected double movementSpeed;
	protected double fallSpeed;

	protected boolean isInAir;
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;

	protected boolean shouldDie=false;
	protected Direction facing;
	protected Direction collisionDirecion;
	
	/**Creates an entity at given position
	 * @param posX - Position X
	 * @param posY - Position Y
	 */
	public Player(int posX, int posY) {
		super(posX, posY, new Model(50,100,Color.blue));
		setHitbox(posX,posY,50,100);

		this.facing = Direction.RIGHT;
		this.fallSpeed = 0;
		this.isInAir = true;
		this.collisionDirecion=Direction.NULL;
	}
	
	/**Moves the entity of 10 pixels left or right depending by movement booleans<br>
	 * Makes the entity fall because of gravity
	 */

    /**Sets fall speed to 10*/
	public void jump() {
		if(!isInAir) {
			this.fallSpeed = -8;
			this.isInAir = true;
		}
	}

	public void move() {
		this.posX+=movementSpeed;
		this.posY+=fallSpeed;
		updateHitbox(posX,posY);

		if(left&&right || !left&&!right) {
			movementSpeed*=0.8;
		}else if(left){
			movementSpeed--;
			this.facing = Direction.LEFT;
		}else if(right) {
			movementSpeed++;
			this.facing = Direction.RIGHT;
		}
		if(movementSpeed>0 && movementSpeed<0.5) movementSpeed=0;
		if(movementSpeed<0 && movementSpeed>-0.5) movementSpeed=0;
		if(movementSpeed>7) movementSpeed=7;
		if(movementSpeed<-7) movementSpeed=-7;

		fallSpeed += 0.3;
		isInAir=true;


	}

	public void checkCollision(Entity block){
		if(xMouvementCollision(getHitbox(),block.getHitbox(),movementSpeed)){
			if(this.posX < block.posX) {
				//right
				this.getHitbox().x=block.getHitbox().x-this.getHitbox().width;
				this.posX = this.getHitbox().x;
				this.movementSpeed=0;
				block.collisionWithPlayer(this,Direction.RIGHT);
			} else {
				//left
				this.getHitbox().x=block.getHitbox().x +this.getHitbox().width;
				this.posX = this.getHitbox().x;
				this.movementSpeed=0;
				block.collisionWithPlayer(this,Direction.LEFT);
			}
		}
		//check collision vertically
		if(yMouvementCollision(getHitbox(),block.getHitbox(),fallSpeed)){
			if(this.getHitbox().y < block.getHitbox().y) {
				//Below
				this.fallSpeed = 0;
				this.getHitbox().y = block.getHitbox().y - this.getHitbox().height;
				this.posY=this.getHitbox().y;
				this.isInAir = false;
				this.facing=Direction.NULL;
				block.collisionWithPlayer(this,Direction.DOWN);
				isInAir=false;
			} else {
				//Above
				this.fallSpeed = 0;
				this.getHitbox().y = block.getHitbox().y + this.getHitbox().height;
				this.posY=this.getHitbox().y;
				this.facing=Direction.NULL;
				block.collisionWithPlayer(this,Direction.UP);
			}
		}
	}

	public void checkCollision(Enemy enemy){
		if(xMouvementCollision(getHitbox(),enemy.getHitbox(),movementSpeed)){
			if(this.posX < enemy.posX) {
				//right
				this.getHitbox().x=enemy.getHitbox().x-this.getHitbox().width;
				this.posX = this.getHitbox().x;
				this.movementSpeed=0;
				enemy.collisionWithPlayer(this,Direction.RIGHT);
			} else {
				//left
				this.getHitbox().x=enemy.getHitbox().x +this.getHitbox().width;
				this.posX = this.getHitbox().x;
				this.movementSpeed=0;
				enemy.collisionWithPlayer(this,Direction.LEFT);
			}
		}
		//check collision vertically
		if(yMouvementCollision(getHitbox(),enemy.getHitbox(),fallSpeed)){
			if(this.getHitbox().y < enemy.getHitbox().y) {
				//Below
				this.fallSpeed = 0;
				this.getHitbox().y = enemy.getHitbox().y - this.getHitbox().height;
				this.posY=this.getHitbox().y;
				this.isInAir=false;
				this.facing=Direction.NULL;
				enemy.collisionWithPlayer(this,Direction.DOWN);
			} else {
				//Above
				this.fallSpeed = 0;
				this.getHitbox().y = enemy.getHitbox().y + this.getHitbox().height;
				this.posY=this.getHitbox().y;
				this.facing=Direction.NULL;
				enemy.collisionWithPlayer(this,Direction.UP);
			}
		}
	}



	@Override
	public void draw(int posX, int posY, Graphics graphics){
		graphics.setColor(getModel().getTexture());
		graphics.fillRect(posX, posY, getModel().width, getModel().height);
	}
	

	public void setLeft(boolean left) {this.left = left;}
	public void setRight(boolean right) {this.right = right;}
	public void setUp(boolean up) {this.up = up;}

	public void setDown(boolean down) {
		if(down && this.down!=down){
			this.getHitbox().height=50;
			this.getModel().height=50;
			this.posY+=50;
		}else if(!down){
			this.posY-=50;
			this.getHitbox().height=100;
			this.getModel().height=100;
		}
		this.down = down;
	}

	public void reset(){
		posX=50;
		posY=50;
		this.facing = Direction.RIGHT;
		this.fallSpeed = 0;
		this.movementSpeed=0;
		this.isInAir = true;
	}
	public boolean shouldDie(){
		if (this.posY>2000 || shouldDie){
			shouldDie=false;
			return true;
		}
		return false;
	}

	public double getFallSpeed() {
		return fallSpeed;
	}

	public void setFallSpeed(double fallSpeed) {
		this.fallSpeed = fallSpeed;
	}
}
