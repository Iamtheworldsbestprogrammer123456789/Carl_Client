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
            //Skickar
            String msg = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Skriv ett enkelt matte tal(+, -, * eller /): ");
            msg = br.readLine();
            while(true) {
                DatagramPacket data = new DatagramPacket(msg.getBytes(), 0, msg.length(), group, portnumber);
                chatMulticastSocket.send(data);
                //Tar emot datan
                byte buf[] = new byte[1024];
                DatagramPacket resultatData = new DatagramPacket(buf, buf.length);
                chatMulticastSocket.receive(resultatData);
                String resultat = new String(resultatData.getData(), 0, resultatData.getLength());
                if (resultat.equals(msg)) {
                    continue;
                }

                // Annars skriv ut resultatet
                System.out.println(resultat);
                break;
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }
}