/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import command.Commands;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model_structure.MyProcess;
import model_structure.define;

/**
 *
 * @author Mr.Tran
 */
public class ProcessListGui extends javax.swing.JFrame {

    /**
     * Creates new form ProcessListGui
     */
    private Vector<MyProcess> listproc;
    private InetAddress ipclient;
    private int row = -1;
    private String pidnum;
    public ProcessListGui(InetAddress ip, Vector<MyProcess> lstproc) {
        initComponents();
        listproc = lstproc;
        ipclient = ip;
        setLocationRelativeTo(null);
        proctable.setShowGrid(false);
        JTableHeader tableHeader= proctable.getTableHeader();
        Font headerfont= new Font("SEGOE UI",Font.BOLD,15);
        tableHeader.setFont(headerfont);
        tableHeader.setForeground(new java.awt.Color(51, 51, 0));
        tableHeader.setBackground(new java.awt.Color(255, 204, 153));
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 32));
        Object[][] data= new Object[listproc.size()][2];
        Object[] columns = {"ProcessName", "PID"};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        proctable.setModel(model);
        
        for(int i=0; i<listproc.size(); i++)
        {
            String procname = listproc.get(i).getImagename();
            String pid = listproc.get(i).getPid();
            
            proctable.setValueAt(procname, i, 0);
            proctable.setValueAt(pid, i, 1);
        }
        proctable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(proctable, value, isSelected, hasFocus, row, column);
                    
                   
                    if (column == 0) {
                        if(isSelected)
                        {
                            component.setBackground(Color.BLACK);
                            component.setForeground(Color.WHITE);
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
                            component.setForeground(Color.WHITE);
                        }
                        else
                        {
                            component.setForeground(Color.BLUE);
                            component.setBackground(Color.WHITE);
                        }
                        
                    }
                    return component;
                }
            });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        proctable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Đóng tiến trình");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 102), 3));
        jPanel3.setForeground(new java.awt.Color(0, 102, 51));
        jPanel3.setPreferredSize(new java.awt.Dimension(329, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 51));
        jLabel2.setText("Tiến Trình");

        jButton2.setFont(new java.awt.Font("Segoe UI Black", 1, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-X.png"))); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setMargin(null);
        jButton2.setMaximumSize(new java.awt.Dimension(41, 39));
        jButton2.setMinimumSize(new java.awt.Dimension(41, 39));
        jButton2.setName(""); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(41, 39));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        proctable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        proctable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Process Name", "PID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        proctable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        proctable.setRowHeight(31);
        jScrollPane1.setViewportView(proctable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jButton1)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MouseClick();
        System.out.println(row +" "+ pidnum);
        
        if(row == -1 || pidnum == null)
        {
            JOptionPane.showMessageDialog(null, "Chọn tiến trình để thực hiện chức năng này.");
        }
        else
        {
            if(row != -1 && pidnum != null)
            {
                try{
                    
                    Socket cmdSocket = new Socket(ipclient, define.CMD_CLIENT_PORT);

                    command.Commands cmd = new Commands(cmdSocket);
                    boolean flag = cmd.KillButton(pidnum);

                    if(flag == true)
                    {
                        String procname = (String) proctable.getValueAt(row, 0);
                        
                        //(DefaultTableModel)proctable.getModel().removeTableModelListener(proctable)
                        DefaultTableModel model = (DefaultTableModel) proctable.getModel();
                        model.removeRow(row);
                        proctable.setModel(model);
                        JOptionPane.showMessageDialog(this, "Tiến trình: \""+procname+ "\" được hủy thành công.");
                        
                        proctable.clearSelection();
                        row = -1;
                        pidnum = null;
                        this.dispose();
                    }
                    else
                    {
                        String procname = (String) proctable.getValueAt(row, 0);
                        JOptionPane.showMessageDialog(null, "Tiến trình: \""+procname+ "\" hủy không thành công.");
                        proctable.clearSelection();
                        row = -1;
                        pidnum = null;
                    }
                    
                    cmdSocket.close();
                    
                }catch(IOException e){
                    
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void MouseClick()
    {
        row = proctable.getSelectedRow();
        pidnum = (String) proctable.getValueAt(row, 1);
                
        /*
        proctable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                row = proctable.getSelectedRow();
                pidnum = (String) proctable.getValueAt(row, 1);
            }
        });
        */
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable proctable;
    // End of variables declaration//GEN-END:variables
}