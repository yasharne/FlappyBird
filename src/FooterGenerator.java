
/**
 *
 * @author yashar nesabian
 */
public class FooterGenerator {

    ScreenManager sm;

    public FooterGenerator(ScreenManager sm) {
        this.sm = sm;
    }

    public void firstTimeGenerate() {
        Footer firstFooter = new Footer(0, sm.fb.getHeight() - 100, sm.fb.getWidth() , 100, sm, this);
        sm.addScreenObject(firstFooter);
        Thread firstThread = new Thread(firstFooter);
        firstThread.start();
        generate();
    }

    public void generate() {
        Footer footer = new Footer(sm.fb.getWidth() - 2, sm.fb.getHeight() - 100, sm.fb.getWidth() + 2, 100, sm, this);
        sm.addScreenObject(footer);
        Thread t = new Thread(footer);
        t.start();
        
    }

}
