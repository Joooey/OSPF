
public class Moment {
    private String momentContent;

    public Moment(String momentContent) {
        this.momentContent = momentContent;
    }

    public String getMomentContent() {
        return momentContent;
    }

    @Override
    public String toString() {
        return momentContent;
    }
}
