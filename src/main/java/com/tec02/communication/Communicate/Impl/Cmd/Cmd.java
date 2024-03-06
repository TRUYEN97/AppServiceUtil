/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tec02.communication.Communicate.Impl.Cmd;

import com.tec02.communication.Communicate.AbsCommunicate;
import com.tec02.communication.Communicate.AbsStreamReadable;
import com.tec02.communication.Communicate.IReadStream;
import com.tec02.communication.Communicate.ISender;
import com.tec02.communication.Communicate.ReadStreamOverTime;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author Administrator
 */
public class Cmd extends AbsCommunicate implements ISender, IReadStream {

    private Process process;
    private final ProcessBuilder builder;

    public Cmd() {
        this(new ReadStreamOverTime());
    }

    public Cmd(AbsStreamReadable reader) {
        if(reader == null){
            throw new NullPointerException("StreamReader == null");
        }
        this.input = reader;
        this.builder = new ProcessBuilder();
        this.builder.redirectErrorStream(true);
    }

    @Override
    public boolean insertCommand(String command, Object... params) {
        destroy();
        String newCommand = params.length == 0 ? command : String.format(command, params);
        this.builder.command("cmd.exe", "/c", newCommand);
        try {
            this.process = builder.start();
            this.input.setReader(process.getInputStream());
            this.out = new PrintStream(process.getOutputStream());
            return true;
        } catch (IOException ex) {
            showException(ex);
            return false;
        }
    }

    public int waitFor() {
        try {
            return this.process.waitFor();
        } catch (InterruptedException ex) {
            return -1;
        }
    }

    public void destroy() {
        try {
            close();
        } catch (IOException e) {
            showException(e);
        }
    }

    @Override
    protected void closeThis() throws IOException {
        if (process != null) {
            process.destroy();
        }
    }

}
