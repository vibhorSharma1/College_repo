import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.Time;

public class carPannel extends JPanel {
    ImageIcon gif;
    BufferedImage car;
    BufferedImage car1;
    BufferedImage car2;
    BufferedImage car3;
    Timer timer;
    boolean isGameOver=false;
    int xAxis=255;
    int yAxis=400;
    public int getRandom(){
        int[] fcaxis={100,160,220,280,340,400};
        int in= (int) Math.floor(Math.random()*6);
        return fcaxis[in];
    }
    int fcyAxis=0,fcx1Axis= getRandom(),fcx2Axis= getRandom(),fcx3Axis= getRandom();
    
    carPannel(){
        setSize(500, 500);
        showBg();
        loopPaint();
        keyboard();
        setFocusable(true);
    }
    void  showBg() {
        try{
            car=ImageIO.read(carPannel.class.getResource("upCAR.jpg"));
            gif=new ImageIcon(carPannel.class.getResource("road.gif"));
            car1=ImageIO.read(carPannel.class.getResource("downCAR.jpg")) ;
            car2=ImageIO.read(carPannel.class.getResource("downCAR.jpg"));
            car3=ImageIO.read(carPannel.class.getResource("downCAR.jpg"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        g.drawImage(gif.getImage(),20,0,500,500,null);
        g.drawImage(car,xAxis,yAxis,50,100,null);
        g.drawImage(car1,fcx1Axis,fcyAxis,50,100,null);
        g.drawImage(car2,fcx2Axis,fcyAxis,50,100,null);
        g.drawImage(car3,fcx3Axis,fcyAxis,50,100,null);
        
    }
    void loopPaint() {
        timer = new Timer(50, (a) -> {
            if(isCollide(xAxis, yAxis, fcyAxis, fcx1Axis, fcx2Axis, fcx3Axis)){
                timer.stop();
                isGameOver=true;
                JOptionPane.showMessageDialog(this, "Game Over!");
            }
            if(fcyAxis>400){
                fcyAxis=0;
                fcx1Axis= getRandom();
                fcx2Axis= getRandom();
                fcx3Axis= getRandom();
            }else{
                fcyAxis+=5;
            }
            repaint();
        });
        timer.start();
    }
    public void keyboard() {
        System.out.println("keyboard called");
        

        addKeyListener(new KeyListener() {
        
            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if(isGameOver){
                    return;
                }
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        if( xAxis<400){
                            xAxis += 10;
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if(xAxis>90){
                            xAxis -= 10;
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(yAxis>0){
                            yAxis -= 10;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(yAxis<400){
                            yAxis += 10;
                        }
                        break;        
                }
                repaint();
                // throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                xAxis+=0;
                // throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
            }

        });
    }
    
    public boolean isCollide(int xAxis, int yAxis, int fcyAxis, int fcx1Axis, int fcx2Axis, int fcx3Axis) {
        int carWidth = 50;  // Width of the cars
        int carHeight = 100; // Height of the cars
    
        // Define the boundaries for the main car
        int carX1 = xAxis;
        int carX2 = xAxis + carWidth;
        int carY1 = yAxis;
        int carY2 = yAxis + carHeight;
    
        // Define boundaries for each of the three other cars
        int[][] otherCars = {
            {fcx1Axis, fcyAxis, fcx1Axis + carWidth, fcyAxis + carHeight},
            {fcx2Axis, fcyAxis, fcx2Axis + carWidth, fcyAxis + carHeight},
            {fcx3Axis, fcyAxis, fcx3Axis + carWidth, fcyAxis + carHeight}
        };
    
        // Check collision with each of the other cars
        for (int[] car : otherCars) {
            int otherCarX1 = car[0];
            int otherCarX2 = car[2];
            int otherCarY1 = car[1];
            int otherCarY2 = car[3];
    
            // Check if there's overlap in both x and y ranges
            if (carX1 < otherCarX2 && carX2 > otherCarX1 && carY1 < otherCarY2 && carY2 > otherCarY1) {
                return true;  // Collision detected
            }
        }
    
        return false;  // No collision detected
    }
}

