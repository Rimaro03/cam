/*


1 /127.0.0.1:1044
2 /127.0.0.1:1045
3 /127.0.0.1:1046
4 /127.0.0.1:1047
5 /127.0.0.1:1048
 */

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;


/*
 * ServerUDP.java
 * Applicazione client/server su trasporto UDP: esempio di echo
 * - il client invia un messaggio ClientUDP.java
 * - il server restituisce lo stesso messaggio ServerUDP.java
 */

public class UDP_server {
    public static void main (String args[]){


        try {

            // IP del server assegnato implicitamente
            DatagramSocket server = new DatagramSocket(2496); // e porta 2496


            // buffer vuoto di DatagramPacket
            // - un array di byte vuoto
            // - la dimensione dell array
            byte[] b1 = new byte[256];
            DatagramPacket messaggioUDP = new DatagramPacket(b1, b1.length);

            //riceve il messaggio UDP e confeziona il messaggio di risposta
            while(true)
            {

                // Ricezione messaggio ----------------------------
                server.receive(messaggioUDP);         	//bloccante

                // Invio risposta ---------------------------------
                byte[] b2 = messaggioUDP.getData();
                DatagramPacket rispostaUDP = new DatagramPacket (b2, b2.length, messaggioUDP.getAddress(), messaggioUDP.getPort());
                InetAddress client = messaggioUDP.getAddress();
                System.out.println(" " + client + ":" + messaggioUDP.getPort());
                server.send(rispostaUDP);
            }



        }catch(UnknownHostException e)
        {
            System.err.println("Host sconosciuto " + e.getMessage());
        }
        catch(SocketException e)
        {
            System.err.println("Connessione non riuscita " + e.getMessage());
        }
        catch(IOException e)
        {
            System.err.println("Ricezione o invio pacchetto non riuscita " +e.getMessage());
        }
    }

}
