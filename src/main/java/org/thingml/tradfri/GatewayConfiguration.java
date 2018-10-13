package org.thingml.tradfri;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class GatewayConfiguration {
    public String gatewayIp;
    public String securityKey;

    public GatewayConfiguration() {
        Properties defaultProps = new Properties();
        FileInputStream in;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            final URL resource = classLoader.getResource("default.properties");
            if ( resource == null ) {
                throw new IllegalStateException("Configure IKEA Gateway IP and security key in default.properties file");
            }
            File file = new File(resource.getFile());
            in = new FileInputStream(file);
            defaultProps.load(in);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties applicationProps = new Properties(defaultProps);
        gatewayIp = applicationProps.getProperty("gatewayIp");
        securityKey = applicationProps.getProperty("securityKey");
    }
}
