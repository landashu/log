package com.wt.cloud.entity;

/**
 * @author big uncle
 * @date 2020/12/22 16:12
 * @module
 **/
public class IpEntity {

    private String hostName;

    private String ipv4;

    private String ipv6;


    public String getHostName() {
        return hostName;
    }

    public IpEntity setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getIpv4() {
        return ipv4;
    }

    public IpEntity setIpv4(String ipv4) {
        this.ipv4 = ipv4;
        return this;
    }

    public String getIpv6() {
        return ipv6;
    }

    public IpEntity setIpv6(String ipv6) {
        this.ipv6 = ipv6;
        return this;
    }

}
