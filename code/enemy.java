package code;

import java.awt.Rectangle;

public class enemy {
	int x, y, yVel, xVel,xPlace=0,timer=0;
	Rectangle enemyHit;
	boolean step = false;
	
	//Making Enemies
	public enemy(int xTemp, int yTemp, int yVelTemp, int xVelTemp) {
		x = xTemp;
		y = yTemp;
		xVel = xVelTemp;
		yVel = yVelTemp;
	}
	
	public void collision(int [][] map, int [][]overlay) {
		enemyHit=getBounds();
		
	//Overlay Hit Boxes
	//Overlays Collisions
		runaway:
		for (int row = 0; row<overlay.length;row++) {	
			for (int col = 0; col<overlay.length;col++) {
				switch (overlay[row][col]) {
				//Tree
				case 1:
					Rectangle tree = new Rectangle(col * 32, row * 32 + 16, 64, 72);
					if (enemyHit.intersects(tree)) {
						xVel = -xVel;
						yVel = -yVel;
						break runaway;
					}
				}
			//Map Collisions
				switch (map[row][col]) {
				//Top & Left of Water
				case 3:
				case 4:
				case 5:
				case 7:
				case 11:
					Rectangle waterTop = new Rectangle(col * 32+16, (row * 32)+24, 32, 32);
					if (enemyHit.intersects(waterTop)){
						yVel = -1;
						break runaway;
					}
					break;
				//Bottom of Water
				case 15:
				case 16:
				case 17:
					Rectangle waterBottom = new Rectangle(col * 32+16, (row * 32)+24, 32, 12);
					if (enemyHit.intersects(waterBottom)){
						yVel=1;
						break runaway;
					}
					break;
				//Right Side of Water
				case 6:
				case 10:
				case 14:
					Rectangle waterRight =  new Rectangle(col * 32+16, (row * 32)+24, 4, 32);
					if (enemyHit.intersects(waterRight)){
						xVel=1;
						break runaway;
					}
					break;
				}
			}
		}
		checkDirection();
		move();
	}
	//Checking Direction After Moving 
	public void checkDirection() {
		if (xVel==0 && yVel==1 && !(xPlace==0||xPlace==16)) {
			xPlace=0;
		}
		else if (xVel==0 && yVel==-1 && !(xPlace==32||xPlace==48)) {
			xPlace=32;
		}
		else if (xVel==-1 && yVel==0 && !(xPlace==64||xPlace==80)) {
			xPlace=64;
		}
		else if (xVel==1 && yVel==0 && !(xPlace==96||xPlace==112)) {
			xPlace=96;
		}
	}
	//Moving Enemies
	public void move() {
		x = x + xVel;
		y = y + yVel;
		timer++;
		//Timer for the animation
		if (timer>25) {
			if (step) {
				step=false;
				xPlace=xPlace -16;
			}else {
				step=true;
				xPlace=xPlace +16;
			}
			timer=0;
		}
	}
	//Making Bounds
	public Rectangle getBounds() {
		return (new Rectangle(x+xVel, y+yVel+36, 32, 30));
	}
}
