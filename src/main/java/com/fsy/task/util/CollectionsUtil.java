package com.fsy.task.util;

import java.util.List;

public class CollectionsUtil {
    public static boolean isEmpty(List list){
        return list == null || list.size()==0;
    }

    public static boolean isNoyEmpty(List list){
        return !isEmpty(list);
    }
}
