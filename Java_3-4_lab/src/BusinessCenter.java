

public class BusinessCenter {
    private int liftFloor;
    private boolean liftFree;
    private Visitor visitorAtControl;
    private Visitor visitorInLift;

    BusinessCenter(){
        visitorAtControl = null;
        visitorInLift = null;
        liftFree = true;
        liftFloor = 0;
    }

    public boolean enterControl(Visitor v) throws InterruptedException {
        synchronized (this) {
            while (visitorAtControl != null) {
                System.out.println("Visitor " + v.toString() + " trying to enter");
                this.wait();
            }
            System.out.println("Visitor " + v.toString() + " is allowed to enter");
            visitorAtControl = v;
            return true;
        }
    }

    public void passControl(Visitor v) throws InterruptedException {
        Thread.sleep(50);
        synchronized (this) {
            System.out.println("Visitor " + v.toString() + " is passed control");
            visitorAtControl = null;
            this.notify();
        }
    }

    public boolean callLiftAndWait(Visitor v) throws InterruptedException{
        synchronized (this) {
            while (!liftFree){
                System.out.println("Visitor " + v.toString() + " waiting for the lift to be free, lift occupied");
                this.wait();
            }
            System.out.println("Visitor " + v.toString() + " called lift and waiting until lift come");
            liftFree = false;
            return true;
        }
    }

    public void moveLift(Visitor v, int targetFloor) throws InterruptedException{
        Thread.sleep(200 * Math.abs(liftFloor - targetFloor));
        if(targetFloor != 0) {
            System.out.println("Lift is coming from " + 0 + " to " + targetFloor);
            liftFloor = targetFloor;
        }else{
            System.out.println("Lift is coming from " + liftFloor + " to " + targetFloor + " for Visitor " + v.toString());
        }
    }

    public void enterLift(Visitor v) {
        visitorInLift = v;
        System.out.println("Visitor " + v + " entering in lift");
    }
    public void exitLift(Visitor v) {
        synchronized (this) {
            System.out.println("Visitor " + v + " leaving the lift");
            liftFree = true;
            visitorInLift = null;
            this.notify();
        }
    }
}
