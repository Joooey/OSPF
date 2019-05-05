import com.google.gson.Gson;
import sun.applet.Main;

import java.util.*;

public class RouteTable {
    private String selfname;
    private Set<RouteRecord> routetable;



    public String getSelfname() {
        return selfname;
    }

    public RouteTable(String selfname) {
        this.routetable = new LinkedHashSet<>();
        this.selfname = selfname;
    }

    public Set<RouteRecord> getRoutetable() {
        return routetable;
    }


    public void init(String name) {
        RouteRecord routeRecord = new RouteRecord(name, 0, "-");
        routetable.add(routeRecord);
    }


    private void Print(Set<RouteRecord> routeRecords) {
        for (RouteRecord routeRecord : routeRecords) {
            System.out.println(routeRecord.getTargetRouter());
        }
        System.out.println("----------");
    }


    public int getCost(String sourcename,String destname,Distance[] distance) {
        for (int i = 0; i < distance.length; i++) {
            Distance d = distance[i];
            if (sourcename.equals(d.getRouter1().getName()) && destname.equals(d.getRouter2().getName())) {
                return d.getDistance();

            } else if (sourcename.equals(d.getRouter2().getName()) && destname.equals(d.getRouter1().getName())) {
                return d.getDistance();
            }
        }
        return -1;
    }

    /**
     * @param name          接收到name的路由表
     * @param newrouteTable
     */
    public synchronized boolean updateRouteTable(String name, RouteTable newrouteTable,Distance[] distances) {

        //新表
        Set<RouteRecord> newrouteTablelist = newrouteTable.getRoutetable();


        System.out.println("before:----");
        Print(routetable);
        boolean existed;
        int cost=getCost(selfname,name,distances);


        for (RouteRecord newRouteTableRecord : newrouteTablelist) {
            //如果是邻接节点

            if (newRouteTableRecord.getNextStep().equals("-")) {
                routetable.add(new RouteRecord(newRouteTableRecord.getTargetRouter(), cost, newRouteTableRecord.getTargetRouter()));
            } else {

                existed = false;

                for (RouteRecord routeRecord : routetable) {
                    if (routeRecord.equals(newRouteTableRecord) || newRouteTableRecord.getTargetRouter().equals(selfname)) {
                        existed = true;
                        break;
                    } else {
                        if (newRouteTableRecord.getTargetRouter().equals(routeRecord.getTargetRouter())) {
                            if (newRouteTableRecord.getCost() + cost < routeRecord.getCost()) {
                                routeRecord.setCost(newRouteTableRecord.getCost() + cost);
                                routeRecord.setNextStep(newrouteTable.selfname);
                            }
                            existed = true;
                            break;
                        }
                    }
                }
                if (!existed) {
                    routetable.add(new RouteRecord(newRouteTableRecord.getTargetRouter(), newRouteTableRecord.getCost() + cost, name));
                }

            }


            System.out.println("after:----");
            Print(routetable);

        }

        return true;

    }


    public void showRouteTable(String name) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(routetable);
        System.out.println(name + "路由表为:" + jsonString);
    }

}
