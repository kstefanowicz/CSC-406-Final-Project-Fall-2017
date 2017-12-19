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
	private int animCounter_ = 0;
	private int centerX1_ = 0, centerY1_ = 0, centerX2_ = 0, centerY2_ = 0;
	private PImage firstBase_, secondBase_;

	public void settings() {

		//Initial Scene configuration
		size(WINDOW_WIDTH, WINDOW_HEIGHT, P3D);
	}

	public void setup() {

		frameRate(60);

		//Image loading section
		backgroundImage_ = loadImage("low_res_gravel.png");

		ball1_ = new BouncingBall(60, 40, 80, 1, this);
		ball2_ = new BouncingBall(-60, 0, 80, 3, this);

		firstBase_ = backgroundImage_; //loadImage("donald-duck.jpg");
		secondBase_ = loadImage("donald-duck.jpg");

		textureMode(NORMAL);
		camera(0, 2*YMIN, 50, 0, 0, 0, 0, 0, -1); 
		lastTime_ = millis();
	}

	public void draw() {

		frameIndex_++;
		background(0);
		lights();

		drawSurface();

		updateTextures();

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

	public void updateTextures(){
		if (animCounter_ == 0) {
			ball1_.setTex_(firstBase_);
			ball2_.setTex_(secondBase_);
			return;
		}

		PImage first = ball1_.getTex_();
		PImage second = ball2_.getTex_();


		first.loadPixels();
		for (int i = 0; i < first.height; i++){
			for (int j = 0; j < first.width; j++){
				//System.out.println("Checking ("+ i + ", " + j + ").");System.out.println("Width: " + first.width + ", Height: " + first.height);
				if ((i - centerX1_) + (j - centerY1_) >= (90 - animCounter_)){
					first.pixels[i*first.width + j] = (first.pixels[i+j] & 0xFF00FFFF) + 0x00A00000;
				}
			}
		}

		first.updatePixels();
		ball1_.setTex_(first);

		second.loadPixels();
		for (int i = 0; i < second.height; i++){
			for (int j = 0; j < second.width; j++){
				if ((i - centerX2_) + (j-centerY2_) == (90 - animCounter_)){
					second.pixels[i*first.width + j] = (second.pixels[i+j] & 0xFF00FFFF) + 0x00FF0000;
				}
			}
		}

		animCounter_ -= 1;

		second.updatePixels();
		ball2_.setTex_(second);
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

	public void keyReleased(){
		if (key == 'r'){
			startTexAnim();
		}
	}

	public void startTexAnim(){
		animCounter_ = 90;
	}

	public static void main(String _args[]) {
		PApplet.main("final_project.FinalProject");
	}

}
