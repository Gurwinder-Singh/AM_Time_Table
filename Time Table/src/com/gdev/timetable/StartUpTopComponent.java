/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable;

import java.awt.Image;
import javax.swing.ImageIcon;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.libManager//StartUp//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "StartUpTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.libManager.StartUpTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_StartUpAction",
        preferredID = "StartUpTopComponent"
)
@Messages({
    "CTL_StartUpAction=StartUp",
    "CTL_StartUpTopComponent=StartUp Window",
    "HINT_StartUpTopComponent=This is a StartUp window"
})
public final class StartUpTopComponent extends TopComponent {

    public StartUpTopComponent() {
        initComponents();
        setName(Bundle.CTL_StartUpTopComponent());
        setToolTipText(Bundle.HINT_StartUpTopComponent());
      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblimage = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(lblimage, org.openide.util.NbBundle.getMessage(StartUpTopComponent.class, "StartUpTopComponent.lblimage.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblimage, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblimage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblimage;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
          ImageIcon ic = new ImageIcon(getClass().getResource("/com/libManager/startup.png"));
          
        lblimage.setIcon(new ImageIcon(ic.getImage().getScaledInstance(WindowManager.getDefault().getMainWindow().getWidth(),
                WindowManager.getDefault().getMainWindow().getHeight(), Image.SCALE_DEFAULT)));
//          lblimage.setIcon(ic);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
