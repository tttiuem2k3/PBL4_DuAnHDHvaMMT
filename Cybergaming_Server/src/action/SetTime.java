
package action;

import DAO.UserDao;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import model_structure.Global;


public class SetTime extends Thread{
    
    private long start;
    private int row;
    public long time;
    private volatile boolean isRunning = true;
    private String username;
    UserDao userDao= new UserDao();
    
    public SetTime(long tStart, int _row, long time, String username)
    {
        this.start = tStart;
        this.row = _row;
        this.time=time;
        this.username=username;
    }
    
    @Override
    public void run()
    {
        
        while(isRunning)
        {
            Calendar endCal = Calendar.getInstance();
            //long starttime = startcal.getTimeInMillis();
            long endtime = endCal.getTimeInMillis();
            String mytimer = getTimeElapsed(endtime);
            this.time= this.time-1;
            if(this.time==0)
            {
                this.time=1;
            }
            String timeend= getTimeEnd(this.time);
            int minute = (int) ((endtime - start)/1000/60);
            
            Global.time[row]=this.time;
            Global.mainGui.main_table.setValueAt(mytimer, row, 5);
            Global.mainGui.main_table.setValueAt(timeend, row, 6);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SetTime.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
       
    }
    public void stopThread() {
        isRunning = false;
    }
     private String getTimeElapsed(long endtime)
    {
       
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
