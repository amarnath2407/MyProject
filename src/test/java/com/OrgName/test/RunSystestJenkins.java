package com.salmon.test;

import com.salmon.test.framework.helpers.UrlBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Developer on 16/09/2016.
 */
public class RunSystestJenkins {

    public static void main(String args[]){
        System.out.println("Started running scripts from RunSystestJenkins ");
        try{
            String userName = UrlBuilder.readValueFromConfig("systest.sudo.user");
            StringBuffer output = new StringBuffer();
            Runtime rt = Runtime.getRuntime();
            System.out.println("Started running scripts from runtime exec ");
            Process proc = rt.exec("ssh autotest1@dev01.dev.wcs.aws.yoox.net 'echo $PATH;export JAVA_HOME=/home/autotest1/jdk/jdk1.8.0_101;export PATH=$JAVA_HOME/bin:$PATH;export M2_HOME=/home/autotest1/maven/apache-maven-3.3.9;" +
                    " export PATH=$M2_HOME/bin:$PATH;cd RegressionTest;mvn -DtestToRun=**//**//*RunSmokeSuite.class clean test -P dev01stg;'");
            //Process proc = rt.exec("ssh autotest1@dev01.dev.wcs.aws.yoox.net 'echo deva test");
            proc.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            System.out.println("output: "+output.toString());
            int exitVal = proc.exitValue();
            System.out.println("Process exited: "+exitVal);
/*
            SSHClient sshClient = new SSHClient();
            sshClient.connectSystestWithProxy("dev01.dev.wcs.aws.yoox.net", userName,UrlBuilder.readValueFromConfig("systest.password"));
            sshClient.executeCommand("echo $PATH;export JAVA_HOME=/home/"+userName+"/jdk/jdk1.8.0_101;export PATH=$JAVA_HOME/bin:$PATH;export M2_HOME=/home/"+userName+"/maven/apache-maven-3.3.9;" +
                    " export PATH=$M2_HOME/bin:$PATH;cd RegressionTest;mvn -DtestToRun=**//**//*RunSmokeSuite.class clean test -P dev01stg;");
            sshClient.disconnect();*/

        }catch(Exception e){
            System.out.println("Error connecting to ssh client:"+e);
        }
    }

}
