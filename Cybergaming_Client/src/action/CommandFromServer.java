
package action;

import chat.ChatGui;
import java.awt.Color;
import view_gui.LoginGui;
import view_gui.MainGuiAccount;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import remote.RemoteClientInit;
import model_structure.Global;
import model_structure.MyProcess;
import model_structure.define;
import view_gui.MainGuiUser;


public class CommandFromServer extends Thread
{
    private static final int LOGIN = 11;
    private static final int LOGOUT = 12;
    private static final int SHUTDOWN = 13;
    private static final int RESTART = 14;
    private static final int DESKTOP = 15;
    private static final int HISTORY = 16;
    private static final int APPS = 17;
    private static final int QUIT = 18;
    private static final int KILL = 19;
    private static final int RECHARGE = 20;
    private static final int BLOCKED = 21;
    private static final int CHAT = 22;
    //private Socket              logSocket;
    private Socket              cmdSocket;
    private BufferedReader      reader;
    private PrintWriter         writer;
    
    public CommandFromServer(Socket sock)
    {
        this.cmdSocket = sock;
        //this.logSocket = Global.loginSocket;
        try {
            reader = new BufferedReader(new InputStreamReader(cmdSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(cmdSocket.getOutputStream()), true);
        } catch (Exception e) {
        }
        
    }
    
    @Override
    public void run() 
    {
        try
        {
            
           
            String line = "";
            int parse = 0;

            line = reader.readLine();
            System.out.println(line);
            if(line == null)
            {
                System.out.print("loi reader");
            }
            else
            {
                parse = ParseInput(line);
                System.out.println("parse: " +parse);
                switch(parse)
                {
                    case LOGIN:
                        CmdLogin();
                        break;
                    case LOGOUT:
                        CmdLogout();
                        break;
                    case SHUTDOWN:
                        CmdShutdown();
                        break;
                    case RESTART:
                        CmdRestart();
                        break;
                    case DESKTOP:
                        CmdDesktop();
                        break;
                    case HISTORY:
                        CmdHistory();
                        break;
                    case APPS:
                        CmdApplication();
                        break;
                    case QUIT:
                        CmdQuit();
                        break;
                    case KILL:
                        CmdKillApps();
                        break;
                    case RECHARGE:
                        CmdRecharge();
                        break;    
                    case BLOCKED:
                        CmdBlocked();
                        break;  
                    case CHAT:
                        CmdChat(line);
                        break;  
                }
            }
            
//            cmdSocket.close();
        } catch (IOException ex) {
            System.exit(0);
        }
    }
    
    private void CmdLogin()
    {
        if(Global.loginGui != null)
        {
            Global.loginGui.dispose();
            MainGuiUser mainGuiUser = new MainGuiUser();
            Global.mainGuiUser = mainGuiUser;
            mainGuiUser.setVisible(true);
            
           
            Global.mainGui=null;
            Global.loginGui = null;
            
            writer.println(define.SUCCESS);
            writer.flush();
        }
        else
        {
            System.out.println("fail");
            writer.println(define.FAIL);
            writer.flush();
        }
    }
    
    private void CmdLogout()
    {
        if(Global.mainGui != null)
        {
            Global.mainGui.settime.stopThread();
           
            Global.mainGui.dispose();
            
            Global.mainGui = null;
            LoginGui loginGui = new LoginGui();
            loginGui.setVisible(true);
            
            Global.loginGui = loginGui;
            if(Global.chatGui!=null)
            {
                Global.chatGui.setVisible(false);
                Global.chatGui=null;
            }
            else
            {
                Global.chatGui=null;
            }
            
            
            writer.println(define.SUCCESS);
            writer.flush();
        }
        else if(Global.mainGuiUser != null)
        {
             Global.mainGuiUser.settime.stopThread();
             Global.mainGuiUser.dispose();
            
            Global.mainGuiUser = null;
            LoginGui loginGui = new LoginGui();
            loginGui.setVisible(true);
            
            Global.loginGui = loginGui;
            if(Global.chatGui!=null)
            {
                Global.chatGui.setVisible(false);
                Global.chatGui=null;
            }
            else
            {
                Global.chatGui=null;
            }
            writer.println(define.SUCCESS);
            writer.flush();
        }    
        else
        {
            writer.println(define.FAIL);
            writer.flush();
        }
    }
    private void CmdRecharge()
    { 
        Global.mainGui.settime.stopThread();
        Global.mainGui.dispose();
        Global.mainGui = null;
        MainGuiAccount guiAccount = new MainGuiAccount();
        Global.mainGui = guiAccount;
        guiAccount.setVisible(true);
        writer.println(define.SUCCESS);
        writer.flush();
        
    }
    private void CmdBlocked()
    { 
        Global.mainGui.settime.stopThread();
        Global.mainGui.dispose();
        Global.mainGui = null;
        LoginGui loginGui= new LoginGui();
        Global.loginGui = loginGui;
        loginGui.setVisible(true);
        writer.println(define.SUCCESS);
        writer.flush();
        if(Global.chatGui!=null)
            {
                Global.chatGui.setVisible(false);
                Global.chatGui=null;
            }
        else
            {
                Global.chatGui=null;
            }
        JOptionPane.showMessageDialog(null, "Tài khoản của bạn đã bị khóa, liên hệ ADMIN để biết thêm chi tiết!");
    }
    private void CmdChat(String text)
    { 
        String part[]= text.split(":");
        if(Global.chatGui==null)
        {
            ChatGui chatGui= new ChatGui();
            Global.chatGui = chatGui;
            Global.chatGui.appendBoldColoredText(Global.chatGui.doc,"ADMIN: ", Color.RED);
            Global.chatGui.appendColoredText(Global.chatGui.doc, part[1]+"\n", Color.BLUE);
            
        }
        else
        {
            Global.chatGui.appendBoldColoredText(Global.chatGui.doc,"ADMIN: ", Color.RED);
            Global.chatGui.appendColoredText(Global.chatGui.doc, part[1]+"\n", Color.BLUE);
            Global.chatGui.setVisible(true);
        }
        writer.println(define.SUCCESS);
        writer.flush();
    }
    private void CmdShutdown()
    {
        writer.println(define.SUCCESS);
        writer.flush();
        String shutdownCmd = "shutdown.exe -s -t 1";
        if(Global.loginGui != null)
        {
            Global.loginGui.dispose();
            Global.loginGui = null;
        }
        if(Global.mainGui != null)
        {
            Global.mainGui.dispose();
            Global.mainGui = null;
        }
        try 
        {
            Runtime.getRuntime().exec(shutdownCmd);
            
        } catch (IOException ex) {
            Logger.getLogger(CommandFromServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
    private void CmdRestart()
    {
        writer.println(define.SUCCESS);
        writer.flush();
        String restartCmd = "shutdown.exe -r -t 1";
        if(Global.loginGui != null)
        {
            Global.loginGui.dispose();
            Global.loginGui = null;
        }
        if(Global.mainGui != null)
        {
            Global.mainGui.dispose();
            Global.mainGui = null;
        }
        try 
        {
            Runtime.getRuntime().exec(restartCmd);
            
        } catch (IOException ex) {
            Logger.getLogger(CommandFromServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
    private void CmdDesktop()
    {
        String ip = cmdSocket.getInetAddress().getHostAddress();
        remote.RemoteClientInit remote = new RemoteClientInit(ip);
        remote.start();
        Global.remoteThread = remote;
        
        writer.println(define.SUCCESS);
        writer.flush();
    }
    
    private void CmdHistory()
    {
        
    }
    
    private void CmdKillApps()
    {
        try {
            writer.println(define.SUCCESS);
            writer.flush();
            String pid = reader.readLine();
            System.out.println("Kill pid "+pid);
            if(pid != null)
            {
                String cmd = "taskkill /PID " + pid;
                Process proc = Runtime.getRuntime().exec(cmd);
                BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String res = input.readLine();
                if(res.startsWith("SUCCESS"))
                {
                    writer.println(define.SUCCESS);
                    writer.flush();
                }
                else
                {
                    writer.println(define.FAIL);
                    writer.flush();
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CommandFromServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void CmdApplication()
    {
        try 
        {
            if(Global.mainGui!=null)
            {
                Global.mainGui.setVisible(true);
            }
            else if(Global.mainGuiUser!=null)
            {
                Global.mainGuiUser.setVisible(true);
            }
            
            Vector<MyProcess> listProc = getProcess();
            ObjectOutputStream objWriter = new ObjectOutputStream(cmdSocket.getOutputStream());
            objWriter.writeObject(listProc);
            objWriter.flush();
            
            
            String reponse = reader.readLine();
            if(reponse.equals(define.SUCCESS))
            {
                System.out.println("APPS success.");
            }
            else
            {
                System.out.println("APPS fail.");
            }
            
            objWriter.close();
        } catch (IOException ex) {
//            Logger.getLogger(CommandFromServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Vector<MyProcess> getProcess()
    {
        Vector<MyProcess> listProcess = new Vector<>();
        try 
        {
            String cmd = "tasklist /fi \"STATUS eq RUNNING\" /nh /fo csv";
            Process process = Runtime.getRuntime().exec(cmd);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            
            while((line = input.readLine()) != null)
            {
                MyProcess temp = new MyProcess();
                String[] substring = line.split("\",\"");
                String substr = substring[0].substring(1);
                
                temp.setImagename(substr);
                temp.setPid(substring[1]);
                
                System.out.println(temp.getImagename());
                System.out.println(temp.getPid());
                
                listProcess.add(temp);
            }
        } catch (IOException ex) {
        }
        return listProcess;
    }
    private void CmdQuit()
    {
        if(Global.remoteThread != null)
        {
            Global.remoteThread.stopthread();
            Global.remoteThread = null;
        }
    }
    
    
    private int ParseInput(String line)
    {
        int result = 0;
        
        if(line.startsWith("LOGIN"))
        {
            // tach ra để lấy costusser
            String[] pasts = line.split(" ");
            long longNumber = Long.parseLong(pasts[1]);
            Global.costuser=longNumber;
            result = LOGIN;
        }
        if(line.equals("LOGOUT"))
        {
            result = LOGOUT;
        }
        if(line.equals("SHUTDOWN"))
        {
            result = SHUTDOWN;
        }
        if(line.equals("RESTART"))
        {
            result = RESTART;
        }
        if(line.equals("DESKTOP"))
        {
            result = DESKTOP;
        }
        if(line.equals("HISTORY"))
        {
            result = HISTORY;
        }
        if(line.equals("APPS"))
        {
            result = APPS;
        }
        if(line.equals("QUIT"))
        {
            result = QUIT;
        }
        if(line.equals("KILL"))
        {
            result = KILL;
        }
        if(line.equals("RECHARGE"))
        {
            result = RECHARGE;
        }
        if(line.equals("BLOCKED"))
        {
            result = BLOCKED;
        }    
        if(line.startsWith("CHAT"))
        {
            result = CHAT;
        } 
        return result;
    }
    
}
