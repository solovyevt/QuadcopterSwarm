package quadcopter;

import utils.Vector;

import java.util.Observable;

//@TODO
public class Quadcopter extends Observable {
	
	private float thrust = 0;

	// Крен, phi, roll
	private float yaw = 0;
	// Тангаж, theta, pitch
	private float pitch = 0;
	// Рыскание, psi, yaw
	private float roll = 0;

	private float x, y, z;
	
	public Quadcopter() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public void update(float dt) {
		
	}
	
	public Vector getForwardVector() {
    	float x = (float) (-Math.cos(Math.toRadians(90+pitch))*Math.sin(Math.toRadians(180-yaw+roll)));
    	float y = (float) (Math.sin(Math.toRadians(90+pitch)));
    	float z = (float) (Math.cos(Math.toRadians(90+pitch))*Math.cos(Math.toRadians(180-yaw+roll)));
    	return new Vector(x, y, z).normalize();
    }

	/*
	public void render(float dt) {
		update(dt);
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef(x, y, z);
		
		GL11.glRotatef(pitch, 1, 0, 0);
		GL11.glRotatef(yaw, 0, 1, 0);
		GL11.glRotatef(roll, 0, 0, 1);
		
		GL11.glPopMatrix();
	}*/
}
