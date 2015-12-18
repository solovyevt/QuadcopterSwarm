package view;

import flocking.Boid;
import org.lwjgl.opengl.GL11;
import utils.Vector;
import view.scene.Primitives;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by solovyevt on 21.11.15 13:37
 */
public class BoidView implements Observer {
    public BoidView(){

    }
    public void update(Observable o, Object arg) {
        if(arg instanceof Boid){
            render((Boid) arg);
        }

        else if(arg instanceof ArrayList    ){
            render((ArrayList<Vector>) arg);
        }
    }

    //UNDER CONSTRUCTION
    public void render(ArrayList<Vector> state){
        /*GL11.glPushMatrix();
        GL11.glTranslatef(state.get(0).x, state.get(0).y, state.get(0).z);
        Primitives.setColor(this.color);
        Primitives.drawSphere(16, 0.3f);
        Vector normalizedVelocity = currentVelocity.normalize();
        Primitives.drawLine(Vector.ZERO, normalizedVelocity);
        GL11.glPopMatrix();*/
    }

    public void render(Boid boid){
        GL11.glPushMatrix();
        GL11.glTranslatef(boid.getCurrentPosition().x, boid.getCurrentPosition().y, boid.getCurrentPosition().z);
        Primitives.setColor(boid.getColor());
        Primitives.drawSphere(16, 0.3f);
        Vector normalizedVelocity = boid.getCurrentVelocity().normalize();
        Primitives.drawLine(Vector.ZERO, normalizedVelocity);
        GL11.glPopMatrix();
    }
}
