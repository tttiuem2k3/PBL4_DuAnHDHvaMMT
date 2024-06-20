// dùng để khai báo ban đầu
package model_structure;

import chat.ChatGui;
import java.net.Socket;


public class Global {
    public static view_gui.LoginGui loginGui = null;
    public static view_gui.MainGuiAccount mainGui = null;
    public static view_gui.MainGuiUser mainGuiUser = null;
    public static remote.RemoteClientInit remoteThread = null;
    public static ClientInfo              clientInfo=null;
    public static Socket    loginSocket = null;
    public static Socket    cmdSocket = null;
    public static String    username = null;
    public static String    password= null;
    
    public static String    idClient = null;
    public static long cost= 0;
    public static long costuser= 0;
    public static ChatGui chatGui= null;
    
}
