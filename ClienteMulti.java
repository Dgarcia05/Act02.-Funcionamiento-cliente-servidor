import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteMulti {

    public static void main(String[] args) {
        try {
            // Nos conectamos al servidor mediante su IP y puerto
            Socket socket = new Socket("127.0.0.1", 5000);
            System.out.println("Conectado al servidor.");

            // Canal de salida: enviamos mensajes al servidor
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Canal de entrada: leemos las respuestas del servidor
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            // Scanner para leer lo que escribe el usuario por teclado
            Scanner scanner = new Scanner(System.in);

            // Bucle de conversacion: el cliente envia mensajes hasta escribir "salir"
            while (true) {
                System.out.print("Tu mensaje: ");
                String mensaje = scanner.nextLine();

                // Si el usuario escribe "salir", terminamos el bucle
                if (mensaje.equalsIgnoreCase("salir")) break;

                // Enviamos el mensaje al servidor
                salida.println(mensaje);

                // Esperamos y mostramos la respuesta del servidor
                System.out.println("Servidor: " + entrada.readLine());
            }

            // Cerramos el socket y el scanner al terminar
            socket.close();
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
