package flocking;

import utils.Tuple;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by solovyevt on 14.11.15 14:10.
 */
public class BoidController{

    private float maxVelocity;

    private int numberOfBoids = 64;
    private int numberOfPredators = 8;

    private ArrayList<Boid> boids = new ArrayList<>(numberOfBoids);

    public BoidController(Vector center, float maxRadius, float maxVelocity, int numberOfBoids, int numberOfPredators){
        this.center = center;
        this.maxRadius = maxRadius;
        this.criticalRadius = maxRadius * 1.5f;
        this.maxVelocity = maxVelocity;
        this.numberOfBoids = numberOfBoids;
        this.numberOfPredators = numberOfPredators;



        initializeBoids();
    }

    void calculateNewPositions(float dt){
        for(int i = 0; i < numberOfBoids; i++){
            boids.get(i).calculateNewPosition(boidPreset);
        }
        for(int i = numberOfBoids; i < boids.size(); i++){
            boids.get(i).calculateNewPosition(predatorPreset);
        }
    }

    void initializeBoids(){
        for(int i = 0; i < numberOfBoids; i++){
            boids.add(new Boid(this, randomSpawnPoint(), randomVelocity(), Color.GREEN, (byte) 0));
        }
        for(int i = 0; i < numberOfPredators; i++){
            boids.add(new Boid(this, randomSpawnPoint(), randomVelocity(), Color.RED, (byte) 1));
        }
    }

    private Vector randomVelocity(){
        Random random = new Random();
        float x = (random.nextFloat() * 2 * maxVelocity) - maxVelocity;
        float y = (random.nextFloat() * 2 * maxVelocity) - maxVelocity;
        float z = (random.nextFloat() * 2 * maxVelocity) - maxVelocity;
        Vector result = new Vector(x, y, z);
        return Vector.mul(result.normalize(), maxVelocity * random.nextFloat());
    }

    private Vector randomSpawnPoint(){
        Random random = new Random();
        float x = (random.nextFloat() * 2 * maxRadius) - maxRadius;
        float y = (random.nextFloat() * 2 * maxRadius) - maxRadius;
        float z = (random.nextFloat() * 2 * maxRadius) - maxRadius;
        Vector result = new Vector(x, y, z);
        return Vector.mul(result.normalize(), maxRadius * random.nextFloat());
    }

    ArrayList<Boid> getBoids(){
        return boids;
    }

    public void render(float dt){
        calculateNewPositions(dt);
        /*for(Boid b: boids){
            b.render(dt);
        }*/
    }

    private final Tuple[] boidPreset = {
            new Tuple<>(5f, 0.01f),
            new Tuple<>(1f, 0.1f),
            new Tuple<>(4f, 0.1f),
            new Tuple<>(3f, 1f),
            new Tuple<>(4f, 0.03f),
            new Tuple<>(4f, 0.01f),
            new Tuple<>(4f, 0.1f),
    };
    private final Tuple[] predatorPreset = {
            new Tuple<>(0f, 0.000001f),
            new Tuple<>(1f, 0.1f),
            new Tuple<>(1f, 0.1f),
            new Tuple<>(3f, 1f),
            new Tuple<>(4f, 0.05f),
            new Tuple<>(4f, 0.1f),
            new Tuple<>(8f, 0.05f),
    };

    public Vector getCenter() {
        return center;
    }

    private Vector center;

    public float getMaxRadius() {
        return maxRadius;
    }

    private float maxRadius;

    public float getCriticalRadius() {
        return criticalRadius;
    }

    private float criticalRadius;

    public float getMaxVelocity() {
        return maxVelocity;
    }

    //Это перейдет к актору
    /*@Override
    public void onReceive(Object message) throws Exception {
        for(int i = 0; i < numberOfBoids; i++){
            boids.get(i).;
        }
        for(int i = numberOfBoids; i < boids.size(); i++){
            boids.get(i).calculateNewPosition(predatorPreset);
        }
    }*/
}
