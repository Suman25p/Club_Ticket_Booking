/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.club_ticket_booking;

/**
 *
 * @author Debasmita
 */
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
public class Client {
    public static void main(String args[]) throws Exception, ClassNotFoundException
    {
        Socket socket=new Socket("localhost", 5000);
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        DataInputStream dis=new DataInputStream(socket.getInputStream());
        ObjectOutputStream oos=new ObjectOutputStream(dos);
        ObjectInputStream ois=new ObjectInputStream(dis);
        Scanner sc=new Scanner(System.in);
        System.out.println(dis.readUTF());
        System.out.print("Enter your name: ");
        String name=sc.nextLine();
        System.out.print("Enter your email: ");
        String email=sc.nextLine();
        dos.writeUTF(name);
        dos.writeUTF(email);
        System.out.println(dis.readUTF());
        System.out.println(dis.readUTF());
        List<String> eventList=(List<String>) ois.readObject();
        System.out.println(eventList.toString());
        System.out.println("Which event do you want to attend?(Give the event number)");
        String event=sc.nextLine();
        dos.writeUTF(event);
        boolean recieve=false;
        System.out.println("Do you have the ticket?(Y/N)");
        String str=sc.nextLine();
        dos.writeUTF(str);
        if(str.equals("Y"))
        {
            recieve=true;
        }
        else
        {
            recieve=false;
        }
        if (recieve==false)
        {
            System.out.println("Your ticket for the event:");
            int len=dis.readInt();
            System.out.println("Image Size: " + len/1024 + "KB");
            JFrame f = new JFrame("Recieved_Ticket.png");
            ImageIcon icon = new ImageIcon("Event_Ticket.png");
            JLabel l = new JLabel();
            l.setIcon(icon);
            f.add(l);
            f.pack();
            f.setVisible(true);
            BufferedImage save=new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = save.createGraphics();                
            f.printAll(graphics);               
            graphics.dispose(); 
            ImageIO.write(save, "png", new File("Ticket.jpg"));
            recieve=true;
        }
        else
        {
            System.out.println("Did you lose the ticket?(Y/N)");
            String lost=sc.nextLine();
            if(lost.equals("Y"))
            {
                System.out.println("Your ticket for the event:");
                JFrame f = new JFrame("Recieved_Ticket_Again.png");
                ImageIcon icon = new ImageIcon("Event_Ticket.png");
                JLabel l = new JLabel();
                l.setIcon(icon);
                f.add(l);
                f.pack();
                f.setVisible(true);
                BufferedImage save=new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = save.createGraphics();                
                f.printAll(graphics);               
                graphics.dispose(); 
                ImageIO.write(save, "png", new File("Ticket(2).jpg"));
            }
        }
    }    
}
