/**********************************************************
* Program	:  Game ISU
* Author	:  Christian Majid
* Due Date	: January 20 2019
* Description	:  Zelda knock off cuz I'm lazy and 
* unoriginal. You can have a couple weapons and kills enemies.
* Make it through the game in one piece.
***********************************************************/

package code;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Rectangle;
import javax.sound.sampled.*;
import java.util.Random;

public class gameBoard extends JPanel implements KeyListener, ActionListener{
	//Images
	private BufferedImage link, attack, water, shrub, rupee,enemySprite,heart,caveCorners, caveExit, caveBlock, dab, chest,
	heartGround,firingImg, arrowFlying, weaponSelect,BOTW,textbox,selectbox,handBoss;
	ImageIcon dirt = new ImageIcon(getClass().getResource("/images/dirt.png"));
	ImageIcon grass1 = new ImageIcon(getClass().getResource("/images/grass1.png"));
	ImageIcon grass2 = new ImageIcon(getClass().getResource("/images/grass2.png"));
	ImageIcon grass3 = new ImageIcon(getClass().getResource("/images/grass3.png"));
	ImageIcon tree = new ImageIcon(getClass().getResource("/images/tree.png"));
	ImageIcon rupeeSingle = new ImageIcon(getClass().getResource("/images/rupeeSingle.png"));
	ImageIcon cliff = new ImageIcon(getClass().getResource("/images/cliff.png"));
	ImageIcon caveEntrance = new ImageIcon(getClass().getResource("/images/caveEntrance.png"));
	ImageIcon caveFloor = new ImageIcon(getClass().getResource("/images/caveFloor.png"));
	ImageIcon dab1 = new ImageIcon(getClass().getResource("/images/dab.png"));
	ImageIcon rock1 = new ImageIcon(getClass().getResource("/images/rock1.png"));
	ImageIcon rock2 = new ImageIcon(getClass().getResource("/images/rock2.png"));
	ImageIcon obtained = new ImageIcon(getClass().getResource("/images/obtained.png"));
	ImageIcon navi = new ImageIcon(getClass().getResource("/images/navi.gif"));
	ImageIcon arrowImg = new ImageIcon(getClass().getResource("/images/arrowCounterImg.png"));
	ImageIcon gameover = new ImageIcon(getClass().getResource("/images/gameover.jpg"));
	ImageIcon endScreen = new ImageIcon(getClass().getResource("/images/endScreen.png"));
	ImageIcon helpMenu = new ImageIcon(getClass().getResource("/images/help.png"));
	ImageIcon tri = new ImageIcon(getClass().getResource("/images/tri.gif"));
	ImageIcon vaa = new ImageIcon(getClass().getResource("/images/vaa.gif"));


	//Variables
	int xPlayer=75, yPlayer=520, xVel=0, yVel=0, xPlace=0, yPlace=0,timer=0, randomInt=0, lives=6, 
			rupeeTimer=0,rupeeCounter=0,rupeeNum=0, section=1, chestTimer=100, obtainedTimer=0,invinsibility = 0,
			blinkTimer=0, firingTimer, arrowXVel=0, arrowYVel=0, arrowY, arrowX, page=0,textSelect=2, arrows=0, bossHealth=600;
	boolean attacking=false, hitboxes = false, cave=false, blinking=false, firing=false,fireReady=false, fireNow=false,weaponChoice=true,
			shop=false, bowObtained=false, win=false,help=false;
	Random rand = new Random ();
	Rectangle wall = new Rectangle();
	Rectangle enemyRect;
	arrays arrays = new arrays();
    String rupeeString = "10", arrowString="";
	Font f1 = new Font("Algerian", Font.BOLD, 18);
	Font f2 = new Font("Algerian", Font.BOLD, 28);
	boss boss = new boss();
	
	//Call First Map
	int[][] map = arrays.maps(section);
	int[][] overlay = arrays.overlays(section);
	enemy [] enemy  = arrays.enemy(section);
	
