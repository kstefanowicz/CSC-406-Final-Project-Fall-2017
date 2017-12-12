package final_project;

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
	private BouncingBall ball1_;
	private BouncingBall ball2_;
	private float lastTime_;
	private int frameIndex_ = 0;
	
	public void settings() {

		//Initial Scene configuration
		size(WINDOW_WIDTH, WINDOW_HEIGHT, P3D);
	}

	public void setup() {

		frameRate(60);
		
		//Image loading section
		backgroundImage_ = loadImage("low_res_gravel.png");

		ball1_ = new BouncingBall(-40, 0, 80, 1);
		ball2_ = new BouncingBall(-60, 0, 80, 3);
		
		textureMode(NORMAL);
		camera(0, 2*YMIN, 50, 0, 0, 0, 0, 0, -1); 
		lastTime_ = millis();
	}

	public void draw() {

		frameIndex_++;
		background(0);
		lights();

		drawSurface();

		ball1_.draw(this);	
		ball2_.draw(this);
		
		int t = millis();
		float dt = (t - lastTime_) * 0.001f;
		
		ball1_.update(dt, 0, 0, 0, 0, 0, 1);
		ball2_.update(dt, 0, 0, 0, 0, 0, 1);
		
		if (ball1_.checkCollision(ball2_)) {
			ball1_.handleCollision(ball2_);
		}
		
		lastTime_ = t;
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

	public static void main(String _args[]) {
		PApplet.main("final_project.FinalProject");
	}

}
