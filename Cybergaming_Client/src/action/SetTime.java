
package action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model_structure.Global;
import model_structure.define;
import view_gui.LoginGui;
import view_gui.MainGuiAccount;


public class SetTime extends Thread{
    
    private long start;
    private long timeend;
    
    private volatile boolean isRunning = true;
    public SetTime(long tStart)
    {
        this.start = tStart;
        
    }
    
    @Override
    public void run()
    {
        
        if(Global.mainGui!= null)
        {
          
        BufferedReader reader = null;
        PrintWriter writer = null;
                
        try {
            reader = new BufferedReader(new InputStreamReader(Global.loginSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(Global.loginSocket.getOutputStream()), true);
            
            writer.println("GETTIME "+Global.username);
            writer.flush();
            
            String response = reader.readLine();
            timeend= Long.parseLong(response);
            
            Global.mainGui.sumtime.setText(getTimeEnd(timeend));
            Global.mainGui.cost.setText("1.000");
        } catch (IOException ex) {
            Logger.getLogger(MainGuiAccount.class.getName()).log(Level.SEVERE, null, ex);
        }  
            int i = 0;
            while(isRunning)
            {
                Calendar endCal = Calendar.getInstance();
                long endtime = endCal.getTimeInMillis();
                
                int minute = (int)((int)(endtime - start)/1000/60);
                float heso=((Global.cost/60f)/1000f);
                float monney =((minute)*heso); // 1 phút == 0.2 k => 1h = 12k
                
                int chanphut=1;
                if(heso<1)
                {
                    chanphut = (int)(1/heso);
                }
                
                if(minute%chanphut==0 && minute!=0)
                {
                    Global.mainGui.cost.setText(String.format("%.1f",monney)+"00");
                }
                String mytimer = getTimeElapsed(endtime);
                Global.mainGui.timeuse.setText(mytimer);

                timeend=timeend-1;
                if(timeend==0) // khi thời gian đã hết
                {
                    try {
                        writer.println("LOGOUT");
                        writer.flush();

                        String response = reader.readLine();
                        if (response.startsWith(define.SUCCESS)) {
                        
                        Global.mainGui.dispose();
                        Global.mainGui=null;
                        
                        LoginGui logingui = new LoginGui();
                        logingui.setVisible(true);
                        JOptionPane.showMessageDialog(null, "Tài khoản của bạn đã hết giờ sử dụng, vui lòng nạp tiền!!!");
                        Global.loginGui = logingui;

                        System.out.println("Đã hết thời gian");
                        this.stopThread();
                        break;
                    }
                    } catch (Exception e) {
                    }
                }
                 Global.mainGui.time.setText(getTimeEnd(timeend));


                i++;
                if(i==60)
                {
                    i=0;
                }
                
                try {    
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SetTime.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }
        if(Global.mainGuiUser !=null)
        {
           
            timeend=359999;
             Global.mainGuiUser.sumtime.setText(getTimeEnd(timeend));
             Global.mainGuiUser.cost.setText("1.000");
             int i = 0;
             while(isRunning)
            {
                Calendar endCal = Calendar.getInstance();
                long endtime = endCal.getTimeInMillis();
                
                int minute = (int)((int)(endtime - start)/1000/60);
                float heso=((Global.costuser/60f)/1000f);
                float monney =((minute)*heso); // 12k /1h
                int chanphut=1;
                if(heso<1)
                {
                    chanphut = (int)(1/heso);
                }
                if(minute%chanphut==0 && minute!=0)
                {
                    Global.mainGuiUser.cost.setText(String.format("%.1f",monney)+"00");
                }
                String mytimer = getTimeElapsed(endtime);
                Global.mainGuiUser.timeuse.setText(mytimer);

                timeend=timeend-1;
                 Global.mainGuiUser.time.setText(getTimeEnd(timeend));

                 
                i++;
                if(i==60)
                {
                    i=0;
                }
                
                try {    
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SetTime.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }    
            
    }
    
    public void stopThread() {
        isRunning = false;
    }
    private String getTimeElapsed(long endtime)
    {
        //Calendar endcal = Calendar.getInstance();
        //long endtime = endcal.getTimeInMillis();
        long elapsedTime = endtime - start+1000;
        elapsedTime = elapsedTime / 1000;

        String seconds = Integer.toString((int)(elapsedTime % 60));
        String minutes = Integer.toString((int)((elapsedTime % 3600) / 60));
        String hours = Integer.toString((int)(elapsedTime / 3600));

        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }

        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }

        if (hours.length() < 2) {
            hours = "0" + hours;
        }

        return hours+":"+minutes+":"+seconds;
    }
     private String getTimeEnd(long endtimes)
    {
        //Calendar endcal = Calendar.getInstance();
        //long endtime = endcal.getTimeInMillis();
        String hours = Integer.toString((int)(endtimes / 3600));
        String minutes = Integer.toString((int)((endtimes % 3600) / 60));
        String seconds = Integer.toString((int)(endtimes % 60));
        
       

        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }

        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }

        if (hours.length() < 2) {
            hours = "0" + hours;
        }

        return hours+":"+minutes+":"+seconds;
    }
}
