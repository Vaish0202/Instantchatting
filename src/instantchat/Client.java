//test1
package instantchat;
import static instantchat.Server.dout;
import static instantchat.Server.f;
import static instantchat.Server.formatLabel;
import static instantchat.Server.vertical;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client  implements ActionListener {

   
    JTextField text1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    Client(){
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(255, 255, 255));
        p1.setBounds(0, 0 , 350, 50);
        p1.setLayout(null);
        f.add(p1);
        
        
        //back arrow
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Warrow.png"));
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
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/profile2.jpeg"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(300, 20, 30, 30);
        p1.add(profile);
        
        
        //video icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video2.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 35, 30);
        p1.add(video);
        
        //phone icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/share.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(250, 20, 30, 30);
        p1.add(phone);
        
        //three dots 
        //phone icon
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/like.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel like = new JLabel(i15);
        like.setBounds(320, 20, 10, 25);
        p1.add(like);
        
        //username
        JLabel name = new JLabel("Sristi");
        name.setBounds(110, 15,100,18);
        name.setForeground(Color.BLACK);
        name.setFont(new Font("Roboto", Font.BOLD, 18));
        p1.add(name);
        
        //Handle name
        JLabel aname = new JLabel("this.is.sristi");
        aname.setBounds(110, 35,100,18);
        aname.setForeground(Color.BLACK);
        aname.setFont(new Font("Roboto", Font.BOLD, 14));
        p1.add(aname);
        
        
         a1 = new JPanel();
        a1.setBounds(5,75,324, 470);
//        a1.setBackground(new Color(255, 255, 255));
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
        f.setLocation(800, 100);
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
     }catch(Exception e){
         e.printStackTrace();
     }
        
        
   }
   public static JPanel formatLabel(String out){
       JPanel panel =  new JPanel();
       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       
       JLabel output = new JLabel("<html><p style=\"width: 150px; font-size: 13px\">" +out+"</p></html>");
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
        new Client();
           
        try{
             Socket s = new Socket("127.0.0.1",6001);
             DataInputStream din = new DataInputStream(s.getInputStream());
                 dout = new DataOutputStream(s.getOutputStream());
                  while(true){
                      a1.setLayout(new BorderLayout());
                      
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    
                    //calling non-static function from static function is not allowed in java is gives error so making box as static and making static object of frame
                    vertical.add(left);
                    
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                    
                    f.validate();
                    
            
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

