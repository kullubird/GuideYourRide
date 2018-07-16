package android.kevin.guideyourride;

/**
 * Created by Kevin on 3/11/2018.
 */

public class BikeDetails {


    public BikeDetails() {
    }

    public int getPagerFlag() {
        return pagerFlag;
    }

    public void setPagerFlag(int pagerFlag) {
        this.pagerFlag = pagerFlag;
    }


    int pagerFlag;
    String name;
    int cutCount;
    float speed;
    int hemetStatus;
    int bikeStatus;
    int alcoholStatus;
    String location;
    public BikeDetails(String name, int cutCount, float speed, int hemetStatus, int bikeStatus, String location, int alcoholStatus,int pagerFlag) {
        this.name = name;
        this.cutCount = cutCount;
        this.speed = speed;
        this.hemetStatus = hemetStatus;
        this.bikeStatus = bikeStatus;
        this.location = location;
        this.alcoholStatus = alcoholStatus;
        this.pagerFlag=pagerFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCutCount() {
        return cutCount;
    }

    public void setCutCount(int cutCount) {
        this.cutCount = cutCount;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getHemetStatus() {
        return hemetStatus;
    }

    public void setHemetStatus(int hemetStatus) {
        this.hemetStatus = hemetStatus;
    }

    public int getBikeStatus() {
        return bikeStatus;
    }

    public void setBikeStatus(int bikeStatus) {
        this.bikeStatus = bikeStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAlcoholStatus() {
        return alcoholStatus;
    }

    public void setAlcoholStatus(int alcoholStatus) {
        this.alcoholStatus = alcoholStatus;
    }

}
