package gamepad;


import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import java.util.Observable;

/**
 * Created by solovyevt on 03.11.15 14:06.
 */
public class GamepadControl extends Observable implements Runnable{
    Controller controller;
    boolean running = true;

    GamepadControl(){
        try {
            Controllers.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        Controllers.poll();

        for (int i = 0; i < Controllers.getControllerCount(); i++) {
            controller = (Controllers.getController(i));
            System.out.println(controller.getName());
        }
        controller = Controllers.getController(0);
        System.out.println("Axis:");
        for (int i = 0; i < controller.getAxisCount(); i++) {
            System.out.println(i + ".   " + controller.getAxisName(i));
        }
        System.out.println("Buttons:");
        for (int i = 0; i < controller.getButtonCount(); i++) {
            System.out.println(i + ".   " + controller.getButtonName(i));
        }
    }

    public void run(){
        float[] currentAxisValues = new float[4];
        while(running){
            controller.poll();
            float[] bufferValues = new float[4];
            bufferValues[0] = controller.getXAxisValue();
            bufferValues[1] = controller.getYAxisValue();
            bufferValues[2] = controller.getZAxisValue();
            bufferValues[3] = controller.getRZAxisValue();
            if(!currentAxisValues.equals(bufferValues)){
                for(float v: bufferValues){
                    System.out.print(v + "  ");
                }
                System.out.println("\n");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void terminate(){
        running = false;
    }
}
