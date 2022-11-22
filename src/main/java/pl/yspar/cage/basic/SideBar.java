package pl.yspar.cage.basic;

public class SideBar {
    private boolean sideBarEnable;

    public SideBar(final boolean t1) {
        this.sideBarEnable = t1;
    }

    public boolean isSideBarEnable() {
        return this.sideBarEnable;
    }

    public void setSideBarEnable(final boolean sideBarEnable) {
        this.sideBarEnable = sideBarEnable;
    }
}
