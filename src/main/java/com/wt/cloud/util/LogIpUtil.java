package com.wt.cloud.util;

import com.giant.cloud.entity.IpEntity;

import java.net.*;
import java.util.Enumeration;

/**
 * @author big uncle
 * @date 2020/12/22 15:54
 * @module
 **/
public class LogIpUtil {

    private static final String LOCALHOST = "127.0.0.1";
    private static final String DEFAULT = "default";

    public static IpEntity getIpAndHostName(){
        Enumeration<NetworkInterface> netInterfaces = null;
        IpEntity ipEntity = new IpEntity();
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface nif = netInterfaces.nextElement();
                Enumeration<InetAddress> InetAddress = nif.getInetAddresses();
                while (InetAddress.hasMoreElements()) {
                    InetAddress inetAddress = InetAddress.nextElement();
                    String ip = inetAddress.getHostAddress();
                    String hostName = inetAddress.getHostName();
                    if(ip.equals(hostName)){
                        continue;
                    }
                    if (inetAddress instanceof Inet4Address) {
                        if(LOCALHOST.equals(ip)){
                            continue;
                        }
                        ipEntity.setIpv4(ip);
                        ipEntity.setHostName(hostName);
                    }
                    else if (inetAddress instanceof Inet6Address) {
                        ipEntity.setIpv6(ip);
                    }
                }
            }
        } catch (SocketException e) {
        }
        if(null == ipEntity.getIpv4()|| "".equals(ipEntity.getIpv4())){
            ipEntity.setIpv4(DEFAULT);
            ipEntity.setHostName(DEFAULT);
        }
        return ipEntity;
    }


}
