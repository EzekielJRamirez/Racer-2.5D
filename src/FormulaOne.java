import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;

import javax.imageio.ImageIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class FormulaOne {
    public FormulaOne() {
        setup();
    }

    public static void setup() {
        appFrame = new JFrame("F1 Racing");
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

        try {
            //TODO: place the image name into this line!!!
            background = ImageIO.read(new File("src/Track.png"));
            player = ImageIO.read(new File("src/Wheel.png"));
            cockpit = ImageIO.read(new File("src/Cockpit.png"));
            track = ImageIO.read(new File("src/Track.png"));
            perspectiveTrack = convertToARGB(ImageIO.read(new File("src/Track.png")));
        } catch (IOException ioe) {

        }
    }

    private static BufferedImage convertToARGB(BufferedImage input) {
        BufferedImage ret = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ret.createGraphics();
        g.drawImage(input, 0, 0, null);
        g.dispose();
        return ret;
    }

    private static class Animate implements Runnable {
        public void run() {
            while (endgame == false) {
                backgroundDraw();
                trackDraw();
                playerDraw();

                try {
                    Thread.sleep(32);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    private static class PlayerMover implements Runnable {
        public PlayerMover() {
            velocitystep = 0.01;
            rotatestep = 0.02;
        }

        public void run() {
            while (endgame == false) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }

                if (upPressed) {
                    p1velocity = p1velocity + velocitystep;
                }
                if (downPressed) {
                    p1velocity = p1velocity - velocitystep;
                }
                if (leftPressed) {
                    if (p1velocity < 0) {
                        p1.rotate(-rotatestep);
                    } else {
                        p1.rotate(rotatestep);
                    }
                }
                if (rightPressed) {
                    if (p1velocity < 0) {
                        p1.rotate(rotatestep);
                    } else {
                        p1.rotate(-rotatestep);
                    }
                }
            }
        }

        private double velocitystep;
        private double rotatestep;
    }

    private static int constrainToCap(int position, int differential, int cap) {
        int ret = differential;
        while (position + ret < 0) {
            ret = ret + cap;
        }
        while (position + ret >= cap) {
            ret = ret - cap;
        }
        return ret;
    }

    private static class CameraMover implements Runnable {
        public CameraMover() {

        }

        public void run() {
            while (endgame == false) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {

                }

                int sumx = (int) (p1velocity * Math.cos(p1.getAngle() - pi / 2.0) + 0.5);
                int sumy = (int) (p1velocity * Math.sin(p1.getAngle() - pi / 2.0) + 0.5);

                camerax = camerax + constrainToCap(camerax, sumx, trackMatrix.elementAt(0).size());
                cameray = cameray + constrainToCap(cameray, sumy, trackMatrix.elementAt(0).size());
            }
        }
    }

    private static Vector<Vector<Vector<Integer>>> splitColors(BufferedImage input) {
        Vector<Vector<Vector<Integer>>> ret = new Vector<Vector<Vector<Integer>>>();

        for (int i = 0; i < input.getWidth(); i++) {
            Vector<Vector<Integer>> tempRow = new Vector<Vector<Integer>>();

            for (int j = 0; j < input.getHeight(); j++) {

                Vector<Integer> temp = new Vector<Integer>();
                int rgb = input.getRGB(i, j);
                int r = (rgb >> 16) & 0x000000FF;
                int g = (rgb >> 8) & 0x000000FF;
                int b = rgb & 0x000000FF;

                temp.addElement(r);
                temp.addElement(g);
                temp.addElement(b);
                tempRow.addElement(temp);
            }

            ret.addElement(tempRow);
        }
        return ret;
    }

    private static void setupTrack() {
        trackMatrix = splitColors(track);
    }

    private static AffineTransformOp rotateImageObject(ImageObject obj) {
        AffineTransform at = AffineTransform.getRotateInstance(-obj.getAngle(), obj.getWidth() / 2.0, obj.getHeight() / 2.0);
        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return atop;
    }
    // 165

    private static AffineTransformOp spinImageObject(ImageObject obj) {
        AffineTransform at = AffineTransform.getRotateInstance(-obj.getInternalAngle(), obj.getWidth() / 2.0, obj.getHeight() / 2.0);
        AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return atop;
    }

    private static void backgroundDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;

        int xshift = XOFFSET + (int) ((p1.getAngle() / twoPi) * new Double(background.getWidth()) + 0.5);
        g2D.drawImage(background, xshift, YOFFSET, null);
        g2D.drawImage(background, xshift - background.getWidth(), YOFFSET, null);
        g2D.drawImage(cockpit, XOFFSET, cockpitShift, null);
        g2D.drawImage(rotateImageObject(p1).filter(player, null), (int) (p1.getX() + 0.5), (int) (p1.getY() + 0.5), null);
    }

    private static Vector<Vector<Vector<Integer>>> perspectiveFromRectangle(Vector<Vector<Vector<Integer>>> inputGrid, int base) {
        Vector<Vector<Vector<Integer>>> ret = new Vector<Vector<Vector<Integer>>>();
        // allocate space for ret
        for (int i = 0; i < inputGrid.size(); i++) {
            Vector<Vector<Integer>> tempRow = new Vector<Vector<Integer>>();
            for (int j = 0; j < inputGrid.elementAt(i).size(); j++) {
                Vector<Integer> tempRGB = new Vector<Integer>();
                tempRGB.addElement(0);
                tempRGB.addElement(0);
                tempRGB.addElement(0);

                tempRow.addElement(tempRGB);
            }
            ret.addElement(tempRow);
        }
        // 166
        // collapse rows from inputGrid into ret
        for (int i = 0; i < inputGrid.size(); i++) {
            for (int j = 0; j < inputGrid.elementAt(i).size(); j++) {
                double xdim = (double) inputGrid.elementAt(i).size();
                double ydim = (double) inputGrid.size();
                double width = xdim - ((double) i / (ydim - 1.0)) * (xdim - (double) base);
                double stepsize = width / xdim;
                double offset = (xdim - width) / 2.0;
                int indexi = i;
                int indexj = (int) (0.5 + offset + (double) j * stepsize);

                //System.out.println("i: " + indexi + ", j: " + indexj);
                ret.elementAt(i).elementAt(j).set(0, inputGrid.elementAt(indexi).elementAt(indexj).elementAt(0));
                ret.elementAt(i).elementAt(j).set(1, inputGrid.elementAt(indexi).elementAt(indexj).elementAt(1));
                ret.elementAt(i).elementAt(j).set(2, inputGrid.elementAt(indexi).elementAt(indexj).elementAt(2));
            }
        }
        return ret;
    }

    private static Vector<Vector<Vector<Integer>>> rotateImage(Vector<Vector<Vector<Integer>>> inputImg,
                                                               double angle, double xpos, double ypos,
                                                               boolean repeatImg) {
        Vector<Vector<Vector<Integer>>> ret = new Vector<Vector<Vector<Integer>>>();

        for (int i = 0; i < inputImg.size(); i++) {
            Vector<Vector<Integer>> tempRow = new Vector<Vector<Integer>>();
            for (int j = 0; j < inputImg.elementAt(i).size(); j++) {
                Vector<Integer> tempPixel = new Vector<Integer>();
                for (int k = 0; k < inputImg.elementAt(i).elementAt(j).size(); k++) {
                    tempPixel.addElement(0);
                }
                tempRow.addElement(tempPixel);
            }
            ret.addElement(tempRow);
        }
        // 167
        for (int i = 0; i < inputImg.size(); i++) {
            for (int j = 0; j < inputImg.elementAt(i).size(); j++) {
                int newj = (int) (0.5 + xpos + ((double) j - xpos) * Math.cos(angle) - ((double) i - ypos) * Math.sin(angle));
                int newi = (int) (0.5 + ypos + ((double) j - xpos) * Math.sin(angle) + ((double) i - ypos) * Math.cos(angle));
                if (repeatImg) {
                    while (newj >= ret.elementAt(0).size()) {
                        newj = newj - ret.elementAt(0).size();
                    }
                    while (newj < 0) {
                        newj = newj + ret.elementAt(0).size();
                    }
                    while (newi >= ret.size()) {
                        newi = newi - ret.size();
                    }
                    while (newi < 0) {
                        newi = newi + ret.size();
                    }
                }

                if (newj < ret.elementAt(0).size() && newj >= 0) {
                    if (newi < ret.size() && newi >= 0) {
                        ret.elementAt(newi).elementAt(newj).set(0, inputImg.elementAt(i).elementAt(j).elementAt(0));
                        ret.elementAt(newi).elementAt(newj).set(1, inputImg.elementAt(i).elementAt(j).elementAt(1));
                        ret.elementAt(newi).elementAt(newj).set(2, inputImg.elementAt(i).elementAt(j).elementAt(2));
                    }
                }
            }
        }

        return ret;
    }

    // 168
    private static Vector<Vector<Vector<Integer>>> duplicate3x3(Vector<Vector<Vector<Integer>>> inputImg) {
        Vector<Vector<Vector<Integer>>> ret = new Vector<>();
        for (int i = 0; i < inputImg.size() * 3; i++) {
            Vector<Vector<Integer>> tempRow = new Vector<>();
            for (int j = 0; j < inputImg.elementAt(0).size() * 3; j++) {
                Vector<Integer> tempPixel = new Vector<Integer>();
                tempPixel.addElement(0);
                tempPixel.addElement(0);
                tempPixel.addElement(0);

                tempRow.addElement(tempPixel);
            }
            ret.addElement(tempRow);
        }
        for (int i = 0; i < ret.size(); i++) {
            for (int j = 0; j < ret.elementAt(i).size(); j++) {
                ret.elementAt(i).elementAt(j).set(0, inputImg.elementAt(i %
                        inputImg.size()).elementAt(j % inputImg.elementAt(0).size()
                ).elementAt(0));
                ret.elementAt(i).elementAt(j).set(1, inputImg.elementAt(i %
                        inputImg.size()).elementAt(j % inputImg.elementAt(0).size()
                ).elementAt(1));
                ret.elementAt(i).elementAt(j).set(2, inputImg.elementAt(i %
                        inputImg.size()).elementAt(j % inputImg.elementAt(0).size()
                ).elementAt(2));
            }
        }
        return ret;
    }

    private static void trackDraw() {
        // use camera's position, p1's rotation, and trapezoid mapper.

        int rectWidth = 500; //500
        int rectHeight = 175; //200
        int base = 150; //250
        int xoffset = 0;
        int yoffset = 232;
        int scaledown = 5;

        Vector<Vector<Vector<Integer>>> cameraView = new Vector<>();
        for (int i = 0; i < rectHeight; i++) {
            Vector<Vector<Integer>> tempRow = new Vector<>();
            for (int j = 0; j < rectWidth; j++) {
                Vector<Integer> tempRGB = new Vector<Integer>();

                int indexi = cameray - (rectHeight - i); // % trackMatrix.size()
                int indexj = camerax - (rectWidth - j + (int) (0.5 + ((double)
                        rectWidth / 2.0)));

                while (indexi < 0) {
                    indexi = indexi + trackMatrix.size();
                }
                while (trackMatrix.size() <= indexi) {
                    indexj = indexi - trackMatrix.size();
                }
                while (indexj < 0) {
                    indexj = indexj + trackMatrix.elementAt(0).size();
                }
                while (trackMatrix.elementAt(0).size() < indexj) {
                    indexj = indexj - trackMatrix.elementAt(0).size();
                }

                tempRGB.addElement(trackMatrix.elementAt(indexi).elementAt(
                        indexi).elementAt(0));
                tempRGB.addElement(trackMatrix.elementAt(indexi).elementAt(
                        indexi).elementAt(1));
                tempRGB.addElement(trackMatrix.elementAt(indexi).elementAt(
                        indexi).elementAt(2));
                tempRow.addElement(tempRGB);
            }
            cameraView.addElement(tempRow);
        }
        Vector<Vector<Vector<Integer>>> userView = perspectiveFromRectangle(cameraView, base);
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;

        for (int i = 0; i < rectHeight; i++) {
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
                Color myColor = new Color(red, green, blue);
                int rgb = myColor.getRGB();
                perspectiveTrack.setRGB(j, i, rgb);
            }
        }
        g2D.drawImage(perspectiveTrack, XOFFSET, YOFFSET + yoffset, null);
    }

    private static void playerDraw() {
        Graphics g = appFrame.getGraphics();
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(cockpit, XOFFSET, cockpitShift, null);
        g2D.drawImage(rotateImageObject(p1).filter(player, null), (int) (p1.getX() + 0.5),
                (int) (p1.getY() + 0.5), null);
    }

    private static class KeyPressed extends AbstractAction {
        public KeyPressed() {
            action = "";
        }

        public KeyPressed(String input) {
            action = input;
        }

        public void actionPerformed(ActionEvent e) {
            if (action.equals("UP")) {
                upPressed = true;
            }
            if (action.equals("DOWN")) {
                downPressed = true;
            }
            if (action.equals("LEFT")) {
                leftPressed = true;
            }
            if (action.equals("RIGHT")) {
                //page 172
                rightPressed = true;
            }
        }

        private String action;
    }

    private static class KeyReleased extends AbstractAction {
        public KeyReleased() {
            action = "";
        }

        public KeyReleased(String input) {
            action = input;
        }

        public void actionPerformed(ActionEvent e) {
            if (action.equals("UP")) {
                upPressed = false;
            }
            if (action.equals("DOWN")) {
                downPressed = false;
            }
            if (action.equals("LEFT")) {
                leftPressed = false;
            }
            if (action.equals("RIGHT")) {
                rightPressed = false;
            }
        }

        private String action;
    }

    private static class QuitGame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            endgame = true;
            // page 173
        }
    }

    private static class StartGame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            endgame = true;
            upPressed = false;
            downPressed = false;
            leftPressed = false;
            rightPressed = false;
            p1 = new ImageObject(p1originalX, p1originalY, p1width, p1height, 0.0);
            p1velocity = 0.0;
            camerax = 0; // 470;
            cameray = 0; //425;
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {

            }
            setupTrack();
            endgame = false;
            Thread t1 = new Thread(new Animate());
            Thread t2 = new Thread(new PlayerMover());
            Thread t3 = new Thread(new CameraMover());
            t1.start();
            t2.start();
            t3.start();
        }
    }

    private static class ImageObject {
        public ImageObject() {

        }

        public ImageObject(double xinput, double yinput, double xwidthinput,
                           double yheightinput, double angleinput) {
            x = xinput;
            // page 174
            y = yinput;
            xwidth = xwidthinput;
            yheight = yheightinput;
            angle = angleinput;
            internalangle = 0.0;
            coords = new Vector<Double>();
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getWidth() {
            return xwidth;
        }

        public double getHeight() {
            return yheight;
        }

        public double getAngle() {
            return angle;
        }

        public double getInternalAngle() {
            return internalangle;
        }

        public void setAngle(double angleinput) {
            angle = angleinput;
        }

        public void setInternalAngle(double internalangleinput) {
            internalangle = internalangleinput;
        }

        // page 175
        public Vector<Double> getCoords() {
            return coords;
        }

        public void setCoords(Vector<Double> coordsinput) {
            coords = coordsinput;
            generateTriangles();
            //printTriangles();
        }

        public void generateTriangles() {
            triangles = new Vector<Double>();
            // format: (0,1), (2,3), (4,5) is the (x,y) coords of a triangle

            // get center point of all coordinates.
            comX = getComX();
            comY = getComY();

            for (int i = 0; i < coords.size(); i = i + 2) {
                triangles.addElement(coords.elementAt(i));
                triangles.addElement(coords.elementAt(i + 1));

                triangles.addElement(coords.elementAt((i + 2) % coords.size()));
                triangles.addElement(coords.elementAt((i + 3) % coords.size()));

                triangles.addElement(comX);
                triangles.addElement(comY);
            }
        }

        public void printTriangles() {
            for (int i = 0; i < triangles.size(); i = i + 6) {
                System.out.print("p0x: " + triangles.elementAt(i) + ", p0y: " +
                        triangles.elementAt(i + 1));
                System.out.print("p1x: " + triangles.elementAt(i + 2) +
                        ", p1y: " + triangles.elementAt(i + 3));
                System.out.print("p2x: " + triangles.elementAt(i + 4) +
                        ", p2y: " + triangles.elementAt(i + 5));
            }
        }
        public double getComX() {
            double ret = 0;
            if (coords.size() > 0) {
                for (int i = 0; i < coords.size(); i = i + 2) {
                    ret = ret + coords.elementAt(i);
                }
                ret = ret / (coords.size() / 2.0);
            }
            return ret;
        }

        public double getComY() {
            double ret = 0;
            if (coords.size() > 0) {
                for (int i = 1; i < coords.size(); i = i + 2) {
                    ret = ret + coords.elementAt(i);
                }
                ret = ret / (coords.size() / 2.0);
            }
            return ret;
        }

        public void move(double xinput, double yinput) {
            x = x + xinput;
            y = y + yinput;
        }

        public void moveto(double xinput, double yinput) {
            x = xinput;
            y = yinput;
        }

        public void screenWrap(double leftEdge, double rightEdge, double topEdge, double bottomEdge) {
            if (x > rightEdge) {
                moveto(leftEdge, getY());
            }
            if (x < leftEdge) {
                moveto(rightEdge, getY());
            }
            if (y > bottomEdge) {
                moveto(getX(), topEdge);
            }
            if (y < topEdge) {
                moveto(getX(), bottomEdge);
            }
        }

        public void rotate(double angleinput) {
            angle = angle + angleinput;
            while (angle > twoPi) {
                angle = angle - twoPi;
            }
            while (angle < 0) {
                angle = angle + twoPi;
            }
        }

        public void spin(double internalangleinput) {
            internalangle = internalangle + internalangleinput;
            while (internalangle > twoPi) {
                internalangle = internalangle - twoPi;
            }
            while (internalangle < 0) {
                internalangle = internalangle + twoPi;
            }
        }

        private double x;
        private double y;
        private double xwidth;
        private double yheight;
        private double angle; // in Radians
        private double internalangle; // in Radians
        private Vector<Double> coords;
        private Vector<Double> triangles;
        private double comX;
        private double comY;
    }

    private static void bindKey(JPanel myPanel, String input) {
        myPanel.getInputMap(IFW).put(KeyStroke.getKeyStroke( "pressed"  +
                input), input + "pressed" );
        myPanel.getActionMap().put(input + " pressed" , new KeyPressed(input
        ));
        myPanel.getInputMap(IFW).put(KeyStroke.getKeyStroke( "released " +
                input), input + "released" );
        myPanel.getActionMap().put(input + " released" , new KeyReleased(
                input));
    }

    public static void main(String[] args) {
        setup();
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setSize(501, 585);
        JPanel myPanel = new JPanel();
        JButton newGameButton = new JButton( "New Game" );
        newGameButton.addActionListener(new StartGame());
        myPanel.add(newGameButton);
        JButton quitButton = new JButton( "Quit Game" );
        quitButton.addActionListener(new QuitGame());
        myPanel.add(quitButton);
        bindKey(myPanel, "UP" );
        bindKey(myPanel, "DOWN" );
        bindKey(myPanel, "LEFT" );
        bindKey(myPanel, "RIGHT" );
        bindKey(myPanel, "F" );
        appFrame.getContentPane().add(myPanel, "South" );
        appFrame.setVisible(true);
    }

    private static Boolean endgame;
    private static BufferedImage background;
    private static BufferedImage player;
    private static BufferedImage cockpit;
    private static BufferedImage track;
    private static BufferedImage perspectiveTrack;
    private static Vector<Vector<Vector<Integer>>> trackMatrix;

    private static int camerax;
    private static int cameray;

    private static int cockpitShift;

    private static Boolean upPressed;
    private static Boolean downPressed;
    private static Boolean leftPressed;
    private static Boolean rightPressed;

    private static ImageObject p1;
    private static double p1width;
    private static double p1height;
    private static double p1originalX;
    private static double p1originalY;
    private static double p1velocity;

    private static int XOFFSET;
    private static int YOFFSET;
    private static int WINWIDTH;
    private static int WINHEIGHT;

    private static double pi;
    private static double quarterPi;
    private static double halfPi;
    private static double threequartersPi;
    private static double fivequartersPi;
    private static double threehalvesPi;
    private static double sevenquartersPi;
    private static double twoPi;

    private static JFrame appFrame;

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
}