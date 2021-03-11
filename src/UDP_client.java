import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;

/*
 * ClientUDP.java
 *
 * Esempio di  applicazione client/server su trasporto UDP:
 *  - il client invia il messaggio "CIAO!"(ClientUDP.java)
 *  - il server restituisce lo stesso messaggio(ServerUDP.java)
 *
 * Indirizzo del client: IP e porta assegnati dal Sistema Operativo
 * Indirizzo del server: IP assegnato dal Sistema Operativo e porta 6000
 *
 *
 *
 */


public class UDP_client

{
    public static void main (String args[])
    {
        try
        {
            //indirizzo del mittente (IP + porta) viene assegnato implicitamente
            DatagramSocket socket = new DatagramSocket();


            // ---------------- INVIO DEL MESSAGGIO ----------------
            //impostazione delle informazioni di stato del pacchetto UDP:
            // - il messaggio UDP come array di byte
            // - la dimensione dell array
            // - indirizzo del destinatario a livello Internet [qui 127.0.0.1]
            // - la porta del destinatario

            byte[] array1 = "Ciao mondo".getBytes();
            String messaggioInviato = new String (array1);
            int dimensione1 = array1.length;
            InetAddress addr = InetAddress.getLocalHost();
            int porta = 2496;
            System.out.println("INVIO DAL CLIENT ("+ addr + " - "+ porta + ") MESSAGGIO INVIATO: " + messaggioInviato);
            DatagramPacket messaggioUDP = new DatagramPacket (array1, dimensione1, addr, porta);

            //invio del pacchetto al server UDP
            //metodo non bloccante, trasporto non affidabile, veloce ma senza certezza di consegna
            socket.send(messaggioUDP);




            // ---------------- RICEZIONE DELLA RISPOSTA ----------------
            //si crea un esemplare vuoto di DatagramPacket in cui
            //verrà inserito il messaggio UDP
            // - array di byte vuoto
            // - la dimensione dell' array
            //array e dimensione dello stesso array sono uguali al precedente pacchetto UDP inviato
            byte[]array2 = new byte[array1.length];
            int dimensione2 = array2.length;
            DatagramPacket rispostaUDP = new DatagramPacket(array2, dimensione2);

            //ricezione della risposta, bloccante con time-out di 10 secondi
            socket.setSoTimeout(10000);
            socket.receive(rispostaUDP);

            //i dati della risposta UDP sono una stinga
            byte[]temp = rispostaUDP.getData();
            String messaggioArrivato = new String (temp);
            System.out.println("RICEZIONE DAL SERVER (" + rispostaUDP.getAddress() + " - " + rispostaUDP.getPort() + " - MESSAGGIO ARRIVATO: " + messaggioArrivato);


            // chiusura socket
            socket.close();

        }catch(UnknownHostException e)
        {
            System.err.println("Host sconosciuto: " + e.getMessage());
        }
        catch(SocketException e)
        {
            System.err.println("Connessione non riuscita: " + e.getMessage());
        }
        catch(IOException e)
        {
            System.err.println("Ricezione o invio pacchetto non riuscita: " + e.getMessage());
        }
    }

}
