package Database;

public interface DBConnectorInterface {

    public abstract void createObject();
    public abstract void readObject();
    public abstract void updateObject();
    public abstract void deleteObject();
}
