package org.thingml.tradfri;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.scandium.DTLSConnector;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.pskstore.StaticPskStore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TradfriGateway implements Runnable {
    private static Logger logger = Logger.getLogger(TradfriGateway.class.getName());

    private String gatewayIp;
    private String securityKey;
    private int polling_rate = 30000;
    private boolean running = false;

    public TradfriGateway() {
    }

    public TradfriGateway(String gatewayIp, String securityKey) {
        this.gatewayIp = gatewayIp;
        this.securityKey = securityKey;
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public int getPollingRate() {
        return polling_rate;
    }

    public void setPollingRate(int polling_rate) {
        // between 1 and 60 seconds
        if (polling_rate < 1000) polling_rate = 1000;
        else if (polling_rate > 60000) polling_rate = 60000;
        this.polling_rate = polling_rate;
    }

    public boolean isRunning() {
        return running;
    }

    public Logger getLogger() {
        return logger;
    }

    /**
     * Observer pattern for asynchronous event notification
     */
    private ArrayList<TradfriGatewayListener> listeners = new ArrayList<TradfriGatewayListener>();

    public void addTradfriGatewayListener(TradfriGatewayListener l) {
        listeners.add(l);
    }

    public void removeTradfriGatewayListener(TradfriGatewayListener l) {
        listeners.remove(l);
    }

    public void clearTradfriGatewayListener() {
        listeners.clear();
    }

    /**
     * Gateway public API
     */
    public void startTradfriGateway() {
        if (running) return;
        running = true;
        new Thread(this).start();
    }

    public void stopTradfriGateway() {
        running = false;
    }

    public void run() {
        for (TradfriGatewayListener l : listeners) l.gateway_initializing();
        Logger.getLogger(TradfriGateway.class.getName()).log(Level.INFO, "Tradfri Gateway is initalizing...");
        initCoap();
        Logger.getLogger(TradfriGateway.class.getName()).log(Level.INFO, "Discovering Devices...");
        if (discovery()) {
            Logger.getLogger(TradfriGateway.class.getName()).log(Level.INFO, "Discovered " + bulbs.size() + " Bulbs.");
            Logger.getLogger(TradfriGateway.class.getName()).log(Level.INFO, "Discovered " + remotes.size() + " Remotes.");
            for (TradfriGatewayListener l : listeners) l.gateway_started();
            try {
                while (running) {
                    Thread.sleep(getPollingRate());
                    Logger.getLogger(TradfriGateway.class.getName()).log(Level.INFO, "Polling bulbs status...");
                    for (TradfriGatewayListener l : listeners) l.polling_started();
                    long before = System.currentTimeMillis();
                    for (LightBulb b : bulbs) {
                        b.updateBulb();
                        //System.out.println(b.toString());
                    }
                    long after = System.currentTimeMillis();
                    for (TradfriGatewayListener l : listeners)
                        l.polling_completed(bulbs.size(), (int) (after - before));
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TradfriGateway.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        running = false;
        for (TradfriGatewayListener l : listeners) l.gateway_stoped();
    }

    // Collection of bulbs and remotes registered on the gateway
    List<LightBulb> bulbs = new ArrayList<>();
    List<Remote> remotes = new ArrayList<>();

    protected boolean discovery() {
        bulbs.clear();
        try {
            CoapResponse response = get(TradfriConstants.DEVICES);
            if (response == null) return false;
            JSONArray devices = new JSONArray(response.getResponseText());
            for (TradfriGatewayListener l : listeners) l.bulb_discovery_started(devices.length());
            for (int i = 0; i < devices.length(); i++) {
                response = get(TradfriConstants.DEVICES + "/" + devices.getInt(i));
                if (response != null) {
                    JSONObject json = new JSONObject(response.getResponseText());
                    if (json.has(TradfriConstants.TYPE)) {
                        int instanceId = json.getInt(TradfriConstants.INSTANCE_ID);
                        if (json.getInt(TradfriConstants.TYPE) == TradfriConstants.TYPE_BULB) {
                            LightBulb b = new LightBulb(instanceId, this, response);
                            bulbs.add(b);
                            for (TradfriGatewayListener l : listeners) l.bulb_discovered(b);
                        } else if (json.getInt(TradfriConstants.TYPE) == TradfriConstants.TYPE_REMOTE) {
                            Remote r = new Remote(instanceId, this, response);
                            remotes.add(r);
                            // listeners will be cool
                        } else {
                            // Not a bulb or a dimmer ... what is it?
                            int type = json.getInt(TradfriConstants.TYPE);
                            int dummyDebugPoint = 0;
                        }
                    }
                }

            }
            for (TradfriGatewayListener l : listeners) l.bulb_discovery_completed();
        } catch (JSONException e) {
            logger.log(Level.SEVERE, "Error parsing response from the Tradfri gateway", e);
            return false;

        }
        return true;
    }


    /**
     * COAPS helpers to GET and SET on the IKEA Tradfri gateway using Californium
     */
    private CoapEndpoint coap = null;

    protected void initCoap() {
        DtlsConnectorConfig.Builder builder = new DtlsConnectorConfig.Builder(); //new InetSocketAddress(0)
        builder.setPskStore(new StaticPskStore("", securityKey.getBytes()));
        coap = new CoapEndpoint(new DTLSConnector(builder.build()), NetworkConfig.getStandard());
    }

    protected CoapResponse get(String path) {
        Logger.getLogger(TradfriGateway.class.getName()).log(Level.INFO, "GET: " + "coaps://" + gatewayIp + "/" + path);
        CoapClient client = new CoapClient("coaps://" + gatewayIp + "/" + path);
        client.setEndpoint(coap);
        CoapResponse response = client.get(1);
        if (response == null) {
            logger.log(Level.SEVERE, "Connection to Gateway timed out, please check ip address or increase the ACK_TIMEOUT in the Californium.properties file");
        }
        return response;
    }

    protected void set(String path, String payload) {
        Logger.getLogger(TradfriGateway.class.getName()).log(Level.INFO, "SET: " + "coaps://" + gatewayIp + "/" + path + " = " + payload);
        CoapClient client = new CoapClient("coaps://" + gatewayIp + "/" + path);
        client.setEndpoint(coap);
        CoapResponse response = client.put(payload, MediaTypeRegistry.TEXT_PLAIN);
        if (response != null && response.isSuccess()) {
            //System.out.println("Yay");
        } else {
            logger.log(Level.SEVERE, "Sending payload to " + "coaps://" + gatewayIp + "/" + path + " failed!");
        }
        client.shutdown();
    }
}
