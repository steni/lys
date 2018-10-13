package org.thingml.tradfri;

/**
 *
 * @author franck
 */
public interface TradfriGatewayListener {
       
    void gateway_initializing();
    void bulb_discovery_started(int total_devices);
    void bulb_discovered(LightBulb b);
    void bulb_discovery_completed();
    void gateway_started();
    
    void gateway_stoped();
    
    void polling_started();
    void polling_completed(int bulb_count, int total_time);
}
