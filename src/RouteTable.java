import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RouteTable {
    private String selfname;
    private List<RouteRecord> routetable;

    public RouteTable(String selfname) {
        this.routetable = new ArrayList<>();
        this.selfname = selfname;
    }

    public List<RouteRecord> getRoutetable() {
        return routetable;
    }


    public void init(String name) {
        RouteRecord routeRecord = new RouteRecord(name, 0, "-");
        routetable.add(routeRecord);
    }

    /**
     * 更新路由表
     *
     * @param newrouteTable
     */
    public void updateRouteTable(String name, RouteTable newrouteTable) {

        List<RouteRecord> newrouteTablelist = newrouteTable.getRoutetable();
        RouteRecord newRouteTableRecord;
        boolean existed;
        for (int i = 0; i < newrouteTablelist.size(); i++) {
            newRouteTableRecord = newrouteTablelist.get(i);

            //如果是邻接节点
            if (newRouteTableRecord.getNextStep().equals("-")) {
                routetable.add(new RouteRecord(newRouteTableRecord.getTargetRouter(), newRouteTableRecord.getCost() + 1, newRouteTableRecord.getTargetRouter()));
            } else {
                existed = false;
                for (int j = 0; j < routetable.size(); j++) {
                    if (newRouteTableRecord.equals(routetable.get(j)) || newRouteTableRecord.getTargetRouter().equals(selfname)) {
                        existed = true;
                        break;
                    }
                    //如果cost<当前cost
                    else if (routetable.get(j).getTargetRouter().equals(newRouteTableRecord.getTargetRouter())) {
                        existed = true;
                        if (routetable.get(j).getCost() > newRouteTableRecord.getCost() + 1) {
                            routetable.remove(j);
                            routetable.add(new RouteRecord(newRouteTableRecord.getTargetRouter(), newRouteTableRecord.getCost() + 1, name));
                        }
                        break;
                    }
                }
                if (!existed) {
                    routetable.add(new RouteRecord(newRouteTableRecord.getTargetRouter(), newRouteTableRecord.getCost() + 1, name));
                }
            }
        }
    }

    /**
     * 用于向路由表中添加初始化时用户输入的路径
     *
     * @param newrouteRecord
     */
    public void updateRouteTable(RouteRecord newrouteRecord) {
        if (!routetable.contains(newrouteRecord)) {
            routetable.add(newrouteRecord);
        }
    }


    public void showRouteTable(String name) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(routetable);
        System.out.println(name + "路由表为:" + jsonString);
    }

}
