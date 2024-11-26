package org.example.dataproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

@Service
@Slf4j
public class DataProducerService {

    @Value("${socket.port}")
    private int port;

    private final Random random = new Random();

    public void startSocketServer() {
        new Thread(() -> {
            try (var serverSocket = new ServerSocket(port)) {
                System.out.println("Socket server started on port " + port);
                var socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                handleClient(socket);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }).start();
    }

    private void handleClient(Socket socket) {
        try (var writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
            while (!socket.isClosed()) {
                var data = generateRandomData();
                System.out.println(data);
                writer.println(data);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    String generateRandomData() {
        var timestamp = System.currentTimeMillis();
        var randomValue = random.nextInt(101);
        var hash = DigestUtils.md5DigestAsHex((timestamp + "" + randomValue).getBytes());
        return timestamp + "," + randomValue + "," + hash.substring(hash.length() - 2);
    }
}
