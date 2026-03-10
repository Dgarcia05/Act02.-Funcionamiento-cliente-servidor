import java.io.*;
import java.net.*;

public class ServidorMulticliente {

    public static void main(String[] args) {
        try {
            // Creamos el servidor escuchando en el puerto 5000
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Servidor multicliente iniciado en puerto 5000...");

            // Bucle infinito para aceptar clientes de forma continua
            while (true) {
                // Esperamos a que un cliente se conecte (bloqueante)
                Socket socket = servidor.accept();
                System.out.println("Nuevo cliente conectado: " + socket.getInetAddress());

                // Creamos un hilo nuevo para atender a este cliente de forma independiente
                Thread hilo = new Thread(new ManejadorCliente(socket));
                hilo.start();
                // El hilo principal vuelve al inicio del bucle y queda listo para otro cliente
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Clase que gestiona la comunicacion con un cliente en un hilo independiente
class ManejadorCliente implements Runnable {

    private Socket socket; // Socket del cliente asignado a este hilo

    // Constructor: recibe el socket del cliente
    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Canal de entrada: leemos mensajes enviados por el cliente
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            // Canal de salida: enviamos respuestas al cliente
            PrintWriter salida = new PrintWriter(
                socket.getOutputStream(), true);

            String mensaje;
            // Leemos mensajes en bucle hasta que el cliente cierre la conexion
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("[" + socket.getInetAddress() + "] dice: " + mensaje);
                // Respondemos al cliente con el mensaje recibido
                salida.println("Servidor recibio: " + mensaje);
            }

        } catch (IOException e) {
            System.out.println("Cliente desconectado: " + socket.getInetAddress());
        } finally {
            // Cerramos el socket pase lo que pase (con o sin error)
            try { socket.close(); } catch (IOException e) {}
        }
    }
}
