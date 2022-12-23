package code;
import java.awt.Rectangle;
import java.util.Random;

public class boss {
	int x, y, yVel=2, xVel=2,xPlace=0, yPlace=0, timer=0, tempMove=1,stillTimer=0; ;
	boolean alive = true,still=false;
	public boss() {
		//Starting location
		x=300;
		y=500;
	}
	
	public void animate() {
		xPlace=xPlace+32;
		if (xPlace>=96) {
			xPlace=0;
		}
		if (xVel>0 && yVel>0) {
			yPlace=0;
		}
		else if (xVel>0 && yVel<0) {
			yPlace=32;
		}
		else if (xVel<0 && yVel<0) {
			yPlace=64;
		}
		else if (xVel<0 && yVel>0) {
			yPlace=96;
		}
	}
	
	public void collision( int[][] overlay) {
		Rectangle bossMoveBox=new Rectangle(x+xVel+36, y+yVel+16, 28, 36);
		for (int row = 0; row < overlay.length; row++) {
			for (int col = 0; col < overlay.length; col++) {
				switch (overlay[row][col]) {
				// Tree
				case 1:
					Rectangle tree = new Rectangle(row * 32, col * 32 + 16, 64, 72);
					if (bossMoveBox.intersects(tree)) {
						xVel = -0;
						yVel = 0;
						break;
					}
				case 4:
				case 5:
					Rectangle caveEntrance = new Rectangle(col * 32, row * 32, 100, 100);
					if (bossMoveBox.intersects(caveEntrance)) {
						xVel = 0;
						yVel = 0;
						break ;
					}
				}

			}
		}
		move();
	}
	
	public void move() {
		Random rand = new Random();
		stillTimer++;
		//Moving
		if(!still) {
			x=x+xVel;
			y=y+yVel;
		}
		//When done moving
		tempMove--;
		if ((tempMove==0||yVel==0||xVel==0)&&!still) {
			tempMove=rand.nextInt(500);
			xVel=rand.nextInt(4)-2;
			if (xVel==0) {
				xVel=2;
			}
			
			yVel=rand.nextInt(4)-2;
			if (yVel==0) {
				yVel=2;
			}
			animate();
			timer=0;
		}
		if (timer==50) {
			animate();
			timer=0;
		}
		else {
			timer++;
		}//Timers to stop movement
		if (stillTimer==500 && still) {
			still=false;
			stillTimer=0;
		}else if (stillTimer==1500&&!still) {
			still=true;
			stillTimer=0;
		}
	}
}
