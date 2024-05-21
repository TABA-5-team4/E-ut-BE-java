package taba.team4.eut.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemUtils {

    private static String hostname = null;

    public static String getHostname() {
        if (hostname == null) {
            synchronized (SystemUtils.class) {
                if (hostname == null) {
                    try {
                        Process p = Runtime.getRuntime().exec("hostname");
                        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        hostname = in.readLine();
                    }
                    catch (IOException e) {
                    }
                    finally {
                        if (hostname == null) {
                            try {
                                hostname = InetAddress.getLocalHost().getHostName();
                            } catch (UnknownHostException e) {}
                        }
                    }
                }
            }
        }
        return hostname;
    }
}
