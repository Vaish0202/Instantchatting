/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instantchat;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
public class Server  implements ActionListener {

   //testing
    JTextField text1;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Server(){
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(0, 0, 0));
        p1.setBounds(0, 0 , 350, 60);
        p1.setLayout(null);
        f.add(p1);
        
        
        //back arrow
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/upArrow.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20, 25, 25);
        p1.add(back);
        
        //action on backbutton
        back.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ae){
                f.setVisible(false);
                //system.exit(0);
            }
        });
        
        //profile icon
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/nikita.jpg"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(45, 20, 30, 30);
        p1.add(profile);
        
        
        //video icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/up-video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(265, 20, 35, 30);
        p1.add(video);
        
        //phone icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/upPhone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(220, 20, 30, 30);
        p1.add(phone);
        
        //three dots 

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3iconUp.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel like = new JLabel(i15);
        like.setBounds(310, 20, 10, 25);
        p1.add(like);
        
        //username
        JLabel name = new JLabel("Vaish");
        name.setBounds(110, 15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Roboto", Font.BOLD, 16));
        p1.add(name);
        
        //Handle name
        JLabel aname = new JLabel("V_Vaish__Vi");
        aname.setBounds(110, 35,100,18);
        aname.setForeground(Color.WHITE);
        aname.setFont(new Font("Roboto", Font.ITALIC, 14));
        p1.add(aname);
        
        
         a1 = new JPanel();
        a1.setBounds(1,61,350, 470);
        a1.setBackground(new Color(12, 9, 10));
        f.add(a1);
        
         text1 = new JTextField();
        text1.setBounds(5, 550, 250,40);
        text1.setFont(new Font("roboto", Font.PLAIN, 16));
        f.add(text1);
        
        JButton send = new JButton("Send");
        send.setBounds(260, 550,70,40);
        send.setBackground(new Color(0,0,0));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        f.add(send);          
   
        f.setSize(350, 600); 
        f.setLocation(400, 100);
        f.getContentPane().setBackground(Color.WHITE);
        send.setFont(new Font("roboto", Font.PLAIN, 14));

        f.setUndecorated(true);
        f.setVisible(true); //default visibility is hidden

}
   public void actionPerformed(ActionEvent ae){
       try{
       String out = text1.getText();
//       System.out.println(out);
        a1.setLayout(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        JLabel output = new JLabel(out);
        JPanel p2 = formatLabel(out);
        p2.setBackground(new Color(58,59,60));
//        p2.add(output);
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        a1.add(vertical, BorderLayout.PAGE_START);
        dout.writeUTF(out);
        text1.setText("");
        
        f.repaint();
        f.invalidate();
        f.validate();
        
       }
       catch(Exception e){
           e.printStackTrace();
       }
   }
   public static JPanel formatLabel(String out){
       JPanel panel =  new JPanel();
       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       
       JLabel output = new JLabel("<html><p style=\"width: 110px; font-size: 13px\">" +out+"</p></html>");
//       output.setBackground(new Color(37,211,0));
        output.setForeground(Color.WHITE);  // Set font color to white
       output.setBackground(Color.BLACK);
       output.setOpaque(true);
       output.setBorder(new EmptyBorder(15, 15 ,15,50));
       panel.add(output);
//       getContentPane().setBackground(Color.WHITE);
       
       
       Calendar cal = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
       JLabel time = new JLabel();
       time.setText(sdf.format(cal.getTime()));
       panel.add(time);
       
    
       
       return panel;
   }
    public static void main(String[] args){
        new Server();
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                 dout = new DataOutputStream(s.getOutputStream());
                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    
                    //calling non-static function from static function is not allowed in java is gives error so making box as static and making static object of frame
                    vertical.add(left);
                    f.validate();
                    
                    

                    
                }
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
