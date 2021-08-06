package top.evanechecssss.sub_name.capabilities.names;


public interface INames {
    void set(String name, String subName, boolean showName, boolean showSub);

    String getName();

    String getSubName();

    boolean getShowSubName();

    boolean getShowName();

    void clean(boolean isName);
}