	public gameBoard() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		// Load Sprite Images
		try {
			link = ImageIO.read(getClass().getResource("/images/link.png"));
			attack = ImageIO.read(getClass().getResource("/images/attack.png"));
			water = ImageIO.read(getClass().getResource("/images/water.png"));
			shrub = ImageIO.read(getClass().getResource("/images/shrub.png"));
			rupee = ImageIO.read(getClass().getResource("/images/rupee.png"));
			enemySprite = ImageIO.read(getClass().getResource("/images/enemy.png"));
			caveCorners = ImageIO.read(getClass().getResource("/images/caveCorners.png"));
			caveExit = ImageIO.read(getClass().getResource("/images/caveExit.png"));
			heart = ImageIO.read(getClass().getResource("/images/heart.png"));
			caveBlock = ImageIO.read(getClass().getResource("/images/caveBlock.png"));
			dab = ImageIO.read(getClass().getResource("/images/dab.png"));
			chest = ImageIO.read(getClass().getResource("/images/chest.png"));
			heartGround = ImageIO.read(getClass().getResource("/images/heartGround.png"));
			firingImg = ImageIO.read(getClass().getResource("/images/firing.png"));
			arrowFlying = ImageIO.read(getClass().getResource("/images/arrowFlying.png"));
			weaponSelect = ImageIO.read(getClass().getResource("/images/weaponSelect.png"));
			BOTW = ImageIO.read(getClass().getResource("/images/linkBOTW.png"));
			textbox = ImageIO.read(getClass().getResource("/images/textbox.png"));
			selectbox = ImageIO.read(getClass().getResource("/images/selectbox.png"));
			handBoss = ImageIO.read(getClass().getResource("/images/handBoss.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		Timer timer = new Timer(10, this);
		timer.start();
	}
	
	//Pressing Keys
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();

		if (!shop) {
			if (key == KeyEvent.VK_SPACE){
				if (!firing && !weaponChoice&&arrows>0) {
					timer=0;
					firing = true;
					xPlace = 0;
				}
			}
			//Switching Weapon
			if (key == KeyEvent.VK_SHIFT && !firing && bowObtained){
				weaponChoice=!weaponChoice;
			}
			
			//Moving Right
			if (key == KeyEvent.VK_RIGHT && yPlayer>0  && yPlayer<620){
				xVel=2;
				yPlace=150;
				yVel=0;
			}//Moving Left
			if (key == KeyEvent.VK_LEFT && yPlayer>0  && yPlayer<620){
				xVel=-2;
				yPlace=75;
				yVel=0;
			}//Moving Down
			if (key == KeyEvent.VK_DOWN && xPlayer>0  && xPlayer<620){
				yVel=2;
				yPlace=0;
				xVel=0;
			}//Moving Up
			if (key == KeyEvent.VK_UP && xPlayer>0  && xPlayer<620){
				yVel=-2;
				yPlace=225;
				xVel=0;	
			}
		}
		//Changing Arrays
			//Caves
		if (cave) {
			if (yPlayer<0||yPlayer>550||xPlayer<60||xPlayer>570) {	
				//Exiting Down
				if (yPlayer>550 && key == KeyEvent.VK_DOWN) {
					yPlayer=70;
					if (section==4) {
						section=3;
						cave=false;
					}
					else if (section==6) {
						section=7;
						yPlayer=75;
						cave=false;
					}
				//Moving Left
				}else if (xPlayer<40 && key == KeyEvent.VK_LEFT) {
					xPlayer=580;
					if (section==5) {
						section=4;
					}
					else if (section==6) {
						section=5;
					}
				//Moving Right
				}else if (xPlayer>580 && key == KeyEvent.VK_RIGHT) {
					xPlayer=40;
					if (section==4) {
						section=5;
					}else if (section==5) {
						section=6;
					}
				}
				map = arrays.maps(section);
				overlay = arrays.overlays(section);
				enemy  = arrays.enemy(section);
			}
		}else {
			//Cave Entrance 1
			if (xPlayer>100 && xPlayer<132 && yPlayer<65&& section==3) {
					section=4;
					map = arrays.maps(section);
					overlay = arrays.overlays(section);
					enemy  = arrays.enemy(section);
					yPlayer=570;
					cave=true;
				}
			//Cave Entrance 2
			else if (xPlayer>477 && xPlayer<520 && yPlayer<65&& section==7) {
				section=6;
				map = arrays.maps(section);
				overlay = arrays.overlays(section);
				enemy  = arrays.enemy(section);
				yPlayer=570;
				cave=true;
			}
			//Switching Screens
			else if (yPlayer<0||yPlayer>650||xPlayer<0||xPlayer>650) {		
				if (yPlayer<0 && key == KeyEvent.VK_UP) {
					yPlayer=680;
					if (section==1) {
						section =2;
						
					}else if (section==2) {
						section=3;
					}
					
				}else if (yPlayer>650 && key == KeyEvent.VK_DOWN) {
					yPlayer=-80;
					if (section==2) {
						section=1;
					
					}else if (section==3) {
						section=2;
					}
					else if (section==7) {
						win=true;
					}
				}
				map = arrays.maps(section);
				overlay = arrays.overlays(section);
				enemy  = arrays.enemy(section);
			}
		}
		if (key==KeyEvent.VK_H) {
			help = true;
		}
	}

	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();	
	}
	
