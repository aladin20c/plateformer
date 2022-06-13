package platform.game.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import platform.framework.gamestates.GameState;
import platform.framework.gamestates.GameStateManager;
import platform.framework.render.Renderer;
import platform.game.entities.*;


public class LevelOneState extends GameState {
	public static Random random=new Random();


	private Player player;
	private Camera camera;
	private ArrayList<Entity> blocks;
	private ArrayList<Enemy>enemiesInLevel;

	private int lastPlayerPosition=0;
	private int lastBlockIndex;

	
	public LevelOneState(GameStateManager gsm) {
		super(gsm);
		System.out.println("[GameStates][LevelOneState]: Creating level state...");
	}

	@Override
	public void init() {
		this.player = new Player(40,40);
		this.camera = new Camera(player);
		enemiesInLevel=new ArrayList<>();
		makeWalls();
	}
	private void reset() {
		this.player.reset();
		lastPlayerPosition=player.getPosX();
		blocks.clear();
		enemiesInLevel.clear();
		makeWalls();
	}
	public void makeWalls(){
		if(this.blocks==null) this.blocks=new ArrayList<>();
		for(int i=50;i<400;i+=50){
			blocks.add(new Wall(i,400,50,50));
		}
		this.lastBlockIndex=this.blocks.get(blocks.size() - 1).getPosX() + 50;
	}






	@Override
	public void tick() {
		moveEntities();
		checkCollisions();
		checkDeath();
		makeMoreWalls();
		findEntitiesToDespawn();
		this.camera.setPosition(player);
	}

	@Override
	public void render(Graphics graphics) {
		Renderer.renderEntity(player,camera,graphics);
		
		for(Entity block : this.blocks) {
			Renderer.renderEntity(block,camera,graphics);
		}
		for(Enemy enemy : this.enemiesInLevel) {
			Renderer.renderEntity(enemy,camera,graphics);
		}
	}



	@Override
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			this.player.setLeft(true);
		}
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			this.player.setRight(true);
		}
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			this.player.setUp(true);
			this.player.jump();
		}
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			this.player.setDown(true);
		}
		if(key == KeyEvent.VK_O) {
			this.reset();
		}
		if(key == KeyEvent.VK_SPACE) {
			this.player.setFallSpeed(player.getFallSpeed()-8);
		}

	}
	@Override
	public void keyReleased(int key) {
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			this.player.setLeft(false);
		}
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			this.player.setRight(false);
		}
		if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			this.player.setUp(false);
		}
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			this.player.setDown(false);
		}
	}





	private void checkDeath() {
		if(player.shouldDie()) reset();
	}
	public void findEntitiesToDespawn(){
		while (!this.blocks.isEmpty()&&this.blocks.get(0).getPosX()<lastPlayerPosition-500){
			this.blocks.remove(0);
		}
		while (!this.enemiesInLevel.isEmpty()&&this.enemiesInLevel.get(0).getPosX()<lastPlayerPosition-500){
			this.enemiesInLevel.remove(0);
		}
	}

	public void makeMoreWalls(){
		if(player.getPosX()>lastPlayerPosition) {
			lastPlayerPosition=player.getPosX();
			if(lastBlockIndex<lastPlayerPosition+500) {
				int beginIndexY = this.blocks.get(blocks.size() - 1).getPosY();
				switch (random.nextInt(9)) {
					case 0:
						blocks.add(new Wall(lastBlockIndex, beginIndexY, 50, 50));
						addTrap(lastBlockIndex,beginIndexY);
						break;
					case 1:
						beginIndexY+=50;
						blocks.add(new Wall(lastBlockIndex, beginIndexY, 50, 50));
						addTrap(lastBlockIndex,beginIndexY);
						break;
					case 2:
						beginIndexY-=50;
						blocks.add(new Wall(lastBlockIndex, beginIndexY , 50, 50));
						addTrap(lastBlockIndex,beginIndexY);
						break;
					case 3:
						beginIndexY-=98;
						blocks.add(new Wall(lastBlockIndex, beginIndexY , 50, 50));
						//addTrap(lastBlockIndex,beginIndexY);
						break;
					case 4:
						beginIndexY+=25;
						blocks.add(new Wall(lastBlockIndex, beginIndexY , 50, 50));
						addTrap(lastBlockIndex,beginIndexY);
						break;
					case 5:
						beginIndexY-=25;
						blocks.add(new Wall(lastBlockIndex, beginIndexY , 50, 50));
						addTrap(lastBlockIndex,beginIndexY);
						break;
					case 6:
						beginIndexY+=100;
						blocks.add(new Wall(lastBlockIndex, beginIndexY , 50, 50));
						addTrap(lastBlockIndex,beginIndexY);
						break;
				}
				lastBlockIndex+=50;
			}
		}
	}
	public void addTrap(int x,int y){
		int r=random.nextInt(30);
		if(r==10 || r==3 ) blocks.add(new Trampoline(x,y-20));
		else if(r==14) blocks.add(new Spikes(x,y-20));
		else if(r==25 ) enemiesInLevel.add(new Enemy(x,y-75));
	}


	public void moveEntities(){
		this.player.move();
		for(Enemy enemy : this.enemiesInLevel) {
			enemy.move();
		}
	}
	public void checkCollisions() {
		for(Entity block : this.blocks) {
			this.player.checkCollision(block);

			for(Enemy enemy : this.enemiesInLevel) {
				enemy.checkCollision(block,player);
			}
			for(Enemy enemy : this.enemiesInLevel) {
				player.checkCollision(enemy);
			}
		}
	}




}
