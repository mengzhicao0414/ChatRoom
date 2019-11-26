package ClientPart;/**
 * @author: mengzhicao
 * @date: 2019-11-23
 * @version: 1.0.0
 * @description:
 */

/**
 * @program: CS513Project
 *
 * @Description: record server information 
 *
 * @author: Mengzhi Cao
 *
 * @create: 2019-11-23
 **/
public class ServerInfo {
    private String ip;
    private int port;

    public ServerInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
