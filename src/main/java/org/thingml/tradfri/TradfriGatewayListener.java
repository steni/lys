package org.thingml.tradfri;

public interface TradfriGatewayListener {
       
    default void gateway_initializing() {}
    default void bulb_discovery_started(int total_devices) {}
    default void bulb_discovered(LightBulb b) {}
    default void bulb_discovery_completed() {}
    default void gateway_started() {}
    default void gateway_stoped() {}
    default void polling_started() {}
    default void polling_completed(int bulb_count, int total_time) {}
}