	//Letting Go of Keys and Resetting Position
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		//Choosing Things in Shop
		if (shop && key== KeyEvent.VK_DOWN) {
			textSelect++;
			if (textSelect>3) {
				textSelect=1;
			}
		}
		else if (shop && key== KeyEvent.VK_UP) {
			textSelect--;
			if (textSelect<1) {
				textSelect=3;
			}
		}
		switch (key) {
		//Right & Left Reset
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_LEFT:
			xVel=0;
			break;
		//Up & Down Reset
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_UP:
			yVel=0;
			break;
		//Attacking & Opening
		case KeyEvent.VK_SPACE :
			if(yPlayer>0 &&yPlayer<650 && xPlayer>0 &&xPlayer<650) {
				if (chestAbove(key)) {
					chestTimer=0;
				}//Run Shop Code
				else if (shop) {
					shopCode();
				}//Fire Arrow Npw
				else if (firing&&fireReady && !weaponChoice) {
					fireNow=true;
					xPlace=452;
					xVel=0;
					yVel=0;
				}//Start Firing Arrow
				else if (firing && !weaponChoice) {
					firing=false;
					fireNow=false;
					fireReady=false;
					xPlace=0;
				}//Sword Attack
				else if (!attacking && weaponChoice) {
					timer=0;
					attacking = true;
					xPlace = 0;
				}
				break;
			}
		case KeyEvent.VK_H :
			//hitboxes=!hitboxes;
			help=false;
		}
	}

	// Actions
	public void actionPerformed(ActionEvent e) {
		// Check Collision before the Repaint
		collision();
		//Enemy Walking Collisions
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].collision(map, overlay);
		}
		if (section==7) {
			boss.collision(overlay);
		}
		repaint();
		//System.out.println(xPlayer + "   -   " + yPlayer + "   -   " + boss.stillTimer + "   -   "+ shop);
	}
	
	//if Chest Above
	public boolean chestAbove(int key) {
		//Top of Link Hitbox for Chest
		Rectangle playerChest= new Rectangle (xPlayer+xVel, yPlayer+yVel-10, 26, 35);
		if (key==KeyEvent.VK_SPACE && yPlace==225) {
			for (int row = 0; row<overlay.length;row++) {	
				for (int col = 0; col<overlay.length;col++) {
					switch (overlay[row][col]){
					case 8:
					case 9:
					case 10:
						//Chest Closed Hitbox
						Rectangle chestClosed = new Rectangle(col * 32, row * 32-8, 32, 32);
						if (playerChest.intersects(chestClosed)) {
							overlay[row+1][col]=13;
							overlay[row][col]=11;
							//Start timer for opening
							chestTimer=0;
							return true;
						}
					case 14:
						Rectangle shopBox = new Rectangle(col*32, row*32, 30, 36);
						if (playerChest.intersects(shopBox)&&!shop) {
							shop=true;
							page=-1;
							return false;
						}
						break;
					}
				}
			}
		}
		return false;
	}
	//Coding for Shop Order
	public void shopCode() {
		switch (page) {
		case -1:
			textSelect=1;
			if (!bowObtained) {
				page=0;
			}else {
				page=4;
			}
			break;
		//Greetings
		case 0:
			page=1;
			break;
		//Asking for Bow
		case 1:
			if (textSelect==1&&rupeeNum>=50) {
				bowObtained=true;
				arrows=arrows+15;
				rupeeNum=rupeeNum-50;
				page=2;
			}
			else if(textSelect==2) {
				page=5;
			}
			else if (textSelect==3){
				textSelect=1;
			}
			else { 
				page=3;
			}
			break;
		//Thank You for Purchase
		case 2:
			shop=false;
			break;
		//Not enough money
		case 3:
			shop=false;
			break;
		//Asking for health or rupeees
		case 4:
			if (textSelect==1&&rupeeNum>=20) {
				rupeeNum=rupeeNum-20;
				page=2;
				lives=6;
			}
			else if(textSelect==2&&rupeeNum>=20) {
				rupeeNum=rupeeNum-20;
				page=2;
				arrows=arrows+5;
			}
			else { 
				page=3;
			}
			break;
		//Nothing today, please come again
		case 5:
			shop=false;
			break;
		}
	}
	
	//Collisions
	public void collision(){	
		Rectangle player = new Rectangle(xPlayer+xVel, yPlayer+yVel+40, 26, 15);
		Rectangle sword = new Rectangle();
		Rectangle arrow = new Rectangle();
		Rectangle playerBoss= new Rectangle (xPlayer+xVel, yPlayer+yVel, 26, 35);
		//Attacking Hit Box
		if (attacking) {
			if (yPlace==0){
				sword = new Rectangle (xPlayer-5, yPlayer+18,50,35);
			}
			else if (yPlace==75) {
				sword = new Rectangle (xPlayer-22, yPlayer-7,33,64);
			}
			else if (yPlace==150) {
				sword = new Rectangle (xPlayer+18, yPlayer-7,33,64);
			}
			else if (yPlace==225) {	
				sword = new Rectangle (xPlayer-15, yPlayer-15,50,35);
			}
		}
		//Arrow Direction
		if (arrowXVel!=0 ||arrowYVel!=0 ) {
			//Arrow Up
			if (arrowYVel==1) {
				arrow = new Rectangle (arrowX+12, arrowY, 12, 42);
			}//Arrow Down
			else if (arrowYVel==-1) {
				arrow = new Rectangle (arrowX+5, arrowY, 12, 42);
			}//Arrow Left
			else if (arrowXVel==-1) {
				arrow = new Rectangle (arrowX+5, arrowY+13, 42, 12);
			}//Arrow Right
			else if (arrowXVel==1) {
				arrow = new Rectangle (arrowX, arrowY+13, 42, 12);
			}
		}
		
	//Hitting Enemies
		for (int i =0; i<enemy.length;i++) {
			enemyRect=new Rectangle(enemy[i].x+enemy[i].xVel, enemy[i].y+enemy[i].yVel+26, 32, 26);
			if (sword.intersects(enemyRect)){
				enemy[i].x=-100;
				enemy[i].y=-100;
			}else if (arrow.intersects(enemyRect)) {
				enemy[i].x=-100;
				enemy[i].y=-100;
				arrowXVel=0;
				arrowYVel=0;
			}
			Rectangle enemyBody = new Rectangle (enemy[i].x+enemy[i].xVel, enemy[i].y+enemy[i].yVel+45, 32, 30);		
			if (player.intersects(enemyBody)&&invinsibility==0) {
				lives=lives-1;
				invinsibility=1000;
			}
		}
		//Hitting boss
		if (section==7) {
			Rectangle bossHitBox=new Rectangle(boss.x, boss.y, 64, 64);
			if (sword.intersects(bossHitBox)) {
				bossHealth=bossHealth-1;
			}else if (arrow.intersects(bossHitBox)) {		
				arrowXVel=0;
				arrowYVel=0;
				bossHealth=bossHealth-30;
				if (!boss.still) {
					boss.still=true;
					boss.stillTimer=0;
				}
			}
			if (playerBoss.intersects(bossHitBox)&&invinsibility==0) {
				lives=lives-1;
				invinsibility=1000;
			}
		}
		
		
	//Overlay Hit Boxes
	//Overlays Collisions
		runaway:
		for (int row = 0; row<overlay.length;row++) {	
			for (int col = 0; col<overlay.length;col++) {
				switch (overlay[row][col]) {
				//Tree
				case 1:
					Rectangle tree = new Rectangle(col * 32, row * 32 + 16, 64, 72);
					if (player.intersects(tree)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					if (arrow.intersects(tree)) {
						arrowXVel=0;
						arrowYVel=0;
						break runaway;
					}
					break;

				// Shrub
				case 2:
					if (attacking) {
						Rectangle shrub = new Rectangle(col * 32, row * 32, 28, 28);
						if (sword.intersects(shrub)) {
							overlay[row][col] = 3;
							break runaway;
						}
					}
					break;

				//Rupees
				case 3:
					Rectangle rupee = new Rectangle(col*32+4, row*32+18, 24, 28);
					if (player.intersects(rupee)) {
						overlay[row][col] = 0;
						rupeeNum++;
						break runaway;
					}
					break;
				case 4:
					//Cliffs
					Rectangle cliff = new Rectangle(col * 32, row * 32, 100, 100);
					if (player.intersects(cliff)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					break;
					//Entrance
				case 5:
					Rectangle caveEntrance = new Rectangle(col * 32, row * 32, 100, 100);
					if (player.intersects(caveEntrance)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					break;
					//Rocks
				case 6:
				case 7:
					Rectangle rock = new Rectangle(col * 32, row * 32+16, 32, 32);
					if (player.intersects(rock)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}//Chest
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
					Rectangle chest = new Rectangle(col * 32, row * 32+10, 32, 32);
					if (player.intersects(chest)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					break;
					//Hearts
				case 13:
					Rectangle heart = new Rectangle(col*32+6, row*32+16, 24, 22);
					if (player.intersects(heart)) {
						obtainedTimer=750;
						overlay[row][col] = 0;
						if (lives==5) {
							lives++;
						}
						else if (lives<5) {
							lives=lives+2;
						}
						break runaway;
					}
					break;
					//Lonk
				case 14:
					Rectangle Lonk = new Rectangle(col * 32, row * 32+32, 32, 32);
					if (player.intersects(Lonk)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					break;
				}
		
			//Map Collisions
				switch (map[row][col]) {
				//Grassy
				//Top & Left of Water
				case 3:
				case 4:
				case 5:
				case 7:
				case 11:
					Rectangle waterTop = new Rectangle(col * 32+16, (row * 32)+24, 32, 32);
					if (player.intersects(waterTop)){
						xVel=0;
						yVel=0;
						break runaway;
					}
					break;
				//Bottom of Water
				case 15:
				case 16:
				case 17:
					Rectangle waterBottom = new Rectangle(col * 32+16, (row * 32)+24, 32, 12);
					if (player.intersects(waterBottom)){
						xVel=0;
						yVel=0;
						break runaway;
					}
					break;
				//Right Side of Water
				case 6:
				case 10:
				case 14:
					Rectangle waterRight =  new Rectangle(col * 32+16, (row * 32)+24, 4, 32);
					if (player.intersects(waterRight)){
						xVel=0;
						yVel=0;
						break runaway;
					}
					break;
					
				//Bottom of Cave
				case 22:
				case 23:
					Rectangle caveBottom = new Rectangle(col * 32 -2, (row * 32) + 24, 32, 12);
					if (player.intersects(caveBottom)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					break;
				// Top of Cave
				case 34:
				case 35:
					Rectangle caveTop = new Rectangle(col * 32 + 16, (row * 32) + 8, 32, 32);
					if (player.intersects(caveTop)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					break;
				case 37:
					Rectangle caveBlank = new Rectangle(col * 32 , (row * 32) , 32, 32);
					if (player.intersects(caveBlank)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					break;
				// Right of Cave
				case 29:
				case 25:
					Rectangle caveRight =  new Rectangle(col * 32-4, (row * 32)+12, 32, 36);
					if (player.intersects(caveRight)){
						xVel=0;
						yVel=0;
						break runaway;
					}
					break;
				// Left of Cave
				case 28:
				case 32:
					Rectangle caveLeft = new Rectangle(col * 32 - 4, (row * 32) + 12, 32, 36);
					if (player.intersects(caveLeft)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
					break;
				//Cave Board
				case 42:
				case 43:
				case 44:
				case 45:
				case 46:
				case 47:
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
				case 58:
					Rectangle caveBoard = new Rectangle(col * 32, (row * 32)+16, 32, 32);
					if (player.intersects(caveBoard)) {
						xVel = 0;
						yVel = 0;
						break runaway;
					}
				}
			}
		}
		move();
	}
	
	//Method to Move Player
	public void move() {
		if (obtainedTimer==0&&!shop) {
			xPlayer=xPlayer+xVel;
			yPlayer=yPlayer+yVel;
		}
	}
	
	//Paint
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		//Find efficient way of doing this
		for (int row = 0; row<map.length;row++) {
			for (int col = 0; col<map.length;col++) {
				//0-3 Drawing Grass
				if (map [col][row] == 0 ){
					grass1.paintIcon(this, g, row * 32, col * 32);
				}
				else if (map [col][row] == 1 ){
					grass2.paintIcon(this, g, row * 32, col * 32);
				}				
				else if (map [col][row] == 2 ){
					grass3.paintIcon(this, g, row * 32, col * 32);
				}
				//Drawing Water
				else if (map [col][row] == 3 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 0, 0, 32, 32, null);
				}
				else if (map [col][row] == 4 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 32, 0, 64, 32, null);	
				}
				else if (map [col][row] == 5 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 64, 0, 96, 32, null);				
				}
				else if (map [col][row] == 6 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 96, 0, 128, 32, null);				
				}
				else if (map [col][row] == 7 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 0, 32, 32, 64, null);
				}
				else if (map [col][row] == 8 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 32, 32, 64, 64, null);	
				}
				else if (map [col][row] == 9 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 64, 32, 96, 64, null);				
				}
				else if (map [col][row] == 10 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 96, 32, 128, 64, null);				
				}
				else if (map [col][row] == 11 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 0, 64, 32, 96, null);
				}
				else if (map [col][row] == 12 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 32, 64, 64, 96, null);				
				}
				else if (map [col][row] == 13 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 64, 64, 96, 96, null);
				}
				else if (map [col][row] == 14 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 96, 64, 128, 96, null);
				}
				else if (map [col][row] == 15 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 0, 96, 32, 128, null);
				}
				else if (map [col][row] == 16 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 32, 96, 64, 128, null);				
				}
				else if (map [col][row] == 17 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 64, 96, 96, 128, null);
				}
				else if (map [col][row] == 18 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 96, 96, 128, 128, null);
				}
				else if (map [col][row] == 19 ){
					g.drawImage(water, row*32, col*32, row*32+32, col*32+32, 40, 40, 72, 72, null);
				}
				//Drawing Cave
				//Floor
				else if (map [col][row] == 20 ){
					caveFloor.paintIcon(this, g, row * 32, col * 32);
				}
				//Edges
				else if (map [col][row] == 21 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 0, 0, 32, 32, null);
				}
				else if (map [col][row] == 22 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 32, 0, 64, 32, null);	
				}
				else if (map [col][row] == 23 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 64, 0, 96, 32, null);				
				}
				else if (map [col][row] == 24 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 96, 0, 128, 32, null);				
				}
				else if (map [col][row] == 25 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 0, 32, 32, 64, null);
				}
				else if (map [col][row] == 26 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 32, 32, 64, 64, null);	
				}
				else if (map [col][row] == 27 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 64, 32, 96, 64, null);				
				}
				else if (map [col][row] == 28 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 96, 32, 128, 64, null);				
				}
				else if (map [col][row] == 29 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 0, 64, 32, 96, null);
				}
				else if (map [col][row] == 30 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 32, 64, 64, 96, null);				
				}
				else if (map [col][row] == 31 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 64, 64, 96, 96, null);
				}
				else if (map [col][row] == 32 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 96, 64, 128, 96, null);
				}
				else if (map [col][row] == 33 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 0, 96, 32, 128, null);
				}
				else if (map [col][row] == 34 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 32, 96, 64, 128, null);				
				}
				else if (map [col][row] == 35 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 64, 96, 96, 128, null);
				}
				else if (map [col][row] == 36 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 96, 96, 128, 128, null);
				}
				else if (map [col][row] == 37 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 40, 40, 72, 72, null);
				}
				//4 Exits
				else if (map [col][row] == 38 ){
					g.drawImage(caveExit, row*32, col*32, row*32+32, col*32+32, 0,0,32,32, null);
				}
				else if (map [col][row] == 39 ){
					g.drawImage(caveExit, row*32, col*32, row*32+32, col*32+32, 0,32,32,64, null);
				}
				else if (map [col][row] == 40 ){
					g.drawImage(caveExit, row*32, col*32, row*32+32, col*32+32, 0, 64,32,96, null);
				}
				else if (map [col][row] == 41 ){
					g.drawImage(caveExit, row*32, col*32, row*32+32, col*32+32, 0,96,32,128, null);
				}
				//Cave Blocks
				else if (map [col][row] == 42 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 0, 0, 32, 32, null);
				}
				else if (map [col][row] == 43 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 32, 0, 64, 32, null);	
				}
				else if (map [col][row] == 44 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 64, 0, 96, 32, null);				
				}
				else if (map [col][row] == 45 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 96, 0, 128, 32, null);				
				}
				else if (map [col][row] == 46 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 0, 32, 32, 64, null);
				}
				else if (map [col][row] == 47 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 32, 32, 64, 64, null);	
				}
				else if (map [col][row] == 48 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 64, 32, 96, 64, null);				
				}
				else if (map [col][row] == 49 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 96, 32, 128, 64, null);				
				}
				else if (map [col][row] == 50 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 0, 64, 32, 96, null);
				}
				else if (map [col][row] == 51 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 32, 64, 64, 96, null);				
				}
				else if (map [col][row] == 52 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 64, 64, 96, 96, null);
				}
				else if (map [col][row] == 53 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 96, 64, 128, 96, null);
				}
				else if (map [col][row] == 54 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 0, 96, 32, 128, null);
				}
				else if (map [col][row] == 55 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 32, 96, 64, 128, null);				
				}
				else if (map [col][row] == 56 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 64, 96, 96, 128, null);
				}
				else if (map [col][row] == 57 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 96, 96, 128, 128, null);
				}
				else if (map [col][row] == 58 ){
					g.drawImage(caveBlock, row*32, col*32, row*32+32, col*32+32, 40, 40, 72, 72, null);
				}//Blank Spot
				else if (map [col][row] == 59 ){
					g.drawImage(caveCorners, row*32, col*32, row*32+32, col*32+32, 40, 40, 72, 72, null);
				}
			}
		}
		//Drawing Overlays
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map.length; col++) {
				//Trees
				if (overlay[col][row] == 1) {
					tree.paintIcon(this, g, row * 32, col * 32);
				}//Shrubs
				else if (overlay[col][row] == 2) {
					g.drawImage(shrub, row * 32, col * 32, row * 32 + 32, col * 32 + 32, 0+(overlay[col][row]-2)*64, 0, 64+(overlay[col][row]-2), 64, null);
				}//Tree
				else if (overlay[col][row] == 3) {
					g.drawImage(rupee, row*32+6, col*32-28, row*32+56 , col*32+24, rupeeCounter*24, 0, rupeeCounter*24+24, 32 , null);
				}//Entrance to Cave
				else if (overlay[col][row] == 4) {
					caveEntrance.paintIcon(this, g, row * 32, col * 32);			
				}//Cliff
				else if (overlay[col][row] == 5) {
					cliff.paintIcon(this, g, row * 32, col * 32);	
				}//Rocks
				else if (overlay [col][row] == 6 ){
				 	rock1.paintIcon(this, g, row * 32, col * 32);
				}
				else if (overlay [col][row] == 7 ){
				 	rock2.paintIcon(this, g, row * 32, col * 32);
				}//Chest
				else if (overlay [col] [row]==8) {
					g.drawImage(chest, row*32+2, col*32-6, row*32+34 , col*32+32-6, 0, 0, 32, 38 , null);
				}
				else if (overlay [col] [row]==11) {
					g.drawImage(chest, row*32+2, col*32-6, row*32+34 , col*32+32-6, 32, 0, 64, 38 , null);
					chestTimer++;
					if (chestTimer==200) {
						overlay[col][row]=12;
					}
				}
				else if (overlay [col] [row]==12) {
					g.drawImage(chest, row*32+2, col*32-6, row*32+34 , col*32+32-6, 64, 0, 96, 38 , null);
				}
				else if (overlay [col] [row]==13) {
					g.drawImage(heartGround, row*32+6, col*32, row*32+32+6, col*32+22, rupeeCounter*32, 0, rupeeCounter*32+32, 22 , null);
				}
				else if (overlay [col] [row]==14) {
					g.drawImage(BOTW, row*32, col*32, row*32+30, col*32+44, 0, 0, 18, 22 , null);
				}
			}
		}

		//Arrow Fired 
		if (arrowXVel!=0||arrowYVel!=0) {
			arrowX=arrowX+arrowXVel;
			arrowY=arrowY+arrowYVel;
			//Arrow Up
			if (arrowYVel==1) {
				g.drawImage(arrowFlying, arrowX+12, arrowY, arrowX+12+12, arrowY+42, 0, 0,  7,  25, null);
			}//Arrow Down
			else if (arrowYVel==-1) {
				g.drawImage(arrowFlying, arrowX+5, arrowY, arrowX+5+12, arrowY+42, 0, 25,  7,  0, null);
			}//Arrow Left
			else if (arrowXVel==-1) {
				g.drawImage(arrowFlying, arrowX+5, arrowY+13, arrowX+5+42, arrowY+12+13, 25, 0,  7,  7, null);
			}//Arrow Right
			else if (arrowXVel==1) {
				g.drawImage(arrowFlying, arrowX, arrowY+13, arrowX+42, arrowY+12+13, 7, 0,  25,  7,null);
			}
		}
		//Timers to adjust animation
		timer++;
		if (!attacking && !firing && timer >= 100) {
			timer = 0;
			xPlace = xPlace + 32;
			//Reset Walking Position
			if (xPlace >= 128) {
				xPlace = 0;
			}
			//Stopped Animation
			if (xVel == 0 && yVel == 0) {
				if (yPlace == 75 || yPlace == 150) {
					xPlace = 64;
				} else {
					xPlace = 0;
				}
			}
		}
		//Sword Animation
		else if (attacking && timer >= 20) {
			timer = 0;
			xPlace = xPlace + 60;
			//After Swing Finished
			if (xPlace >= 480) {
				xPlace = 0;
				attacking = false;
			}
		}
		
		//Bow Animation
		else if (firing && timer >= 50 && !fireReady) {
			timer = 0;
			//After Draw Finished
			if (xPlace >= 105 ) {
				fireReady=true;
				xPlace=155;
			}else {
				xPlace = xPlace + 36;
			}
		}//Drawing Arrow
		else if (firing && timer >= 40 && fireReady && (xVel != 0 || yVel != 0 && !fireNow)) {
			timer = 0;
			if (xPlace >= 404 ) {
				xPlace=152;
			}else {
				xPlace = xPlace + 36;
			}
		}//Still Draw Arrow
		else if (firing && timer >= 40 && fireReady && xVel == 0 && yVel == 0 && !fireNow && !firing) {
			timer = 0;
			xPlace=152;
		}//Firing Arrow
		else if (firing && timer >= 50 && fireNow) {
			timer = 0;
			xPlace = xPlace + 36;
			//After Fired
			if (xPlace >= 561) {
				xPlace=0;
				fireReady=false;
				firing=false;
				fireNow=false;
				arrowXVel=0;
				arrowYVel=0;
				arrows--;
				switch (yPlace) {
				case 0:
					arrowYVel=1;
					break;
				case 75:
					arrowXVel=-1;
					break;
				case 150:
					arrowXVel=1;
					break;
				case 225:
					arrowYVel=-1;
					break;
				}
				arrowX=xPlayer;
				arrowY=yPlayer;
			}
		}
		//Invinsibility
		if (invinsibility>0) {
			invinsibility--;
			if (blinkTimer==50) {
				blinking=!blinking;
				blinkTimer=0;
			}blinkTimer++;
		}//Character Animations
		if(invinsibility==0||blinking) {
			//Blit Walking
			//Obtaining Item
			if (obtainedTimer>0) {
				obtainedTimer--;
				obtained.paintIcon(this, g, xPlayer, yPlayer-21);
			}
					
			else if (!attacking && !firing) {
				g.drawImage(link, xPlayer, yPlayer, xPlayer + 32, yPlayer + 44, xPlace, yPlace, xPlace + 32, yPlace + 44,
						null);
			//Blit Sword (Based on Direction)
			} else if (attacking){
				//Dab
	//			g.drawImage(dab, xPlayer, yPlayer, xPlayer + 32, yPlayer + 44, xPlace, yPlace, xPlace + 32, yPlace + 44,
	//					null);
	
				//Facing Down
				if (yPlace == 0) {
					g.drawImage(attack, xPlayer-11, yPlayer, xPlayer-11 + 55, yPlayer + 75, xPlace, yPlace, xPlace + 60,
							yPlace + 75, null);
				//Facing Left
				} else if (yPlace == 75) {
					g.drawImage(attack, xPlayer - 20, yPlayer - 15, xPlayer + 40, yPlayer + 60, xPlace, yPlace, xPlace + 60,
							yPlace + 75, null);
				//Facing Right
				} else if (yPlace == 150) {
					g.drawImage(attack, xPlayer, yPlayer - 15, xPlayer + 60, yPlayer + 60, xPlace, yPlace, xPlace + 60,
							yPlace + 75, null);
				//Facing Up
				} else if (yPlace == 225) {
					g.drawImage(attack, xPlayer - 20, yPlayer - 15, xPlayer + 40, yPlayer + 60, xPlace, yPlace, xPlace + 60,
							yPlace + 75, null);
				}
				
			}//Firing Animation
			else if (firing ){
				//Down
				if (yPlace == 0) {
					g.drawImage(firingImg, xPlayer-4, yPlayer-2, xPlayer + 55-5, yPlayer + 69-2, xPlace, yPlace, xPlace + 35,
							yPlace + 40, null);
				}//Left
				else if (yPlace == 75) {
					g.drawImage(firingImg, xPlayer-30+7, yPlayer-10+7, xPlayer + 55-30+7, yPlayer + 69-10+7, xPlace+35-4, 40, xPlace-4,
							40 + 40, null);
				}//Right
				else if (yPlace == 150) {
					g.drawImage(firingImg, xPlayer+3, yPlayer-10+7, xPlayer + 55+3, yPlayer + 69-10+7, xPlace, 40, xPlace + 34,
							40 + 40, null);
				}//Up
				else if (yPlace == 225) {
					g.drawImage(firingImg, xPlayer-6, yPlayer-10-7, xPlayer + 55-6, yPlayer + 69-10-7, xPlace-2, 80, xPlace + 33,
							80 + 40, null);
				}
			}
		}
		
		//Blit Enemies
		for (int i =0; i<enemy.length;i++) {
			g.drawImage(enemySprite, enemy[i].x, enemy[i].y, enemy[i].x + 32, enemy[i].y + 50, enemy[i].xPlace, 0, enemy[i].xPlace+16, 25, null);
			//Enemy Hitbox
			if (hitboxes) {
				g.drawRect(enemy[i].x+enemy[i].xVel, enemy[i].y+enemy[i].yVel+36, 32, 30);	
				g.drawRect(enemy[i].x+enemy[i].xVel, enemy[i].y+enemy[i].yVel+26, 32, 26);	
				g.setColor(new Color (0,255,255));
				g.drawRect(enemy[i].x+enemy[i].xVel, enemy[i].y+enemy[i].yVel+45, 32, 30);
			}
		}
		//Blit boss
		if (boss.alive&&section==7) {
			g.drawImage(handBoss, boss.x, boss.y, boss.x + 64, boss.y + 64, boss.xPlace, boss.yPlace, boss.xPlace+32, boss.yPlace+32, null);
			g.setColor(new Color (255,0,0));
			g.fillRect(150,20,bossHealth/2,30);
			g.setFont(f2);
			g.setColor(new Color (255,255,255));
			g.drawString(("BOSS"), 155, 45);
			if (bossHealth<=0) {
				boss.alive=false;
				overlay[18][8]=0;
				overlay[18][10]=0;
			}
		}
		
		//Showing Hit Boxes
		if (hitboxes) {
			g.setColor(new Color (255,255,255));
			g.drawRect(xPlayer+xVel, yPlayer+yVel+40, 26, 15);		
			g.drawRect(xPlayer+xVel+5, yPlayer+yVel-10, 16, 35);		
			g.drawRect(xPlayer+xVel, yPlayer+yVel, 26, 35);
				for (int row = 0; row<overlay.length;row++) {
					for (int col = 0; col<overlay.length;col++) {
						switch (overlay[row][col]) {
						case 1:	
							g.drawRect(col * 32, row * 32 + 16, 64, 72);	
							break;
						case 3:							
							g.drawRect(col*32+6, row*32+18, 20 , 28);							
							break;
						case 4:			
							g.drawRect(col * 32, row * 32, 100, 100);							
							break;
						case 5:		
							g.drawRect(col * 32, row * 32, 100, 100);							
							break;
						case 6:
							 g.drawRect(col * 32, row * 32+16, 32, 32);
							 break;
						case 7:
							 g. drawRect(col * 32, row * 32+16, 32, 32);
							 break;
						case 8:
						case 9:
						case 10:
						 	g. drawRect(col * 32, row * 32-8, 32, 32);	
						case 11:
						case 12:
							g. drawRect(col * 32, row * 32+10, 32, 32);
						 	break;
						case 13:
							g. drawRect(col*32+6, row*32+16, 24, 22);
							break;
						case 14:
							g. drawRect(col*32, row*32, 30, 36);
							g. drawRect(col * 32, row * 32+32, 32, 32);
							break;
						}
					}
				}
				for (int row = 0; row<overlay.length;row++) {
					for (int col = 0; col<overlay.length;col++) {
						switch (map[row][col]) {
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
							g.drawRect(col * 32, (row * 32)+16, 32, 32);
							break;
						}
					}
				}
				
				//Attack Animation
			if (attacking) {
				if (yPlace==0){
					g.drawRect (xPlayer-5, yPlayer+18,50,35);
				}
				else if (yPlace==75) {
					g.drawRect (xPlayer-22, yPlayer-7,33,64);
				}
				else if (yPlace==150) {
					g.drawRect (xPlayer+18, yPlayer-7,33,64);
				}
				else if (yPlace==225) {	
					g.drawRect (xPlayer-15, yPlayer-15,50,35);
				}
			}
			
			//Arrows Hit Box
			if (arrowXVel!=0 ||arrowYVel!=0 ) {
				//Arrow Up
				if (arrowYVel==1) {
					g.drawRect(arrowX+12, arrowY, 12, 42);
				}//Arrow Down
				else if (arrowYVel==-1) {
					g.drawRect(arrowX+5, arrowY, 12, 42);
				}//Arrow Left
				else if (arrowXVel==-1) {
					g.drawRect(arrowX+5, arrowY+13, 42, 12);
				}//Arrow Right
				else if (arrowXVel==1) {
					g.drawRect(arrowX, arrowY+13, 42, 12);
				}
			}
			
			//Boss hitbox
			if (section==7) {
				g.drawRect(boss.x, boss.y, 64, 64);	
			}
		}   
		//In cave
		if (cave) {
			g.setColor(new Color(0, 0, 0));
			Area cover = new Area(new Rectangle2D.Double(0, 0, 1000, 1000));
			Area view = new Area(new Ellipse2D.Double(xPlayer - 63, yPlayer - 54, 150, 150));
			cover.subtract(view);
			g2d.fill(cover);
		}
		//Shop Page Print
		if (shop) {
			g.drawImage(textbox, 0, 440, 655, 625, 0, page*185, 655, page*185+185, null);		
			if (page==1 ) {
				g.drawImage(selectbox, 450, 250, 177+450, 185+250, 0, 0*185, 177, 1*185, null);
				navi.paintIcon(this, g, 495, 243+(35*textSelect));
			}
			else if (page==4){
				g.drawImage(selectbox, 450, 250, 177+450, 185+250, 0, 1*185, 177, 2*185, null);
				navi.paintIcon(this, g, 495-17, 243+(35*textSelect));
			}
		}
		// Health
		if (lives == 6) {
			g.drawImage(heart, 0, 0, 32, 52, 0, 0, 16, 26, null);
			g.drawImage(heart, 32, 0, 64, 52, 0, 0, 16, 26, null);
			g.drawImage(heart, 64, 0, 96, 52, 0, 0, 16, 26, null);
		} else if (lives == 5) {
			g.drawImage(heart, 0, 0, 32, 52, 0, 0, 16, 26, null);
			g.drawImage(heart, 32, 0, 64, 52, 0, 0, 16, 26, null);
			g.drawImage(heart, 64, 0, 96, 52, 16, 0, 32, 26, null);
		} else if (lives == 4) {
			g.drawImage(heart, 0, 0, 32, 52, 0, 0, 16, 26, null);
			g.drawImage(heart, 32, 0, 64, 52, 0, 0, 16, 26, null);
			g.drawImage(heart, 64, 0, 96, 52, 32, 0, 48, 26, null);
		} else if (lives == 3) {
			g.drawImage(heart, 0, 0, 32, 52, 0, 0, 16, 26, null);
			g.drawImage(heart, 32, 0, 64, 52, 16, 0, 32, 26, null);
			g.drawImage(heart, 64, 0, 96, 52, 32, 0, 48, 26, null);
		} else if (lives == 2) {
			g.drawImage(heart, 0, 0, 32, 52, 0, 0, 16, 26, null);
			g.drawImage(heart, 32, 0, 64, 52, 32, 0, 48, 26, null);
			g.drawImage(heart, 64, 0, 96, 52, 32, 0, 48, 26, null);
		} else if (lives == 1) {
			g.drawImage(heart, 0, 0, 32, 52, 16, 0, 32, 26, null);
			g.drawImage(heart, 32, 0, 64, 52, 32, 0, 48, 26, null);
			g.drawImage(heart, 64, 0, 96, 52, 32, 0, 48, 26, null);
		} else if (lives == 0) {
			g.drawImage(heart, 0, 0, 32, 52, 32, 0, 48, 26, null);
			g.drawImage(heart, 32, 0, 64, 52, 32, 0, 48, 26, null);
			g.drawImage(heart, 64, 0, 96, 52, 32, 0, 48, 26, null);
		}

		// Rupee Counter
		rupeeSingle.paintIcon(this, g, 4, 32);
		g.setColor(new Color(255, 255, 255));
		g.setFont(f1);
		g.drawString(("x"), 24, 58);
		g.setFont(f2);
		rupeeString = rupeeNum + "";
		g.drawString((rupeeString), 36, 58);

		// Rupee Timer for Animation
		if (rupeeCounter > 3) {
			rupeeCounter = 0;
		}
		rupeeTimer++;
		// Reset back
		if (rupeeTimer >= 200) {
			rupeeCounter++;
			rupeeTimer = 0;
			
		}//Weapon Select
		if (weaponChoice) {
			g.drawImage(weaponSelect, 0, 66, 80, 106, 0, 0, 40, 20, null);
		}
		else if (!weaponChoice) {
			g.drawImage(weaponSelect, 0, 66, 80, 106, 0, 20, 40, 40, null);
		}
		
		//Arrow Number
		arrowImg.paintIcon(this, g, 4, 110);
		g.setColor(new Color(255, 255, 255));
		g.setFont(f1);
		g.drawString(("x"), 33, 138);
		g.setFont(f2);
		arrowString = arrows + "";
		g.drawString((arrowString), 45, 138);
		//Help
		if (help) {
			helpMenu.paintIcon(this, g, 0, 0);
		}
		//Gameover
		if (lives<=0) {
			gameover.paintIcon(this, g, -330, -50);
		}
		if (win) {
			endScreen.paintIcon(this, g, -35, -0);
			vaa.paintIcon(this, g, 0, 250);
			//Dab
			g.drawImage(dab, 180, 300, 180 + 32+100, 300 + 44+100, xPlace, yPlace, xPlace + 32, yPlace + 44,
					null);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
			navi.paintIcon(this, g, rand.nextInt(600), (rand.nextInt(250))+400);
		}
		repaint();
	}
	
	//Main
	public static void main(String[] args) throws IOException  {
		gameBoard frame = new gameBoard();
		JFrame window = new JFrame();
		window.setTitle("Practice");
		window.setSize(655,679);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(frame);
		window.requestFocus();
		window.setVisible(true);
	}
}