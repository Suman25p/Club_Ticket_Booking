/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.club_ticket_booking;

/**
 *
 * @author Suman
 */
import java.awt.Color;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
public class Server {
    public static void main(String args[]) throws Exception
    {
        ServerSocket server =new ServerSocket(5000);
        server.setReuseAddress(true);
        while(true)
        {
            Socket client=server.accept();
            System.out.println("New client connected!");
            ClientHandler clientSock=new ClientHandler(client);
            new Thread(clientSock).start();
        }
    }
    private static class ClientHandler implements Runnable
    {
        private final Socket clientSocket;
        public ClientHandler(Socket socket)
        {
            this.clientSocket=socket;
        }
        @Override
        public void run()
        {
            try
            {
                DataOutputStream dos=new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dis=new DataInputStream(clientSocket.getInputStream());
                ObjectOutputStream oos=new ObjectOutputStream(dos);
                ObjectInputStream ois=new ObjectInputStream(dis);
                boolean login=false;
                String name="";
                if(login==false)
                {
                    System.out.println("Waiting for the client to log in!");
                    dos.writeUTF("Enter your name and email to log in!");
                    name=dis.readUTF();
                    System.out.println("Client "+name+" "+dis.readUTF()+" has logged in");
                    dos.writeUTF("You have logged in!");
                    login=true;
                }
                if(login==true)
                {
                   dos.writeUTF("List of upcoming club events: ");
                   List<String> list=new ArrayList<>();
                   list.add("1. Anubhav Auditions 2023");
                   list.add("2. Resonance Auditions 2023");
                   list.add("3. Ghungroo Auditions 2023");
                   list.add("4. Anahita Auditions 2023");
                   list.add("5. Geeks United Coding Competition");
                   oos.writeObject(list);
                   String event=dis.readUTF();
                   System.out.println("Event chosen by the client: "+event);
                   int evt=Integer.parseInt(event);
                   String hasTicket=dis.readUTF();
                   if (hasTicket.equals("N"))
                   {
                    dos.writeUTF("Your ticket for the event:");
                    int width=300;
                    int height=300;
                    BufferedImage buffImg=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d=buffImg.createGraphics();
                    g2d.setColor(Color.white);
                    g2d.fillRect(0, 0, width, height);
                    g2d.setColor(Color.black);
                    g2d.drawString(name, 55, 125);
                    g2d.drawString(name, 55, 125);
                    switch(evt)
                    {
                       case 1: {
                           g2d.drawString("Anubhav Auditions 2023", 55, 100);
                           break;
                       }
                       case 2: {
                           g2d.drawString("Resonance Auditions 2023", 55, 100);
                           break;
                       }
                       case 3: {
                           g2d.drawString("Ghungroo Auditions 2023", 55, 100);
                           break;
                       }
                       case 4: {
                           g2d.drawString("Anahita Auditions 2023", 55, 100);
                           break;
                       }
                       case 5: {
                           g2d.drawString("Geeks United Coding Competition", 55, 100);
                           break;
                       }
                       default: {
                           dos.writeUTF("There is no event with this number!");
                       }
                    }
                    System.out.println("Ticket is generated!");
                    g2d.dispose();
                    File file=new File("Event_Ticket.png");
                    ImageIO.write(buffImg, "png", file);
                    BufferedImage img = ImageIO.read(new File("Event_Ticket.png"));
                    ImageIO.write(img, "png", clientSocket.getOutputStream());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(img, "png", baos);
                    baos.flush();
                    byte[] bytes = baos.toByteArray();
                    baos.close();
                    System.out.println("Sending ticket to client. ");
                    dos.writeInt(bytes.length);
                    //dos.write(bytes, 0, bytes.length);
                    System.out.println("Ticket sent to client. ");
                   }
                   
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
    
