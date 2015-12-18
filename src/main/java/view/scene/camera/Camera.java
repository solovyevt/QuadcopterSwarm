package view.scene.camera;

import utils.Vector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Camera {
	private Vector location;
	private float pitch = 0;
	private float yaw = 0;
	private float roll = 0;
	private float fov = 60;
	private float aspectRatio;
	private float zNear = 0.001f;
	private float zFar = 1000f;
	
	private Matrix4f viewMatrix = new Matrix4f();
	private static Vector3f X_AXIS = new Vector3f(1, 0, 0);
	private static Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	private static Vector3f Z_AXIS = new Vector3f(0, 0, 1);
	
	public Camera (float aspectRatio, float x, float y, float z) {
		this.aspectRatio = aspectRatio;
		this.location = new Vector(x, y, z);
	}
	
	public void applyOrthographicMatrix() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-Display.getWidth()/50f, Display.getWidth()/50f, -Display.getHeight()/50f, Display.getHeight()/50f, 0, 10000);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void applyPerspectiveMatrix() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(fov, aspectRatio, zNear, zFar);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public void applyModelviewMatrix(boolean resetMatrix) {
		if (resetMatrix) {
			viewMatrix.setIdentity();
			glLoadIdentity();
		}
		viewMatrix.rotate((float) Math.toRadians(pitch), X_AXIS);
		viewMatrix.rotate((float) Math.toRadians(yaw), Y_AXIS);
		viewMatrix.rotate((float) Math.toRadians(roll), Z_AXIS);
		viewMatrix.translate(new Vector3f(-location.x, -location.y, -location.z));
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public void processMouse(float mouseSpeed, float maxLookUp, float maxLookDown) {
    	if (!Mouse.isGrabbed()) return;
    	float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
		float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;
		if (yaw + mouseDX >= 360) {
			yaw = yaw + mouseDX - 360;
		} else if (yaw + mouseDX < 0) {
			yaw = 360 - yaw + mouseDX;
		} else {
			yaw += mouseDX;
		}
		if (pitch - mouseDY >= maxLookDown && pitch - mouseDY <= maxLookUp) {
			pitch += -mouseDY;
		} else if (pitch - mouseDY < maxLookDown) {
			pitch = maxLookDown;
		} else if (pitch - mouseDY > maxLookUp) {
			pitch = maxLookUp;
		}
    }
    
    public void processKeyboard(int delta, float speedX, float speedY, float speedZ) {
    	boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W);
        boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S);
        boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A);
        boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D);
        boolean flyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        boolean flyDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
        
        if (keyUp && keyRight && !keyLeft && !keyDown) {
            moveFromLook(speedX * delta, 0, -speedZ * delta);
        }
        if (keyUp && keyLeft && !keyRight && !keyDown) {
        	moveFromLook(-speedX * delta, 0, -speedZ * delta);
        }
        if (keyUp && !keyLeft && !keyRight && !keyDown) {
        	moveFromLook(0, 0, -speedZ * delta);
        }
        if (keyDown && keyLeft && !keyRight && !keyUp) {
        	moveFromLook(-speedX * delta, 0, speedZ * delta);
        }
        if (keyDown && keyRight && !keyLeft && !keyUp) {
        	moveFromLook(speedX * delta, 0, speedZ * delta);
        }
        if (keyDown && !keyUp && !keyLeft && !keyRight) {
        	moveFromLook(0, 0, speedZ * delta);
        }
        if (keyLeft && !keyRight && !keyUp && !keyDown) {
        	moveFromLook(-speedX * delta, 0, 0);
        }
        if (keyRight && !keyLeft && !keyUp && !keyDown) {
        	moveFromLook(speedX * delta, 0, 0);
        }
        if (flyUp && !flyDown) {
        	moveFromLook(0, speedY * delta, 0);
		}
		if (flyDown && !flyUp) {
			moveFromLook(0, -speedY * delta, 0);
		}
    }
    
    public void moveFromLook(float dx, float dy, float dz) {
    	Vector forward = getForwardVector();
    	Vector right = Vector.cross(forward, Vector.UP).normalize();
    	location = Vector.add(location, Vector.mul(forward, dz));
    	location = Vector.add(location, Vector.mul(Vector.UP, dy));
    	location = Vector.add(location, Vector.mul(right, -dx));
    }
	
    public Vector getForwardVector() {
    	float x = (float) (-Math.cos(Math.toRadians(pitch))*Math.sin(Math.toRadians(yaw)));
    	float y = (float) (Math.sin(Math.toRadians(pitch)));
    	float z = (float) (Math.cos(Math.toRadians(pitch))*Math.cos(Math.toRadians(yaw)));
    	return new Vector(x, y, z).normalize();
    }
    
    public float getPitch() {
    	return pitch;
    }
    
    public float getYaw() {
    	return yaw;
    }
    
    public float getRoll() {
    	return roll;
    }
    
    public float getAspectRatio() {
    	return aspectRatio;
    }
    
	public Vector getLocation() {
		return location;
	}
}
