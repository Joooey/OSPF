import java.util.ArrayList;
import java.util.List;

public class RouteTable {
    private List<RouteRecord> routetable;

    public RouteTable() {
        this.routetable = new ArrayList<>();
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
     * @param newrouteTable
     */
    public void updateRouteTable(RouteTable newrouteTable) {
        List<RouteRecord> newrouteTablelist=newrouteTable.getRoutetable();
        RouteRecord newRouteTableRecord;
        for (int i = 0; i < newrouteTablelist.size(); i++) {
            newRouteTableRecord = newrouteTablelist.get(i);
            if (!routetable.contains(newRouteTableRecord)) {

                routetable.add(newRouteTableRecord);
                //需要用dijkstra算法更新
            }
        }
    }

    /**
     * 用于向路由表中添加初始化时用户输入的路径
     * @param newrouteRecord
     */
    public void updateRouteTable(RouteRecord newrouteRecord) {

        if (!routetable.contains(newrouteRecord)) {

            routetable.add(newrouteRecord);
        }
    }

}
