public class RouteRecord {

    private String targetRouter;
    private int cost;
    private String nextStep;

    public RouteRecord(String targetRouter, int cost, String nextStep) {
        this.targetRouter = targetRouter;
        this.cost = cost;
        this.nextStep = nextStep;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public void setTargetRouter(String targetRouter) {
        this.targetRouter = targetRouter;
    }

    public String getNextStep() {
        return nextStep;
    }

    public String getTargetRouter() {
        return targetRouter;
    }

    public int getCost() {
        return cost;
    }
}
