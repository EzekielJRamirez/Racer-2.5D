import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.time.LocalTime;
import java.util.Random;
import java.util.Vector;

import java.time.Duration;
import java.time.Instant;
import java.util.Vector;
import java.util.Random;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

import javax.imageio.ImageIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Polygon;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class FormulaOne{
    public FormulaOne(){
        setup();
    }

    public static void setup(){
        appFrame = new JFrame("The Legend of Zelda: Link's Awakening");
        XOFFSET = 0;
        YOFFSET = 40;
        WINWIDTH = 500;
        WINHEIGHT = 500;
        pi = 3.14159265358979;
        quarterPi = 0.25 * pi;
        halfPi = 0.5 * pi;
        threequartersPi = 0.75 * pi;
        fivequartersPi = 1.25 * pi;
        threehalvesPi = 1.5 * pi;
        sevenquartersPi = 1.75 * pi;
        twoPi = 2.0 * pi;
        endgame = false;
        p1width = 228;
        p1height = 228;
        cockpitShift = 350;
        p1originalX = (double) XOFFSET + ((double) WINWIDTH / 2.0) - (p1width / 2.0);
        p1originalY = (double) YOFFSET + (double) cockpitShift;
        trackMatrix = new Vector<Vector<Vector<Integer>>>();

        try{
            //TODO: place the image name into this line!!!
            background = ImageIO.read(new File("PhotoNameHere"));
            player = ImageIO.read(new File("djf"));
            cockpit = ImageIO.read(new File(""));
            track = ImageIO.read(new File("sdf"));
            perspectiveTrack = convertToARGB(ImageIO.read(new File("fsd")));
        }catch (IOException ioe){

        }
    }

    private static BufferedImage convertToARGB(BufferedImage input){
        BufferedImage ret = new BufferedImage(input.getWidth(),input.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ret.createGraphics();
        g.drawImage(input, 0, 0, null);
        g.dispose();
        return ret;
    }

    private static class Animate implements Runnable{
        public void run(){
            while (endgame == false){
                backgroundDraw();
                trackDraw();
                playerDraw();

                try{
                    Thread.sleep(32);
                }catch (InterruptedException e){

                }
            }
        }
    }

    private static class PlayerMover implements Runnable{
        public PlayerMover(){
            velocitystep = 0.01;
            rotatestep = 0.02;
        }
        public void run(){
            while (endgame == false){
                try{
                    Thread.sleep(10);
                }catch (InterruptedException e){

                }

                if(upPressed){
                    p1velocity = p1velocity + velocitystep;
                }
                if(downPressed){
                    p1velocity = p1velocity - velocitystep;
                }
                if(leftPressed){
                    if(p1velocity < 0){
                        p1.rotate(-rotatestep);
                    }else{
                        p1.rotate(rotatestep);
                    }
                }
                if(rightPressed){
                    if(p1velocity < 0){
                        p1.rotate(rotatestep);
                    }else{
                        p1.rotate(-rotatestep);
                    }
                }
            }
        }
        private double velocitystep;
        private double rotatestep;
    }

    private static int constrainToCap(int position, int differential, int cap){
        int ret = differential;
        while(position + ret < 0){
            ret = ret + cap;
        }
        while (position + ret >= cap){
            ret = ret -cap;
        }
        return ret;
    }

    private static class CameraMover implements Runnable{
        public CameraMover(){

        }

        public void run(){
            while (endgame ==false){
                try{
                    Thread.sleep(10);
                }catch (Exception e){

                }

                int sumx = (int)(p1velocity * Math.cos(p1.getAngle() - pi/2.0) + 0.5);
                int sumy = (int)(p1velocity * Math.sin(p1.getAngle() - pi/2.0) + 0.5);

                camerax = camerax + constrainToCap(camerax, sumx, trackMatrix, elementAt(0).size());
                cameray = cameray + constrainToCap(cameray, sumy, trackMatrix, elementAt(0).size());
            }
        }
    }
}

public class main {
    // 170 - 175
    // 170
    Vector<Vector<Vector<Integer>>> userView = perspectiveFromRectangle(cameraView, base);
    Graphics g = appFrame.getGraphics();
    Graphics2D g2D = (Graphics2D) g;

    for (int = 0; i < rectHeight; i++) {
        for (int j = 0; j < rectWidth; j++) {
            int alpha = 255;
            int red = userView.elementAt(i).elementAt(j).elementAt(0);
            int green = userView.elementAt(i).elementAt(j).elementAt(1);
            int blue = userView.elementAt(i).elementAt(j).elementAt(2);

            while (red < 0) {
                red = red + 256;
            }
            while (256 <= red) {
                red = red - 256;
            }
            while (green < 0) {
                green = green + 256;
            }
            while (256 <= green) {
                green = green - 256;
            }
            while (blue < 0) {
                blue = blue + 256;
            }
            while (256 <= blue) {
                blue = blue - 256;
            }
            // page 171
        }
    }
}
