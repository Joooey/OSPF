/**
 * @author Joey
 * @date 2019/5/5
 */
public class Distance {
    private Router Router1;
    private Router Router2;
    private int distance;

    public Distance(Router router1, Router router2, int distance) {
        Router1 = router1;
        Router2 = router2;
        this.distance = distance;
    }

    public Router getRouter1() {
        return Router1;
    }

    public Router getRouter2() {
        return Router2;
    }

    public int getDistance() {
        return distance;
    }


}

