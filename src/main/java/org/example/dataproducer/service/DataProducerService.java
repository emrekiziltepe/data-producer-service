package org.example.dataproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Service
@Slf4j
public class DataProducerService {

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            System.out.println("Server started on port 9090...");
            while (true) {
                var socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            while (true) {
                var timestamp = System.currentTimeMillis();
                var randomValue = (int) (Math.random() * 101);
                var hash = DigestUtils.md5DigestAsHex((timestamp + "" + randomValue).getBytes());
                var data = timestamp + "," + randomValue + "," + hash.substring(hash.length() - 2);
                System.out.println(randomValue);
                writer.println(data);
                Thread.sleep(200);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
