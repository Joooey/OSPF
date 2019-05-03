import com.google.gson.Gson;

import java.util.*;

public class RouteTable {
    private String selfname;
    private Vector<RouteRecord> routetable;

    public RouteTable(String selfname) {
        this.routetable = new Vector<>();
        this.selfname = selfname;
    }

    public Vector<RouteRecord> getRoutetable() {
        return routetable;
    }


    public void init(String name) {
        RouteRecord routeRecord = new RouteRecord(name, 0, "-");
        routetable.add(routeRecord);
    }

    /**
     * @param name          接收到name的路由表
     * @param newrouteTable
     */
    public boolean updateRouteTable(String name, RouteTable newrouteTable) {

        Vector<RouteRecord> newrouteTablelist = newrouteTable.getRoutetable();
        RouteRecord newRouteTableRecord;
//        showRouteTable(selfname);

        boolean existed;
        for (int i = 0; i < newrouteTablelist.size(); i++) {
            newRouteTableRecord = newrouteTablelist.get(i);

            //如果是邻接节点
            if (newRouteTableRecord.getNextStep().equals("-")) {
                routetable.add(new RouteRecord(newRouteTableRecord.getTargetRouter(), newRouteTableRecord.getCost() + 1, newRouteTableRecord.getTargetRouter()));
            } else {
                existed = false;
                int size = routetable.size();
                for (int j = 0; j < size; j++) {
                    if (newRouteTableRecord.equals(routetable.get(j)) || newRouteTableRecord.getTargetRouter().equals(selfname)) {
                        existed = true;
                        break;
                    } else if (routetable.get(j).getTargetRouter().equals(newRouteTableRecord.getTargetRouter())) {
                        existed = true;
                        if (routetable.get(j).getCost() > (newRouteTableRecord.getCost() + 1)) {
                            routetable.get(j).setCost(newRouteTableRecord.getCost() + 1);
                            routetable.get(j).setNextStep(name);
                        }
                        break;
                    }
                }
                if (!existed) {
                    routetable.add(new RouteRecord(newRouteTableRecord.getTargetRouter(), newRouteTableRecord.getCost() + 1, name));
                }

            }
        }
//        //创建一个集合
//        Vector list = new Vector();
//        //遍历数组往集合里存元素
//        for (RouteRecord aRoutetable : routetable) {
//            //如果集合里面没有相同的元素才往里存
//            if (!list.contains(aRoutetable)) {
//                list.add(aRoutetable);
//            }
//        }
//        routetable = list;
//        Gson gson=new Gson();
//        System.out.println(selfname+"@@@"+gson.toJson(routetable));

        return true;

    }


    public void showRouteTable(String name) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(routetable);
        System.out.println(name + "路由表为:" + jsonString);
    }

}
