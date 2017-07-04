package com.sopt.freety.freety.view.recruit.data;

/**
 * Created by cmslab on 7/4/17.
 */

public class WriteRequestData {

    private final String title;
    private final String content;
    private final int price;
    private final String serviceTime;
    private final int typeCut;
    private final int typeDye;
    private final int typePerm;
    private final int typeEct;

    private WriteRequestData(Builder builder) {
        this.title = builder.getTitle();
        this.content = builder.getContent();
        this.price = builder.getPrice();
        this.serviceTime = builder.getServiceTime();
        this.typeCut = builder.getTypeCut();
        this.typeDye = builder.getTypeDye();
        this.typePerm = builder.getTypePerm();
        this.typeEct = builder.getTypeEct();
    }

    public static class Builder {

        private final String title;
        private final String content;
        private final String serviceTime;

        private int price = 0;
        private int typeCut = 0;
        private int typeDye = 0;
        private int typePerm = 0;
        private int typeEct = 0;

        public Builder(final String title, final String content, final String serviceTime) {
            this.title = title;
            this.content = content;
            this.serviceTime = serviceTime;
        }

        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }

        public Builder addTypeCut() {
            this.typeCut = 1;
            return this;
        }

        public Builder addTypeDye() {
            this.typeDye = 1;
            return this;
        }

        public Builder addTypePerm() {
            this.typePerm = 1;
            return this;
        }

        public Builder addTypeEct() {
            this.typeEct = 1;
            return this;
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
    }

}
