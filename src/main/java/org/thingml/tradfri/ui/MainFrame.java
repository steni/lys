package org.thingml.tradfri.ui;

import org.thingml.tradfri.GatewayConfiguration;
import org.thingml.tradfri.LightBulb;
import org.thingml.tradfri.TradfriGateway;
import org.thingml.tradfri.TradfriGatewayListener;

import java.util.prefs.Preferences;

/**
 * @author franck
 */
public class MainFrame extends javax.swing.JFrame implements TradfriGatewayListener {
    private Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    private TradfriGateway gateway = new TradfriGateway();

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        GatewayConfiguration configuration = new GatewayConfiguration();
        jTextFieldIP.setText(prefs.get("TradfriGatewayIP", configuration.gatewayIp));
        jTextFieldKey.setText(prefs.get("TradfriGatewayKey", configuration.securityKey));
        gateway.addTradfriGatewayListener(this);
        jButtonStop.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldIP = new javax.swing.JTextField();
        jButtonConnect = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldKey = new javax.swing.JTextField();
        jProgressBarDiscover = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCheckBoxShowOnlyOnline = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldPRate = new javax.swing.JTextField();
        jButtonStop = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPaneBulbs = new javax.swing.JScrollPane();
        jPanelBulbs = new javax.swing.JPanel();
        loggingPanel1 = new org.thingml.tradfri.ui.LoggingPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IKEA Tradfri Test Application - Tellu IoT");

        jLabel1.setText("Gateway IP:");

        jTextFieldIP.setText("10.3.1.85");

        jButtonConnect.setText("Start");
        jButtonConnect.addActionListener(this::jButtonConnectActionPerformed);

        jLabel2.setText("Security Key:");

        jTextFieldKey.setText("kQxkI7S6Ao4rgwYC");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.png"))); // NOI18N

        jLabel4.setText("Discovery Progress:");

        jCheckBoxShowOnlyOnline.setText("Show Only Online Bulbs");

        jLabel5.setText("Polling Rate:");

        jTextFieldPRate.setText("5000");

        jButtonStop.setText("Stop");
        jButtonStop.addActionListener(this::jButtonStopActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jProgressBarDiscover, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonConnect)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonStop))
                                        .addComponent(jTextFieldIP)
                                        .addComponent(jTextFieldKey)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jTextFieldPRate)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBoxShowOnlyOnline)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jTextFieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jTextFieldKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jCheckBoxShowOnlyOnline)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jTextFieldPRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jProgressBarDiscover, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jButtonConnect)
                                                                .addComponent(jButtonStop))
                                                        .addComponent(jLabel4))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanelBulbs.setLayout(new javax.swing.BoxLayout(jPanelBulbs, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPaneBulbs.setViewportView(jPanelBulbs);

        jSplitPane1.setLeftComponent(jScrollPaneBulbs);
        jSplitPane1.setRightComponent(loggingPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
        gateway.setGatewayIp(jTextFieldIP.getText());
        gateway.setSecurityKey(jTextFieldKey.getText());
        prefs.put("TradfriGatewayIP", jTextFieldIP.getText().trim());
        prefs.put("TradfriGatewayKey", jTextFieldKey.getText().trim());
        gateway.startTradfriGateway();
        jButtonConnect.setEnabled(false);
    }//GEN-LAST:event_jButtonConnectActionPerformed

    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
        gateway.stopTradfriGateway();
        jButtonStop.setEnabled(false);
    }//GEN-LAST:event_jButtonStopActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JCheckBox jCheckBoxShowOnlyOnline;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBulbs;
    private javax.swing.JProgressBar jProgressBarDiscover;
    private javax.swing.JScrollPane jScrollPaneBulbs;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTextFieldIP;
    private javax.swing.JTextField jTextFieldKey;
    private javax.swing.JTextField jTextFieldPRate;
    private org.thingml.tradfri.ui.LoggingPanel loggingPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void gateway_initializing() {
        jTextFieldIP.setEditable(false);
        jTextFieldKey.setEditable(false);
        jCheckBoxShowOnlyOnline.setEnabled(false);
    }

    @Override
    public void bulb_discovery_started(int total_devices) {
        jProgressBarDiscover.setMaximum(total_devices);
        jProgressBarDiscover.setValue(1);
    }

    @Override
    public void bulb_discovered(LightBulb b) {
        jProgressBarDiscover.setValue(jProgressBarDiscover.getValue() + 1);
        if (b.isOnline() || !jCheckBoxShowOnlyOnline.isSelected()) {
            BulbPanel p = new BulbPanel(b);
            jPanelBulbs.add(p);
            jPanelBulbs.revalidate();
            jPanelBulbs.repaint();
        }
    }

    @Override
    public void bulb_discovery_completed() {

    }

    @Override
    public void gateway_started() {
        jButtonStop.setEnabled(true);
    }

    @Override
    public void gateway_stoped() {
        jButtonStop.setEnabled(false);
        jButtonConnect.setEnabled(true);
        jTextFieldIP.setEditable(true);
        jTextFieldKey.setEditable(true);
        jCheckBoxShowOnlyOnline.setEnabled(true);
        jPanelBulbs.removeAll();
        jPanelBulbs.revalidate();
        jPanelBulbs.repaint();
    }

    @Override
    public void polling_started() {

    }

    @Override
    public void polling_completed(int bulb_count, int total_time) {

    }
}
