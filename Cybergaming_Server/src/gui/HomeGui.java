
package gui;

import DAO.SettingDao;
import DAO.UserDao;
import action.CreateConnection;
import action.SetTime;
import chat.ChatGui;
import command.Commands;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;


import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model_structure.Global;
import model_structure.MyProcess;
import model_structure.User;
import model_structure.define;


public final class HomeGui extends javax.swing.JFrame {

    private int                     numpc = new SettingDao().getNumofpc();
    private long                    start;
    private int                     row = -1;
    public int                      rowuser = -1;
    private String                  iprow = "#";
    private String                  state = "#";
    public  String                  username = "#";
    public  String                  name="#";
    public  String                  phone="#";
    public  String                  sotienconlai="#";
    public  String                  status="#";
    public  DefaultTableModel       tableModel;
    private BufferedReader          reader;
    private PrintWriter             writer;
    private UserDao                 userDao=new UserDao();
    private SettingDao              settingDao= new SettingDao();
    private SetTime                 setTime;
    
    public HomeGui() {
        
        initComponents();
        setLocationRelativeTo(null);
        
        // sử lý sự kiện khi chọn vào một hàng của bảng main(máy trạm)
        main_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                row = main_table.getSelectedRow();
                if(row !=-1)
                {
                iprow = (String) main_table.getValueAt(row, 1);
                state = (String) main_table.getValueAt(row, 2);
                username=(String) main_table.getValueAt(row, 3);
                Global.namepc=(String) main_table.getValueAt(row, 0);
                }
                
                
                if(state == null)
                {
                    state = "#";
                    username="#";
                } 
            }
        });
        // sử lý sự kiện khi chọn vào một hàng của bảng user
        user_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                rowuser = user_table.getSelectedRow();
                if(rowuser !=-1)
                {
                    username=(String) user_table.getValueAt(rowuser, 0);
                    name=(String) user_table.getValueAt(rowuser, 1);
                    phone=(String) user_table.getValueAt(rowuser, 2);
                    sotienconlai=(String) user_table.getValueAt(rowuser, 3);
                    status=(String) user_table.getValueAt(rowuser, 4);
                    
                    // gán biến trung gian để sử dụng sau
                    Global.username=username;
                    Global.name=name;
                    Global.phone=phone;
                    Global.sotienconlai=sotienconlai;
                    Global.status=status;
                    
                }
            }
        });
        // sử lý sự kiện khi chọn vào một tab panel
        TabPane.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e)
            {
                if(TabPane.getSelectedIndex()== TabPane.indexOfTab("Hội viên"))
                {
                    settableuser(); // làm mới bảng usertable
                }
            }
        });
    // khởi tạo main_table - máy trạm 
        Object[][] data= new Object[numpc][7];
        for(int i=0; i<numpc; i++)
        {
            String tenmay="";
            if(i>=9)
            {
                tenmay = "MAY-"+(i+1);
            }
            else
            {
                tenmay = "MAY-0"+(i+1);
            }
            data[i][0]=tenmay;
            data[i][2]=define.DISCONNECT;

        }
        Object[] columns = {"Tên máy", "Địa chỉ IP","Trạng thái","Tên người dùng","Bắt đầu lúc","Thời gian sử dụng","Thời gian còn lại"};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        main_table.setModel(model);
        // in màu giao diện các cột cho main_table - máy trạm 
        customtablegui1();
        // khởi tạo table_user - hội viện cùng 
        settableuser();
        // khởi tạo giá trị tab cài đặt
        somay.setValue(Global.numpc);
        giatien.setValue(Global.cost);
        giatienuser.setValue(Global.costuser);
        // tạo conection
        CreateConnection connection = new CreateConnection();
        connection.start();    
        
    }
    public void customtablegui1()
    {
        main_table.setShowGrid(false);
        JTableHeader tableHeader= main_table.getTableHeader();
        Font headerfont= new Font("SEGOE UI",Font.BOLD,15);
        tableHeader.setFont(headerfont);
        tableHeader.setForeground(new java.awt.Color(51, 51, 0));
        tableHeader.setBackground(new java.awt.Color(255, 204, 153));
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 32));
        
        main_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(main_table, value, isSelected, hasFocus, row, column);
                    
                   
                    if (column == 0) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                        }
                        else
                        {
                            component.setForeground(Color.BLACK);
                            component.setBackground(Color.WHITE);
                        }
                        
                    }
                   
                    else if (column == 1) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                        }
                        else
                        {
                            if(value==null)
                            {
                                component.setBackground(Color.WHITE);
                            }
                            else
                            {
                                component.setBackground(new java.awt.Color(255, 255, 204));
                                component.setForeground(new java.awt.Color(102, 102, 0));
                            }
                            
                        }
                        
                    }
                    else if (column == 2) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                        }
                        else
                        {
                            
                            if(value.equals(define.DISCONNECT))
                            {
                                component.setBackground(new java.awt.Color(255, 204, 204));
                                component.setForeground(Color.RED);
                            }
                            else if(value.equals(define.OFFLINE))
                            {
                                component.setBackground(new java.awt.Color(204, 255, 204));
                                component.setForeground(new java.awt.Color(0, 204, 0));
                            }
                            else if(value.equals(define.ONLINE))
                            {
                                component.setBackground(new java.awt.Color(153, 204, 255));
                                component.setForeground(Color.BLUE);
                            }
                            
                        }
                        
                    }
                    else if (column == 3) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                        }
                        else
                        {
                            if(value==null)
                            {
                                component.setBackground(Color.WHITE);
                                component.setForeground(Color.BLACK);
                            }
                            else if(value.equals("USER"))
                            {
                                component.setBackground(new java.awt.Color(204, 255, 204));
                                component.setForeground(Color.BLACK);
                            }
                            else
                            {
                                component.setBackground(Color.WHITE);
                                component.setForeground(Color.BLACK);
                            }
                            
                        }
                        
                    }
                    else if (column == 4) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                        }
                        else
                        {
                            component.setBackground(Color.WHITE);
                            component.setForeground(Color.BLACK);
                        }
                        
                    }
                    else if (column == 5) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                        }
                        else
                        {
                            if(value!=null)
                            {
                                component.setBackground(new java.awt.Color(153, 204, 255));
                                component.setForeground(Color.BLUE);
                            }
                            else
                            {
                                component.setBackground(Color.WHITE);
                                
                            }
                        }
                        
                    }
                    else if (column == 6) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                        }
                        else
                        {
                            if(value!=null)
                            {
                                component.setBackground(new java.awt.Color(255, 204, 204));
                                component.setForeground(Color.RED);
                            }
                            else
                            {
                                component.setBackground(Color.WHITE);
                                
                            }
                        }
                        
                    }
                    
                    return component;
                }
            });
        this.setVisible(true);
    }
    public void settableuser()
    {
        try {
            rowuser=-1;
            DefaultTableModel userModel = new DefaultTableModel();
            userModel.addColumn("Tên đăng nhập");
            userModel.addColumn("Họ và tên");
            userModel.addColumn("Số điện thoại");
            userModel.addColumn("Số tiền");
            userModel.addColumn("Trạng thái");
            List<User> users= userDao.getallUser();
            for(int i=0; i< users.size();i++)
            {
                if(users.get(i).getStatus().equals("ONLINE"))
                {
                    
                    long time=0;
                    for(int j=0;j<numpc;j++)
                    {
                        String name=(String)main_table.getValueAt(j, 3);
                        
                        if(name!=null)
                        {
                            if(name.equals(users.get(i).getUsername().toUpperCase()))
                            {
                                time=Global.time[j];
                            }
                        }
                    }
                    
                    userModel.addRow(new Object[]{users.get(i).getUsername().toUpperCase() , users.get(i).getName(), users.get(i).getPhone(), changetomoney(time), users.get(i).getStatus()});
                }
                else
                {
                    userModel.addRow(new Object[]{users.get(i).getUsername().toUpperCase() , users.get(i).getName(), users.get(i).getPhone(), changetomoney(users.get(i).getTime()), users.get(i).getStatus()});
                }
                
            }
            user_table.setModel(userModel);
            user_table.setShowGrid(false);
            JTableHeader tableHeader= user_table.getTableHeader();
            Font headerfont= new Font("SEGOE UI",Font.BOLD,15);
            tableHeader.setFont(headerfont);
            tableHeader.setForeground(new java.awt.Color(51, 51, 0));
            tableHeader.setBackground(new java.awt.Color(255, 204, 153));
            tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 32));
            user_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(user_table, value, isSelected, hasFocus, row, column);
                    
                    
                    if (column == 0) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                            component.setForeground(Color.RED);
                            component.setBackground(new java.awt.Color(204, 255, 255));
                        }
                        
                    }
                   
                    else if (column == 1) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                           
                            component.setForeground(new java.awt.Color(0, 153, 0));
                            component.setBackground(Color.WHITE);
                        }
                        
                    }
                    else if (column == 2) {
                        if(isSelected)
                        {
                                component.setBackground(Color.BLACK);
                                component.setForeground(Color.WHITE);
                        }
                        else
                        {
                                component.setForeground(Color.BLUE);
                                component.setBackground(Color.WHITE);
                        }
                        
                    }
                    else if (column == 3) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                            
                            component.setForeground(new java.awt.Color(102, 0, 102));
                            component.setBackground(new java.awt.Color(204, 204, 255));
                        }
                        
                    }
                    else if (column == 4) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                            
                            if(value.equals("OFFLINE"))
                            {
                                component.setBackground(new java.awt.Color(204, 204, 204));
                                
                                component.setForeground(new java.awt.Color(51, 51, 51));
                            }
                            else if(value.equals("ONLINE"))
                            {
                                component.setBackground(new java.awt.Color(204, 255, 204));
                                component.setForeground(new java.awt.Color(0, 153, 0));
                            }
                            else if(value.equals("BLOCKED"))
                            {
                                component.setBackground(new java.awt.Color(255, 204, 204));
                                component.setForeground(Color.RED);
                            }
                        }
                        
                    }
                 
                    return component;
                }
            });
            user_table.setVisible(true);
        } catch (Exception e) {
        }
        
    }
     public void settableuserfind()
    {
        try {
            rowuser=-1;
            DefaultTableModel userModel = new DefaultTableModel();
            userModel.addColumn("Tên đăng nhập");
            userModel.addColumn("Họ và tên");
            userModel.addColumn("Số điện thoại");
            userModel.addColumn("Số tiền");
            userModel.addColumn("Trạng thái");
            List<User> users=Global.users;
            for(int i=0; i< users.size();i++)
            {
                if(users.get(i).getStatus().equals("ONLINE"))
                {
                    
                    long time=0;
                    for(int j=0;j<numpc;j++)
                    {
                        String name=(String)main_table.getValueAt(j, 3);
                        
                        if(name!=null)
                        {
                            if(name.equals(users.get(i).getUsername().toUpperCase()))
                            {
                                time=Global.time[j];
                            }
                        }
                    }
                    
                    userModel.addRow(new Object[]{users.get(i).getUsername().toUpperCase() , users.get(i).getName(), users.get(i).getPhone(), changetomoney(time), users.get(i).getStatus()});
                }
                else
                {
                    userModel.addRow(new Object[]{users.get(i).getUsername().toUpperCase() , users.get(i).getName(), users.get(i).getPhone(), changetomoney(users.get(i).getTime()), users.get(i).getStatus()});
                }
                
            }
            user_table.setModel(userModel);
            user_table.setShowGrid(false);
            JTableHeader tableHeader= user_table.getTableHeader();
            Font headerfont= new Font("SEGOE UI",Font.BOLD,15);
            tableHeader.setFont(headerfont);
            tableHeader.setForeground(new java.awt.Color(51, 51, 0));
            tableHeader.setBackground(new java.awt.Color(255, 204, 153));
            tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 32));
            user_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(user_table, value, isSelected, hasFocus, row, column);
                    
                    
                    if (column == 0) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                            component.setForeground(Color.RED);
                            component.setBackground(new java.awt.Color(204, 255, 255));
                        }
                        
                    }
                   
                    else if (column == 1) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                           
                            component.setForeground(new java.awt.Color(0, 153, 0));
                            component.setBackground(Color.WHITE);
                        }
                        
                    }
                    else if (column == 2) {
                        if(isSelected)
                        {
                                component.setBackground(Color.BLACK);
                                component.setForeground(Color.WHITE);
                        }
                        else
                        {
                                component.setForeground(Color.BLUE);
                                component.setBackground(Color.WHITE);
                        }
                        
                    }
                    else if (column == 3) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                            
                            component.setForeground(new java.awt.Color(102, 0, 102));
                            component.setBackground(new java.awt.Color(204, 204, 255));
                        }
                        
                    }
                    else if (column == 4) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                            
                            if(value.equals("OFFLINE"))
                            {
                                component.setBackground(new java.awt.Color(204, 204, 204));
                                
                                component.setForeground(new java.awt.Color(51, 51, 51));
                            }
                            else if(value.equals("ONLINE"))
                            {
                                component.setBackground(new java.awt.Color(204, 255, 204));
                                component.setForeground(new java.awt.Color(0, 153, 0));
                            }
                            else if(value.equals("BLOCKED"))
                            {
                                component.setBackground(new java.awt.Color(255, 204, 204));
                                component.setForeground(Color.RED);
                            }
                        }
                        
                    }
                 
                    return component;
                }
            });
            user_table.setVisible(true);
        } catch (Exception e) {
        }
        
    }
    public long changetimetolong(String timestring)
    {
        long time=0;
        String[] parts = timestring.split(":");
        int j=3600;
        for(int i=0;i<=2;i++)
        {
            time=time+ Integer.parseInt(parts[i])*j;
            j=j/60;
        }   
        return time;
    }
    public String changetomoney(long time)
    {
         
        int minute= (int)time/60;
        float heso=((Global.cost/60f)/1000f);
        float cost= minute*heso; // 1h=12k
        String formattedCost = String.format("%.1f", cost);
        String money=formattedCost+"00 VNĐ";
        return money;
    }
    public long changetotime(long sotien)
    {
        long time;
        int heso=(int)(Global.cost/60);
        int minute= (int)sotien/(heso);//1h=12k
        time =minute*60;
        return time;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabPane = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        MoneyButton = new javax.swing.JButton();
        LoginButton = new javax.swing.JButton();
        LogoutButton = new javax.swing.JButton();
        ShutdownButton = new javax.swing.JButton();
        RestartButton = new javax.swing.JButton();
        DesktopButton = new javax.swing.JButton();
        AppsButton = new javax.swing.JButton();
        AppsButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        main_table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_fullname = new javax.swing.JTextField();
        txt_cmnd = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btRegister = new javax.swing.JButton();
        txt_password = new javax.swing.JPasswordField();
        txt_retype = new javax.swing.JPasswordField();
        sotien = new javax.swing.JLabel();
        sotiendau = new javax.swing.JSpinner();
        jPanel6 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        naptien = new javax.swing.JButton();
        capnhat = new javax.swing.JButton();
        xoa1 = new javax.swing.JButton();
        khoa = new javax.swing.JButton();
        xoa = new javax.swing.JButton();
        xoa2 = new javax.swing.JButton();
        xoa3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        user_table = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btRegister1 = new javax.swing.JButton();
        giatien = new javax.swing.JSpinner();
        giatienuser = new javax.swing.JSpinner();
        somay = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CSM SERVER");
        setUndecorated(true);

        TabPane.setBackground(new java.awt.Color(204, 255, 255));
        TabPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 51), 3, true));
        TabPane.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setEnabled(false);
        jToolBar1.setMargin(new java.awt.Insets(0, 0, 0, 3));

        MoneyButton.setBackground(new java.awt.Color(255, 255, 153));
        MoneyButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MoneyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/currency_dollar_red.png"))); // NOI18N
        MoneyButton.setText("Tính tiền");
        MoneyButton.setFocusable(false);
        MoneyButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MoneyButton.setInheritsPopupMenu(true);
        MoneyButton.setMaximumSize(new java.awt.Dimension(87, 30));
        MoneyButton.setMinimumSize(new java.awt.Dimension(87, 30));
        MoneyButton.setOpaque(true);
        MoneyButton.setPreferredSize(new java.awt.Dimension(87, 30));
        MoneyButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        MoneyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoneyButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(MoneyButton);

        LoginButton.setBackground(new java.awt.Color(255, 204, 255));
        LoginButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LoginButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/door-in-icon.png"))); // NOI18N
        LoginButton.setText("Mở Máy");
        LoginButton.setFocusable(false);
        LoginButton.setHideActionText(true);
        LoginButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        LoginButton.setMaximumSize(new java.awt.Dimension(84, 30));
        LoginButton.setMinimumSize(new java.awt.Dimension(84, 30));
        LoginButton.setOpaque(true);
        LoginButton.setPreferredSize(new java.awt.Dimension(84, 30));
        LoginButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(LoginButton);

        LogoutButton.setBackground(new java.awt.Color(204, 255, 255));
        LogoutButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LogoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/door-out-icon.png"))); // NOI18N
        LogoutButton.setText("Đăng Xuất");
        LogoutButton.setFocusable(false);
        LogoutButton.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        LogoutButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        LogoutButton.setMaximumSize(new java.awt.Dimension(90, 30));
        LogoutButton.setMinimumSize(new java.awt.Dimension(90, 30));
        LogoutButton.setOpaque(true);
        LogoutButton.setPreferredSize(new java.awt.Dimension(90, 30));
        LogoutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        LogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(LogoutButton);

        ShutdownButton.setBackground(new java.awt.Color(255, 204, 204));
        ShutdownButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ShutdownButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/shutdown-icon.png"))); // NOI18N
        ShutdownButton.setText("Shutdown");
        ShutdownButton.setFocusable(false);
        ShutdownButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ShutdownButton.setInheritsPopupMenu(true);
        ShutdownButton.setMaximumSize(new java.awt.Dimension(87, 30));
        ShutdownButton.setMinimumSize(new java.awt.Dimension(87, 30));
        ShutdownButton.setOpaque(true);
        ShutdownButton.setPreferredSize(new java.awt.Dimension(87, 30));
        ShutdownButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ShutdownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShutdownButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(ShutdownButton);

        RestartButton.setBackground(new java.awt.Color(204, 255, 204));
        RestartButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        RestartButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/NX1-Restart-icon.png"))); // NOI18N
        RestartButton.setText("Restart");
        RestartButton.setFocusable(false);
        RestartButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        RestartButton.setMaximumSize(new java.awt.Dimension(80, 30));
        RestartButton.setMinimumSize(new java.awt.Dimension(80, 30));
        RestartButton.setOpaque(true);
        RestartButton.setPreferredSize(new java.awt.Dimension(80, 30));
        RestartButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        RestartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestartButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(RestartButton);

        DesktopButton.setBackground(new java.awt.Color(204, 204, 255));
        DesktopButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DesktopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/monitor-desktop-icon.png"))); // NOI18N
        DesktopButton.setText("Giám sát");
        DesktopButton.setFocusable(false);
        DesktopButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        DesktopButton.setMaximumSize(new java.awt.Dimension(84, 30));
        DesktopButton.setMinimumSize(new java.awt.Dimension(84, 30));
        DesktopButton.setOpaque(true);
        DesktopButton.setPreferredSize(new java.awt.Dimension(84, 30));
        DesktopButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        DesktopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesktopButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(DesktopButton);

        AppsButton.setBackground(new java.awt.Color(181, 194, 204));
        AppsButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AppsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/Apps-system-software-update-icon.png"))); // NOI18N
        AppsButton.setText("Ứng dụng");
        AppsButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        AppsButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        AppsButton.setMaximumSize(new java.awt.Dimension(89, 30));
        AppsButton.setMinimumSize(new java.awt.Dimension(89, 30));
        AppsButton.setOpaque(true);
        AppsButton.setPreferredSize(new java.awt.Dimension(89, 30));
        AppsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AppsButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(AppsButton);

        AppsButton1.setBackground(new java.awt.Color(130, 204, 204));
        AppsButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AppsButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-messs.png"))); // NOI18N
        AppsButton1.setText("Nhắn Tin");
        AppsButton1.setFocusable(false);
        AppsButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        AppsButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        AppsButton1.setMaximumSize(new java.awt.Dimension(89, 30));
        AppsButton1.setMinimumSize(new java.awt.Dimension(89, 30));
        AppsButton1.setOpaque(true);
        AppsButton1.setPreferredSize(new java.awt.Dimension(92, 30));
        AppsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AppsButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(AppsButton1);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(700, 375));

        main_table.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        main_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên máy", "Địa chỉ IP", "Trạng thái", "Tên người dùng", "Bắt đầu lúc", "Thời gian sử dụng", "Thời gian còn lại"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        main_table.setRowHeight(31);
        main_table.setSelectionBackground(new java.awt.Color(0, 0, 204));
        main_table.setSelectionForeground(new java.awt.Color(255, 255, 255));
        main_table.getTableHeader().setResizingAllowed(false);
        main_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(main_table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabPane.addTab("Máy trạm", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đăng ký", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(362, 400));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tên đăng nhập");

        txt_username.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_username.setPreferredSize(new java.awt.Dimension(120, 20));

        txt_fullname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_fullname.setPreferredSize(new java.awt.Dimension(120, 20));

        txt_cmnd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_cmnd.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Mật khẩu");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Nhập lại mật khẩu");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Họ và Tên");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Số điện thoại");

        btRegister.setBackground(new java.awt.Color(153, 255, 204));
        btRegister.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btRegister.setText("Đăng ký");
        btRegister.setFocusable(false);
        btRegister.setHideActionText(true);
        btRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegisterActionPerformed(evt);
            }
        });

        txt_password.setPreferredSize(new java.awt.Dimension(120, 20));

        txt_retype.setPreferredSize(new java.awt.Dimension(120, 20));
        txt_retype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_retypeActionPerformed(evt);
            }
        });

        sotien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sotien.setText("Số tiền nạp lần đầu (VNĐ)");

        sotiendau.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sotiendau.setModel(new javax.swing.SpinnerNumberModel(10000L, 5000L, null, 1000L));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sotien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sotiendau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_cmnd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_retype, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(btRegister)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_retype, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cmnd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sotien, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sotiendau, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(393, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(416, 416, 416))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TabPane.addTab("Đăng ký", jPanel4);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar2.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar2.setRollover(true);
        jToolBar2.setEnabled(false);
        jToolBar2.setFocusable(false);

        naptien.setBackground(new java.awt.Color(255, 255, 204));
        naptien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        naptien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/currency_dollar_red.png"))); // NOI18N
        naptien.setText("Nạp tiền");
        naptien.setFocusable(false);
        naptien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        naptien.setInheritsPopupMenu(true);
        naptien.setMaximumSize(new java.awt.Dimension(84, 30));
        naptien.setMinimumSize(new java.awt.Dimension(84, 30));
        naptien.setName(""); // NOI18N
        naptien.setOpaque(true);
        naptien.setPreferredSize(new java.awt.Dimension(84, 30));
        naptien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        naptien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                naptienActionPerformed(evt);
            }
        });
        jToolBar2.add(naptien);

        capnhat.setBackground(new java.awt.Color(204, 255, 204));
        capnhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        capnhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/update-icon.png"))); // NOI18N
        capnhat.setText("Cập nhật ");
        capnhat.setFocusable(false);
        capnhat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        capnhat.setInheritsPopupMenu(true);
        capnhat.setMaximumSize(new java.awt.Dimension(90, 30));
        capnhat.setMinimumSize(new java.awt.Dimension(90, 30));
        capnhat.setOpaque(true);
        capnhat.setPreferredSize(new java.awt.Dimension(90, 30));
        capnhat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        capnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capnhatActionPerformed(evt);
            }
        });
        jToolBar2.add(capnhat);

        xoa1.setBackground(new java.awt.Color(204, 255, 255));
        xoa1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-unlockk.png"))); // NOI18N
        xoa1.setText("Mở khóa");
        xoa1.setFocusable(false);
        xoa1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        xoa1.setInheritsPopupMenu(true);
        xoa1.setMaximumSize(new java.awt.Dimension(94, 30));
        xoa1.setMinimumSize(new java.awt.Dimension(94, 30));
        xoa1.setOpaque(true);
        xoa1.setPreferredSize(new java.awt.Dimension(94, 30));
        xoa1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        xoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa1ActionPerformed(evt);
            }
        });
        jToolBar2.add(xoa1);

        khoa.setBackground(new java.awt.Color(255, 204, 204));
        khoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        khoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-lockk.png"))); // NOI18N
        khoa.setText("Khóa");
        khoa.setFocusable(false);
        khoa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        khoa.setInheritsPopupMenu(true);
        khoa.setMaximumSize(new java.awt.Dimension(84, 30));
        khoa.setMinimumSize(new java.awt.Dimension(84, 30));
        khoa.setOpaque(true);
        khoa.setPreferredSize(new java.awt.Dimension(84, 30));
        khoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        khoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                khoaActionPerformed(evt);
            }
        });
        jToolBar2.add(khoa);

        xoa.setBackground(new java.awt.Color(204, 204, 204));
        xoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-delete.png"))); // NOI18N
        xoa.setText("Xóa");
        xoa.setFocusable(false);
        xoa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        xoa.setInheritsPopupMenu(true);
        xoa.setMaximumSize(new java.awt.Dimension(80, 30));
        xoa.setMinimumSize(new java.awt.Dimension(80, 30));
        xoa.setOpaque(true);
        xoa.setPreferredSize(new java.awt.Dimension(80, 30));
        xoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaActionPerformed(evt);
            }
        });
        jToolBar2.add(xoa);

        xoa2.setBackground(new java.awt.Color(204, 204, 255));
        xoa2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/search-icon.png"))); // NOI18N
        xoa2.setText("Tìm kiếm");
        xoa2.setFocusable(false);
        xoa2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        xoa2.setInheritsPopupMenu(true);
        xoa2.setMaximumSize(new java.awt.Dimension(90, 30));
        xoa2.setMinimumSize(new java.awt.Dimension(90, 30));
        xoa2.setOpaque(true);
        xoa2.setPreferredSize(new java.awt.Dimension(90, 30));
        xoa2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        xoa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa2ActionPerformed(evt);
            }
        });
        jToolBar2.add(xoa2);

        xoa3.setBackground(new java.awt.Color(255, 204, 255));
        xoa3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoa3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-reneww.png"))); // NOI18N
        xoa3.setText("Làm mới");
        xoa3.setToolTipText("");
        xoa3.setFocusable(false);
        xoa3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        xoa3.setInheritsPopupMenu(true);
        xoa3.setMaximumSize(new java.awt.Dimension(90, 30));
        xoa3.setMinimumSize(new java.awt.Dimension(90, 30));
        xoa3.setOpaque(true);
        xoa3.setPreferredSize(new java.awt.Dimension(90, 30));
        xoa3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa3ActionPerformed(evt);
            }
        });
        jToolBar2.add(xoa3);

        user_table.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        user_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên đăng nhập", "Họ và tên", "Số điện thoại", "Số tiền", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        user_table.setRowHeight(31);
        jScrollPane1.setViewportView(user_table);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TabPane.addTab("Hội viên", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cài đặt", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(362, 400));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Số lượng máy");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Giá tiền hội viên (VNĐ/1h)");
        jLabel8.setToolTipText("");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Giá tiền khách hàng (VNĐ/1h)");

        btRegister1.setBackground(new java.awt.Color(153, 255, 204));
        btRegister1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btRegister1.setText("OK");
        btRegister1.setFocusable(false);
        btRegister1.setHideActionText(true);
        btRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegister1ActionPerformed(evt);
            }
        });

        giatien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        giatien.setModel(new javax.swing.SpinnerNumberModel(10000L, 1000L, null, 1000L));

        giatienuser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        giatienuser.setModel(new javax.swing.SpinnerNumberModel(12000L, 5000L, null, 1000L));

        somay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        somay.setModel(new javax.swing.SpinnerNumberModel(10, 5, null, 1));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(giatienuser, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(giatien, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(somay, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(btRegister1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(somay, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(giatien, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(giatienuser, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(btRegister1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(421, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(394, 394, 394))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        TabPane.addTab("Cài đặt", jPanel7);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 0));
        jLabel6.setText("PHÒNG MÁY TTT CYBER GAMING !!!");

        jButton1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-X.png"))); // NOI18N
        jButton1.setFocusPainted(false);
        jButton1.setMargin(new java.awt.Insets(2, 10, 3, 10));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(268, 268, 268)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TabPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TabPane, javax.swing.GroupLayout.PREFERRED_SIZE, 455, Short.MAX_VALUE))
        );

        TabPane.getAccessibleContext().setAccessibleName("Function");
        TabPane.getAccessibleContext().setAccessibleDescription("Function");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegister1ActionPerformed
        int numofpc = (int) somay.getValue();
        long cost = (long)giatien.getValue();
        long costuser = (long)giatienuser.getValue();
        new SettingDao().setting(numofpc, cost, costuser);
        JOptionPane.showMessageDialog(null, "Vui lòng khởi động lại !");
    }//GEN-LAST:event_btRegister1ActionPerformed

    private void xoa3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa3ActionPerformed
        settableuser();
    }//GEN-LAST:event_xoa3ActionPerformed

    private void xoa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa2ActionPerformed
        new FindUserGui();
    }//GEN-LAST:event_xoa2ActionPerformed

    private void xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaActionPerformed
        if(rowuser!=-1 && !status.equals("ONLINE") )
        {
            if(!status.equals("ONLINE"))
            {
                userDao.deleteuser(username);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Tài khoản đang online, vui lòng đăng xuất tài khoản trước !");
            }

        }
        else
        {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản để xóa !");
        }
        settableuser();
        rowuser=-1;
    }//GEN-LAST:event_xoaActionPerformed

    private void khoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_khoaActionPerformed
        if(rowuser!=-1)
        {
            if(status.equals("ONLINE"))
            {
                Socket socket = null;
                try
                {
                    for(int i=0; i<numpc;i++)
                    {
                        String name=(String) main_table.getValueAt(i, 3);
                        if(name!=null)
                        {
                            if(name.equals(username))
                            {
                                row=i;
                                iprow=(String) main_table.getValueAt(i, 1);
                                state = (String) main_table.getValueAt(i, 2);
                                break;
                            }
                        }
                    }

                    InetAddress ipclient = InetAddress.getByName(iprow);
                    socket = new Socket(ipclient, define.CMD_CLIENT_PORT);
                    Commands cmd = new Commands(socket);
                    boolean flag = cmd.BlockUser();
                    if(flag == true)
                    {
                        main_table.setValueAt(define.OFFLINE, row, 2);  //Trang thai OFFLINE
                        main_table.setValueAt(null, row, 3);  //Ten nguoi dung
                        main_table.setValueAt(null, row, 4);  //bắt đầu lúc

                        if(Global.threadtime[row] != null)
                        {
                            Global.threadtime[row].stopThread();
                            Global.threadtime[row] = null;
                        }
                        String timestring=(String) main_table.getValueAt(row, 6);
                        long time=changetimetolong(timestring);
                        userDao.setUserlogout(time, username);//cai lại thời gian trạng thái cho user
                        userDao.setstatususer("BLOCKED", username);// block user
                        main_table.setValueAt(null, row, 5);  //thoi gian su dung
                        main_table.setValueAt(null, row, 6);
                        
                    }
                    
                    if(Global.chatGui[row]!=null)
                    {
                        Global.chatGui[row].setVisible(false);
                        Global.chatGui[row]=null;
                    }
                    else
                    {
                        Global.chatGui[row]=null;
                    }
                    Global.username1[row]=null;
                    socket.close();
                }catch(IOException ex){
                }
            }
            if(status.equals("OFFLINE"))
            {
                userDao.setstatususer("BLOCKED", username);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Tài khoản đã bị khóa !");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản để khóa !");
        }
        settableuser();
        rowuser=-1;
    }//GEN-LAST:event_khoaActionPerformed

    private void xoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa1ActionPerformed
        if(rowuser!=-1 && status.equals("BLOCKED") )
        {
            userDao.setstatususer("OFFLINE", username);

        }
        else if(rowuser!=-1&& !status.equals("BLOCKED"))
        {
            JOptionPane.showMessageDialog(null, "Tài khoản không bị khóa !");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản để mở khóa !");
        }
        settableuser();
    }//GEN-LAST:event_xoa1ActionPerformed

    private void capnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capnhatActionPerformed
        if(rowuser!=-1 && !status.equals("ONLINE"))
        {
            new UpdateGui();

        }
        else if(status.equals("ONLINE"))
        {
            JOptionPane.showMessageDialog(null, "Tài khoản "+username+" hiện đang online !");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản để cập nhật !");
        }
        settableuser();
    }//GEN-LAST:event_capnhatActionPerformed

    private void naptienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_naptienActionPerformed
        if(rowuser!=-1)
        {
            new RechargeGui();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản để nạp tiền!");
        }
        
        rowuser=-1;
        user_table.clearSelection();
    }//GEN-LAST:event_naptienActionPerformed

    private void txt_retypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_retypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_retypeActionPerformed

    private void btRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegisterActionPerformed
        // TODO add your handling code here:
        String username = txt_username.getText();

        char[] char1 = txt_password.getPassword();
        String password = new String(char1);

        char[] char2 = txt_retype.getPassword();
        String retype = new String(char2);

        String fullname = txt_fullname.getText();
        String sodienthoai = txt_cmnd.getText();
        long sotien1 = (long)sotiendau.getValue();
        int heso=(int)(Global.cost/60);
        
        long sotien= sotien1/heso*60;  //12k/1h
        
        if(username.equals("") || password.equals("") || retype.equals("") || fullname.equals("") || sodienthoai.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Phải điền vào tất cả các ô.");
        }
        else
        {
            if(!password.equals(retype))
            {
                JOptionPane.showMessageDialog(null, "Nhập lại password không chính xác.");
            }
            else
            {
                if(userDao.checkStatuslogin(username)!=null)
                {
                    JOptionPane.showMessageDialog(null, "Username đã được sử dụng vui lòng đăng kí lại.");
                }
                else
                {
                    int n=userDao.insertuser(username, password, fullname, sodienthoai, sotien);
                    if(n>0)
                    {
                        JOptionPane.showMessageDialog(null, "Đăng ký thành công.");

                        txt_username.setText("");
                        txt_password.setText("");
                        txt_retype.setText("");
                        txt_fullname.setText("");
                        txt_cmnd.setText("");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Đăng ký không thành công.");
                    }
                }

            }
        }
    }//GEN-LAST:event_btRegisterActionPerformed

    private void AppsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AppsButtonActionPerformed
        // TODO add your handling code here:
        MouseClick();
        if(state.equals(define.ONLINE))
        {
            Socket socket = null;
            try
            {
                InetAddress ipclient = InetAddress.getByName(iprow);
                socket = new Socket(ipclient, define.CMD_CLIENT_PORT);

                Commands cmd = new Commands(socket);
                Vector<MyProcess> flag = cmd.AppsButton();

                if(flag != null)
                {
                    System.out.println("APPS cmd send client success.");

                    ProcessListGui procListGui = new ProcessListGui(ipclient, flag);
                    procListGui.setVisible(true);

                }
                else
                {
                    System.out.println("Remote cmd send client fail.");
                }

                socket.close();
            }catch(IOException ex){
            }
        }
        else
        {
            if(state.equals(define.OFFLINE))
            {
                JOptionPane.showMessageDialog(null, "Máy chưa đăng nhập.");
            }
            else
            {
                if(state.equals(define.DISCONNECT))
                {
                    JOptionPane.showMessageDialog(null, "Máy đã ngắt kết nối.");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Chọn máy tính để thực hiện chức năng này.");
                }
            }
        }
        state = "#";
        main_table.clearSelection();
    }//GEN-LAST:event_AppsButtonActionPerformed

    private void DesktopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesktopButtonActionPerformed
        // TODO add your handling code here:
        //MouseClick();

        if(state.equals(define.ONLINE)||state.equals(define.OFFLINE))
        {
            Socket socket = null;
            try
            {
                InetAddress ipclient = InetAddress.getByName(iprow);
                socket = new Socket(ipclient, define.CMD_CLIENT_PORT);

                Commands cmd = new Commands(socket);
                boolean flag = cmd.DesktopButton();

                if(flag == true)
                {
                    System.out.println("Remote cmd send client success.");
                }
                else
                {
                    System.out.println("Remote cmd send client fail.");
                }

                socket.close();
            }catch(IOException ex){
            }
        }
        else
        {
            if(state.equals(define.DISCONNECT))
            {
                JOptionPane.showMessageDialog(null, "Máy đã ngắt kết nối.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Chọn máy tính để thực hiện chức năng này.");
            }

        }
        state = "#";
        main_table.clearSelection();
    }//GEN-LAST:event_DesktopButtonActionPerformed

    private void RestartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestartButtonActionPerformed
        // TODO add your handling code here:
        //MouseClick();

        if(state.equals(define.OFFLINE)||state.equals(define.ONLINE))
        {
            Socket socket = null;
            try
            {
                InetAddress ipclient = InetAddress.getByName(iprow);
                socket = new Socket(ipclient, define.CMD_CLIENT_PORT);
                Commands cmd = new Commands(socket);
                boolean flag = cmd.RestartButton();
                if(flag == true)
                {
                    main_table.setValueAt(define.DISCONNECT, row, 2);  //Trang thai ONLINE
                    main_table.setValueAt(null, row, 3);  //Ten nguoi dung
                    main_table.setValueAt(null, row, 4);  //bắt đầu lúc
                    main_table.setValueAt(null, row, 5);  //đã dùng

                    JOptionPane.showMessageDialog(null, "Đã restart máy "+(row+1)+" !!!");
                }

                //socket.close();
            }catch(IOException ex){
            }
        }
        else // State = DISCONNECT
        {

            if(state.equals(define.DISCONNECT))
            {
                JOptionPane.showMessageDialog(null, "Máy đã ngắt kết nối.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Chọn máy tính để thực hiện chức năng này.");
            }
        }
        state = "#";
        main_table.clearSelection();
    }//GEN-LAST:event_RestartButtonActionPerformed

    private void ShutdownButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShutdownButtonActionPerformed
        // TODO add your handling code here:
        MouseClick();

        if(state.equals(define.OFFLINE)||state.equals(define.ONLINE))
        {
            Socket socket = null;
            try
            {
                InetAddress ipclient = InetAddress.getByName(iprow);
                socket = new Socket(ipclient, define.CMD_CLIENT_PORT);
                Commands cmd = new Commands(socket);
                boolean flag = cmd.ShutdownButton();
                if(flag == true)
                {
                    main_table.setValueAt(define.DISCONNECT, row, 2);  //Trang thai ONLINE
                    main_table.setValueAt(null, row, 3);  //Ten nguoi dung
                    main_table.setValueAt(null, row, 4);  //bắt đầu lúc
                    main_table.setValueAt(null, row, 5);  //đã dùng
                    main_table.setValueAt(null, row, 6);  //
                    JOptionPane.showMessageDialog(null, "Đã tắt máy "+(row+1)+" !!!");
                }

                socket.close();
            }catch(IOException ex){
            }
        }
        else // State = DISCONNECT
        {
            if(state.equals(define.DISCONNECT))
            {
                JOptionPane.showMessageDialog(null, "Máy đã ngắt kết nối.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Chọn máy tính để thực hiện chức năng này.");
            }
        }
        state = "#";
        main_table.clearSelection();
    }//GEN-LAST:event_ShutdownButtonActionPerformed

    private void LogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtonActionPerformed
        // TODO add your handling code here:
        MouseClick();

        if(state.equals(define.ONLINE))
        {
            Socket socket = null;
            try
            {
                InetAddress ipclient = InetAddress.getByName(iprow);
                socket = new Socket(ipclient, define.CMD_CLIENT_PORT);
                Commands cmd = new Commands(socket);
                boolean flag = cmd.LogoutButton();
                if(flag == true)
                {
                    main_table.setValueAt(define.OFFLINE, row, 2);  //Trang thai OFFLINE
                    main_table.setValueAt(null, row, 3);  //Ten nguoi dung
                    main_table.setValueAt(null, row, 4);  //bắt đầu lúc

                    if(Global.threadtime[row] != null)
                    {
                        Global.threadtime[row].stopThread();
                        Global.threadtime[row] = null;
                    }
                    String timestring=(String) main_table.getValueAt(row, 6);
                    long time=changetimetolong(timestring);
                    userDao.setUserlogout(time, username);//cai lại thời gian trạng thái cho user

                    main_table.setValueAt(null, row, 5);  //thoi gian su dung
                    main_table.setValueAt(null, row, 6);

                    JOptionPane.showMessageDialog(null, "Đăng xuất tài khoản thành công ở máy "+(row+1)+" !!!");
                }
                if(Global.chatGui[row]!=null)
                {
                    Global.chatGui[row].setVisible(false);
                    Global.chatGui[row]=null;
                }
                else
                {
                    Global.chatGui[row]=null;
                }
                Global.username1[row]=null;
                socket.close();
            }catch(IOException ex){
            }
        }
        else
        {
            if(state.equals(define.DISCONNECT)) //state = DISCONNECT
            {
                JOptionPane.showMessageDialog(null, "Máy đã ngắt kết nối.");
            }
            else    // State = ONLINE
            {
                if(state.equals(define.OFFLINE))
                {
                    JOptionPane.showMessageDialog(null, "Máy đã đăng xuất.");
                }
                else    // state = null
                {
                    JOptionPane.showMessageDialog(null, "Chọn máy tính để thực hiện chức năng này.");
                }
            }
        }
        state = "#";
        main_table.clearSelection();
    }//GEN-LAST:event_LogoutButtonActionPerformed

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        // TODO add your handling code here:
        MouseClick();

        if(state.equals(define.OFFLINE))
        {
            Socket socket = null;
            try
            {
                InetAddress ipclient = InetAddress.getByName(iprow);
                socket = new Socket(ipclient, define.CMD_CLIENT_PORT);
                Commands cmd = new Commands(socket);
                boolean flag = cmd.LoginButton();
                if(flag == true)
                {
                    Calendar startCal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String startTime = sdf.format(startCal.getTime());
                    start = startCal.getTimeInMillis();

                    main_table.setValueAt(define.ONLINE, row, 2);  //Trang thai ONLINE
                    main_table.setValueAt("USER", row, 3);  //Ten nguoi dung
                    main_table.setValueAt(startTime, row, 4);  //bắt đầu lúc

                    SetTime settime = new SetTime(start, row,359999,"USER");
                    settime.start();
                    Global.threadtime[row] = settime;

                    JOptionPane.showMessageDialog(null, "Đã mở máy "+(row+1)+" !!!");
                }

                socket.close();
            }catch(IOException ex){
            }
        }
        else
        {

            if(state.equals(define.DISCONNECT)) //state = DISCONNECT
            {
                JOptionPane.showMessageDialog(null, "Máy đã ngắt kết nối.");
            }
            else    // State = ONLINE
            {
                if(state.equals(define.ONLINE))
                {
                    JOptionPane.showMessageDialog(null, "Máy đã đăng Nhập.");
                }
                else    // state = null
                {
                    JOptionPane.showMessageDialog(null, "Chọn máy tính để thực hiện chức năng này.");
                }
            }
        }
        state="#";
        main_table.clearSelection();
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void MoneyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoneyButtonActionPerformed
        // TODO add your handling code here:

        MouseClick();
        if(state.equals(define.ONLINE) && username.equals("USER"))
        {

            new BillGui(row);
            

        }
        else if(state.equals(define.ONLINE) && !username.equals("User"))
        {
            JOptionPane.showMessageDialog(null, "Máy đang sử dụng tài khoản hội viên !!!");
        }
        else
        {
            if(state.equals(define.OFFLINE))
            {
                JOptionPane.showMessageDialog(null, "Máy chưa đăng nhập.");
            }
            else
            {
                if(state.equals(define.DISCONNECT))
                {
                    JOptionPane.showMessageDialog(null, "Máy đã ngắt kết nối.");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Chọn máy tính để thực hiện chức năng này.");
                }
            }
        }
        state=null;
        main_table.clearSelection();
    }//GEN-LAST:event_MoneyButtonActionPerformed

    private void AppsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AppsButton1ActionPerformed
        if(state.equals(define.ONLINE))
        {
            if(Global.chatGui[row]==null)
            {
                ChatGui chatGui = new ChatGui(iprow,username);
                Global.chatGui[row] = chatGui;
            }
            else
            {
                Global.chatGui[row].setVisible(true);
            }
            
        }
        else
        {

            if(state.equals(define.DISCONNECT)) //state = DISCONNECT
            {
                JOptionPane.showMessageDialog(null, "Máy đã ngắt kết nối.");
            }
            else    // State = ONLINE
            {
                if(state.equals(define.OFFLINE))
                {
                    JOptionPane.showMessageDialog(null, "Máy không đăng nhập.");
                }
                else    // state = null
                {
                    JOptionPane.showMessageDialog(null, "Chọn máy tính để thực hiện chức năng này.");
                }
            }
        }
        state="#";
        main_table.clearSelection();
    }//GEN-LAST:event_AppsButton1ActionPerformed

    
    private void MouseClick()
    {
        row = main_table.getSelectedRow();
        rowuser=user_table.getSelectedRow();
        if(row !=-1)
                {
                iprow = (String) main_table.getValueAt(row, 1);
                state = (String) main_table.getValueAt(row, 2);
                username=(String) main_table.getValueAt(row, 3);
                }
        if(rowuser != -1)
            {
                    username=(String) user_table.getValueAt(rowuser, 0);
                    name=(String) user_table.getValueAt(rowuser, 1);
                    phone=(String) user_table.getValueAt(rowuser, 2);
                    sotienconlai=(String) user_table.getValueAt(rowuser, 3);
                    status=(String) user_table.getValueAt(rowuser, 4);
            }
        if(state == null)
        {
            state = "#";
        }
        
    }
    
    
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         * Nimbus
//         */
//        try {
//
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                    
//                }
//            }
//            //javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(HomeGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(HomeGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(HomeGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(HomeGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new HomeGui().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AppsButton;
    private javax.swing.JButton AppsButton1;
    private javax.swing.JButton DesktopButton;
    private javax.swing.JButton LoginButton;
    private javax.swing.JButton LogoutButton;
    private javax.swing.JButton MoneyButton;
    private javax.swing.JButton RestartButton;
    private javax.swing.JButton ShutdownButton;
    private javax.swing.JTabbedPane TabPane;
    private javax.swing.JButton btRegister;
    private javax.swing.JButton btRegister1;
    private javax.swing.JButton capnhat;
    private javax.swing.JSpinner giatien;
    private javax.swing.JSpinner giatienuser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton khoa;
    public javax.swing.JTable main_table;
    private javax.swing.JButton naptien;
    private javax.swing.JSpinner somay;
    private javax.swing.JLabel sotien;
    private javax.swing.JSpinner sotiendau;
    private javax.swing.JTextField txt_cmnd;
    private javax.swing.JTextField txt_fullname;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JPasswordField txt_retype;
    private javax.swing.JTextField txt_username;
    public javax.swing.JTable user_table;
    private javax.swing.JButton xoa;
    private javax.swing.JButton xoa1;
    private javax.swing.JButton xoa2;
    private javax.swing.JButton xoa3;
    // End of variables declaration//GEN-END:variables
}
