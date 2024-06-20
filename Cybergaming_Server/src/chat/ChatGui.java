
package chat;

import action.SetTime;
import command.Commands;
import java.awt.Color;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import model_structure.Global;
import model_structure.define;

public final class ChatGui extends javax.swing.JFrame {
    private Socket          socket;
    private BufferedReader  reader;
    private PrintWriter     writer;
    private String          iprow=null;
    public StyledDocument doc;
    public ChatGui(String iprow, String username) {
        this.iprow= iprow;
        initComponents();
        setLocationRelativeTo(null);
        ten.setText("CHAT - "+username);
        doc = this.chatPane.getStyledDocument();
 
        this.setVisible(true);
        
    }


    public void appendColoredText(StyledDocument doc, String text, Color color) {
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, color);

        try {
            doc.insertString(doc.getLength(), text, set);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setVisible(true);
    }
    public void appendBoldColoredText(StyledDocument doc, String text, Color color) {
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, color);
        StyleConstants.setBold(set, true);
        try {
            doc.insertString(doc.getLength(), text, set);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        ten = new javax.swing.JLabel();
        xacnhan = new javax.swing.JButton();
        mess = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatPane = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jPanel3.setForeground(new java.awt.Color(0, 255, 51));
        jPanel3.setToolTipText("");
        jPanel3.setPreferredSize(new java.awt.Dimension(370, 43));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-X.png"))); // NOI18N
        jButton1.setAlignmentX(0.5F);
        jButton1.setAlignmentY(1.0F);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton1.setMaximumSize(new java.awt.Dimension(22, 43));
        jButton1.setMinimumSize(new java.awt.Dimension(22, 43));
        jButton1.setPreferredSize(new java.awt.Dimension(36, 36));
        jButton1.setRequestFocusEnabled(false);
        jButton1.setRolloverEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ten.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        ten.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addComponent(ten, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
            .addComponent(ten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        xacnhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xacnhan.setForeground(new java.awt.Color(255, 51, 0));
        xacnhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/png/icon-sendmess.png"))); // NOI18N
        xacnhan.setHideActionText(true);
        xacnhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xacnhanActionPerformed(evt);
            }
        });

        mess.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mess.setActionCommand("<Not Set>");
        mess.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 51), 3));
        mess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messActionPerformed(evt);
            }
        });

        chatPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 3));
        chatPane.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chatPane.setFocusable(false);
        chatPane.setMargin(null);
        jScrollPane1.setViewportView(chatPane);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Nhập tin nhắn:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mess)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(xacnhan, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xacnhan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mess, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void xacnhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xacnhanActionPerformed
        
        Socket socket = null;
            try
            {
                InetAddress ipclient = InetAddress.getByName(this.iprow);
                socket = new Socket(ipclient, define.CMD_CLIENT_PORT);
                Commands cmd = new Commands(socket);
                String text=mess.getText();
                boolean flag = cmd.ChatUser(text);
                if(flag == true)
                {
                    appendBoldColoredText(doc, "Bạn: ", Color.BLACK);
                    appendColoredText(doc, text+"\n", new java.awt.Color(0, 153, 0));
                }
                socket.close();
            }catch(IOException ex){
            }
            mess.setText("");
            this.setVisible(true);
    }//GEN-LAST:event_xacnhanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void messActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane chatPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mess;
    public javax.swing.JLabel ten;
    private javax.swing.JButton xacnhan;
    // End of variables declaration//GEN-END:variables

   

}
