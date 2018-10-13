package org.thingml.tradfri;

public class Main {
    public static void main(String[] args) {
        GatewayConfiguration configuration = new GatewayConfiguration();

        TradfriGateway gw = new TradfriGateway(configuration.gatewayIp, configuration.securityKey);
        gw.initCoap();
        gw.discovery();
        for (LightBulb bulb : gw.bulbs) {
            bulb.updateBulb();
            System.out.println(bulb.toString());
        }

        System.exit(0);
    }

}
