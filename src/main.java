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

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Polygon;
import java.awt.Color;



public class main {
    // 164-168
    //164
    private static Vector<Vector<Vector<Integer>>> splitColors
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
}
