

package remote;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;


class ScreenSpyer extends Thread {

    Socket socket = null; 
    Robot robot = null; // Used to capture screen
    Rectangle rectangle = null; //Used to represent screen dimensions
    
    private boolean isRunning = true;
   
    public ScreenSpyer(Socket socket, Robot robot,Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        rectangle = rect;
        //start();
    }

    @Override
    public void run(){
        
        
            
        
        ObjectOutputStream oos = null; //Used to write an object to the streem


        try{
            //Prepare ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            /*
             * gửi kích thước màn hình đến server để server tính toán bắt sự kiện chuột
             * 
             */
            oos.writeObject(rectangle);
//            oos.flush();
        }catch(IOException ex){
            ex.printStackTrace();
        }

       while(isRunning){
            //Capture screen
            BufferedImage image = robot.createScreenCapture(rectangle);
        
            ImageIcon imageIcon = new ImageIcon(image);

            //Send captured screen to the server
            try {
                System.out.println("before sending image");
                oos.writeObject(imageIcon);
                oos.flush();
                oos.reset(); //Clear ObjectOutputStream cache
                System.out.println("New screenshot sent");
            } catch (IOException ex) {
               ex.printStackTrace();
            }

            //wait for 100ms to reduce network traffic
            try{
                Thread.sleep( 100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        
       }
        
    
}
