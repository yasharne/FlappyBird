
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.net.URL;
import javax.swing.JApplet;


/**
 *
 * @author yashar nesabian
 */
public class Intro {
    
    private static JApplet ss;

    public static void main(String[] args) {
        
        javax.swing.JFrame window = new javax.swing.JFrame("Flappy Bird");
        Class applet2 = null;
        try {
            applet2 = Class.forName("FlappyBird");
        } catch (ClassNotFoundException ex) {
        }
        JApplet appletToLoad = null;
        try {
            appletToLoad = (JApplet) applet2.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
        }
        appletToLoad.setStub(new AppletStub() {

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public URL getDocumentBase() {
                return ss.getDocumentBase();
            }

            @Override
            public URL getCodeBase() {
                return ss.getCodeBase();
            }

            @Override
            public String getParameter(String name) {
                return ss.getParameter(name);
            }

            @Override
            public AppletContext getAppletContext() {
                return ss.getAppletContext();
            }

            @Override
            public void appletResize(int width, int height) {
            }
        });
        window.setLayout(new GridLayout(1, 0));
        window.add(appletToLoad);
        appletToLoad.init();
        appletToLoad.start();

        /*try {
        FlappyBird flappyBird = new FlappyBird();
        flappyBird.init();
        flappyBird.start();
        javax.swing.JFrame window = new javax.swing.JFrame("Flappy Bird");
        window.setContentPane(flappyBird);
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        } catch (ExceptionInInitializerError e) {
        System.out.println(e);
        }*/
    }
}
