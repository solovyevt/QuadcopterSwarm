package view.scene;

import org.lwjgl.opengl.GL11;
import utils.Vector;
import org.lwjgl.util.glu.Sphere;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Primitives {
	public static void setColor(Color c) {
		glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}


	public static void drawLine(Vector v1, Vector v2){
		glBegin(GL11.GL_LINES);
		glVertex3f(v1.x, v1.y, v1.z);
		glVertex3f(v2.x, v2.y, v2.z);
		glEnd();
	}
	public static void drawSphere(int resolution, float radius) {
		Sphere s = new Sphere();
		s.draw(radius, resolution, resolution);
	}

}
