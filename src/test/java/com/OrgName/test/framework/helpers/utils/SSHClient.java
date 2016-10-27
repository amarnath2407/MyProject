package com.salmon.test.framework.helpers.utils;


import java.io.IOException;
import java.io.InputStream;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSHClient {

    static final Logger LOG = LoggerFactory.getLogger(SSHClient.class);
    private JSch jsch = new JSch();
    private Session session;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;

    //Connect to unix machine
    public void connect(String machineNameOrIpAddress, String user, String password) throws IOException {
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session = jsch.getSession(user, machineNameOrIpAddress, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            LOG.info("Connected to Unix machine");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    //connect to systest with proxy
    public void connectSystestWithProxy(String machineNameOrIpAddress, String user, String password) throws IOException {
        try {
            LOG.info("Started connecting to Unix machine");
            String proxyCommand = "ssh -W %h:%p 52.58.246.16";
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jsch.addIdentity("~/.ssh/wsclnx-dev.pem");
            session = jsch.getSession(user, machineNameOrIpAddress, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.setConfig("GSSAPIAuthentication", "no");
            //session.setProxy(new ProxySOCKS5("52.58.246.16",22));
            // create port from 2233 on local system to port 22 on tunnelRemoteHost
            session.setPortForwardingL(2233,"52.58.246.16", 22);
            session.connect();
            LOG.info("Connected to Unix machine with Proxy");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    //Disconnect the machine
    public void disconnect() {
        channel.disconnect();
        session.disconnect();
        LOG.info("DisConnected to Unix machine");
    }

    //Copy the file into Unix machine
    public void copyFileToUnix(String srcLocation, String destLocation) throws JSchException, SftpException, IOException {
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        channelSftp.put(srcLocation, destLocation);
        channelSftp.disconnect();
    }


    //Copy file from Unix into windows machine
    public void copyFileToWindows(String srcLocation, String destLocation) throws JSchException, SftpException, IOException {
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        channelSftp.get(srcLocation, destLocation);
        channelSftp.disconnect();
    }


    //Execute the command into unix machine
    public void executeCommand(String cmd) throws JSchException, IOException {
        channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(cmd);
        ((ChannelExec) channel).setPty(true);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();
        channel.connect();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                LOG.info("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
        }
    }

}
