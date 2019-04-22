import java.util.ArrayList;
import java.util.List;

public class RouteTable {
    private List<RouteRecord> routetable=new ArrayList<>();
    public void init(List<RouteRecord> routeTable,String name){
        RouteRecord routeRecord = new RouteRecord(name,0,"-");
        routeTable.add(routeRecord);
    }
    public void updateRouteTable(List<RouteRecord> routeTable,RouteRecord routeRecord){
        routeTable.add(routeRecord);
    }

}
