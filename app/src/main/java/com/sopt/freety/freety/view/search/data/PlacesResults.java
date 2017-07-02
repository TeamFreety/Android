package com.sopt.freety.freety.view.search.data;

import java.util.List;

/**
 * Created by cmslab on 6/29/17.
 */

public class PlacesResults {
    private List<PlaceResult> results;

    public List<PlaceResult> getResults() {
        return results;
    }

    public class PlaceResult {
        private String formatted_address;
        private Geometry geometry;
        private String name;

        public String getName() {
            return name;
        }

        public String getFormattedAddress() {
            return formatted_address;
        }

        public double getLat() {
            return geometry.getLocation().getLat();
        }

        public double getLng() {
            return geometry.getLocation().getLng();
        }
    }

    public class Geometry {
        private MyLatLng location;

        public MyLatLng getLocation() {
            return location;
        }
    }

    public class MyLatLng {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }
}
