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
            System.out.println("Skriv ett enkelt matte tal(+, -, * eller /): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            msg = br.readLine();
            DatagramPacket data = new DatagramPacket(msg.getBytes(), 0, msg.length(), group, portnumber);
            chatMulticastSocket.send(data);
            DatagramPacket resultData = new DatagramPacket(new byte[1024], 1024);
            chatMulticastSocket.receive(resultData);
            String resultat = new String(resultData.getData(), 0, resultData.getLength());
            System.out.println("Resultat " + resultat);
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }
}