import java.util.Random;

public class Visitor implements Runnable{
    private BusinessCenter place;
    static private int totalCount = 0;
    private int num;
    private int floor;

    Visitor(BusinessCenter p){
        this.place = p;
        totalCount++;
        num = totalCount;
        floor = new Random().nextInt(100)%19 + 1;
        float pp = Float.valueOf("98")/0;
    }

    public void run(){
        try{
        enterBuilding();
        goUp();
        doSomeWork();
        goDown();
        }catch (InterruptedException e){
            System.out.println("Interrupted exception");
        }
    }
    private void enterBuilding(){
        try {
            if (place.enterControl(this))
                place.passControl(this);
        }catch (InterruptedException e){

        }
    }
    private void goUp()throws InterruptedException{
        if (place.callLiftAndWait(this)){
            place.moveLift(this,0);
            place.enterLift(this);
            place.moveLift(this, this.floor);
            place.exitLift(this);
        }

    }
    private void doSomeWork()throws InterruptedException{
        Thread.sleep(6000);
    }
    private void goDown()throws InterruptedException{
        place.callLiftAndWait(this);
        place.moveLift(this,this.floor);
        place.enterLift(this);
        place.moveLift(this, 0);
        place.exitLift(this);
    }

    @Override
    public String toString() {
        return String.valueOf(this.num);
    }
}
