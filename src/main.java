import java.awt.*;
import java.util.Vector;

public class main {
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
            Color myColor = new Color(red, green, blue);
            int rgb = myColor.getRGB();
            perspectiveTrack.setRGB(j, i, rgb);
        }
    }
    g2D.drawImage(perspectiveTrack,XOFFSET,YOFFSET +yoffset,null);
}

private static void playerDraw() {
    Graphics g = appFrame.getGraphics();
    Graphics2D g2D = (Graphics2D) g;
    g2D.drawImage(cockpit, XOFFSET, cockpitShift, null);
    g2D.drawImage(rotateImageObject(p1).filter(player, null), (int)(p1.getX() + 0.5),
            (int)(p1.getY() + 0.5), null);
}

private static class KeyPressed extends AbstractAction {
    public KeyPressed() {
        action = "";
    }
    public KeyPressed (String input) {
        action = input;
    }

    public void actionPerformed (ActionEvent e) {
        if (action.equals("UP")) {
            upPressed = true;
        }
        if (action.equals("DOWN")) {
            downPressed = true;
        }
        if (action.equals ("LEFT")) {
            leftPressed = true;
        }
        if (action.equals("RIGHT")) {
            //page 172
            rightPressed = true;
        }
    }
    private String action;
}

private static class KeyReleased extends Abstraction {
    public KeyReleased () {
        action = "";
    }

    public KeyReleased (String input) {
        action = input;
    }

    public void actionPerformed (ActionEvent e) {
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
    public void actionPerformed (ActionEvent e) {
        endgame = true;
        // page 173
    }
}

private static class StartGame implements ActionListener {
    public void actionPerformed (ActionEvent e) {
        endgame = true;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        p1 = new ImageObject(p1originalX, p1original, p1width, p1height, 0.0);
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

    public ImageObject (double xinput, double yinput, double xwidthinput,
                        double yheightinput, double angleinput) {
        x = xinput;
        // page 174
        y = yinput;
        xwidth = xwidthinput;
        yheight = yheightinput;
        angle = angleinput;
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
}
}
