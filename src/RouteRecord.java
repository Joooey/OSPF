public class RouteRecord {

    private String routerid;
    private int cost;
    private String nextStep;

    public RouteRecord(String routerid, int cost, String nextStep) {
        this.routerid = routerid;
        this.cost = cost;
        this.nextStep = nextStep;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public void setRouterid(String routerid) {
        this.routerid = routerid;
    }

    public String getNextStep() {
        return nextStep;
    }

    public String getRouterid() {
        return routerid;
    }

    public int getCost() {
        return cost;
    }
}
