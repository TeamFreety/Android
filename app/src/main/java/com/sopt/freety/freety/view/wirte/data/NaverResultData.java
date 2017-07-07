package com.sopt.freety.freety.view.wirte.data;

import java.util.List;

/**
 * Created by cmslab on 7/5/17.
 */

public class NaverResultData {

    private NaverResult result;

    private class NaverResult {

        private int total;
        private String userquery;
        private List<NaverAddress> items;

        public List<NaverAddress> getItems() {
            return items;
        }

    }

    private class NaverAddress {
        private String address;
        private NaverAddressDetail addrdetail;

        public NaverAddressDetail getAddrdetail() {
            return addrdetail;
        }

    }

    private class NaverAddressDetail {
        private String sigugun;
        private String sido;
        private String address;

        public String getSigugun() {
            return sigugun;
        }

        public String getSido() {
            return sido;
        }

        public String getFullAddress() {
            return address;
        }
    }

    public String getSigugun() {
        return result.getItems().get(0).getAddrdetail().getSigugun();
    }

    public String getSido() {
        return result.getItems().get(0).getAddrdetail().getSido();
    }

    public String getFullAddress() {
        return result.getItems().get(0).getAddrdetail().getFullAddress();
    }
}
