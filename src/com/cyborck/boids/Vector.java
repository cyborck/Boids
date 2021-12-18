package com.cyborck.boids;

public class Vector {
    private double x;
    private double y;

    public Vector () {
        x = 0;
        y = 0;
    }

    public Vector ( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    public static Vector random ( double maxX, double maxY ) {
        double x = Math.random() * maxX;
        double y = Math.random() * maxY;
        return new Vector( x, y );
    }

    public static Vector fromAngle ( double angle, double magnitude ) {
        double x = Math.cos( angle ) * magnitude;
        double y = Math.sin( angle ) * magnitude;
        return new Vector( x, y );
    }

    public static Vector add ( Vector v1, Vector v2 ) {
        double x = v1.getX() + v2.getX();
        double y = v1.getY() + v2.getY();
        return new Vector( x, y );
    }

    public static Vector subtract ( Vector v1, Vector v2 ) {
        double x = v1.getX() - v2.getX();
        double y = v1.getY() - v2.getY();
        return new Vector( x, y );
    }

    public static Vector multiply ( Vector v, double d ) {
        double x = v.getX() * d;
        double y = v.getY() * d;
        return new Vector( x, y );
    }

    public double distanceSq (Vector v){
        return subtract( this, v ).magnitudeSq();
    }

    public double distance ( Vector v ) {
        return subtract( this, v ).magnitude();
    }

    public double angle () {
        return Math.acos( x / magnitude() );
    }

    public double angle360 () {
        return Math.atan2( y, x );
    }

    public double magnitudeSq(){
        return x*x + y*y;
    }

    public double magnitude () {
        return Math.sqrt( x * x + y * y );
    }

    public void setMagnitude ( double magnitude ) {
        normalize();
        multiply( magnitude );
    }

    public void normalize () {
        double magnitude = magnitude();
        x /= magnitude;
        y /= magnitude;
    }

    public void add ( Vector v ) {
        x += v.getX();
        y += v.getY();
    }

    public void subtract ( Vector v ) {
        x -= v.getX();
        y -= v.getY();
    }

    public void multiply ( double d ) {
        x *= d;
        y *= d;
    }

    public double getX () {
        return x;
    }

    public void setX ( double x ) {
        this.x = x;
    }

    public double getY () {
        return y;
    }

    public void setY ( double y ) {
        this.y = y;
    }

    @Override
    public String toString () {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
