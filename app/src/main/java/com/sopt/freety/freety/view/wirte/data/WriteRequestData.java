package com.sopt.freety.freety.view.wirte.data;

/**
 * Created by cmslab on 7/4/17.
 */

public class WriteRequestData {

    private final String title;
    private final String content;
    private final int price;
    private final String serviceTime;
    private final double latitude;
    private final double longitude;
    private final int typeCut;
    private final int typeDye;
    private final int typePerm;
    private final int typeEct;
    private final String sido;
    private final String sigugun;
    private final String fullAddress;

    private WriteRequestData(Builder builder) {
        this.title = builder.getTitle();
        this.content = builder.getContent();
        this.price = builder.getPrice();
        this.serviceTime = builder.getServiceTime();
        this.latitude = builder.getLatitude();
        this.longitude = builder.getLongitude();
        this.typeCut = builder.getTypeCut();
        this.typeDye = builder.getTypeDye();
        this.typePerm = builder.getTypePerm();
        this.typeEct = builder.getTypeEct();
        this.sido = builder.getSido();
        this.sigugun = builder.getSigugun();
        this.fullAddress = builder.getFullAddress();
    }

    public static class Builder {

        private final String title;
        private final String content;
        private final String serviceTime;

        private String sigugun;
        private String sido;
        private String fullAddress;
        private int price = 0;
        private int typeCut = 0;
        private int typeDye = 0;
        private int typePerm = 0;
        private int typeEct = 0;
        private double latitude = 0;
        private double longitude = 0;

        public Builder(final String title, final String content, final String serviceTime) {
            this.title = title;
            this.content = content;
            this.serviceTime = serviceTime;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder setTypeCut(boolean isTypeCut) {
            this.typeCut = isTypeCut ? 1 : 0;
            return this;
        }

        public Builder setTypeDye(boolean isTypeDye) {
            this.typeDye = isTypeDye ? 1 : 0;
            return this;
        }

        public Builder setTypePerm(boolean isTypePerm) {
            this.typePerm = isTypePerm ? 1 : 0;
            return this;
        }

        public Builder setTypeEct(boolean isTypeEct) {
            this.typeEct = isTypeEct ? 1 : 0;
            return this;
        }

        public Builder setLatitude(double lat) {
            this.latitude = lat;
            return this;
        }

        public Builder setLongitude(double lng) {
            this.longitude = lng;
            return this;
        }

        public Builder setSido(String sido) {
            this.sido = sido;
            return this;
        }

        public Builder setSigugun(String sigugun) {
            this.sigugun = sigugun;
            return this;
        }

        public Builder setFullAddress(String address) {
            this.fullAddress = address;
            return this;
        }

        public WriteRequestData build() {
            return new WriteRequestData(this);
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public int getPrice() {
            return price;
        }

        public String getServiceTime() {
            return serviceTime;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public int getTypeCut() {
            return typeCut;
        }

        public int getTypeDye() {
            return typeDye;
        }

        public int getTypePerm() {
            return typePerm;
        }

        public int getTypeEct() {
            return typeEct;
        }

        public String getSido() {
            return sido;
        }

        public String getSigugun() {
            return sigugun;
        }

        public String getFullAddress() {
            return fullAddress;
        }
    }

}
