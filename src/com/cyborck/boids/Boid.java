package com.cyborck.boids;

import java.awt.*;

public class Boid {
    private static final double RADIUS = 10; // radius of the circle that contains all the corners
    private static final double VISIBLE_RADIUS = 50; // radius where the boid can spot other boids and 'interact' with them
    private static final double VELOCITY = 200;
    private static final Color COLOR = new Color( 231, 114, 31 );

    private final World world;
    private final Vector position;
    private final Vector velocity;

    private Vector acceleration;

    public Boid ( Vector position, World world ) {
        this.world = world;
        this.position = position;
        velocity = Vector.fromAngle( Math.random() * 2 * Math.PI, 1 );
        acceleration = new Vector();
    }

    private Vector getNearestPosition ( Boid other ) {
        // check positions on all sides
        double shortestDistance = Double.MAX_VALUE;
        int index = -1;
        Vector[] ps = other.getAllPositions();
        for ( int i = 0; i < ps.length; i++ ) {
            double distance = position.distance( ps[ i ] );
            if ( distance < shortestDistance ) {
                shortestDistance = distance;
                index = i;
            }
        }
        return ps[ index ];
    }

    public Vector[] getAllPositions () {
        Vector[] positions = new Vector[ 9 ];
        int index = 0;
        for ( int i = -1; i <= 1; i++ ) {
            int xOffset = i * world.getWidth();
            for ( int j = -1; j <= 1; j++ ) {
                int yOffset = j * world.getHeight();
                positions[ index++ ] = Vector.add( position, new Vector( xOffset, yOffset ) );
            }
        }

        return positions;
    }

    public void applyForce ( Vector force ) {
        acceleration.add( force ); // mass = 1
    }

    public void updateBehavior () {
        // boid behavior
        Boid[] visibleBoids = world.getBoidsInRadius( this );

        if ( visibleBoids.length > 0 ) {
            // alignment
            Vector desiredAlignment = new Vector();
            for ( Boid b: visibleBoids )
                desiredAlignment.add( b.getVelocity() );
            desiredAlignment.setMagnitude( VELOCITY );
            Vector alignment = steer( desiredAlignment );

            // cohesion
            double cohesionCenterX = 0;
            double cohesionCenterY = 0;
            for ( Boid b: visibleBoids ) {
                Vector p = getNearestPosition( b );
                cohesionCenterX += p.getX();
                cohesionCenterY += p.getY();
            }
            cohesionCenterX /= visibleBoids.length;
            cohesionCenterY /= visibleBoids.length;
            Vector cohesionCenter = new Vector( cohesionCenterX, cohesionCenterY );
            Vector cohesion = steerTarget( cohesionCenter );

            // separation
            Vector separation = new Vector();
            for ( Boid b: visibleBoids ) {
                Vector p = getNearestPosition( b );
                separation.add( Vector.subtract( p, getPosition() ) );
            }
            separation.multiply( -1 );

            // apply behaviors
            applyForce( alignment );
            applyForce( cohesion );
            applyForce( separation );
        }
    }

    public void move ( double delta ) {
        acceleration.multiply( delta );
        velocity.add( acceleration );
        velocity.setMagnitude( VELOCITY );
        position.add( Vector.multiply( velocity, delta ) );
        acceleration = new Vector();

        // when the boid disappears from the edges of the screen, it should reappear on the other side
        if ( position.getX() < 0 ) position.add( new Vector( world.getWidth(), 0 ) );
        else if ( position.getX() >= world.getWidth() ) position.subtract( new Vector( world.getWidth(), 0 ) );
        if ( position.getY() < 0 ) position.add( new Vector( 0, world.getHeight() ) );
        else if ( position.getY() >= world.getHeight() ) position.subtract( new Vector( 0, world.getHeight() ) );
    }

    public void draw ( Graphics2D g ) {
        double rotation = rotation();

        Vector a = Vector.add( position, Vector.fromAngle( rotation, RADIUS ) );
        Vector b = Vector.add( position, Vector.fromAngle( rotation + 0.75 * Math.PI, RADIUS ) );
        Vector c = Vector.add( position, Vector.fromAngle( rotation + 1.25 * Math.PI, RADIUS ) );
        Gui.drawTriangle( g, a, b, c, COLOR );
    }

    public boolean visibleFor ( Boid b ) {
        if ( position.distance( b.getPosition() ) <= VISIBLE_RADIUS ) return true;
        return position.distance( getNearestPosition( b ) ) < VISIBLE_RADIUS;
    }

    public double rotation () {
        return velocity.angle360();
    }

    private Vector steer ( Vector desired ) {
        return Vector.subtract( desired, velocity );
    }

    private Vector steerTarget ( Vector target ) {
        Vector desired = Vector.subtract( target, position );
        desired.setMagnitude( VELOCITY );
        return steer( desired );
    }

    public Vector getPosition () {
        return position;
    }

    public Vector getVelocity () {
        return velocity;
    }

    public Vector getAcceleration () {
        return acceleration;
    }
}
