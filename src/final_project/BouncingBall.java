package final_project;

import processing.core.PApplet;

public class BouncingBall implements ApplicationConstants {

	private float bx_ = -20, by_ = 0, bz_ = 30;
	private float Vx_ = 12, Vy_ = 0, Vz_ = 0;
	private float rad_ = 5;
	private float refl_ = 0.8f;
	private static final float ZERO_SPEED = 0.01f;

	public BouncingBall() {
		
	}
	
	public void draw(PApplet app) {
		app.pushMatrix();
		
		app.translate(bx_, by_, bz_);
		app.noStroke();
		app.sphere(rad_);
		
		app.popMatrix();
	}
	
	public void update(float dt) {
		
		if (bz_ <= rad_) {
			Vz_ = refl_ * PApplet.abs(Vz_);
			Vx_ *= refl_;
			Vy_ *= refl_;
			
			if (PApplet.abs(Vx_) < ZERO_SPEED)
				Vx_ = 0.f;
			if (PApplet.abs(Vy_) < ZERO_SPEED)
				Vy_ = 0.f;
			if (PApplet.abs(Vz_) < ZERO_SPEED)
				Vz_ = 0.f;

		}

		float halfdt2 = 0.5f * dt*dt;
		
		bx_ += Vx_ * dt;
		by_ += Vy_ * dt;
		bz_ += Vz_ * dt - G*halfdt2;		
		
		Vz_ -= G * dt;
	}
}
