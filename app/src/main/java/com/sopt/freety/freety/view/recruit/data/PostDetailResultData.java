package com.sopt.freety.freety.view.recruit.data;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cmslab on 7/2/17.
 */

public class PostDetailResultData {

    private final static String[] HAIR_TYPES = new String[]{"커트", "염색", "기타", "파마"};
    private PostDetailMetaData postDetail;
    private List<PostDetailImage> postImgs;
    private int postImgsSize;
    private PostDetailLocation postLocation;
    private PostDetailWriterInfo writerInfo;
    private String isPicked;
    private int pickCount;

    private class PostDetailMetaData {
        private String title;
        private String content;
        private int servicePrice;
        private String serviceTime;
        private int typeCut;
        private int typeDye;
        private int typePerm;
        private int typeEtc;

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public boolean[] getTypes() {
            boolean[] types = new boolean[4];
            types[0] = typeCut == 1;
            types[1] = typeDye == 1;
            types[2] = typePerm == 1;
            types[3] = typeEtc == 1;
            return types;
        }

        public int getServicePrice() {
            return servicePrice;
        }

        public String getServiceTime() {
            return serviceTime;
        }
    }

    private class PostDetailImage {
        private String img;
        private int isMainImg;

        public String getImg() {
            return img;
        }

        public boolean isMain() {
            return isMainImg == 1;
        }

    }

    private class PostDetailLocation {
        private String fullAddress;
        private String sido;
        private String sigugun;
        private String dong;
        private String detail;
        private double latitude;
        private double longitude;

        public LatLng getLatLng() {
            Log.i("Recruit", "getLatLng: " + latitude + " " + longitude);
            return new LatLng(latitude, longitude);
        }

        public String getFullAddress() {
            return fullAddress;
        }
    }

    private class PostDetailWriterInfo {
        private int writerId;
        private String writerImg;
        private String writerName;
        private String writerBelongName;

        public String getWriterImg() {
            return writerImg;
        }

        public String getWriterName() {
            return writerName;
        }

        public String getBelongName() {
            return writerBelongName;
        }
    }

    public String getTitle() {
        return postDetail.getTitle();
    }

    public String getWriterName() {
        return writerInfo.getWriterName();
    }

    public String getWriterImageURL() {
        return writerInfo.getWriterImg();
    }

    public boolean isPicked() {
        return isPicked.equals("picked");
    }

    public int getPickCount() {
        return pickCount;
    }

    public String getHairType() {
        StringBuilder sb = new StringBuilder();
        boolean[] types = postDetail.getTypes();
        for (int i = 0; i < types.length; i++) {
            if (types[i]) {
                sb.append(HAIR_TYPES[i]);
                if (i < 3) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }

    public String getPrice() {
        return String.format("%,d", postDetail.getServicePrice());
    }

    public String getDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = sdf.parse(postDetail.getServiceTime());
        if (date.getHours() > 12) {
            String realHours = String.valueOf(date.getHours() - 12);
            return String.format("%d년 %d월 %d일 오후 %d시", date.getYear(), date.getMonth(), date.getDay(), realHours);
        } else {
            return String.format("%d년 %d월 %d일 오전 %d시", date.getYear(), date.getMonth(), date.getDay(), date.getHours());
        }
    }

    public String getContent() {
        return postDetail.getContent();
    }

    public LatLng getLatLng() {
        return postLocation.getLatLng();
    }

    public String getAddress() {
        return postLocation.getFullAddress();
    }

    public String getWriterBelongName() {
        return writerInfo.getBelongName();
    }

    public List<String> getImageList() {
        List<String> imageList = new ArrayList<>();
        for (PostDetailImage postImg : postImgs) {
            if (postImg.isMain()) {
                imageList.add(0, postImg.getImg());
            } else {
                imageList.add(postImg.getImg());
            }
        }
        return imageList;
    }

}
