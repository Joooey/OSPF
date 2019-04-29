import java.util.Vector;

/**
 * @author dmrfcoder
 * @date 2019-04-17
 */
public interface IMomentsView {
    void initView();

    void addMoment(Moment moment);

    void replaceMoments(Vector<Moment> moments);
}
