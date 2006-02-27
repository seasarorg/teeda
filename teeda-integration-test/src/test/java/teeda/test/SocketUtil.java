package teeda.test;

import java.io.IOException;
import java.net.ServerSocket;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author manhole
 */
public class SocketUtil {

    public static int findFreePort() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(0);
            return ss.getLocalPort();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            closeServerSocketNoException(ss);
        }
    }

    static void closeServerSocketNoException(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
