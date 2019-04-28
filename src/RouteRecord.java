public class RouteRecord {

    private String targetRouter;
    private int cost;
    private String nextStep;

    public RouteRecord(String targetRouter, int cost, String nextStep) {
        this.targetRouter = targetRouter;
        this.cost = cost;
        this.nextStep = nextStep;
    }

    public RouteRecord() {
    }

    public String getTargetRouter() {
        return targetRouter;
    }

    public int getCost() {
        return cost;
    }

    public String getNextStep() {
        return nextStep;
    }


}
