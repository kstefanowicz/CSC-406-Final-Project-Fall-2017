package final_project;

import java.util.Vector;

import Jama.Matrix;
import processing.core.PApplet;

public class BouncingBall implements ApplicationConstants {

	private float bx_ = -40, by_ = 0, bz_ = 80;
	private float mass = 1;
	private float Vx_ = -9, Vy_ = 0, Vz_ = 10;
	private float rad_ = 5;
	private float refl_ = 1f;
	private static final float ZERO_SPEED = 0.02f;

	public BouncingBall() {
		
	}
	
	public BouncingBall(float x, float y, float z, float mass) {
		bx_ = x;
		by_ = y;
		bz_ = z;
		this.mass = mass;
	}
	
	public void draw(PApplet app) {
		System.out.println(Vx_ + " " + Vy_ + " " + Vz_);
		app.pushMatrix();
		
		app.translate(bx_, by_, bz_);
		app.noStroke();
		app.sphere(rad_);
		
		app.popMatrix();
	}
	
	public Vector3 getVector() {
		return new Vector3(Vx_, Vy_, Vz_);
	}
	
	public Vector3 getCenter() {
		return new Vector3(bx_, by_, bz_);
	}
	
	public float getMass() {
		return mass;
	}
	
	public float getRadius() {
		return rad_;
	}
	
	public void move(Vector3 movementVector) {
		bx_ += movementVector.getX();
		by_ += movementVector.getY();
		bz_ += movementVector.getZ();
	}
	
	public void update(float dt, float Ax, float Ay, float Az, float Nx, float Ny, float Nz) {
		
		float dx = bx_ - Ax, dy = by_ - Ay, dz = bz_ - Az;
		float dotProd = dx*Nx + dy*Ny + dz*Nz;
		float normN = PApplet.sqrt(Nx*Nx+Ny*Ny+Nz*Nz);
		float dist = PApplet.abs(dotProd/normN);
		if (dist <= rad_) {
			System.out.println("Bounce!");			
			//	We are going to consider that the energy gets absorbed at
			//	the moment of contact
			Vx_ *= refl_;
			Vy_ *= refl_;
			Vz_ *= refl_;
			
			//	Compute the component of incident velocity along the normal vector
			float dotProdViN = Vx_*Nx + Vy_*Ny + Vz_*Nz;
			float alpha = -2*dotProdViN/normN;
			
			//	And compute the symmetry relative to the plane of contact 
			Vx_ += alpha*Nx;
			Vy_ += alpha*Ny;
			Vz_ += alpha*Nz;
			
			if (PApplet.abs(Vx_) < ZERO_SPEED)
				Vx_ = 0.f;
			if (PApplet.abs(Vy_) < ZERO_SPEED)
				Vy_ = 0.f;
			if (PApplet.abs(Vz_) < ZERO_SPEED)
				Vz_ = 0.f;

		}
		
		if (bx_ <= XMIN || bx_ >= XMAX) {
			Vx_ *= -1;
		}
		if (by_ <= YMIN || by_ >= YMAX) {
			Vy_ *= -1;
		}

		float halfdt2 = 0.5f * dt*dt;
		
		bx_ += Vx_ * dt;
		by_ += Vy_ * dt;
		bz_ += Vz_ * dt - G*halfdt2;		
		
		Vz_ -= G * dt;
	}
	
	public void setMovementVector(Vector3 vector) {
		Vx_ = vector.getX();
		Vz_ = vector.getZ();
		Vy_ = vector.getY();
	}
	
	/**
	 * Checks if the two balls have collided.
	 * If the collided it sets there positions so they just touch
	 * @param other - Ball to check collision with
	 * @return boolean - whether or not the balls collided
	 */
	public boolean checkCollision(BouncingBall other) {
		Vector3 bCenter = other.getCenter();
		Vector3 aCenter = getCenter();
		Vector3 bVector = other.getVector();
		Vector3 aVector = getVector();
		
		// get the relative movemnt between the two objects
		Vector3 relativeMovementVector = aVector.minus(bVector).multiply(1/2);
		float dist = aCenter.distance(bCenter);
		float sumRadius = other.getRadius() + getRadius();
		dist -= sumRadius;
		
		// if the movements magnitude is less than the distance between the centers minus there radiuses
		// then they won't hit
		float magnatude = relativeMovementVector.getMagnitude();
		if (magnatude < dist) {
			System.out.println("movement less than dist minus radius");
			return false;
		}
		
		Vector3 n = relativeMovementVector.normalize();
		
		Vector3 c = bCenter.minus(aCenter);
		
		float d = n.dot(c);
		
		// if d is less than zero than the balls are not moving towards eachother
		if (d <= 0) {
			System.out.println("d <= 0");
			return false;
		}
		
		float lengthC = c.getMagnitude();
		
		float f = (lengthC * lengthC) - (d * d);
		
		float sumRadiusSquared = sumRadius * sumRadius;
		
		// f is how close the spheres will get to eachother
		// if this is greater than the square of the radiuses 
		// than they won't hit
		if (f >= sumRadiusSquared) {
			System.out.println("f >= sumRadiusSqaured");
			return false;
		}
		
		float t = sumRadiusSquared - f;
		
		// if t is negative we cant get the square root
		if (t < 0) {
			System.out.println("t < 0");
			return false;
		}
		
		// distance is how far the ball must travel to collide
		float distance = d - (float) Math.sqrt((double) t);
		float mag = relativeMovementVector.getMagnitude();
		// if the distance is greater than the movements magnitude then they don't collide
		if (mag < distance) {
			System.out.println("mag < distance");
			return false;
		}
		
		// scale the movement for each ball so they just touch
		float scaleMovement = relativeMovementVector.getMagnitude() / aVector.getMagnitude();
		move(aVector.multiply(scaleMovement));
		other.move(bVector.multiply(scaleMovement));
		return true;
	}
	
	public void handleCollision(BouncingBall other) {
		Vector3 otherCenter = other.getCenter();
		Vector3 thisCenter = getCenter();
		Vector3 otherVector = other.getVector();
		Vector3 thisVector = getVector();
		float otherMass = other.getMass();
		float thisMass = getMass();
		
		//find the normalized vector from center of one circle to other
		Vector3 n = otherCenter.minus(thisCenter);
		n = n.normalize();
		
		//find the length along n
		float a1 = otherVector.dot(n);
		float a2 = thisVector.dot(n);
		
		//find the magnitude p for the difference in momentum
		float magnitudeP = (2 * (a1 - a2)) / (otherMass + thisMass);
		
		//calculate the new vector for otherCircle
		Vector3 otherNewVector = otherVector.minus(n.multiply(magnitudeP * thisMass));
		Vector3 thisNewVector = thisVector.plus(n.multiply(magnitudeP * otherMass));
		
		other.setMovementVector(otherNewVector);
		setMovementVector(thisNewVector);
	}
	
}
