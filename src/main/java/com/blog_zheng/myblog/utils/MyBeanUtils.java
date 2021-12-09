package com.blog_zheng.myblog.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class MyBeanUtils {

    /**
     * return an array of all the null properties' names of the passed in object
     *
     * @param obj the object that we want to extract null fields from
     * @return an array of all the null properties' names of obj. Null if error happens
     */
    public static String[] getAllNullProperties(Object obj) {
        final BeanWrapper src = new BeanWrapperImpl(obj);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> nullNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                nullNames.add(pd.getName());
            }
        }
        String[] result = new String[nullNames.size()];
        return nullNames.toArray(result);
    }

}
