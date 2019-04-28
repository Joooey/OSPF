import com.google.gson.Gson;

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

        RouteRecord tempRecord=null;
        int mincost=1000000000;
        for (int i = 0; i < newrouteTablelist.size(); i++) {

            newRouteTableRecord = newrouteTablelist.get(i);
            for(int j=0;j<routetable.size();j++) {
                if(routetable.get(j).getTargetRouter()==newRouteTableRecord.getTargetRouter()){

                }

            }


                    if (!routetable.contains(newRouteTableRecord)) {


                    if (newRouteTableRecord.getCost() < mincost) {
                        mincost = newRouteTableRecord.getCost();
                        tempRecord = newRouteTableRecord;
                    }

                    //需要用dijkstra算法更新
                }

        }
        routetable.add(tempRecord);
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


    public void showRouteTable(String name){
        Gson gson = new Gson();
        String jsonString = gson.toJson(routetable);
        System.out.println(name+"路由表为:"+jsonString);
    }

}
