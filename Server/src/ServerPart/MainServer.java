package ServerPart;/**
 * @author: mengzhicao
 * @date: 2019-11-21
 * @version: 1.0.0
 * @description:
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: CS513Project
 *
 * @Description: Serever main function, it will start a serversocket as server
 *
 * @author: Mengzhi Cao
 *
 * @create: 2019-11-21
 **/


public class MainServer {
    public static void main(String[] args) {
        int port = 8000;
        //start a new server listener to keep listening the request from the clients
        ServerListener server = new ServerListener(port);
        server.start();

    }
}
