
package action;

import DAO.UserDao;
import chat.ChatGui;
import com.mysql.cj.jdbc.PreparedStatementWrapper;
import com.mysql.cj.xdevapi.PreparableStatement;
import database.ConnectSqlServer;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import model_structure.Global;
import model_structure.define;


public class LoginFromClient extends Thread{

    private static final int    WAIT_INFO = 0;
    private static final int    WAIT_USERNAME = 1;
    private static final int    WAIT_PASSWORD = 2;
    private static final int    WAIT_GETTIME = 3;
    private static final int    WAIT_LOGOUT = 4;
    private static final int    CHANGE_PASS = 5;
    private static final int    WAIT_CHAT = 6;
    private Socket              cSocket;
    private BufferedReader      reader;
    private PrintWriter         writer;
    
    private String              idClient;
    private String              username;
    private String              password;
    private long                 time;
    public SetTime              setTime;
    private UserDao             userDao= new UserDao();
    //private int                 state;
    
    public LoginFromClient(Socket socket) throws IOException
    {
        this.cSocket = socket;
        this.reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
        this.writer = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()), true);
    }
    
    @Override
    public void run()
    {
        boolean finish = false;
        
        String line;
        //state = WAIT_INFO;
        try
        {
            while(true)
            {
                line = reader.readLine();
                System.out.println(line);
                
                int state = ParseInput(line); 
                if(line.equals("QUIT"))
                {
                    System.out.println("nhan dc quit");  
                    break;
                }
                else
                
               {
                    switch(state)
                    {
                        case WAIT_INFO:
                            finish = CmdInfo(line);
                            break;
                        case WAIT_USERNAME:
                            finish = CmdUser(line);
                            break;
                        case WAIT_PASSWORD:
                            finish = CmdPassword(line);
                            break;
                        case WAIT_GETTIME:
                            finish = CmdGettime(line);
                            break;    
                        case WAIT_LOGOUT:
                            finish = CmdLogout(line);
                            break;
                        case CHANGE_PASS:
                            finish = Cmdchangepass(line);
                            break;  
                        case WAIT_CHAT:
                            finish = CmdChat(line);
                            break;
                    }
                }
            }//end while
            
            cSocket.close();
        }catch(IOException ex){
        }
        String username="";
        if((String)Global.mainGui.main_table.getValueAt(Integer.parseInt(idClient), 3)!=null)
        {
            username=(String)Global.mainGui.main_table.getValueAt(Integer.parseInt(idClient), 3);
        }
        
        Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 1);
        Global.mainGui.main_table.setValueAt(define.DISCONNECT, Integer.parseInt(idClient), 2);
        Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 3);
        Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 4);
        
        if(Global.threadtime[Integer.parseInt(idClient)]!=null)
        {
            Global.threadtime[Integer.parseInt(idClient)].stopThread();
        }
        
        if(!username.equals("USER") && !username.equals(""))
        {
            userDao.setUserlogout(setTime.time, username); // cài lại thời gian và trạng thái khi user logout
            setTime=null;
        }
        
        
        Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 5);
        Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 6);
        System.out.println("Máy "+(Integer.parseInt(idClient)+1)+" đã tắt!!!");
        Global.chatGui[Integer.parseInt(idClient)]=null;
    }
    
    private boolean CmdInfo(String line)
    {
        boolean result = false;
        String ipclient = cSocket.getInetAddress().getHostAddress();
        idClient = "";
        if(line.startsWith("INFO"))
        {
            idClient = GetParameter(line);
            
            Global.mainGui.main_table.setValueAt(ipclient, Integer.parseInt(idClient), 1);
            Global.mainGui.main_table.setValueAt(define.OFFLINE, Integer.parseInt(idClient), 2);
            Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 3);
            Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 4);
            Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 5);
            Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 6);
            
            reply(define.SUCCESS, "INFO command success.");
            //state = WAIT_USERNAME;
            result = true;//--------------------------
        }
        else
        {
            reply(define.FAIL, "INFO command fail.");
            result = false;
        }
        
        return result;
    }
    
    private boolean CmdLogout(String line)
    {
        boolean result = false;
        
        if(line.startsWith("LOGOUT"))
        {
            Global.mainGui.main_table.setValueAt(define.OFFLINE, Integer.parseInt(idClient), 2);
            Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 3);
            Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 4);
             
            Global.threadtime[Integer.parseInt(idClient)].stopThread();
     
            Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 5);
            Global.mainGui.main_table.setValueAt(null, Integer.parseInt(idClient), 6);
            reply(define.SUCCESS, "Logout success.");
            
        }
        
        //state = WAIT_USERNAME;
        userDao.setUserlogout(Global.threadtime[Integer.parseInt(idClient)].time, username); // cài lại thời gian và trạng thái khi user logout
            
        setTime=null;
        System.out.println("LOGOUT finish.");
        Global.mainGui.settableuser();
        
        if(Global.chatGui[Integer.parseInt(idClient)]!=null)
        {
            Global.chatGui[Integer.parseInt(idClient)].setVisible(false);
            Global.chatGui[Integer.parseInt(idClient)]=null;
        }
        else
        {
            Global.chatGui[Integer.parseInt(idClient)]=null;
        }
        Global.username1[Integer.parseInt(idClient)]=null;
        return result;
    }
    
    private boolean CmdUser(String line)
    {
        boolean result = false;
        
        if(line.startsWith("USER "))
        {
            username = GetParameter(line);
            reply(define.SUCCESS, "USER command success.");
            //state = WAIT_PASSWORD;
            result = false;
        }
        else
        {
            reply(define.FAIL, "USER command fail.");
            //state = WAIT_USERNAME;
            result = false;
        }
        
        return result;
    }
    
    
    private boolean  CmdPassword(String line)
    {
        boolean result = false;
        if(line.startsWith("PASS "))
        {
            password  = GetParameter(line);
            this.time= userDao.gettimeUser(username);
            if(CheckUserPass() == true && CheckUserStatus().equals("OFFLINE"))
            {
                if(this.time>0)
                {
                    Global.username1[Integer.parseInt(idClient)]=username;
                    reply(define.SUCCESS, Global.cost+" Login successful.");
                    userDao.setUserwhenlogin(username);// cài trạng thái thành hoạt động khi user đăng nhập
                    Calendar startCal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String startTime = sdf.format(startCal.getTime());
                    long start = startCal.getTimeInMillis();
                    Global.mainGui.main_table.setValueAt(define.ONLINE, Integer.parseInt(idClient), 2);
                    Global.mainGui.main_table.setValueAt(username.toUpperCase(), Integer.parseInt(idClient), 3);
                    Global.mainGui.main_table.setValueAt(startTime, Integer.parseInt(idClient), 4);

                    // lay time

                    setTime = new SetTime(start, Integer.parseInt(idClient), this.time,username);
                    setTime.start();
                    Global.threadtime[Integer.parseInt(idClient)] = setTime;// đưa vao mang idclient hàng số mấy 
                    result = false; 
                    Global.mainGui.settableuser();
                }
                
                else if(this.time<=0)
                {
                    reply(define.FAIL, "User is timeout(LOGIN FALL)");
                    result = false;
                }
            }
            
            else if(CheckUserStatus().equals("ONLINE"))
            {
                reply(define.FAIL, "User is working(LOGIN FALL)");
                result = false;
            }   
             else if(CheckUserStatus().equals("BLOCKED"))
            {
                reply(define.FAIL, "User is blocked(LOGIN FALL)");
                result = false;
            }
            else
            {
                reply(define.FAIL, "Username or password incorrect.(LOGIN FALL)");
                result = false;
            }
            
            //state = WAIT_LOGOUT;
        }
        return result;
    }
    private boolean CmdGettime(String line)
    {
        boolean result = false;
            this.time = userDao.gettimeUser(username);
            writer.println(this.time);
            writer.flush();
            result = false;
        return result;
    }
    private boolean Cmdchangepass(String line)
    {
            boolean result = false;
            String part[]= line.split(" ");
            userDao.changepass(username, part[1]);
            reply(define.SUCCESS, "CHANGE PASS success.");
            result = false;
        return result;
    }
    private boolean CmdChat(String line)
    {
        boolean result = false;
            String part[]= line.split(":");
            int row =  Integer.parseInt(part[1]);
            String text= part[2];
            String ipclient = cSocket.getInetAddress().getHostAddress();
            String username=Global.username1[Integer.parseInt(idClient)];
            if(Global.chatGui[row]==null)
            {
                if(username==null)
                {
                    String name = "MÁY-"+(row+1);
                    if(row+1<10)
                    {
                        name ="MÁY-0"+(row+1);
                    }
                    this.username=name;
                    ChatGui chatGui= new ChatGui(ipclient, name);
                    Global.chatGui[row]=chatGui;
                    Global.chatGui[row].appendBoldColoredText(Global.chatGui[row].doc, name+": ", Color.BLUE);
                    Global.chatGui[row].appendColoredText(Global.chatGui[row].doc, text+"\n", Color.BLACK);
                }
                else
                {
                    ChatGui chatGui= new ChatGui(ipclient, username.toUpperCase());
                    Global.chatGui[row]=chatGui;
                    Global.chatGui[row].appendBoldColoredText(Global.chatGui[row].doc, username.toUpperCase()+": ", Color.BLUE);
                    Global.chatGui[row].appendColoredText(Global.chatGui[row].doc, text+"\n", Color.BLACK);
                }
            }
            else
            {
                if(username==null)
                {
                    String name = "MÁY-"+(row+1);
                    if(row+1<10)
                    {
                        name ="MÁY-0"+(row+1);
                    }
                    Global.chatGui[row].appendBoldColoredText(Global.chatGui[row].doc, name+": ", Color.BLUE);
                    Global.chatGui[row].appendColoredText(Global.chatGui[row].doc, text+"\n", Color.BLACK);
                }
                else
                {
                    Global.chatGui[row].appendBoldColoredText(Global.chatGui[row].doc, username.toUpperCase()+": ", Color.BLUE);
                    Global.chatGui[row].appendColoredText(Global.chatGui[row].doc, text+"\n", Color.BLACK); 
                }
                
            }    
            Global.chatGui[row].setVisible(true);
            reply(define.SUCCESS, "SEND CHAT success.");
            result = false;
        return result;
    }
    private boolean CheckUserPass()
    {
        boolean result = false;
        
 
            if(!username.equals(""))
            {
                String pass= userDao.checkPasslogin(username);

                if(password.equals(pass))
                {
                    result = true;
                   
                }
                else
                {
                    result = false;
                }
            }
        
        
        return result;
    }
     private String CheckUserStatus()
    {
        String result = "0";
        
            
            if(!username.equals(""))
            {
                if(userDao.checkStatuslogin(username)==null)
                {
                    result = "0";
                    return result;
                }
                else
                {
                    String status= userDao.checkStatuslogin(username);
                    if(status.equals("ONLINE"))
                    {
                        result = "ONLINE";

                    }
                    else if( status.equals("BLOCKED"))
                    {
                        result = "BLOCKED";
                    }
                    else
                    {
                        result = "OFFLINE";
                    }
                }

            }
        
        
        return result;
    }
    private String GetParameter(String line)
    {
        String param;
        int p = 0;
        p = line.indexOf(" ");
        
        if(p >= line.length() || p ==-1) {
            param = "";
        }
        else {
            param = line.substring(p+1,line.length());
        }
        
        return param;
    }
    
    private int ParseInput(String line)
    {
        int result = WAIT_INFO;
        
        if(line.startsWith("INFO"))
        {
            result = WAIT_INFO;
        }
        if(line.startsWith("USER"))
        {
            result = WAIT_USERNAME;
        }
        if(line.startsWith("PASS"))
        {
            result = WAIT_PASSWORD;
        }
        if(line.startsWith("GETTIME"))
        {
            result = WAIT_GETTIME;
        }
        if(line.startsWith("LOGOUT"))
        {
            result = WAIT_LOGOUT;
        }
        if(line.startsWith("CHANGEPASS"))
        {
            result = CHANGE_PASS;
        }
        if(line.startsWith("CHAT"))
        {
            result = WAIT_CHAT;
        }
        return result;
    }
    
    // Tra loi code ve client
    void reply(String code, String text) {
        writer.println(code + " " + text);
        writer.flush();
    }
}
