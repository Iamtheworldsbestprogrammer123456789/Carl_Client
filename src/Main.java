import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Main {
    public static void main(String[] args) {
        int portnumber = 8880;
        if (args.length >= 1){
            portnumber = Integer.parseInt(args[0]);
        }
        try{
            MulticastSocket chatMulticastSocket = new MulticastSocket(portnumber);

            InetAddress group = InetAddress.getByName("224.4.5.6");

            chatMulticastSocket.joinGroup(group);

            String msg = "";
            System.out.println("Type a message for the server: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            msg = br.readLine();
            DatagramPacket data = new DatagramPacket(msg.getBytes(), 0, msg.length(), group, portnumber);
            chatMulticastSocket.send(data);

            chatMulticastSocket.close();
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }
}