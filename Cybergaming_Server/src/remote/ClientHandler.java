

package remote;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;


class ClientHandler extends Thread {

    private JDesktopPane desktop = null;
    private Socket cSocket = null;
    private JInternalFrame interFrame = new JInternalFrame("Client Screen",
                                                            true, true, true);
    private JPanel cPanel = new JPanel();
    
    private ClientScreenReciever scrRecv;
    
    public ClientHandler(Socket cSocket, JDesktopPane desktop) {
        this.cSocket = cSocket;
        this.desktop = desktop;
        //start();
    }

    /*
     * Draw GUI per each connected client
     */
    public void drawGUI(){
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel,BorderLayout.CENTER);
        interFrame.setSize(200,200);
        desktop.add(interFrame);
        try {
            //Initially show the internal frame maximized
            interFrame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
        //this allows to handle KeyListener events
        cPanel.setFocusable(true);
        interFrame.setVisible(true);
    }

    @Override
    public void run(){

        //used to represent client screen size
        Rectangle clientScreenDim = null;
        //Used to read screenshots and client screen dimension
        ObjectInputStream ois = null;
        //start drawing GUI
        drawGUI();

        try{
            //Read client screen dimension
            ois = new ObjectInputStream(cSocket.getInputStream());
            clientScreenDim =(Rectangle) ois.readObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        //Start recieveing screenshots
        scrRecv = new ClientScreenReciever(ois,cPanel);
        scrRecv.start();
        //Start sending events to the client
        ClientCommandsSender cmdSend = new ClientCommandsSender(cSocket,cPanel,clientScreenDim);
    }

    public void childthreadstop()
    {
        scrRecv.stop();
    }
}
