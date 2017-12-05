package final_project;


import processing.core.PApplet;
import processing.core.PImage;

import java.awt.geom.Point2D;
import java.util.Vector;

import Jama.*;

//  NEW	
//	This version supports a manipulator with a 3D workspace and joint space.
//	The inverse kinematics implemented allows us to adjust the orientation of the
//	tip of the manipulator in addition to its position. (check out handleKeyPressed for that).
//
//	A line //NEW
//	indicates a block that has changed from V1

public class FinalProject extends PApplet implements ApplicationConstants {

	

	/**
	 * The background.
	 * What type of ship doesn't have a starry sky?
	 */
	private PImage backgroundImage_;
	private BouncingBall ball1_;
	private float lastTime_;
	private int frameIndex_ = 0;

	
	public void settings() {
		size(WINDOW_WIDTH, WINDOW_HEIGHT, P3D);
	}

	public void setup() {
		frameRate(400);

		//Image loading section
		backgroundImage_ = loadImage("low_res_gravel.png");
		
		ball1_ = new BouncingBall();
		
		textureMode(NORMAL);
		camera(0, 2*YMIN, 50, 0, 0, 0, 0, 0, -1); 
		lastTime_ = millis();
	}

	public void draw() {
	  // flickering background to see the framerate interference
	  // when loading an image. there should be none since the images
	  // are loaded in their own thread.
	  
	  
		frameIndex_++;
		if (frameIndex_ % 4 == 0) {
			background(0);
			lights();

			drawSurface();

			ball1_.draw(this);	
		}
		
		int t = millis();
		float dt = (t - lastTime_) * 0.001f;
		ball1_.update(dt);
		
		lastTime_ = t;
	}
	
	void drawSurface() {

		beginShape(QUADS);
		texture(backgroundImage_);

		vertex(XMIN, YMAX, 0, 0, 0);
		vertex(XMIN, YMIN, 0, 0, 1);
		vertex(XMAX, YMIN, 0, 1, 0);
		vertex(XMAX, YMAX, 0, 1, 1);

		endShape(CLOSE);   
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("final_project.FinalProject");
	}
}
