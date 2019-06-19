package com.holma.myserviceapp.Common;

import com.holma.myserviceapp.Model.Service;
import com.holma.myserviceapp.Model.User;

public class Common {
    public static final String BASE_URL = "http://10.0.2.2:3000/";
    public static final String API_KEY = "1234";
    public static final int DEFAULT_COLUMN_COUNT = 0;
    public static final int FULL_WIDTH_COLUMN = 1;

    public static User currentUser;
    public static Service currentService;
}
