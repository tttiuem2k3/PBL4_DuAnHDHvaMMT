
package model_structure;

import DAO.SettingDao;
import action.SetTime;
import chat.ChatGui;
import java.util.List;



public class Global {
    public static int  numpc = new SettingDao().getNumofpc();
    public static long cost= new SettingDao().getCost();
    public static long costuser= new SettingDao().getCostUser();
    public static gui.HomeGui mainGui = null;
    public static SetTime[] threadtime = new SetTime[numpc];// số lượng máy tính ứng với mảng settime
    public static long[]  time= new long[numpc];
    public static String  namepc= "#";
    public static String  username= "#";
    public static String  name= "#";
    public static String  phone= "#";
    public static String  sotienconlai= "#";
    public static String  status="#";
    public static List<User> users= null;
    public static ChatGui[] chatGui= new ChatGui[numpc];
    public static String  username1[]= new String[numpc];
}
