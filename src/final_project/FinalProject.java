package final_project;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;


public class FinalProject extends PApplet implements ApplicationConstants {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The background.
	 * What type of ship doesn't have a starry sky?
	 */
	private PImage backgroundImage_;
	private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>();
	private BouncingBall ball1_;
	private BouncingBall ball2_;
	private float lastTime_;
	private int frameIndex_ = 0;
	private int animCounter_ = 0;
	private int centerX1_ = 0, centerY1_ = 0, centerX2_ = 0, centerY2_ = 0;
	private PImage firstBase_, secondBase_;
	private float cameraX, cameraY, cameraZ;

	public void settings() {

		//Initial Scene configuration
		size(WINDOW_WIDTH, WINDOW_HEIGHT, P3D);
	}

	public void setup() {

		frameRate(60);

		//Image loading section
		backgroundImage_ = loadImage("low_res_gravel.png");

		firstBase_ = backgroundImage_; //loadImage("donald-duck.jpg");
		secondBase_ = loadImage("donald-duck.jpg");
		
		balls.add(new BouncingBall(-40, 10, 80, 4, this));
		balls.add(new BouncingBall(-60, 15, 80, 3, this));
		balls.add(new BouncingBall(-80, 30, 50, 2, this));
		balls.add(new BouncingBall(-20, 4, 30, 6, this));
		balls.add(new BouncingBall(-80, 5, 50, 5, this));
		balls.add(new BouncingBall(-20, 7, 30, 7, this));
		balls.add(new BouncingBall(-80, 2, 50, 5.5f, this));
		balls.add(new BouncingBall(-20, 8, 30, 3, this));
		
		for (BouncingBall ball : balls) {
			ball.setTex(secondBase_);
		}
		
		textureMode(NORMAL);
		camera(0, 2*YMIN, 50, 0, 0, 0, 0, 0, -1); 
		lastTime_ = millis();
	}

	public void draw() {

		frameIndex_++;
		background(0);
		lights();

		drawSurface();
		
		for (BouncingBall ball : balls) {
			ball.draw(this);
		}
		
		int t = millis();
		float dt = (t - lastTime_) * 0.001f;
		
		for (BouncingBall ball : balls) {
			ball.update(dt, 0, 0, 0, 0, 0, 1);
			for (BouncingBall otherBall : balls) {
				if (ball != otherBall) {
					if (ball.checkCollision(otherBall, dt)) {
						ball.handleCollision(otherBall);
					}
				}
			}
		}

		lastTime_ = t;
	}
	
	public void keyReleased() {
		if (keyCode == 32) {
			float rad = (float) (Math.random() * 8 + 2);
			float x = (float) (Math.random() * XMAX * 2 - XMAX);
			float y = (float) (Math.random() * YMAX * 2 - YMAX);
			float z = (float) (Math.random() * 100 + rad + 1);
			BouncingBall ball = new BouncingBall(x, y, z, rad, this);
			ball.setTex(secondBase_);
			balls.add(ball);
		}
		
		if (key == 'r'){
			startTexAnim();
		}
	}
	
	public void setCenters(int theX1, int theY1, int theX2, int theY2){
		centerX1_ = theX1;
		centerY1_ = theY1;
		centerX2_ = theX2;
		centerY2_ = theY2;
	}

	void drawSurface(){

		pushMatrix();

		beginShape(QUADS);
		texture(backgroundImage_);

		vertex(XMIN, YMAX, 0, 0, 0);
		vertex(XMIN, YMIN, 0, 0, 1);
		vertex(XMAX, YMIN, 0, 1, 0);
		vertex(XMAX, YMAX, 0, 1, 1);

		endShape(CLOSE);

		popMatrix();
	}

	public void startTexAnim(){
		animCounter_ = 90;
	}

	public static void main(String _args[]) {
		PApplet.main("final_project.FinalProject");
	}

}
