/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.communication.socket.Unicast.Client;

import com.tec02.communication.socket.Unicast.commons.ClientLogger;
import com.tec02.communication.socket.Unicast.commons.Interface.IIsConnect;
import java.net.Socket;
import com.tec02.communication.socket.Unicast.commons.Interface.Idisconnect;
import com.tec02.communication.socket.Unicast.commons.Keywords;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import com.tec02.communication.socket.Unicast.commons.Interface.IObjectClientReceiver;

/**
 *
 * @author Administrator
 */
public class SocketClient implements Runnable, Idisconnect, IIsConnect {

    private final String host;
    private final String hostName;
    private final int port;
    private final IObjectClientReceiver clientReceiver;
    private final ClientLogger logger;
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private boolean connect;
    private boolean debug;

    public SocketClient(String host, int port, IObjectClientReceiver objectAnalysis) {
        this(host, Keywords.SERVER, port, objectAnalysis);
    }

    public SocketClient(String hostName, String host, int port, IObjectClientReceiver objectAnalysis) {
        this.host = host;
        this.hostName = hostName;
        this.port = port;
        this.logger = new ClientLogger("log/socket/client", host, hostName, port);
        this.clientReceiver = objectAnalysis;
        this.debug = false;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean connect() {
        try {
            this.socket = new Socket(host, port);
            this.outputStream = new PrintWriter(socket.getOutputStream(), true);
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.logger.logConnected();
            this.connect = true;
            return true;
        } catch (Exception ex) {
            if (debug) {
                ex.printStackTrace();
                this.logger.addlog(Keywords.ERROR, ex.getLocalizedMessage());
            }
            return false;
        }
    }

    public boolean send(String data) {
        try {
            if (!isConnect()) {
                return false;
            }
            this.outputStream.println(data);
            this.logger.logSend(data);
            return true;
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
                this.logger.addlog(Keywords.ERROR, e.getLocalizedMessage());
            }
            return false;
        }
    }

    @Override
    public void run() {
        try {
            String data;
            while (isConnect() && (data = this.inputStream.readLine()) != null) {
                if (data.trim().isBlank()) {
                    continue;
                }
                this.logger.logReceived(data);
                this.clientReceiver.receiver(this, data);
            }
        } catch (Exception ex) {
            if (debug) {
                ex.printStackTrace();
                this.logger.addlog("ERROR", ex.getLocalizedMessage());
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean isConnect() {
        return this.socket != null && this.socket.isConnected() && connect;
    }

    @Override
    public boolean disconnect() {
        if (!isConnect()) {
            return true;
        }
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            connect = false;
            this.logger.logDisconnect();
            return true;
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
                this.logger.addlog("ERROR", e.getLocalizedMessage());
            }
            return false;
        }
    }

}
