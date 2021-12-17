package com.cyborck.boids;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final Boid[] boids;
    private final int width, height;

    public World ( int boidNumber, int width, int height ) {
        // generate boids
        boids = new Boid[ boidNumber ];
        for ( int i = 0; i < boidNumber; i++ )
            boids[ i ] = new Boid( Vector.random( width, height ), this );

        this.width = width;
        this.height = height;
    }

    public void simulate () {
        LocalTime lastUpdate = LocalTime.now();
        while ( true ) {
            LocalTime now = LocalTime.now();
            double delta = lastUpdate.until( now, ChronoUnit.NANOS ) * Math.pow( 10, -9 );
            if ( delta > 0 ) {
                // first update all boids, then move them
                for ( Boid b: boids ) b.updateBehavior();
                for ( Boid b: boids ) b.move( delta );
                lastUpdate = now;
            }
        }
    }

    public Boid[] getBoidsInRadius ( Boid boid ) {
        List<Boid> boidsInRadius = new ArrayList<>();
        for ( Boid b: boids )
            if ( b.visibleFor( boid ) )
                boidsInRadius.add( b );

        boidsInRadius.remove( boid );
        return boidsInRadius.toArray( new Boid[ 0 ] );
    }

    public Boid[] getBoids () {
        return boids;
    }

    public int getWidth () {
        return width;
    }

    public int getHeight () {
        return height;
    }
}
