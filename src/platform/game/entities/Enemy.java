package platform.game.entities;


import platform.framework.render.Model;
import platform.game.states.LevelOneState;
import platform.game.utils.Direction;

import java.awt.*;

public class Enemy extends Entity {


	protected double movementSpeed;
	protected double fallSpeed;
	protected boolean isInAir;

	protected Direction facing;


	public Enemy(int posX, int posY) {
		super(posX, posY, new Model(50,50, new Color(LevelOneState.random.nextInt(256), LevelOneState.random.nextInt(256), LevelOneState.random.nextInt(256))));
		setHitbox(posX,posY,50,50);

		this.facing = Direction.LEFT;
		this.fallSpeed = 0;
		this.isInAir = true;
	}


	@Override
	public void draw(int posX, int posY, Graphics graphics){
		graphics.setColor(getModel().getTexture());
		graphics.fillRect(posX, posY, getModel().width, getModel().height);
	}

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

		if(facing==Direction.NULL) {
			movementSpeed*=0.8;
		}else if(facing==Direction.LEFT) {
			movementSpeed-=0.5;
		}else if(facing==Direction.RIGHT) {
			movementSpeed+=0.5;
		}

		if(movementSpeed>5) movementSpeed=5;
		if(movementSpeed<-5) movementSpeed=-5;

		fallSpeed += 0.3;
		isInAir=true;
	}



	public void checkCollision(Entity block,Player player){
		//check collision vertically
		if(yMouvementCollision(getHitbox(),block.getHitbox(),fallSpeed)){
			if(this.getHitbox().y < block.getHitbox().y) {
				//Below
				this.fallSpeed = 0;
				this.getHitbox().y = block.getHitbox().y - this.getHitbox().height;
				this.posY=this.getHitbox().y;
				isInAir=false;

				if(player.getPosX()<this.posX)facing=Direction.LEFT;
				else  facing=Direction.RIGHT;

				//if(player.getPosY()>this.posY+50)
					jump();
			} else {
				//Above
				this.fallSpeed = 0;
				this.getHitbox().y = block.getHitbox().y + this.getHitbox().height;
				this.posY=this.getHitbox().y;
				if(player.getPosX()<this.posX){
					facing=Direction.LEFT;
				}else {
					facing=Direction.RIGHT;
				}
			}
		}
		if(xMouvementCollision(getHitbox(),block.getHitbox(),movementSpeed)){
			if(this.posX < block.posX) {
				//right
				this.getHitbox().x=block.getHitbox().x-this.getHitbox().width;
				this.posX = this.getHitbox().x;
				this.movementSpeed=0;

				if(player.getPosX()>this.posX)jump();
				else facing=Direction.LEFT;

			} else {
				//left
				this.getHitbox().x=block.getHitbox().x +this.getHitbox().width;
				this.posX = this.getHitbox().x;
				this.movementSpeed=0;

				if(player.getPosX()<this.posX) {
					jump();
				} else {
					facing=Direction.RIGHT;
				}

			}
		}

	}



	@Override
	public void collisionWithPlayer(Player player, Direction direction) {
		if(direction!=Direction.DOWN){
			player.shouldDie=true;
		}else {
			player.fallSpeed=-7;
		}
	}

}
