
package main_run;

import connect.RecvConnectionFromServer;
import connect.SendConnectionToServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import model_structure.ClientInfo;
import model_structure.Global;
import view_gui.Changepass;
import view_gui.LoginGui;



public class Cybergaming_Client 
{
  
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        // TODO code application logic here
        // connect to server
        ClientInfo info = SetIPserver();
        Global.clientInfo=info;
        Global.idClient=info.getID();
        SendConnectionToServer connect = new SendConnectionToServer(info);
        Socket clSocket = connect.DoConnect();
        
        Global.loginSocket = clSocket;
        
        // Nhan cac ket noi tu server
        // Command cua server
        RecvConnectionFromServer connect1 = new RecvConnectionFromServer();
        connect1.start();
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginGui logingui = new LoginGui();
                logingui.setVisible(true); 
                Global.loginGui = logingui;
            }
        });
        
        
    }
    
    private static ClientInfo SetIPserver()
    {

        
        ClientInfo info = new ClientInfo();
        File myfile = new File("D:\\Code\\PBL4\\code\\cybergaming.txt"); 
//            File myfile = new File("C:\\Users\\trant\\Downloads\\cybergaming.txt"); 
//            File myfile = new File("cybergaming.txt"); 
    if (myfile.exists()) {
        try {
            BufferedReader buffin;
            buffin = new BufferedReader(new InputStreamReader(new FileInputStream(myfile)));

            String ID = buffin.readLine();
            if (ID != null) {
                info.setID(ID);
                
            } else {
                System.out.println("ID server null");
            }

            String IP = buffin.readLine();
            if (IP != null) {
                info.setIP(IP);
            } else {
                System.out.println("IP server null");
            }

            buffin.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    } else {
        System.out.println("File không tồn tại");
    }

    return info;
    }
}
