import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import java.awt.image.BufferedImage;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


public class FormulaOne {
    // 164 - 168
    // 164
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
        Graphics g = appFrame.getGrpahics();
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

    private static Vector<Vector<Vector<Integer>>> rotateImage(Vector<Vector<Vector<Integer>>> inputImg, double angle, double xpos, double ypos, boolean repeatImg) {
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
        // This is where Colin is... 

        for (int i = 0; i < inputImg.size(); i++) {
            for(){

            }

        }


    }

    // 170 - 175
    // 170
    Vector<Vector<Vector<Integer>>> userView = perspectiveFromRectangle(cameraView, base);
    Graphics g = appFrame.getGraphics();
    Graphics2D g2D = (Graphics2D) g;

    for(int =0;i<rectHeight;i++)

    {
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
  
  public double getComX()
        {
        double ret=0;
        if(coords.size()>0)
        {
        for(int i=0;i<coords .size();i=i+2)
        {
        ret=ret+coords.elementAt(i);
        }
        ret=ret/(coords.size()/2.0);
        }
        return ret;
        }
public double getComY()
        {
        double ret=0;
        if(coords.size()>0)
        {
        for(int i=1;i<coords .size();i=i+2)
        {
        ret=ret+coords.elementAt(i);
        }
        ret=ret/(coords.size()/2.0);
        }
        return ret;
        }
public void move(double xinput,double yinput)
        {
        x=x+xinput;
        y=y+yinput;
        }
public void moveto(double xinput,double yinput)
        {
        x=xinput;
        y=yinput;
        }
public void screenWrap(double leftEdge,double rightEdge,double topEdge,double bottomEdge)
        {
        if(x>rightEdge)
        {
        moveto(leftEdge,getY());
        }
        if(x<leftEdge)
        {
        moveto(rightEdge,getY());
        }
        if(y>bottomEdge)
        {
        moveto(getX(),topEdge);
        }
        if(y<topEdge)
        {
        moveto(getX(),bottomEdge);
        }
        }
public void rotate(double angleinput)
        {
        angle=angle+angleinput;
        while(angle>twoPi)
        {
        angle=angle-twoPi;
        }
        while(angle< 0)
        {
        angle=angle+twoPi;
        }
        }
public void spin(double internalangleinput)
        {
        internalangle=internalangle+internalangleinput;
        while(internalangle>twoPi)
        {
        internalangle=internalangle-twoPi;
        }
        while(internalangle< 0)
        {
        internalangle=internalangle+twoPi;
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
private static void bindKey(JPanel myPanel,String input)
        {
        myPanel.getInputMap(IFW).put(KeyStroke.getKeyStroke( ”pressed ” +
        input),input+ ” pressed” );
        myPanel.getActionMap().put(input+ ” pressed” ,new KeyPressed(input
        ));
        myPanel.getInputMap(IFW).put(KeyStroke.getKeyStroke( ”released ” +
        input),input+ ” released” );
        myPanel.getActionMap().put(input+ ” released” ,new KeyReleased(
        input));
        }
public static void main(String[]args)
        {
        setup();
        appFrame.setDefaultCloseOperation(JFrame.EXIT ON CLOSE);
        appFrame.setSize(501,585);
        JPanel myPanel=new JPanel();
        JButton newGameButton=new JButton( ”New Game” );
        newGameButton.addActionListener(new StartGame());
        myPanel.add(newGameButton);
        JButton quitButton=new JButton( ”Quit Game” );
        quitButton.addActionListener(new QuitGame());
        myPanel.add(quitButton);
        bindKey(myPanel, ”UP” );
        bindKey(myPanel, ”DOWN” );
        bindKey(myPanel, ”LEFT” );
        bindKey(myPanel, ”RIGHT” );
        bindKey(myPanel, ”F” );
        appFrame.getContentPane().add(myPanel, ”South” );
        appFrame.setVisible(true);
        }
private static Boolean endgame;
private static BufferedImage background ;
private static BufferedImage player ;
private static BufferedImage cockpit ;
private static BufferedImage track ;
private static BufferedImage perspectiveTrack ;
private static Vector<Vector<Vector<Integer> > > trackMatrix ;
private static int camerax ;
private static int cameray ;
private static int cockpitShift ;
private static Boolean upPressed ;
private static Boolean downPressed ;
private static Boolean lef tPressed ;
private static Boolean rightPressed ;
private static ImageObject p1;
private static double p1width ;
private static double p1height ;
private static double p1originalX ;
private static double p1originalY ;
private static double p1veloci ty ;
private static int XOFFSET;
private static int YOFFSET;
private static int WINWIDTH;
private static int WINHEIGHT;
private static double pi ;
private static double quarterPi ;
private static double halfPi ;
private static double threequartersPi ;
private static double fivequartersPi ;
private static double threehalvesPi ;
private static double sevenquartersPi ;
private static double twoPi ;
private static JFrame appFrame;
private static final int IFW = JComponent .WHEN_IN_FOCUSEDWINDOW;
}

