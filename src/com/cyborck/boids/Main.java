package com.cyborck.boids;

public class Main {
    public static void main ( String[] args ) {
        System.setProperty( "sun.java2d.uiScale", "1.0" );

        int width = 2000;
        int height = 2000;
        int boidNumber = 500;

        World world = new World( boidNumber, width, height );
        new Gui( width, height, world.getBoids() );
        world.simulate();
    }
}
