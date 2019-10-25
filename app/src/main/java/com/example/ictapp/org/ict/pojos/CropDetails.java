package com.example.ictapp.org.ict.pojos;

public class CropDetails {
    String cropName,cropDescription,cropHarvestTime,cropRegisterDate,cropImage;
    int cropId;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropDescription() {
        return cropDescription;
    }

    public void setCropDescription(String cropDescription) {
        this.cropDescription = cropDescription;
    }

    public String getCropHarvestTime() {
        return cropHarvestTime;
    }

    public void setCropHarvestTime(String cropHarvestTime) {
        this.cropHarvestTime = cropHarvestTime;
    }

    public String getCropRegisterDate() {
        return cropRegisterDate;
    }

    public void setCropRegisterDate(String cropRegisterDate) {
        this.cropRegisterDate = cropRegisterDate;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getCropQuantity() {
        return cropQuantity;
    }

    public void setCropQuantity(int cropQuantity) {
        this.cropQuantity = cropQuantity;
    }

    int cropQuantity;
}
