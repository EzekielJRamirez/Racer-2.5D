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