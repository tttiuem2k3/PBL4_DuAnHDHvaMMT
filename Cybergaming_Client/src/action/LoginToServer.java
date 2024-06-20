package action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import model_structure.Global;
import model_structure.define;


public class LoginToServer {
    
    private String          username = null;
    private String          password = null;
    private Socket          socket;
    private BufferedReader  reader;
    private PrintWriter     writer;
    
    public LoginToServer(String usern, String pass, Socket sock)
    {
        this.username = usern;
        this.password = pass;
        this.socket = sock;
        try{
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        }catch(Exception ex){
            System.out.println("Loi Reader va Writer");
        }
        
    }
    
    public int DoLogin()
    {
        int result = 0;
        try 
        {
            //gửi username cho server
            writer.println("USER "+username);
            writer.flush();
            
            String response = reader.readLine();
            if(!response.startsWith(define.SUCCESS))   //USER cmd fail
            {
                System.out.println("Server reply: "+response);
                result = 2;
            }
            else    //USER cmd success
            {
                
                writer.println("PASS "+password);
                writer.flush();
                
                response = reader.readLine();
                if(response.startsWith(define.FAIL+" User is working"))   //Login fall tài khoản đã được sử dụng
                {
                    System.out.println("Server reply: "+response);
                    result = 1;
                }
                else if(response.startsWith(define.FAIL+" User is blocked"))
                {
                    System.out.println("Server reply: "+response);
                    result = 4;
                }
                else if(response.startsWith(define.FAIL+" User is timeout"))
                {
                    System.out.println("Server reply: "+response);
                    result = 0;
                }
                else if(response.startsWith(define.FAIL+" Username"))  //Login fall tài khoản hoặc mật khẩu không đúng 
                {
                    System.out.println("Server reply: "+response);
                    result = 2;
                }
                else    // Login success.
                {
                    Global.username=this.username;
                    Global.password=this.password;
                    System.out.println("Server reply: "+response);
                    String[] pasts = response.split(" ");
                    long longNumber = Long.parseLong(pasts[1]);
                    Global.cost=longNumber;
                    
                    result = 3;
                  
                }
            }
        } catch (IOException ex) {
            System.out.println("Reader error.");
        }
        return result;
    }
}
