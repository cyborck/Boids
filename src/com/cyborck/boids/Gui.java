package com.cyborck.boids;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public Gui ( int width, int height, Boid... boids ) {
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setResizable( false );

        getContentPane().add( new BoidDrawer( boids, width, height ) );

        pack();
        setLocationRelativeTo( null );
        setVisible( true );
    }

    public static void drawTriangle ( Graphics2D g, Vector a, Vector b, Vector c, Color color ) {
        Polygon triangle = new Polygon();
        triangle.addPoint( ( int ) a.getX(), ( int ) a.getY() );
        triangle.addPoint( ( int ) b.getX(), ( int ) b.getY() );
        triangle.addPoint( ( int ) c.getX(), ( int ) c.getY() );

        g.setColor( color );
        g.fillPolygon( triangle );
    }

    private static class BoidDrawer extends Component {
        private final Boid[] boids;
        private final int width, height;

        public BoidDrawer ( Boid[] boids, int width, int height ) {
            this.boids = boids;
            this.width = width;
            this.height = height;
            setPreferredSize( new Dimension( width, height ) );
        }

        @Override
        public void paint ( Graphics g ) {
            Graphics2D g2d = ( Graphics2D ) g;
            g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

            // draw background
            g2d.setColor( Color.BLACK );
            g2d.fillRect( 0, 0, width, height );

            // draw boids
            for ( Boid b: boids ) b.draw( g2d );

            repaint();
        }
    }
}
