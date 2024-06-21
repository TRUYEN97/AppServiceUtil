/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qt.communication.Communicate.Impl.Telnet;

import com.qt.communication.Communicate.IConnect;
import com.qt.communication.Communicate.AbsCommunicate;
import com.qt.communication.Communicate.AbsStreamReadable;
import com.qt.communication.Communicate.IReadStream;
import com.qt.communication.Communicate.ISender;
import com.qt.communication.Communicate.ReadStream;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.net.telnet.TelnetClient;

/**
 *
 * @author Administrator
 */
public class Telnet extends AbsCommunicate implements ISender, IReadStream, IConnect {

    private final TelnetClient telnet;
    private String host;

    public Telnet() {
        this(new ReadStream());
    }

    public Telnet(AbsStreamReadable readable) {
        this.telnet = new TelnetClient();
        this.input = readable;
    }

    @Override
    public boolean connect(String host, int port) {
        for (int i = 0; i < 10; i++) {
            try {
                telnet.connect(host, port);
                if (telnet.isConnected()) {
                    input.setReader(telnet.getInputStream());
                    out = new PrintStream(telnet.getOutputStream());
                    this.host = host;
                    return true;
                }
            } catch (Exception ex) {
                showException(ex);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
        return false;

    }

    public int getPort() {
        return telnet.getLocalPort();
    }

    public String getHost() {
        return this.host;
    }

    @Override
    public boolean isConnect() {
        return telnet.isConnected();
    }

    @Override
    protected void closeThis() throws IOException {
        if (telnet != null) {
            telnet.disconnect();
        }
    }
}
