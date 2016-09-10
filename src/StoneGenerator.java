

/**
 *
 * @author Yashar Nesabian
 */
public class StoneGenerator extends Thread {

    ScreenManager sm;

    public StoneGenerator(ScreenManager sm) {
        this.sm = sm;
    }

    @Override
    public void run() {
        while (!ScreenManager.isHit || !Bird.hitByGround) {

            int randomHeight = 100 + (int) (150 * Math.random());
            Stone stone1 = new Stone(sm.fb.getWidth() + 200, 0, 50, randomHeight, sm, true, false);
            sm.addScreenObject(stone1);
            Thread t1 = new Thread(stone1);
            t1.start();

            Stone stone2 = new Stone(sm.fb.getWidth() + 200, randomHeight + 90, 50, sm.fb.getHeight() - (90 + 100 + randomHeight), sm, false, false);
            sm.addScreenObject(stone2);
            Thread t2 = new Thread(stone2);
            t2.start();
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
            }
        }
    }

}
