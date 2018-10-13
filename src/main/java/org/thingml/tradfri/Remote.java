package org.thingml.tradfri;

import org.eclipse.californium.core.CoapResponse;

public class Remote {
    private int id;
    private TradfriGateway gateway;
    private CoapResponse response;

    public Remote(int id, TradfriGateway gateway, CoapResponse response) {
        this.id = id;
        this.gateway = gateway;
        this.response = response;
    }
}
